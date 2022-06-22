package com.bytehonor.sdk.beautify.lang.thread;

import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 一次消费一个后休眠
 * 
 * @author lijianqiang
 *
 * @param <T>
 */
public class LinkedThread<T> {

    private static final Logger LOG = LoggerFactory.getLogger(LinkedThread.class);

    private final ConcurrentLinkedQueue<T> queue;

    private final Thread thread;

    private LinkedThread(QueueConsumer<T> consumer, long millis) {
        this.queue = new ConcurrentLinkedQueue<T>();
        this.thread = new Thread(new LinkedTask<T>(new QueueProducer<T>() {

            @Override
            public T produce() {
                return poll();
            }
        }, consumer, millis));
    }

    public static <T> LinkedThread<T> create(QueueConsumer<T> consumer, String name) {
        return create(consumer, name, 500L);
    }

    public static <T> LinkedThread<T> create(QueueConsumer<T> consumer, String name, long millis) {
        Objects.requireNonNull(consumer, "consumer");
        Objects.requireNonNull(name, "name");

        LinkedThread<T> bt = new LinkedThread<T>(consumer, millis);
        bt.thread.setName(name);
        return bt;
    }

    public void start() {
        this.thread.start();
        LOG.info("[{}] start", thread.getName());
    }

    public void add(T payload) {
        if (payload == null) {
            LOG.warn("add null");
            return;
        }
        this.queue.add(payload);
    }

    public T poll() {
        return this.queue.poll();
    }
}
