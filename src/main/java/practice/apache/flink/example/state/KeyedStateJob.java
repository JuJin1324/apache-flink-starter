package practice.apache.flink.example.state;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import practice.apache.flink.source.TemperatureData;
import practice.apache.flink.source.TemperatureDataSource;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/01/06
 */
public class KeyedStateJob {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<TemperatureData> dataStream = env
                .addSource(new TemperatureDataSource());

        dataStream
                .keyBy(TemperatureData::getKey)
                .flatMap(new TemperatureAlertFlatMapFunc(0.3))
//                .map(new TemperatureAlertMapFunc(0.3))
                .process(new TemperatureAlertPrintFunction())
                .setParallelism(4);

        env.execute("Keyed State Job");
    }
}
