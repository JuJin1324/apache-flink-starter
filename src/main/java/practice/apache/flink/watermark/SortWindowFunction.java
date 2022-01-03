package practice.apache.flink.watermark;

import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/01/01
 */
public class SortWindowFunction extends ProcessWindowFunction<SampleData, SampleData, Long, TimeWindow> {

    @Override
    public void process(Long key, ProcessWindowFunction<SampleData, SampleData, Long, TimeWindow>.Context context, Iterable<SampleData> in, Collector<SampleData> out) throws Exception {
        System.out.println("start: " + DateTimeUtils.toLocalDateTime(context.window().getStart(), ZoneId.of("UTC")));
        System.out.printf("SortWindowFunction process by idx: %d\n", getRuntimeContext().getIndexOfThisSubtask());

        List<SampleData> dataList = new ArrayList<>();
        in.iterator().forEachRemaining(dataList::add);

        dataList.stream()
                .sorted(SampleData.orderByTimestampASC())
                .forEach(out::collect);
    }
}
