package com.gmail.pdnghiadev.ex7_3contextualmenu.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by PDNghiaDev on 12/26/2015.
 */
public class DBHelper extends SQLiteOpenHelper {
    // Tạo bảng
    public static final String CREATE_TABLE = "CREATE TABLE " + DBConstract.BOOKMARK_TABLE
            + "(" + DBConstract.BOOKMARK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DBConstract.BOOKMARK_COLUMN_ID + " TEXT, "
            + DBConstract.BOOKMARK_COLUMN_TITLE + " TEXT, "
            + DBConstract.BOOKMARK_COLUMN_SCORE + " INT, "
            + DBConstract.BOOKMARK_COLUMN_COMMENT_COUNT + " INT, "
            + DBConstract.BOOKMARK_COLUMN_URL + " TEXT, "
            + DBConstract.BOOKMARK_COLUMN_STICKY + " TEXT, "
            + DBConstract.BOOKMARK_COLUMN_AUTHOR + " TEXT, "
            + DBConstract.BOOKMARK_COLUMN_SUBREDDIT + " TEXT, "
            + DBConstract.BOOKMARK_COLUMN_DOMAIN + " TEXT, "
            + DBConstract.BOOKMARK_COLUMN_CREATEUTC + " INTEGER);";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBConstract.BOOKMARK_TABLE);
        onCreate(db);
    }
}
