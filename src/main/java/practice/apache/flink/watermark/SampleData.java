package practice.apache.flink.watermark;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.ZoneId;
import java.util.Comparator;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/01/01
 */

@Getter
@AllArgsConstructor
public class SampleData {
    private Long key;
    private String name;
    private Long timestamp;

    @Override
    public String toString() {
        return "SampleData{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", time=" + DateTimeUtils.toLocalDateTime(timestamp, ZoneId.of("UTC")) +
                '}';
    }

    public static Comparator<SampleData> orderByTimestampASC() {
        return (o1, o2) -> (int) (o1.getTimestamp() - o2.getTimestamp());
    }
}
