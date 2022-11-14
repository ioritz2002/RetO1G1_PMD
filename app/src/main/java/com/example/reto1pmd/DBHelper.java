package com.example.reto1pmd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBHelper extends SQLiteOpenHelper implements BaseColumns {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "reto1_g1_pmd_database";

    // User
    public static final String TABLE_NAME_USER = "t_user";
    public static final String USER_COLUMN_NAME = "nombre";
    public static final String USER_COLUMN_LASTNAME = "apellido";
    public static final String USER_COLUMN_POINT = "puntiacion";
    public static final String USER_COLUMN_TIME = "hora";

    // Character
    public static final String TABLE_NAME_CHARACTER = "t_character";
    public static final String CHARACTER_COLUMN_NAME = "nombre";
    public static final String CHARACTER_COLUMN_IMG = "img";
    public static final String CHARACTER_COLUMN_URL = "url";
    public static final String CHARACTER_COLUMN_POINT = "puntiacion";

    private static final String CREATE_USER_TABLE =
            "CREATE TABLE IF NOT EXISTS " +
                    TABLE_NAME_USER + "(" +
                    _ID + "INTEGER PRIMARY KEY," +
                    USER_COLUMN_NAME + "VARCHAR," +
                    USER_COLUMN_LASTNAME + "VARCHAR," +
                    USER_COLUMN_POINT + "INTEGER," +
                    USER_COLUMN_TIME + "TIMESTAMP)";

    private static final String SQL_DELETE_USER =
            "DROP TABLE IF EXISTS " + TABLE_NAME_USER;

    private static final String CREATE_CHARACTER_TABLE =
            "CREATE TABLE IF NOT EXISTS " +
                    TABLE_NAME_CHARACTER + "(" +
                    _ID + "INTEGER PRIMARY KEY," +
                    CHARACTER_COLUMN_NAME + "VARCHAR," +
                    CHARACTER_COLUMN_IMG + "VARCHAR," +
                    CHARACTER_COLUMN_POINT + "INTEGER," +
                    CHARACTER_COLUMN_URL + "TIMESTAMP)";

    private static final String SQL_DELETE_CHARACTER =
            "DROP TABLE IF EXISTS " + TABLE_NAME_CHARACTER;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_CHARACTER_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_USER);
        db.execSQL(SQL_DELETE_CHARACTER);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
