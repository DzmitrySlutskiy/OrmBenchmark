package com.sebbia.ormbenchmark.ntv;

/**
 * Created by Dzmitry_Slutski on 19.12.2014.
 */
public class DBHelper {

    static {
        System.loadLibrary("hello-jni");
    }

    public native String stringFromJNI(String path);

    public native void execSQL(String path);

    public native void openDB(String path);

    public native void beginTran();

    public native void endTran();

    public native void insertSQL(String s1, String s2, int i1);
}
