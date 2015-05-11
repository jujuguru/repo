package com.jlabs.sf.db;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class DBAdapter {
	
	static final String tableBaby = "TB_BABY";
	static final String tableLog = "TB_LOG";
	
	//������ ���̽��� �̿��ϴ� ���ؽ�Ʈ
	private Context context;
	//������ ������ü 
	private SQLiteDatabase db;
	
	
	public DBAdapter(Context context) {
		this.context = context;
		this.open();
		//test code
		/*
		this.insert_dialog("1 day class","start english study 1day");
		this.insert_dialog("2 day class","start english study 2day");
		this.insert_dialog("3 day class","start english study 3day");
		Log.d("kingreturn","db insert done");
		*/
	}
	
	public void open() throws SQLException {
		try {
			
			db = (new DBHelper(context).getWritableDatabase());
		} catch(SQLiteException e) {
			//db = (new DBHelper(context).getReadableDatabase());
		}
	}
	
	public void close() {
		db.close();
	}
	
	public long insertBaby(String name,String sex,String birthday) {
		ContentValues values = new ContentValues();
		values.put("name",name);
		values.put("sex",sex);		
		values.put("birthday",birthday);		
		return db.insert(tableBaby,null,values);
	}
	
	public long insertLog(String baby_id, String type, String strat_time, String note) {
		ContentValues values = new ContentValues();
		values.put("baby_id",baby_id);
		values.put("type",type);
		values.put("start_time",strat_time);
		values.put("note",note);		
		return db.insert(tableLog,null,values);
	}
	
	public boolean updateLog(long rowId, String baby_id, String type, String strat_time, String note) {
		ContentValues values = new ContentValues();
		values.put("baby_id",baby_id);
		values.put("type",type);
		values.put("start_time",strat_time);
		values.put("note",note);		
		return db.update(tableLog, values, "_id=" + rowId, null) > 0;
	}

	public boolean deleteLog(long rowId) {
		return db.delete(tableLog, "_id = " + rowId, null) > 0;	
	}
    
	/**
	 * TODO : ? �� ���� ġȯ
	 * @param strDay 'yyyy-MM-dd' 10�ڸ� ����
	 * @return
	 */
	public boolean deleteDayLog(String strDay) {
		return db.delete(tableLog, "start_time like '" + strDay + "%'", null) > 0;	
	}
	
	public boolean deleteAllLog() {
		return db.delete(tableLog, null, null) > 0;	
	}

	public Cursor getLog(long rowId) throws SQLException {
		Cursor cursor = db.query(tableLog, //table name
				new String[] {"_id","baby_id","type","start_time","note"}, //colum ��
			"_id=" + rowId,
			null,
			null,
			null,
			null,
			null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;
	}
	
	public Cursor getAllLogs() {
		Cursor cursor = db.query(tableLog, //table name
			new String[] {"_id","baby_id","type","start_time","note"}, //colum ��
			null, //where
			null, //where ���� ������ ������
			null, //group by
			null, //having
			null//"start_time" + " ASC" //order by ȭ�鿡�� ������ ������ ������ ��ȸ�ÿ��� ������ �ʿ� ����.
			);
		return cursor;
	}
	
	/**
	 * TODO : ? �� ���� ġȯ
	 * @param strDay 'yyyy-MM-dd' 10�ڸ� ����
	 * @return
	 */
	public Cursor getDayLogs(String strDay) {
		Cursor cursor = db.query(tableLog, //table name
			new String[] {"_id","baby_id","type","start_time","note"}, //colum ��
			"start_time like '" + strDay + "%'", //where
			null, //where ���� ������ ������
			null, //group by
			null, //having
			null//"start_time" + " ASC" //order by ȭ�鿡�� ������ ������ ������ ��ȸ�ÿ��� ������ �ʿ� ����.
			);
		return cursor;
	}

	/*
	public ArrayList<Dialog> get_all_dialog() {
		ArrayList<Dialog> dialogs = new ArrayList<Dialog>();
		Cursor c = select_all_dialog();
		if(c.moveToFirst()) {
			//int indexId = c.getColumnIndex("_id");
			int indexSubject = c.getColumnIndex("subject");
			int indexDialog = c.getColumnIndex("dialog");
			do {
				
				String Subject = c.getString(indexSubject);
				String Dialog = c.getString(indexDialog);
				
				dialogs.add(new Dialog(Subject,Dialog));
			} while(c.moveToNext());
		}
		
		return dialogs;
	}
	*/
	
	
}

