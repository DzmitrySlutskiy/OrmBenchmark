package com.sebbia.ormbenchmark.ntvself;

import android.content.Context;

import com.epam.database.NativeSQLiteConnection;
import com.sebbia.ormbenchmark.Benchmark;
import com.sebbia.ormbenchmark.utils.Log;

import java.io.File;
import java.util.List;

/**
 * Created by IstiN on 08.12.2014.
 */
public class NtvSelfBenchmark extends Benchmark<NtvSelfEntity> {

//    private NativeSQLiteConnection dbConnection;
    private DBHelper helper;
    @Override
    public void init(Context context) {
        super.init(context);
//        context.deleteDatabase("no_orm");

        File file = context.getDatabasePath("NTVSelf");
        file.getParentFile().mkdirs();

        helper = new DBHelper();
        helper.open(file.getAbsolutePath(), "label");
        helper.executeSQL("CREATE TABLE IF NOT EXISTS entity (_id INTEGER PRIMARY KEY AUTOINCREMENT, field1 TEXT, field2 TEXT, blob BLOB, date INTEGER);");
//        dbConnection = new NativeSQLiteConnection();
//        dbConnection.open(file.getAbsolutePath(), "label");
//        dbConnection.execute("CREATE TABLE IF NOT EXISTS entity (_id INTEGER PRIMARY KEY AUTOINCREMENT, field1 TEXT, field2 TEXT, blob BLOB, date INTEGER);", null);
    }

    @Override
    public void dispose(Context context) {
        super.dispose(context);
//        dbHelper.delete(TestEntity.class, null, null);
//        dbConnection.close();
        helper.close();
    }

    @Override
    public void saveEntitiesInTransaction(List<NtvSelfEntity> entities) {
        helper.beginTran();
//        dbConnection.execute("BEGIN;", null);
        for (int i = 0; i < entities.size(); i++) {
            NtvSelfEntity ntvEntity = entities.get(i);
            Object[] args = new Object[4];
            args[0] = ntvEntity.getField1();
            args[1] = ntvEntity.getField2();
            args[2] = ntvEntity.getBlob();
            args[3] = ntvEntity.getDate();
            helper.insertSQL("INSERT INTO entity (field1, field2, blob, date)  VALUES (?,?,?,?)", args);
            //Log.d("NtvSelfBenchmark","f1: "+ntvEntity.getField1()+" f2: "+ntvEntity.getField2());
//            dbConnection.executeForLastInsertedRowId(
//                    "INSERT INTO entity (field1, field2, blob, date)  VALUES (?,?,?,?)", args);
        }
//        dbConnection.execute("COMMIT;", null);
        helper.endTranSuccess();
    }

    @Override
    public List<NtvSelfEntity> loadEntities() {
        return null;
    }


    @Override
    public void clearCache() {

    }

    @Override
    public String getName() {
        return "NTVSelf";
    }

    @Override
    public Class<? extends NtvSelfEntity> getEntityClass() {
        return NtvSelfEntity.class;
    }
}
