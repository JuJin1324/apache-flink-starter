package practice.apache.flink.watermark;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/01/01
 */
public class SortByWatermarkAndTimestampJob {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        LocalDateTime now = LocalDateTime.now();

        WatermarkStrategy<SampleData> watermarkStrategy = WatermarkStrategy
                .<SampleData>forBoundedOutOfOrderness(Duration.ofMinutes(1))
                .withTimestampAssigner((event, timestamp) -> event.getTimestamp());

        DataStream<SampleData> dataStream = env
                .fromElements(
                        new SampleData(1L, "1-1", toEpochMillis(now)),
                        new SampleData(1L, "1-2", toEpochMillis(now.plusMinutes(1L))),
                        new SampleData(1L, "1-4", toEpochMillis(now.plusMinutes(3L))),
                        new SampleData(1L, "1-3", toEpochMillis(now.plusMinutes(2L))),
                        new SampleData(1L, "1-6", toEpochMillis(now.plusMinutes(5L))),
                        new SampleData(1L, "1-5", toEpochMillis(now.plusMinutes(4L))),
                        new SampleData(2L, "2-5", toEpochMillis(now.plusMinutes(4L))),
                        new SampleData(2L, "2-1", toEpochMillis(now)),
                        new SampleData(2L, "2-2", toEpochMillis(now.plusMinutes(1L))),
                        new SampleData(2L, "2-4", toEpochMillis(now.plusMinutes(3L))),
                        new SampleData(2L, "2-3", toEpochMillis(now.plusMinutes(2L))),
                        new SampleData(2L, "2-6", toEpochMillis(now.plusMinutes(5L)))
                )
                .assignTimestampsAndWatermarks(watermarkStrategy);

        dataStream
                .keyBy(SampleData::getKey)
                .window(TumblingEventTimeWindows.of(Time.minutes(1)))
                .process(new SortWindowFunction())
                .name("sort by watermark and timestamp");

        env.execute("Watermark Example");
    }

    public static Long toEpochMillis(LocalDateTime dateTimeUTC) {
        return dateTimeUTC
                .atZone(ZoneId.of("UTC"))
                .toInstant()
                .toEpochMilli();
    }
}
