package practice.apache.flink.watermark;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Created by Yoo Ju Jin(jujin@100fac.com)
 * Created Date : 2021/12/13
 * Copyright (C) 2021, Centum Factorial all rights reserved.
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateTimeUtils implements Serializable {

    public static LocalDateTime toLocalDateTime(Long epochMillis, ZoneId resZoneId) {
        return Instant.ofEpochMilli(epochMillis)
                .atZone(resZoneId)
                .toLocalDateTime();
    }
}
