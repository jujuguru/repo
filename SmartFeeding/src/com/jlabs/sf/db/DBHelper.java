package com.jlabs.sf.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 *
 * @author root
 *
 */
public class DBHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "SmartFeeding.db";
	private static final int DB_VER = 1;
	
	public DBHelper(Context context) {
		super(context,DB_NAME,null,DB_VER);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql = "create table TB_BABY("
					+ "_id integer primary key autoincrement,"
					+ "name text,"
					+ "sex text,"
					+ "birthday text);";
		db.execSQL(sql);
		sql = "create table TB_LOG("
				+ "_id integer primary key autoincrement,"
				+ "baby_id integer,"
				+ "type text,"
				+ "start_time text,"
				+ "note text);";
		db.execSQL(sql);
		
		Log.d("jlabs","db create");
	}

	//DB 업그레이드 필요시 호출(version 값에 따라 반응)
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String sql_droptable = "DROP TABLE IF EXISTS TB_BABY;";
		db.execSQL(sql_droptable);
		sql_droptable = "DROP TABLE IF EXISTS TB_LOG;";
		db.execSQL(sql_droptable);
		
		//this.onCreate(db); //필요여부 확인
	}

}

