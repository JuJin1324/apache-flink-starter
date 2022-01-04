package practice.apache.flink.watermark;

import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.WindowAssigner;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.triggers.ProcessingTimeTrigger;
import org.apache.flink.streaming.api.windowing.triggers.Trigger;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/01/03
 */
public class CustomProcessTimeWindows extends WindowAssigner<Object, TimeWindow> {
    private final long windowSizeMs;

    private Long startTime;

    private CustomProcessTimeWindows(long windowSizeMs) {
        this.windowSizeMs = windowSizeMs;
    }

    public static CustomProcessTimeWindows of(long windowSizeMs) {
        return new CustomProcessTimeWindows(windowSizeMs);
    }

    @Override
    public Collection<TimeWindow> assignWindows(Object element, long timestamp, WindowAssignerContext context) {
        if (startTime == null || context.getCurrentProcessingTime() - startTime >= windowSizeMs) {
            startTime = context.getCurrentProcessingTime();
        }
        long endTime = startTime + windowSizeMs;

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
