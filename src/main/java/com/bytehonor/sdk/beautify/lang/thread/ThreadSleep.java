package com.bytehonor.sdk.beautify.lang.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.define.bytehonor.util.RandomUtils;

/**
 * @author lijianqiang
 *
 */
public class ThreadSleep {

    private static final Logger LOG = LoggerFactory.getLogger(ThreadSleep.class);

    public static void sleep(long millis) {
        if (millis < 1L) {
            return;
        }
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            LOG.error("sleep", e);
        }
    }

    /**
     * 
     * @param min
     * @param max
     */
    public static void rand(int min, int max) {
        int rand = RandomUtils.getInt(min, max);
        sleep(100L * rand);
    }
}
