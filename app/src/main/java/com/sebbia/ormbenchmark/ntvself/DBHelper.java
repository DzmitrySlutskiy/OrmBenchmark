package com.sebbia.ormbenchmark.ntvself;

import com.sebbia.ormbenchmark.Blob;

import java.util.Date;

/**
 * DBHelper
 * Created by Dzmitry_Slutski on 08.01.2015.
 */
public class DBHelper {
    static {
        System.loadLibrary("NativeSQLite");
    }

    public void open(String path, String label) {
        nativeOpen(path/*, SQLiteDatabase.CREATE_IF_NECESSARY,
                label,
                Log.isLoggable("SQLiteStatements", Log.VERBOSE),
                Log.isLoggable("SQLiteTime", Log.VERBOSE)*/);
    }

    public void close() {
        try {
            nativeClose();
        } finally {
        }
    }

    public void beginTran() {
        execSQL("BEGIN;");
    }

    public void endTranRollback() {
        execSQL("ROLLBACK;");
    }

    public void endTranSuccess() {
        execSQL("COMMIT;");
    }

    public void insertSQL(String sql, Object[] args) {
        prepareStatement(sql);

        for (Object value : args) {
            if (value instanceof String) {
                nativeBindString((String) value);
            } else if (value instanceof Integer) {
                nativeBindInt((Integer) value);
            } else if (value instanceof Long) {
                nativeBindLong((Long) value);
            } else if (value instanceof Blob) {
                byte[] blobArray = ((Blob) value).getBackingArray();
                nativeBindBlob(blobArray, blobArray.length);
            } else if (value instanceof Date) {
                nativeBindLong(((Date) value).getTime());
            } else {
                throw new RuntimeException("not support datatype:" + value.toString());
            }
        }

        execStatement();
    }

    public void executeSQL(String sql) {
        execSQL(sql);
    }

    private static native void execSQL(String path);

    private static native void execStatement();

    private static native void insertSQL(String SQL);

    private static native void nativeOpen(String path/*, int openFlags, String label,
                                          boolean enableTrace, boolean enableProfile*/);

    private static native void nativeClose();

    private static native void nativeReleaseBinds();

    private static native void nativeBindInt(int value);

    private static native void nativeBindLong(long value);

    private static native void nativeBindBlob(byte[] value, int size);

    private static native void nativeBindString(String value);

    private static native void prepareStatement(String sql);

}
