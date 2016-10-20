package com.engloryintertech.small.datas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.engloryintertech.small.model.dao.DaoMaster;
import com.engloryintertech.small.model.dao.DaoSession;

public class DatabaseLoader {

	private static final String DATABASE_NAME = "SMall_DB";
	private static DaoSession daoSession;

	public static void initHelper(Context context) {
		DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DATABASE_NAME, null);
		SQLiteDatabase db = helper.getWritableDatabase();
		DaoMaster daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
	}

	public static DaoSession getDaoSession() {
		return daoSession;
	}

}