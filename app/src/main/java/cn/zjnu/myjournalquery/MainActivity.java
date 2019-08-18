package cn.zjnu.myjournalquery;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONArray;
import org.json.JSONObject;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

	private CheckBox cb_Name;
	private CheckBox cb_ISSN;
	private CheckBox cb_Abbreviation;
	private CheckBox cb_Press;
	private CheckBox cb_CCF;
	private CheckBox cb_SCI;
	private CheckBox cb_IF;

	private EditText edt_Name;
	private EditText edt_ISSN;
	private EditText edt_Abbreviation;
	private EditText edt_Press;
	private EditText edt_CCFPartition;
	private EditText edt_CCFCategory;
	private EditText edt_SCIPartition;
	private EditText edt_SCIBigSubjects;
	private EditText edt_SCISmSubjects;
	private EditText edt_IFYear;
	private EditText edt_IFMin;
	private EditText edt_IFMax;

	private Button btn_select;

	
	private Handler mMainHandler;
	private ExecutorService mThreadPool;
	
	private String response;
	
	private InputStream is;
	private OutputStream os;
	private InputStreamReader isr;
	private BufferedReader br;


	private int num;//记录总条数
	private String ans;
	private String originAddress = "http://bnc1010.wicp.vip:24003/Search";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        init();
    }

	private void init() {
    	initview();
    	//initListener();
    	//load();
	}

	@SuppressLint("HandlerLeak")
	private void initview() {
		cb_Name =  findViewById(R.id.cb_Name);
		cb_ISSN = findViewById(R.id.cb_ISSN);
		cb_Abbreviation = findViewById(R.id.cb_Abbreviation);
		cb_Press = findViewById(R.id.cb_Press);
		cb_CCF = findViewById(R.id.cb_CCF);
		cb_SCI = findViewById(R.id.cb_SCI);
		cb_IF = findViewById(R.id.cb_IF);

		edt_Name = findViewById(R.id.edt_Name);
		edt_ISSN = findViewById(R.id.edt_ISSN);
		edt_Abbreviation = findViewById(R.id.edt_Abbreviation);
		edt_Press = findViewById(R.id.edt_Press);
		edt_CCFPartition = findViewById(R.id.edt_CCFPartition);
		edt_CCFCategory = findViewById(R.id.edt_CCFCategory);
		edt_SCIPartition = findViewById(R.id.edt_SCIPartition);
		edt_SCIBigSubjects = findViewById(R.id.edt_SCIBigSubjects);
		edt_SCISmSubjects = findViewById(R.id.edt_SCISmSubjects);
		edt_IFYear = findViewById(R.id.edt_IFYear);
		edt_IFMax = findViewById(R.id.edt_IFMax);
		edt_IFMin = findViewById(R.id.edt_IFMin);

		btn_select = findViewById(R.id.btn_select);

		btn_select.setOnClickListener(new MyonClickListener());


		mThreadPool = Executors.newCachedThreadPool();
		mMainHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				MyUtils.LOGD("msg.obj.toString()", msg.obj.toString());
				ans = msg.obj.toString();
				Intent intent = new Intent(MainActivity.this,InfoActivity.class);
				intent.putExtra("cn.zjnu.myjuornamquery.ans",ans);
//				MyUtils.LOGD("Ans", ans);
				startActivity(intent);
			}

		};
	}


	private class MyonClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			mThreadPool.execute(new Runnable() {
				@Override
				public void run() {
					//构造HashMap
					
			        HashMap<String, String> params = new HashMap<String, String>();

			        //判断哪些信息输入了
			        if(cb_Name.isChecked()){
			        	params.put(User.NAME, edt_Name.getText().toString().trim());
					}
			        if(cb_ISSN.isChecked()){
			        	params.put(User.ISSN,edt_ISSN.getText().toString().trim());
					}
			        if(cb_Abbreviation.isChecked()){
			        	params.put(User.ABBREVIATION,edt_Abbreviation.getText().toString().trim());
					}
			        if(cb_Press.isChecked()){
			        	params.put(User.PRESS,edt_Press.getText().toString().trim());
					}
			        if(cb_CCF.isChecked()){
			        	params.put(User.CCFPARTITION,edt_CCFPartition.getText().toString().trim());
			        	params.put(User.CCFCATEGORY,edt_CCFCategory.getText().toString().trim());
					}
			        if(cb_SCI.isChecked())
					{
						params.put(User.SCIPARTITION,edt_SCIPartition.getText().toString().trim());
						params.put(User.SCIBIGSUBJECTS,edt_SCIBigSubjects.getText().toString().trim());
						params.put(User.SCISMSUJECTS,edt_SCISmSubjects.getText().toString().trim());
					}
			        if(cb_IF.isChecked())
					{
						params.put(User.IFYEAR,edt_IFYear.getText().toString().trim());
						params.put(User.IFMIN,edt_IFMin.getText().toString().trim());
						params.put(User.IFMAX,edt_IFMax.getText().toString().trim());
					}
			        try {
			            //构造完整URL
			            String compeletedURL = HttpUtil.getURLWithParams(originAddress, params);
//			            MyUtils.LOGD("compeletedURL", compeletedURL);
//			            MyUtils.LOGD("res",HttpUtil.isInternetAvailable());
			            //发送请求..
			            HttpUtil.sendHttpRequest(compeletedURL, new HttpCallbackListener() {
			                @Override
			                public void onFinish(String response) {
								System.out.println(response);
			                    Message message = Message.obtain();
			                    message.obj = response;
			                    mMainHandler.sendMessage(message);

			                }


			            });
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
					
				}
			});
		}
    }
}
