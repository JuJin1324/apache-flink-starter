package practice.apache.flink.example.window;

import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import practice.apache.flink.DateTimeUtils;
import practice.apache.flink.source.TemperatureData;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/01/01
 */
public class SortByTimeWindowFunction extends ProcessWindowFunction<TemperatureData, TemperatureData, Long, TimeWindow> {

    @Override
    public void process(Long key, ProcessWindowFunction<TemperatureData, TemperatureData, Long, TimeWindow>.Context context, Iterable<TemperatureData> in, Collector<TemperatureData> out) throws Exception {
        List<TemperatureData> dataList = new ArrayList<>();
        in.forEach(dataList::add);

        dataList.stream()
                .sorted(TemperatureData.orderByTimestampASC())
                .forEach(sampleData -> System.out.println("sampleData = " + sampleData));

        LocalDateTime watermark = DateTimeUtils.toLocalDateTime(context.currentWatermark(), ZoneId.of("UTC"));
        System.out.println("watermark = " + watermark);

        dataList.stream()
                .sorted(TemperatureData.orderByTimestampASC())
                .forEach(out::collect);
    }
}
