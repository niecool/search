package com.nie.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhaochengye
 * @date 2019-04-23 14:58
 */
public class NamedThreadFactory implements ThreadFactory {
    private String prefix;
    private ThreadFactory threadFactory;
    private final AtomicInteger id = new AtomicInteger(1);

    public NamedThreadFactory(String prefix) {
        this.prefix = prefix;
        this.threadFactory = Executors.defaultThreadFactory();
    }

    public Thread newThread(Runnable r) {
        Thread t = this.threadFactory.newThread(r);
        t.setName(this.prefix + "-" + this.id.getAndIncrement());
        return t;
    }
}
