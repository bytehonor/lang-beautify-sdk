package com.bytehonor.sdk.lang.bytehonor.task;

import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AsyncTaskExecutor {

    private static final Logger LOG = LoggerFactory.getLogger(AsyncTaskExecutor.class);

    private ThreadPoolExecutor executor;

    private AsyncTaskExecutor() {
        this.executor = new ThreadPoolExecutor(2, 4, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(10000));
    }

    /**
     * 延迟加载(线程安全)
     *
     */
    private static class LazyHolder {
        private static AsyncTaskExecutor instance = new AsyncTaskExecutor();
    }

    public static void init(int corePoolSize, int maxPoolSize) {
        shutdown();
        getInstance().executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(10000));
    }

    private static AsyncTaskExecutor getInstance() {
        return LazyHolder.instance;
    }

    public static void execute(Runnable r) {
        Objects.requireNonNull(r, "runnable");
        try {
            getInstance().executor.execute(r);
        } catch (Exception e) {
            LOG.error("AsyncTaskPoolExecutor execute({}) error:{}", r.getClass().getSimpleName(), e);
        }
    }

    public static void shutdown() {
        boolean shutdown = getInstance().executor.isShutdown();
        LOG.info("shutdown:{}", shutdown);
        if (!shutdown) {
            getInstance().executor.shutdown();
        }
    }

    public static int queueSize() {
        return getInstance().executor.getQueue().size();
    }
}
