package com.example.actiontogether;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class DealActivity extends Activity {
	public static final int RES = 1;
	
	NfcAdapter nfcAdapter;
	PendingIntent pIntent;
	IntentFilter[] filters;

	String myResult;
	TextView ctxt, ptxt, rtxt;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_deal);

			Intent intent = getIntent();
			
			ctxt = (TextView) findViewById(R.id.cash);
			ctxt.setText(intent.getExtras().getString("myCash"));
			
			System.out.println(ctxt.getText().toString());
			
			ptxt = (TextView) findViewById(R.id.price);
			
			rtxt = (TextView) findViewById(R.id.presult);
			
			
			
			//nfc어탭터 설정
			nfcAdapter = NfcAdapter.getDefaultAdapter(this);
			
		if (nfcAdapter == null) {
			// NFC가 null이면 토스트 메세지
//			nfcDetect();
			Toast.makeText(getApplicationContext(), "NFC not connected.", Toast.LENGTH_SHORT).show();
			return ;
		}
		nfcDetect();
		
		//nfc 데이터 활성화에 필요한 인텐트 생성
		Intent i = new Intent(this, this.getClass());
		i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		pIntent = PendingIntent.getActivity(this, 0, i, 0);
		// nfc 데이터 활성화에 필요한 인텐트 필터 생성
		IntentFilter ndefFilter = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
		IntentFilter ndefFilter1 = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
		IntentFilter ndefFilter2 = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
		filters = new IntentFilter[] { ndefFilter2 };
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		
//		//nfc 데이터 활성화에 필요한 인텐트 생성
//		Intent i = new Intent(this, this.getClass());
//		i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//		pIntent = PendingIntent.getActivity(this, 0, i, 0);
//		//nfc 데이터 활성화에 필요한 인텐트 필터 생성
//		IntentFilter ndefFilter = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
//		IntentFilter ndefFilter1 = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
//		IntentFilter ndefFilter2 = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
//		filters = new IntentFilter[] { ndefFilter2 };
		nfcAdapter.enableForegroundDispatch(this, pIntent, filters, null);
	}
	
	

	@Override
//	protected void onPause() {
//		// TODO Auto-generated method stub
//		super.onPause();
//		nfcAdapter.disableForegroundDispatch(this);
//	}

	protected void onNewIntent(Intent intent) {
		System.out.println("onNewIntent call...");
		super.onNewIntent(intent);
		setIntent(intent);
		processIntent(intent);
	}

	private void processIntent(Intent i) {

		String action = i.getAction();
		if (action.equalsIgnoreCase(NfcAdapter.ACTION_NDEF_DISCOVERED)
				|| action.equalsIgnoreCase(NfcAdapter.ACTION_TECH_DISCOVERED)
				|| action.equalsIgnoreCase(NfcAdapter.ACTION_TAG_DISCOVERED)) {

			Parcelable[] rawData = i.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
			NdefRecord[] records = ((NdefMessage) rawData[0]).getRecords();
//			 byte[] myTagType = records[0].getType();
			String tagType = new String(records[0].getType());
			byte[] myTagData = records[0].getPayload();
			System.out.println("Record Type : " + tagType);
			if (tagType.equalsIgnoreCase("T")) {
//				stateText.setText(new String(myTagData).substring(3));
//				idText.setText(stuname);
//				String classid = new String(myTagData).substring(3);
				//sendData(classid, stuid, stuname);
				ptxt.setText(new String(myTagData).substring(3));
				
				new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Closing Activity").setMessage("Are you sure you want to close this activity?")
		        .setPositiveButton("Yes", new DialogInterface.OnClickListener(){
		        	@Override
		        	public void onClick(DialogInterface dialog, int which) {
		        		
		        		int a = Integer.parseInt(ctxt.getText().toString());
		        		int b = Integer.parseInt(ptxt.getText().toString());
		        		
		        		String result = Integer.toString(a-b);
		        		rtxt.setText(result);
		        		
		        		Intent intent = new Intent(DealActivity.this,MainActivity.class);
		        		intent.putExtra("result", result);
		        		setResult(RES,intent);
		        		
		        		finish();    
		        	}

		        }).show();
//		    .setNegativeButton("No", null)
//		    .show();
			} else {
				System.out.println("Unknown type...");
			}
		}
	}


	public void nfcDetect() {

		if (nfcAdapter.isEnabled()) {
			onRestart();
		} else {
			System.out.println("223=====================");
			AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
			System.out.println("224=====================");
			alt_bld.setMessage("NFC connect?").setCancelable(false)
					.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							// Action for 'Yes' Button
							// 4.2.2 (API 17) �겫占쏙옙苑� NFC 占쎄퐬占쎌젟 占쎌넎野껋럩�뵠
							// 癰귨옙野껋럥留�.
							if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
								startActivity(new Intent(android.provider.Settings.ACTION_NFC_SETTINGS));
							} else {
								startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
							}
						}
					}).setNegativeButton("No", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							// Action for 'NO' Button
							dialog.cancel();
						}
					});
			AlertDialog alert = alt_bld.create();
			// Title for AlertDialog
			alert.setTitle("Title");
			// Icon for AlertDialog
			alert.show();
		}

	}

	protected void onRestart() {
		super.onRestart();
	}
}
