package com.bytehonor.sdk.lang.bytehonor.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * 一次消费多个
 * 
 * @author lijianqiang
 *
 * @param <T>
 */
public class LinkedBatchTask<T> extends WhileSleepRunner {

    private final QueueProducer<T> producer;
    private final QueueBatchConsumer<T> consumer;

    private final long millis;

    public LinkedBatchTask(QueueProducer<T> producer, QueueBatchConsumer<T> consumer, long millis) {
        this.producer = producer;
        this.consumer = consumer;
        this.millis = millis;
    }

    @Override
    public final void runThenSleep() {
        int size = consumer.size();
        List<T> payloads = new ArrayList<T>(size * 2);
        for (int i = 0; i < size; i++) {
            T payload = producer.produce();
            if (payload == null) {
                break;
            }
            payloads.add(payload);
        }
        consumer.consume(payloads);
    }

    @Override
    public long millis() {
        return millis;
    }

}
