package practice.apache.flink.watermark;

import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/01/01
 */
public class SortWindowFunction extends ProcessWindowFunction<SampleData, SampleData, Long, TimeWindow> {

    @Override
    public void process(Long key, ProcessWindowFunction<SampleData, SampleData, Long, TimeWindow>.Context context, Iterable<SampleData> in, Collector<SampleData> out) throws Exception {
        System.out.println("in.iterator().next() = " + in.iterator().next());
        out.collect(in.iterator().next());
    }
}
