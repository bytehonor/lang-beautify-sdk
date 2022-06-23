package com.bytehonor.sdk.beautify.lang.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.bytehonor.sdk.define.bytehonor.task.SafeTask;

/**
 * @author lijianqiang
 *
 */
public class ScheduleTaskExecutor {

    private static final ScheduledExecutorService SERVICE = Executors.newSingleThreadScheduledExecutor();

    public static void schedule(SafeTask command, long delaySeconds, long periodSeconds) {
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
        SERVICE.scheduleAtFixedRate(command, delaySeconds, periodSeconds, TimeUnit.SECONDS);
    }
}
