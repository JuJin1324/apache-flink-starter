package practice.apache.flink.example.window;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import practice.apache.flink.source.TemperatureData;
import practice.apache.flink.source.TemperatureDataSource;

import java.time.Duration;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/01/01
 */
public class SortByProcessTimeWindowJob {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        WatermarkStrategy<TemperatureData> watermarkStrategy = WatermarkStrategy
                .<TemperatureData>forBoundedOutOfOrderness(Duration.ofMinutes(1))
                .withTimestampAssigner((event, timestamp) -> event.getTimestamp());

        DataStream<TemperatureData> dataStream = env
                .addSource(new TemperatureDataSource())
                .assignTimestampsAndWatermarks(watermarkStrategy)
                .setParallelism(4);

        dataStream
                .keyBy(TemperatureData::getKey)
                .window(TumblingProcessingTimeWindows.of(Time.seconds(10)))
                .process(new SortByTimeWindowFunction())
                .name("sort by process time")
                .setParallelism(4);

        env.execute("Sort by process time window");
    }
}
