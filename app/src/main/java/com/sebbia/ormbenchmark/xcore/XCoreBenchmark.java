package com.sebbia.ormbenchmark.xcore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.sebbia.ormbenchmark.Benchmark;
import com.sebbia.ormbenchmark.Blob;
import com.sebbia.ormbenchmark.utils.Utils;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import by.istin.android.xcore.db.IDBConnection;
import by.istin.android.xcore.db.impl.DBHelper;
import by.istin.android.xcore.model.CursorModel;
import by.istin.android.xcore.utils.CursorUtils;

/**
 * Created by IstiN on 08.12.2014.
 */
public class XCoreBenchmark extends Benchmark<BenchmarkModel> {

    private DBHelper dbHelper;

    @Override
    public void init(Context context) {
        super.init(context);
//        context.deleteDatabase("no_orm");
        dbHelper = ContentProvider.getDBHelper(context);
        dbHelper.createTablesForModels(ContentProvider.ENTITIES);
    }

    @Override
    public void dispose(Context context) {
        super.dispose(context);
//        dbHelper.delete(TestEntity.class, null, null);
    }

    @Override
    public void saveEntitiesInTransaction(List<BenchmarkModel> entities) {
        IDBConnection writableDbConnection = dbHelper.getWritableDbConnection();
        String tableName = DBHelper.getTableName(TestEntity.class);
        writableDbConnection.beginTransaction();
        for (int i = 0; i < entities.size(); i++) {
            BenchmarkModel benchmarkModel = entities.get(i);
            ContentValues contentValues = benchmarkModel.getContentValues();
            contentValues.put(TestEntity.ID, i);
            writableDbConnection.insert(tableName, contentValues);
        }
        writableDbConnection.setTransactionSuccessful();
        writableDbConnection.endTransaction();
    }

    @Override
    public List<BenchmarkModel> loadEntities() {
        final Cursor cursor = dbHelper.query(TestEntity.class, null, null, null, null, null, null, null);
        final CursorModel model = new CursorModel(cursor);
        return new List<BenchmarkModel>() {

            @Override
            public void add(int location, BenchmarkModel object) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean add(BenchmarkModel object) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean addAll(int location, Collection<? extends BenchmarkModel> collection) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean addAll(Collection<? extends BenchmarkModel> collection) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void clear() {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean contains(Object object) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean containsAll(Collection<?> collection) {
                throw new UnsupportedOperationException();
            }

            @Override
            public BenchmarkModel get(int location) {
                final CursorModel cursor = model.get(location);
                return createModel(cursor);
            }

            @Override
            public int indexOf(Object object) {
                return model.indexOf(object);
            }

            @Override
            public boolean isEmpty() {
                return model.isEmpty();
            }

            @NonNull
            @Override
            public Iterator<BenchmarkModel> iterator() {
                final Iterator<Cursor> iterator = model.iterator();
                return new Iterator<BenchmarkModel>() {
                    @Override
                    public boolean hasNext() {
                        return iterator.hasNext();
                    }

                    @Override
                    public BenchmarkModel next() {
                        Cursor next = iterator.next();
                        return createModel(next);
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }

            @Override
            public int lastIndexOf(Object object) {
                return model.lastIndexOf(object);
            }

            @NonNull
            @Override
            public ListIterator<BenchmarkModel> listIterator() {
                final ListIterator<Cursor> cursorListIterator = model.listIterator();
                return new ListIterator<BenchmarkModel>() {
                    @Override
                    public void add(BenchmarkModel object) {
                        throw new UnsupportedOperationException();
                    }

                    @Override
                    public boolean hasNext() {
                        return cursorListIterator.hasNext();
                    }

                    @Override
                    public boolean hasPrevious() {
                        return cursorListIterator.hasPrevious();
                    }

                    @Override
                    public BenchmarkModel next() {
                        return createModel(cursorListIterator.next());
                    }

                    @Override
                    public int nextIndex() {
                        return cursorListIterator.nextIndex();
                    }

                    @Override
                    public BenchmarkModel previous() {
                        return createModel(cursorListIterator.previous());
                    }

                    @Override
                    public int previousIndex() {
                        return cursorListIterator.previousIndex();
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }

                    @Override
                    public void set(BenchmarkModel object) {
                        throw new UnsupportedOperationException();
                    }
                };
            }

            @NonNull
            @Override
            public ListIterator<BenchmarkModel> listIterator(int location) {
                final ListIterator<Cursor> cursorListIterator = model.listIterator(location);
                return new ListIterator<BenchmarkModel>() {
                    @Override
                    public void add(BenchmarkModel object) {
                        throw new UnsupportedOperationException();
                    }

                    @Override
                    public boolean hasNext() {
                        return cursorListIterator.hasNext();
                    }

                    @Override
                    public boolean hasPrevious() {
                        return cursorListIterator.hasPrevious();
                    }

                    @Override
                    public BenchmarkModel next() {
                        return createModel(cursorListIterator.next());
                    }

                    @Override
                    public int nextIndex() {
                        return cursorListIterator.nextIndex();
                    }

                    @Override
                    public BenchmarkModel previous() {
                        return createModel(cursorListIterator.previous());
                    }

                    @Override
                    public int previousIndex() {
                        return cursorListIterator.previousIndex();
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }

                    @Override
                    public void set(BenchmarkModel object) {
                        throw new UnsupportedOperationException();
                    }
                };
            }

            @Override
            public BenchmarkModel remove(int location) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean remove(Object object) {
                return model.remove(object);
            }

            @Override
            public boolean removeAll(Collection<?> collection) {
                return model.removeAll(collection);
            }

            @Override
            public boolean retainAll(Collection<?> collection) {
                throw new UnsupportedOperationException();
            }

            @Override
            public BenchmarkModel set(int location, BenchmarkModel object) {
                throw new UnsupportedOperationException();
            }

            @Override
            public int size() {
                return model.size();
            }

            @NonNull
            @Override
            public List<BenchmarkModel> subList(int start, int end) {
                throw new UnsupportedOperationException();
            }

            @NonNull
            @Override
            public Object[] toArray() {
                throw new UnsupportedOperationException();
            }

            @NonNull
            @Override
            public <T> T[] toArray(T[] array) {
                throw new UnsupportedOperationException();
            }
        };
    }

    private BenchmarkModel createModel(final Cursor cursor) {
        return new BenchmarkModel() {
            @Override
            public String getField1() {
                return CursorUtils.getString(TestEntity.FIELD_1, cursor);
            }

            @Override
            public String getField2() {
                return CursorUtils.getString(TestEntity.FIELD_2, cursor);
            }

            @Override
            public Blob getBlob() {
                byte[] blob = CursorUtils.getBlob(TestEntity.BLOB, cursor);
                return Utils.deserialize(blob);
            }

            @Override
            public Date getDate() {
                long date = CursorUtils.getLong(TestEntity.DATE, cursor);
                return new Date(date);
            }
        };
    }

    @Override
    public void clearCache() {

    }

    @Override
    public String getName() {
        return "XCORE";
    }

    @Override
    public Class<? extends BenchmarkModel> getEntityClass() {
        return BenchmarkModel.class;
    }
}
