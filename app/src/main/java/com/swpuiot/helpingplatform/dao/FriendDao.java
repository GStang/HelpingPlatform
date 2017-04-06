package com.swpuiot.helpingplatform.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

/**
 * Created by DELL on 2017/4/6.
 */
public class FriendDao extends SQLiteOpenHelper {
    public static final String ADD_FRIEDN = "CREATE TABLE friend( id integer primary key autoincrement," +
            "objectid text,username text,headimg text)";
    private Context mcontext;

    public FriendDao(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mcontext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ADD_FRIEDN);
        Logger.i("创建数据库成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
