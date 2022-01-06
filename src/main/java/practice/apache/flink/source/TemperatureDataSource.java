package practice.apache.flink.source;

import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/01/03
 */
public class TemperatureDataSource implements SourceFunction<TemperatureData> {


    @Override
    public void run(SourceContext<TemperatureData> ctx) throws Exception {
        long millis = 1000L;
        LocalDateTime now = LocalDateTime.now();

        makeEvent(millis, "1-5",  1.5, now.plusMinutes(4L), ctx);
        makeEvent(millis, "1-4",  1.4, now.plusMinutes(3L), ctx);
        makeEvent(millis, "1-1",  1.1, now, ctx);
        makeEvent(millis, "1-2",  1.2, now.plusMinutes(1L), ctx);
        makeEvent(millis, "1-3",  1.3, now.plusMinutes(2L), ctx);
        makeEvent(millis, "1-6",  1.6, now.plusMinutes(5L), ctx);
        makeEvent(millis, "1-7",  1.7, now.plusMinutes(6L), ctx);
        makeEvent(millis, "1-9",  1.9, now.plusMinutes(8L), ctx);
        makeEvent(millis, "1-8",  1.8, now.plusMinutes(7L), ctx);
        makeEvent(millis, "1-12", 2.2,  now.plusMinutes(11L), ctx);
        makeEvent(millis, "1-11", 2.1,  now.plusMinutes(10L), ctx);
        makeEvent(millis, "1-10", 2.0,  now.plusMinutes(9L), ctx);

        Thread.sleep(10000000L);
    }

    private void makeEvent(long millis, String name, double temperature, LocalDateTime dateTime, SourceContext<TemperatureData> ctx) throws InterruptedException {
        Thread.sleep(millis);
//        System.out.println("out " + name + " " + dateTime);
        System.out.println("out " + name);
//        System.out.println("out " + name + " " + LocalDateTime.now());
        ctx.collect(new TemperatureData(1L, name, temperature, toEpochMillis(dateTime)));
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
