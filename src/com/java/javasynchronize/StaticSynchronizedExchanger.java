package com.java.javasynchronize;

public class StaticSynchronizedExchanger {

    protected static Object object = null;

    public static synchronized void setObject(Object o) {
        object = o;
    }

    public static synchronized Object getObject() {
        return object;
    }

    public  static void setObj(Object o) {
        synchronized (SynchronizedExchanger.class) {
            object = o;
        }
    }

    public static Object getObj() {
        synchronized (StaticSynchronizedExchanger.class) {
            return object;
        }
    }
}
