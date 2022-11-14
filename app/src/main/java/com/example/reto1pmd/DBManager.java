package com.example.reto1pmd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

    private DBHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    public Integer update_PuntuacionUsuario(Integer idUsuario, Integer puntuacionPersonaje){
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.USER_COLUMN_POINT, puntuacionPersonaje);

        return database.update(dbHelper.TABLE_NAME_USER, contentValues,
                dbHelper._ID + " = " + idUsuario , null);
    }

    public Cursor select_UltimoUsuario(){
        String[] cols = new String[] {
                dbHelper._ID,
                dbHelper.USER_COLUMN_NAME
        };
        Cursor cursor = database.query(dbHelper.TABLE_NAME_USER, cols, dbHelper.USER_COLUMN_TIME + " IN " + "(SELECT MAX(" + dbHelper.USER_COLUMN_TIME + ") FROM " +  dbHelper.TABLE_NAME_USER+ " )", null, null, null, null);

        if (cursor != null){
            cursor.moveToNext();
        }
        return cursor;
    }



}
