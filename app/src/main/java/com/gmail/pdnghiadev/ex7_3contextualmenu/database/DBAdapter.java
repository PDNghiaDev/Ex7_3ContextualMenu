package com.gmail.pdnghiadev.ex7_3contextualmenu.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gmail.pdnghiadev.ex7_3contextualmenu.model.Children;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PDNghiaDev on 12/26/2015.
 */
public class DBAdapter {

    private SQLiteDatabase db;
    private Context mContext;
    private DBHelper dbHelper;

    public DBAdapter(Context mContext) {
        this.mContext = mContext;
        dbHelper = new DBHelper(mContext, DBConstract.DATABASE_NAME, null, DBConstract.DATABASE_VERSION);
    }

    // TODO: Open the adapter
    public DBAdapter open() {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    // TODO: Close the adapter
    public void close() {
        db.close();
    }

    // TODO: Insert data into Table
    public boolean insertChildren(Children children) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBConstract.BOOKMARK_COLUMN_ID, children.getId());
        contentValues.put(DBConstract.BOOKMARK_COLUMN_TITLE, children.getTitle());
        contentValues.put(DBConstract.BOOKMARK_COLUMN_SCORE, children.getScore());
        contentValues.put(DBConstract.BOOKMARK_COLUMN_COMMENT_COUNT, children.getCommentCount());
        contentValues.put(DBConstract.BOOKMARK_COLUMN_URL, children.getUrl());
        contentValues.put(DBConstract.BOOKMARK_COLUMN_STICKY, children.isStickyPost());
        contentValues.put(DBConstract.BOOKMARK_COLUMN_AUTHOR, children.getAuthor());
        contentValues.put(DBConstract.BOOKMARK_COLUMN_SUBREDDIT, children.getSubreddit());
        contentValues.put(DBConstract.BOOKMARK_COLUMN_DOMAIN, children.getDomain());
        contentValues.put(DBConstract.BOOKMARK_COLUMN_CREATEUTC, children.getCreateUTC());
        db.insert(DBConstract.BOOKMARK_TABLE, null, contentValues);
        return true;
    }

    // TODO: Delete a row with id
    public boolean deleteChildren(String id) {
        Log.d("TAG", "deleteChildren: " + id);
        return db.delete(DBConstract.BOOKMARK_TABLE, DBConstract.BOOKMARK_COLUMN_ID + "=\"" + id + "\"", null) > 0;
    }

    // TODO: Get all row of Table
    public List<Children> getAllBookmark() {
        List<Children> list = new ArrayList<>();

        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBConstract.BOOKMARK_TABLE, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Children children = new Children();
            children.setId(cursor.getString(1)); // Id
            children.setTitle(cursor.getString(2)); // Title
            children.setScore(cursor.getInt(3)); // Score
            children.setCommentCount(cursor.getInt(4)); // Comment Count
            children.setUrl(cursor.getString(5)); // Url
            children.setIsStickyPost(Boolean.parseBoolean(cursor.getString(6))); // Sticky
            children.setAuthor(cursor.getString(7)); // Author
            children.setSubreddit(cursor.getString(8)); // SubReddit
            children.setDomain(cursor.getString(9)); // Domain
            children.setCreateUTC(cursor.getLong(10)); // CreateUTC
            list.add(children);
            cursor.moveToNext();
        }

        cursor.close();
        return list;
    }
}
