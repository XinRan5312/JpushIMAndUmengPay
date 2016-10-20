package com.engloryintertech.small.im.db;

import android.content.Context;


/**
 * [数据库管理类，数据采用GreenDao来实现，所有实现通过模板自动生成；通过获取daoSession来获取所有的dao，从而实现操作对象]
 *
 * @author devin.hu
 * @version 1.0
 * @date 2013-9-17
 *
 **/
public class DBManager {

    private static DBManager instance;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    /**
     * [获取DBManager实例，单例模式实现]
     *
     * @param context
     * @return
     */
    public static DBManager getInstance(Context context) {
        if (instance == null) {
            synchronized (DBManager.class) {
                if (instance == null) {
                    instance = new DBManager(context);
                }
            }
        }
        return instance;
    }

    /**
     * 构造方法
     * @param context
     */
    private DBManager(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, context.getPackageName(), null);
                daoMaster = new DaoMaster(helper.getWritableDatabase());
            }
            daoSession = daoMaster.newSession();
        }
    }

    public DaoMaster getDaoMaster() {
        return daoMaster;
    }

    public void setDaoMaster(DaoMaster daoMaster) {
        this.daoMaster = daoMaster;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public void setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
    }
}
