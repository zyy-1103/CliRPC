package com.rpcdemo.utils;

import java.util.concurrent.atomic.AtomicInteger;

public class IDGenerator {
    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(0);
    public static int getID() {
        return ATOMIC_INTEGER.incrementAndGet();
    }
}
