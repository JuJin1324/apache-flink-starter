package practice.apache.flink.watermark;

import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.WindowAssigner;
import org.apache.flink.streaming.api.windowing.triggers.ProcessingTimeTrigger;
import org.apache.flink.streaming.api.windowing.triggers.Trigger;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/01/03
 */
public class OneMinutesProcessTimeWindows extends WindowAssigner<Object, TimeWindow> {
    private Long startTime;

    @Override
    public Collection<TimeWindow> assignWindows(Object element, long timestamp, WindowAssignerContext context) {
        long windowSize = 60 * 1000L;
        if (startTime == null || context.getCurrentProcessingTime() - startTime >= windowSize) {
            startTime = context.getCurrentProcessingTime();
        }
        long endTime = startTime + windowSize;

        return Collections.singletonList(new TimeWindow(startTime, endTime));
    }

    @Override
    public Trigger<Object, TimeWindow> getDefaultTrigger(StreamExecutionEnvironment env) {
        return ProcessingTimeTrigger.create();
    }

    @Override
    public TypeSerializer<TimeWindow> getWindowSerializer(ExecutionConfig executionConfig) {
        return new TimeWindow.Serializer();
    }

    @Override
    public boolean isEventTime() {
        return false;
    }
}
