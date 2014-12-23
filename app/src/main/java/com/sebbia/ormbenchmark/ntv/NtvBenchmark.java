package com.sebbia.ormbenchmark.ntv;

import android.content.Context;

import com.epam.database.NativeSQLiteConnection;
import com.sebbia.ormbenchmark.Benchmark;

import java.io.File;
import java.util.List;

/**
 * Created by IstiN on 08.12.2014.
 */
public class NtvBenchmark extends Benchmark<NtvEntity> {

    private NativeSQLiteConnection dbConnection;

    @Override
    public void init(Context context) {
        super.init(context);
//        context.deleteDatabase("no_orm");

        File file = context.getDatabasePath("dbntv");
        file.getParentFile().mkdirs();

        dbConnection = new NativeSQLiteConnection();
        dbConnection.open(file.getAbsolutePath(), "label");
        dbConnection.execute("CREATE TABLE IF NOT EXISTS entity (_id INTEGER PRIMARY KEY AUTOINCREMENT, field1 TEXT, field2 TEXT, blob BLOB, date INTEGER);", null);
    }

    @Override
    public void dispose(Context context) {
        super.dispose(context);
//        dbHelper.delete(TestEntity.class, null, null);
        dbConnection.close();
    }

    @Override
    public void saveEntitiesInTransaction(List<NtvEntity> entities) {
        dbConnection.execute("BEGIN;", null);
        for (int i = 0; i < entities.size(); i++) {
            NtvEntity ntvEntity = entities.get(i);
            Object[] args = new Object[4];
            args[0] = ntvEntity.getField1();
            args[1] = ntvEntity.getField2();
            args[2] = ntvEntity.getBlob();
            args[3] = ntvEntity.getDate();
            dbConnection.executeForLastInsertedRowId(
                    "INSERT INTO entity (field1, field2, blob, date)  VALUES (?,?,?,?)", args);
        }
        dbConnection.execute("COMMIT;", null);
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
