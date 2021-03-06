package com.summer.desktop.db;

//by summer on 2017-06-09.

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.summer.desktop.bean.dbbean.AppDBBean;
import com.summer.lib.database.DatabaseHelper;

import java.sql.SQLException;

public class DbHelper extends DatabaseHelper {

    protected static String TABLE_NAME = "sqlite-desktop.db";

    protected static DbHelper instance;


    private DbHelper(Context context) {
        super(context, TABLE_NAME, null, 2);
    }

    public static synchronized DbHelper getHelper(Context context) {
        context = context.getApplicationContext();
        if (instance == null) {
            synchronized (DatabaseHelper.class) {
                if (instance == null)
                    instance = new DbHelper(context);
            }
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, AppDBBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
//        try{
//            LogUtil.E("onUpgrade");
//          //  TableUtils.dropTable(connectionSource,PlanTipBean.class,true);
//            TableUtils.createTable(connectionSource, PlanTipBean.class);
//        } catch (SQLException e){
//            e.printStackTrace();
//        }
    }
}
