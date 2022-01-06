package practice.apache.flink.example.window;

import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;
import practice.apache.flink.source.TemperatureData;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/01/03
 */
public class OrderedPrintFunction extends KeyedProcessFunction<Long, TemperatureData, TemperatureData> {

    @Override
    public void processElement(TemperatureData in, KeyedProcessFunction<Long, TemperatureData, TemperatureData>.Context ctx, Collector<TemperatureData> out) throws Exception {
        int idx = getRuntimeContext().getIndexOfThisSubtask();
        System.out.printf("[%d] in = %s\n", idx, in);
    }
}
