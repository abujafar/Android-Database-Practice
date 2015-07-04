package com.hakeem.boxingscores;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hakeem.model.BoxerDAO;

public class BoxingMain extends ActionBarActivity {
	
	Button btnAdd, btnFind, btnPrevious, btnNext, btnDelete,btnUpdate;
	boolean newRecord;
	EditText edBoxerName, edWeight, edWins, edLoss;
	TextView tvId;
	BoxerDAO boxerDAO;
	Cursor cursor;
	String[] columns;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.boxing_main_layout);
		
		Log.i("main","starting onCreate");
		columns = new String[] {BoxerDAO._ID, BoxerDAO.BOXER_NAME, BoxerDAO.WEIGHT_CLASS, BoxerDAO.WINS, BoxerDAO.LOSSES};
		
		
		Log.i("main","creating dap");
		boxerDAO = new BoxerDAO(this);
		
		Log.i("main","Setting Buttons");
		btnAdd = (Button) findViewById(R.id.btnAdd);
		btnFind = (Button) findViewById(R.id.btnFind);
		btnPrevious = (Button) findViewById(R.id.btnPrevious);
		btnNext = (Button) findViewById(R.id.btnNext);
		btnDelete = (Button) findViewById(R.id.btnRemove);
		btnUpdate = (Button) findViewById(R.id.btnUpdate);
		
		Log.i("main","Setting button listeners");
		btnAdd.setOnClickListener(btnListener);
		btnDelete.setOnClickListener(btnListener);
		btnUpdate.setOnClickListener(btnListener);
		btnNext.setOnClickListener(btnListener);
		btnPrevious.setOnClickListener(btnListener);
		btnFind.setOnClickListener(btnListener);
		
		Log.i("main","setting textviews");
		tvId = (TextView) findViewById(R.id.tv_id);
		edBoxerName = (EditText) findViewById(R.id.edNameDisplay);
		edWeight = (EditText) findViewById(R.id.edWeightDisplay);
		edWins = (EditText) findViewById(R.id.edWinsDisplay);
		edLoss = (EditText) findViewById(R.id.edLossDisplay);
		cursor = boxerDAO.query(columns, null, null, null);
		newRecord = false;
		
		Log.i("main","displaying records");
		
		cursor = boxerDAO.queryAll();
		
		if(cursor.moveToFirst()){
			setFields();
		}
		else{
			
			
			Toast.makeText(getBaseContext(), "No Records to Display", Toast.LENGTH_LONG).show();
		}	
		
		Log.i("main","Ending onCreate");
		
	}
	
	OnClickListener btnListener = new OnClickListener(){

		@Override
		public void onClick(View v) {

			switch(v.getId()){
			
			case R.id.btnAdd:
				
				
				
				if(newRecord == false){
					
					clearFields();
					newRecord = true;
					btnAdd.setText(R.string.btn_save);
					Toast.makeText(getBaseContext(), "Enter Record and press save when finished", Toast.LENGTH_LONG).show();
					
				}
				else{
					
					ContentValues values = new ContentValues();
					newRecord = false;
					btnAdd.setText(R.string.btn_add_display);
					
					values.put(BoxerDAO.BOXER_NAME, edBoxerName.getText().toString());
					values.put(BoxerDAO.WEIGHT_CLASS, edWeight.getText().toString());
					values.put(BoxerDAO.WINS, edWins.getText().toString());
					values.put(BoxerDAO.LOSSES,edLoss.getText().toString());
					
					boxerDAO.insert(values);
					cursor = boxerDAO.queryAll();
					
					
					
				}				
				
				break;
			case R.id.btnPrevious:
				
				if(cursor.moveToPrevious()){
					
					setFields();
					
				}
				else{
					
					Toast.makeText(getBaseContext(), "This is the first record", Toast.LENGTH_LONG).show();
					cursor.moveToFirst();
				}
				
				
				break;
			case R.id.btnNext:
				
				if(cursor.moveToNext()){
					
					setFields();
				}
				else{
					
					Toast.makeText(getBaseContext(), "This is the last record", Toast.LENGTH_LONG).show();
					cursor.moveToLast();
				}
				
				
				break;
			case R.id.btnRemove:
				
				int delId;
				
				if(cursor.getCount() > 0){
					
					delId = Integer.parseInt(tvId.getText().toString());
					boxerDAO.delete(delId);
					cursor = boxerDAO.queryAll();
					cursor.moveToFirst();
					setFields();
				}
				
				break;
			case R.id.btnUpdate:
				
				int id;
				ContentValues values = new ContentValues();
				
				id = Integer.parseInt(tvId.getText().toString());
				
				values.put(BoxerDAO.BOXER_NAME, edBoxerName.getText().toString());
				values.put(BoxerDAO.WEIGHT_CLASS, edWeight.getText().toString());
				values.put(BoxerDAO.WINS, edWins.getText().toString());
				values.put(BoxerDAO.LOSSES,edLoss.getText().toString());					
				boxerDAO.update(values, tvId.getText().toString(), null);
				Log.i("main","after update");
				cursor = boxerDAO.queryAll();
				Log.i("main","after queryAll");
				findRecord(id);
				Log.i("main","after findRecord");
				setFields();
				Log.i("main","after setFields");
				break;
			case R.id.btnFind:
				
				
				
				
			
			
			}//end switch
			
			
		}
		
		
	};
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.boxing_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void clearFields(){
		
		tvId.setText("");
		edBoxerName.setText("");
		edWeight.setText("");
		edWins.setText("");
		edLoss.setText("");
		
	}
	
	public void setFields(){
		
		
		Log.i("main","in set fields");
		
		if(cursor.getCount() > 0){			
			
			tvId.setText(Integer.toString(cursor.getInt(cursor.getColumnIndex(BoxerDAO._ID))));
			edBoxerName.setText(cursor.getString(cursor.getColumnIndex(BoxerDAO.BOXER_NAME)));
			edWeight.setText(cursor.getString(cursor.getColumnIndex(BoxerDAO.WEIGHT_CLASS)));
			edWins.setText(Integer.toString(cursor.getInt(cursor.getColumnIndex(BoxerDAO.WINS))));
			edLoss.setText(Integer.toString(cursor.getInt(cursor.getColumnIndex(BoxerDAO.LOSSES))));
			
		}
		
		
	}
	
	private void findRecord(int id){
		
		boolean found = false;
		//cursor.moveToFirst();
		cursor.moveToPosition(-1);
		
		while(found == false && cursor.moveToNext())
		{
			if(id == cursor.getInt(cursor.getColumnIndex(BoxerDAO._ID)))
				found = true;			
			
		}
		
	}
}
