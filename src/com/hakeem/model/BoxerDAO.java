package com.hakeem.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BoxerDAO {
	
	public static final String TABLE_NAME = "Boxer";
	public static final String _ID  = "_id";
	public static final String BOXER_NAME = "boxer_name";
	public static final String WEIGHT_CLASS = "weight_class";
	public static final String WINS = "wins";
	public static final String LOSSES ="losses";
	
	private final BoxScoresHelper myScoresHelper;
	private SQLiteDatabase myBoxerDB;
	
	public BoxerDAO(Context context){
		
		myScoresHelper = BoxScoresHelper.getInstance(context);
		
	}
	
	public Cursor query(String[] projection,String selection,String[] selectionArgs, String orderBy){
		
		Cursor cursor;
		myBoxerDB = myScoresHelper.getReadableDatabase();
		
		cursor = myBoxerDB.query(TABLE_NAME, projection, selection, selectionArgs, null, null, orderBy);
		
		//myBoxerDB.close();
		
		return cursor;
		
		
	}
	
	public Cursor queryAll(){
		
		Cursor cursor;
		myBoxerDB = myScoresHelper.getReadableDatabase();
		
		cursor = myBoxerDB.rawQuery("Select * From " + TABLE_NAME, null);
		
		//myBoxerDB.close();
		return cursor;
		
	}
	
	public int delete(int id){
		
		int rowsDel;
		myBoxerDB = myScoresHelper.getWritableDatabase();
		
		rowsDel = myBoxerDB.delete(TABLE_NAME, _ID + " = " + id , null);
		
		//myBoxerDB.close();
		return rowsDel;
		
	}
	
	public long insert(ContentValues values){
		
		long insertId = -1;
		
		myBoxerDB = myScoresHelper.getWritableDatabase();
		
		insertId = myBoxerDB.insert(TABLE_NAME, null, values);
		
		//myBoxerDB.close();
		
		return insertId;
		
	}
	
	public int update(ContentValues values,String selection, String[] selectionArgs){
		
		int updatedRows;
		myBoxerDB = myScoresHelper.getWritableDatabase();
		
		updatedRows = myBoxerDB.update(TABLE_NAME, values, _ID + " = " + selection, selectionArgs);
		//myBoxerDB.close();
		
		return updatedRows;
		
	}
	
	
	

}
