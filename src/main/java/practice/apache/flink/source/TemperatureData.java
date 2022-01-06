package practice.apache.flink.source;

import lombok.AllArgsConstructor;
import lombok.Getter;
import practice.apache.flink.DateTimeUtils;

import java.time.ZoneId;
import java.util.Comparator;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/01/01
 */

@Getter
@AllArgsConstructor
public class TemperatureData {
    private Long key;
    private String name;
    private double temperature;
    private Long timestamp;

    @Override
    public String toString() {
        return "SampleData{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", temperature='" + temperature + '\'' +
                ", time=" + DateTimeUtils.toLocalDateTime(timestamp, ZoneId.of("UTC")) +
                '}';
    }

    public static Comparator<TemperatureData> orderByTimestampASC() {
        return (o1, o2) -> (int) (o1.getTimestamp() - o2.getTimestamp());
    }
}
