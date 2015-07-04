package com.hakeem.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;

public class BoxScoresHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "boxing_scores.db";
	private static final int VERSION = 1;
	private static BoxScoresHelper instance = null;
	
	public static BoxScoresHelper getInstance(Context context){
		
		if(instance == null){
			
			instance = new BoxScoresHelper(context);
			
		}
		
		return instance;
	}
	
	private BoxScoresHelper(Context context) {
		super(context, DB_NAME, null, VERSION);


		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL(createBoxerSQLString());
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("Drop Table If Exists " + BoxerDAO.TABLE_NAME);
		onCreate(db);
		
	}
	
	private String createBoxerSQLString(){
		
		String boxerCreateString = "create table " + BoxerDAO.TABLE_NAME +
				"(" + BoxerDAO._ID + " Integer Primary Key AutoIncrement, " +
				BoxerDAO.BOXER_NAME +  " Text Not Null, " +
				BoxerDAO.WEIGHT_CLASS + " Text Not Null, " +
				BoxerDAO.WINS + " Integer Not Null, " +
				BoxerDAO.LOSSES + " Integer Not Null);";
				
		
		return boxerCreateString;
	}

}
