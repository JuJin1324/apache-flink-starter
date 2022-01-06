package practice.apache.flink.example.state;

import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import scala.Tuple3;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/01/06
 */
public class TemperatureAlertPrintFunction extends ProcessFunction<Tuple3<Long, Double, Double>, Void> {

    @Override
    public void processElement(Tuple3<Long, Double, Double> value, ProcessFunction<Tuple3<Long, Double, Double>, Void>.Context ctx, Collector<Void> out) throws Exception {
        System.out.println("alert value = " + value);
    }
}
