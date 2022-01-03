package practice.apache.flink.watermark;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/01/01
 */

@Getter
@AllArgsConstructor
@ToString
public class SampleData {
    private Long key;
    private String name;
    private Long timestamp;
}
