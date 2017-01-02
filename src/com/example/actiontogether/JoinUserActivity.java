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
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class JoinUserActivity extends Activity {
	private Button joinBut;
	private Button joinCancelBut;
	private EditText joinId;
	private EditText joinPass;
	private EditText joinName;
	
	ObjectMapper mapper = new ObjectMapper();
	HashMap<String, Boolean> map = new HashMap<String, Boolean>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_join_user);
		
		joinBut = (Button) findViewById(R.id.joinBut);
		joinCancelBut = (Button) findViewById(R.id.joinCancelBut);
		joinId = (EditText) findViewById(R.id.joinId);
		joinPass = (EditText) findViewById(R.id.joinPass);
		joinName = (EditText) findViewById(R.id.joinName);
		
		
	}
	
	//가입하기 버튼입니당
	public void joinBut(View v){
		//===========================================
		//일단 중복아이디 값과  확인후 회원가입 할수 있도록 해야하는 아직 구현 안함
		//===========================================
		new JoinAsyncTask().execute();
	}
	
	//취소하기 버튼입니다ㅏ앙
	public void joinCancelBut(View v){
		Toast.makeText(getApplicationContext(), "취소하기버튼을 눌렀습니다", Toast.LENGTH_SHORT).show();
		Intent i = new Intent(JoinUserActivity.this,LoginActivity.class);
		startActivity(i);
		finish();
	}
	
	public class JoinAsyncTask extends AsyncTask<String, Void, String>{
		boolean bresult;
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			System.out.println("회원가입 어씽크에 환용합니다");
			String response = null;
			StringBuilder sb = new StringBuilder();
			HttpURLConnection conn;
			try{
				
				String param = "action=액션명(회원가입)&id="+joinId.getText().toString() +"&pass="+joinPass.getText().toString()+"&name="+joinName.getText().toString();
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
				bresult = map.get("flag");
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			if (bresult) {

				Toast.makeText(getApplicationContext(), "회원가입 완료", Toast.LENGTH_SHORT).show();
				Intent i = new Intent(JoinUserActivity.this, MainActivity.class);
				startActivity(i);
				finish();
			} else {
				Toast.makeText(getApplicationContext(), "인터넷 상태를 확인하세요", Toast.LENGTH_SHORT).show();
			}
			super.onPostExecute(result);
		}
	}
}
