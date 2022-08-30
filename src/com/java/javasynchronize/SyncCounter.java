package com.java.javasynchronize;

public class SyncCounter {
    private long count = 0;

    public void incCount() {
        this.count++;
    }

    public long getCount() {
        return this.count;
    }
}
