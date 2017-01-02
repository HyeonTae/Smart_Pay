package com.example.actiontogether;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText userId;
	private EditText userPass;
	
	private static final String NO_DATA = "NO_DATA";
	
	SharedPreferences sp;
	SharedPreferences.Editor ed;
	private CheckBox autolg;
	
	
	ObjectMapper mapper = new ObjectMapper();
	HashMap<String, String> map = new HashMap<String, String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		String atLogId;
		String atLogPass;
		String name;
		
		sp = getSharedPreferences("sp", MODE_PRIVATE);
		ed = sp.edit();
		
		userId = (EditText) findViewById(R.id.id_input);
		userPass = (EditText) findViewById(R.id.pass_input);
		autolg = (CheckBox) findViewById(R.id.AutoLogin);
		
		atLogId = sp.getString("autoid", NO_DATA);
		atLogPass = sp.getString("autopass", NO_DATA);
		name = sp.getString("name", NO_DATA);
		
		
		if (!atLogId.equals(NO_DATA) && !atLogPass.equals(NO_DATA)) {

			Intent i = new Intent(LoginActivity.this, MainActivity.class);
			i.putExtra("name", name);
			startActivity(i);
			finish();
		}
	}

	//로그인 버튼 클릭시
	public void loginBut(View v){
		String id = userId.getText().toString().trim();
		String pass = userPass.getText().toString().trim();

		// 로그인화면에서 아이디와 패스워드가 모두 입력시
				if (!id.isEmpty() && !pass.isEmpty()) {
					new LoginAsincTask().execute();
					Toast.makeText(getApplicationContext(), "로그인버튼을 눌렀습니다", Toast.LENGTH_SHORT).show();
					// 바로 메인액티비티로 넘어간다
					// Intent i = new Intent(LoginActivity.this,MainActivity.class);
					// startActivity(i);
					// finish();
				} else {
					// 아이디와 비밀번호가 빈칸일 시 토스트로 알림을 해준다
					Toast.makeText(getApplicationContext(), "아아디와 비밀번호를 다시 확인 해주세요", Toast.LENGTH_SHORT).show();
				}

	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		new AlertDialog.Builder(this).setTitle("종료확인").setMessage("종료 하시겠습니까?").setNegativeButton("아니요", null).setPositiveButton("종료합니다", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				// TODO Auto-generated method stub
				finish();
			}
		}).show();
	}
	
	public class LoginAsincTask extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			System.out.println("로그인 어씽크에 오신걸 환영합니다");
			String response = null;
			StringBuilder sb = new StringBuilder();
			HttpURLConnection conn;
			try{
			
			String param = "action=액션명(loginToClass)&id="+userId.getText().toString() +"&pass="+userPass.getText().toString();
			conn= (HttpURLConnection) new URL("http://172.20.10.3:8060/서버이름/main.do").openConnection();
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(),"UTF-8"));
			bw.write(param);
			bw.flush();
			bw.close();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
			
			String str = "";
			while((str=br.readLine()) !=null){
				sb.append(str);
			}
			System.out.println("값"+sb.toString());
			map = mapper.readValue(sb.toString(), new TypeReference<HashMap<String, String>>() {
			});
			System.out.println(map);
			System.out.println("---------------"+map.get("name"));
			return map.get("name");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		//자동로그인합니당
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			System.out.println("result가 무엇이 왔니?->"+result);
			if(result.equals("error")==true){
				Toast.makeText(getApplicationContext(), "계정을 다시 확인 해주세요", Toast.LENGTH_SHORT).show();
			}else{
				if(autolg.isChecked()){
					ed.putString("autoid", userId.getText().toString());
					ed.putString("autopass", userPass.getText().toString());
					ed.putString("name", result);
					ed.commit();
					
					Intent i = new Intent(LoginActivity.this,MainActivity.class);
					i.putExtra("name", result);
					startActivity(i);
					finish();
				}else{
					ed.putString("name", result);
					ed.commit();
					Intent i = new Intent(LoginActivity.this,MainActivity.class);
					i.putExtra("name", result);
					startActivity(i);
					finish();
				}
			}
			super.onPostExecute(result);
		}
		
	}
}
