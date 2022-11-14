package com.example.reto1pmd;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;

public class DBHelper extends SQLiteOpenHelper implements BaseColumns {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "reto1_g1_pmd.db";

    // User
    public static final String TABLE_NAME_USER = "t_user";
    public static final String USER_COLUMN_ID = "id";
    public static final String USER_COLUMN_NAME = "nombre";
    public static final String USER_COLUMN_LASTNAME = "apellido";
    public static final String USER_COLUMN_POINT = "point";
    public static final String USER_COLUMN_TIME = "hora";

    private static final String CREATE_USER_TABLE =
            "CREATE TABLE IF NOT EXISTS " +
                    TABLE_NAME_USER + "(" +
                    USER_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    USER_COLUMN_NAME + " TEXT NOT NULL," +
                    USER_COLUMN_LASTNAME + " TEXT NOT NULL," +
                    USER_COLUMN_POINT + " INTEGER," +
                    USER_COLUMN_TIME + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP);";

    private static final String SQL_DELETE_USER =
            "DROP TABLE IF EXISTS " + TABLE_NAME_USER;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_USER);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
