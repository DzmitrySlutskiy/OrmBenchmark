package com.sebbia.ormbenchmark.xcore;

import android.content.Context;

import by.istin.android.xcore.db.IDBConnection;
import by.istin.android.xcore.db.impl.DBHelper;
import by.istin.android.xcore.provider.DBContentProvider;

public class ContentProvider extends DBContentProvider {

    public static final Class<?>[] ENTITIES = new Class<?>[]{
        TestEntity.class
    };

    @Override
    public Class<?>[] getEntities() {
        return ENTITIES;
    }

    public static IDBConnection getWritableConnection(Context context) {
        return getWritableConnection(context, ENTITIES);
    }

    public static IDBConnection getReadableConnection(Context context) {
        return getReadableConnection(context, ENTITIES);
    }

    public static DBHelper getDBHelper(Context context) {
        return getDBHelper(context, ENTITIES);
    }

}
