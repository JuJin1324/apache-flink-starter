package practice.apache.flink.example.state;

import lombok.RequiredArgsConstructor;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.util.Collector;
import practice.apache.flink.source.TemperatureData;
import scala.Tuple3;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/01/06
 */

@RequiredArgsConstructor
public class TemperatureAlertMapFunc extends RichMapFunction<TemperatureData, Tuple3<Long, Double, Double>> {
    private final double             threshold;
    private       ValueState<Double> lastTempState;

    @Override
    public void open(Configuration parameters) {
        ValueStateDescriptor<Double> lastTempDescriptor =
                new ValueStateDescriptor<>("lastTemp", Double.class);
        lastTempState = getRuntimeContext().getState(lastTempDescriptor);
    }

    @Override
    public Tuple3<Long, Double, Double> map(TemperatureData in) throws Exception {
        Tuple3<Long, Double, Double> result = null;
        Double lastTemp = lastTempState.value();
        if (lastTemp != null) {
            double tempDiff = Math.abs(in.getTemperature() - lastTemp);
            if (tempDiff > threshold) {
                result = new Tuple3<>(in.getKey(), in.getTemperature(), tempDiff);
            }
        }
        lastTempState.update(in.getTemperature());

        return result;
    }
}
