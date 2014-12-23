package com.epam.database;

/**
 * Test NativeSQLiteConnection
 * Created by Dzmitry_Slutski on 19.12.2014.
 */
public class DBHelper2 {

    private NativeSQLiteConnection mConn;

    public DBHelper2() {

        mConn = NativeSQLiteConnection.get();
    }

    public void execSQL(String path) {
        mConn.execute(path, null);
    }

    public void openDB(String path) {
        mConn.open(path, "label");
    }

    public void close() {
        mConn.close();
    }

    public long executeForLastInsertedRowId(String sql, Object[] bindArgs) {
        return mConn.executeForLastInsertedRowId(sql, bindArgs);
    }

    public void beginTran() {
        mConn.execute("BEGIN;", null);
    }

    public void endTran() {
        mConn.execute("COMMIT;", null);
    }
}
