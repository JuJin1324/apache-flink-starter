package practice.apache.flink.watermark.jobs;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import practice.apache.flink.watermark.*;

import java.time.Duration;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/01/01
 */
public class SortByProcessTimeWindowJob {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        WatermarkStrategy<SampleData> watermarkStrategy = WatermarkStrategy
                .<SampleData>forBoundedOutOfOrderness(Duration.ofMinutes(1))
                .withTimestampAssigner((event, timestamp) -> event.getTimestamp());

        DataStream<SampleData> dataStream = env
                .addSource(new SampleDataSource())
                .assignTimestampsAndWatermarks(watermarkStrategy)
                .setParallelism(4);

        /* TumblingXXXTimeWindows 의 기본 Offset(윈도우 실행 초) 가 기본 0초 이어서 만약 10시 1분 1초에 프로그램 시작되었으면
         * 10시 2분 0초에 윈도우가 실행된다. 그래서 1분 1초에 프로그램을 시작했으면 2분 0초까지 59초를 기다린 후에 윈도우 함수가 실행되기 때문에
         * 정상으로 나오고 1분 58초 같이 2분 0초에 가까운 시간에 실행한 경우 2초 기다린 후 바로 윈도우 함수가 실행되기 때문에 이상한 결과가 나올 수 있다. */
        DataStream<SampleData> sortedDateStream = dataStream
                .keyBy(SampleData::getKey)
//                .window(TumblingProcessingTimeWindows.of(Time.minutes(1)))
                .window(new CustomProcessTimeWindows())
                .process(new SortByTimeWindowFunction())
                .name("sort by process time")
                .setParallelism(4);

        sortedDateStream
                .keyBy(SampleData::getKey)
                .process(new OrderedPrintFunction())
                .name("ordered print")
                .setParallelism(4);

        env.execute("Sort by process time window");
    }
}