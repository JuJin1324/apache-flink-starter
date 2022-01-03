package practice.apache.flink.watermark;

import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/01/03
 */
public class OrderedPrintFunction extends KeyedProcessFunction<Long, SampleData, SampleData> {

    @Override
    public void processElement(SampleData in, KeyedProcessFunction<Long, SampleData, SampleData>.Context ctx, Collector<SampleData> out) throws Exception {
        int idx = getRuntimeContext().getIndexOfThisSubtask();
        System.out.printf("[%d] in = %s\n", idx, in);
    }
}
