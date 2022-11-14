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

    public void insertUser(String name, String lastname) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DBHelper.USER_COLUMN_NAME, name);
        contentValue.put(DBHelper.USER_COLUMN_LASTNAME, lastname);
        database.insert(DBHelper.TABLE_NAME_USER, null, contentValue);
    }

    public Boolean update_PuntuacionUsuario(Integer idUsuario, Integer puntuacion) {
        database.execSQL("UPDATE " + dbHelper.TABLE_NAME_USER +
                " SET " + dbHelper.USER_COLUMN_POINT + "=" + puntuacion +
                " WHERE "+ dbHelper.USER_COLUMN_ID + "=" + idUsuario);

        return true;
    }

    public Cursor select_UltimoUsuario() {
        Cursor cursor = database.rawQuery(
                "select " +
                        "rowid" + "," +
                        dbHelper.USER_COLUMN_NAME + "," +
                        dbHelper.USER_COLUMN_LASTNAME +
                        " from " + dbHelper.TABLE_NAME_USER +
                        " order by " + dbHelper.USER_COLUMN_TIME + " DESC LIMIT 1", null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


}
