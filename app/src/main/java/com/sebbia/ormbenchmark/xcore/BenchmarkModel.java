package com.sebbia.ormbenchmark.xcore;

import android.content.ContentValues;

import com.sebbia.ormbenchmark.BenchmarkEntity;
import com.sebbia.ormbenchmark.Blob;
import com.sebbia.ormbenchmark.utils.Utils;

import java.util.Date;

/**
 * Created by IstiN on 08.12.2014.
 */
public class BenchmarkModel implements BenchmarkEntity {

    private ContentValues contentValues = new ContentValues();

    public ContentValues getContentValues() {
        return contentValues;
    }

    @Override
    public void setField1(String field1) {
        contentValues.put(TestEntity.FIELD_1, field1);
    }

    @Override
    public void setField2(String field2) {
        contentValues.put(TestEntity.FIELD_2, field2);
    }

    @Override
    public void setBlob(Blob blob) {
        contentValues.put(TestEntity.BLOB, Utils.serialize(blob));
    }

    @Override
    public String getField1() {
        return null;
    }

    @Override
    public String getField2() {
        return null;
    }

    @Override
    public Blob getBlob() {
        return null;
    }

    @Override
    public void setDate(Date date) {
        contentValues.put(TestEntity.DATE, date.getTime());
    }

    @Override
    public Date getDate() {
        return null;
    }

}
