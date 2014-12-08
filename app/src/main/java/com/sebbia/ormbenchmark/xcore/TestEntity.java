package com.sebbia.ormbenchmark.xcore;

import android.provider.BaseColumns;

import by.istin.android.xcore.annotations.dbByteArray;
import by.istin.android.xcore.annotations.dbLong;
import by.istin.android.xcore.annotations.dbString;

/**
 * Created by IstiN on 08.12.2014.
 */
public class TestEntity implements BaseColumns {

    @dbLong
    public static final String ID = _ID;

    @dbString
    public static final String FIELD_1 = "field1";

    @dbString
    public static final String FIELD_2 = "field2";

    @dbByteArray
    public static final String BLOB = "blob";

    @dbLong
    public static final String DATE = "date";

}
