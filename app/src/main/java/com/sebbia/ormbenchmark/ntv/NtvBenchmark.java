package com.sebbia.ormbenchmark.ntv;

import android.content.Context;

import com.sebbia.ormbenchmark.Benchmark;

import java.io.File;
import java.util.List;

/**
 * Created by IstiN on 08.12.2014.
 */
public class NtvBenchmark extends Benchmark<NtvEntity> {

    private DBHelper dbHelper;

    @Override
    public void init(Context context) {
        super.init(context);
        context.deleteDatabase("no_orm");

        File file = context.getDatabasePath("dbntv");
        file.getParentFile().mkdirs();
        dbHelper = new DBHelper();
        dbHelper.openDB(file.getAbsolutePath());
        dbHelper.execSQL("CREATE TABLE IF NOT EXISTS entity (_id INTEGER PRIMARY KEY AUTOINCREMENT, field1 TEXT, field2 TEXT, blob BLOB, date INTEGER);");
    }

    @Override
    public void dispose(Context context) {
        super.dispose(context);
//        dbHelper.delete(TestEntity.class, null, null);
    }

    @Override
    public void saveEntitiesInTransaction(List<NtvEntity> entities) {
        dbHelper.beginTran();
        for (int i = 0; i < entities.size(); i++) {
            NtvEntity ntvEntity = entities.get(i);
            dbHelper.insertSQL(
                    ntvEntity.getField1(),
                    ntvEntity.getField2(),
                    (int)ntvEntity.getDate().getTime()
            );
        }
        dbHelper.endTran();
    }

    @Override
    public List<NtvEntity> loadEntities() {
        return null;
    }



    @Override
    public void clearCache() {

    }

    @Override
    public String getName() {
        return "NTV";
    }

    @Override
    public Class<? extends NtvEntity> getEntityClass() {
        return NtvEntity.class;
    }
}
