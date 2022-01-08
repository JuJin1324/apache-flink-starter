package practice.apache.flink.example.state;

import lombok.RequiredArgsConstructor;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
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
public class TemperatureAlertFlatMapFunc extends RichFlatMapFunction<TemperatureData, Tuple3<Long, Double, Double>> {
    private final double             threshold;
    private       ValueState<Double> lastTempState;

    @Override
    public void open(Configuration parameters) {
        ValueStateDescriptor<Double> lastTempDescriptor =
                new ValueStateDescriptor<>("lastTemp", Double.class);
        lastTempDescriptor.setQueryable("lastTemp");
        // TODO: 쿼리 가능 상태로 변경 및 쿼리 클라이언트 생성

        lastTempState = getRuntimeContext().getState(lastTempDescriptor);
    }

    @Override
    public void flatMap(TemperatureData in, Collector<Tuple3<Long, Double, Double>> out) throws Exception {
        Double lastTemp = lastTempState.value();
        if (lastTemp != null) {
            double tempDiff = Math.abs(in.getTemperature() - lastTemp);
            if (tempDiff > threshold) {
                out.collect(new Tuple3<>(in.getKey(), in.getTemperature(), tempDiff));
            }
        }
        lastTempState.update(in.getTemperature());
    }
}
