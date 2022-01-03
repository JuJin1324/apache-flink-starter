package practice.apache.flink.watermark;

import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/01/03
 */
public class SampleDataSource implements SourceFunction<SampleData> {

    @Override
    public void run(SourceContext<SampleData> ctx) throws Exception {
        long millis = 1000L;
        LocalDateTime now = LocalDateTime.now();

        Thread.sleep(millis);
        System.out.println("out 1-5");
        ctx.collect(new SampleData(1L, "1-5", toEpochMillis(now.plusMinutes(4L))));
        Thread.sleep(millis);
        System.out.println("out 1-1");
        ctx.collect(new SampleData(1L, "1-1", toEpochMillis(now)));
        Thread.sleep(millis);
        System.out.println("out 1-2");
        ctx.collect(new SampleData(1L, "1-2", toEpochMillis(now.plusMinutes(1L))));
        Thread.sleep(millis);
        System.out.println("out 1-4");
        ctx.collect(new SampleData(1L, "1-4", toEpochMillis(now.plusMinutes(3L))));
        Thread.sleep(millis);
        System.out.println("out 1-3");
        ctx.collect(new SampleData(1L, "1-3", toEpochMillis(now.plusMinutes(2L))));
        Thread.sleep(millis);
        System.out.println("out 1-6");
        ctx.collect(new SampleData(1L, "1-6", toEpochMillis(now.plusMinutes(5L))));
        Thread.sleep(millis);
        System.out.println("out 1-7");
        ctx.collect(new SampleData(1L, "1-7", toEpochMillis(now.plusMinutes(6L))));
        Thread.sleep(millis);
        System.out.println("out 1-9");
        ctx.collect(new SampleData(1L, "1-9", toEpochMillis(now.plusMinutes(8L))));
        Thread.sleep(millis);
        System.out.println("out 1-8");
        ctx.collect(new SampleData(1L, "1-8", toEpochMillis(now.plusMinutes(7L))));
        Thread.sleep(millis);
        System.out.println("out 1-12");
        ctx.collect(new SampleData(1L, "1-12", toEpochMillis(now.plusMinutes(11L))));
        Thread.sleep(millis);
        System.out.println("out 1-11");
        ctx.collect(new SampleData(1L, "1-11", toEpochMillis(now.plusMinutes(10L))));
        Thread.sleep(millis);
        System.out.println("out 1-10");
        ctx.collect(new SampleData(1L, "1-10", toEpochMillis(now.plusMinutes(9L))));

//        Thread.sleep(millis);
//        ctx.collect(new SampleData(2L, "2-5", toEpochMillis(now.plusMinutes(4L))));
//        Thread.sleep(1000L);
//        ctx.collect(new SampleData(2L, "2-1", toEpochMillis(now)));
//        Thread.sleep(1000L);
//        ctx.collect(new SampleData(2L, "2-2", toEpochMillis(now.plusMinutes(1L))));
//        Thread.sleep(1000L);
//        ctx.collect(new SampleData(2L, "2-4", toEpochMillis(now.plusMinutes(3L))));
//        Thread.sleep(1000L);
//        ctx.collect(new SampleData(2L, "2-3", toEpochMillis(now.plusMinutes(2L))));
//        Thread.sleep(1000L);
//        ctx.collect(new SampleData(2L, "2-6", toEpochMillis(now.plusMinutes(5L))));
//        Thread.sleep(1000L);
        Thread.sleep(10000000L);
    }

    @Override
    public void cancel() {
    }

    public static Long toEpochMillis(LocalDateTime dateTimeUTC) {
        return dateTimeUTC
                .atZone(ZoneId.of("UTC"))
                .toInstant()
                .toEpochMilli();
    }
}
