package com.example.actiontogether;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	TextView nameTxt,cashTxt;
	public static final int REQ = 1; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		nameTxt = (TextView) findViewById(R.id.nameTxt);
		cashTxt = (TextView) findViewById(R.id.cashTxt);
		
		nameTxt.setText("hjh");
		cashTxt.setText("20000");

		
//		Intent intent = getIntent();
//		cashTxt.setText(intent.getExtras().getString("result"));
		
		findViewById(R.id.cbtn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this,ChargingCashActivity.class);
				startActivity(i);
			}
		});
		
		findViewById(R.id.dbtn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this,DealActivity.class);
				i.putExtra("myCash", cashTxt.getText());
				startActivityForResult(i, REQ);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == REQ){
			cashTxt.setText(data.getStringExtra("result"));
		}
	}
	
	
	

}
