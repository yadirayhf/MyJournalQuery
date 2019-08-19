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
	private CheckBox cb_Press;
	private CheckBox cb_CiteScore;
	private CheckBox cb_Hindex;
	private CheckBox cb_SCI;
	private CheckBox cb_Watch;
	private CheckBox cb_IF;
	private CheckBox cb_CCF;

	private EditText edt_Name;
	private EditText edt_ISSN;
	private EditText edt_Press;
	private EditText edt_Csl;
	private EditText edt_Csh;
	private EditText edt_Hdl;
	private EditText edt_Hdh;
	private EditText edt_Fenqu;
	private EditText edt_Bsj;
	private EditText edt_Ssj;
	private EditText edt_Watchl;
	private EditText edt_Watchh;
	private EditText edt_Ifl;
	private EditText edt_Ifh;
	private EditText edt_CCF;

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
		cb_Name = findViewById(R.id.cb_Name);
		cb_ISSN = findViewById(R.id.cb_ISSN);
		cb_Press = findViewById(R.id.cb_Press);
		cb_CiteScore = findViewById(R.id.cb_CiteScore);
		cb_Hindex = findViewById(R.id.cb_Hindex);
		cb_SCI = findViewById(R.id.cb_SCI);
		cb_Watch = findViewById(R.id.cb_Watch);
		cb_IF = findViewById(R.id.cb_IF);
		cb_CCF = findViewById(R.id.cb_CCF);

		edt_Name = findViewById(R.id.edt_Name);
		edt_ISSN = findViewById(R.id.edt_ISSN);
		edt_Press = findViewById(R.id.edt_Press);
		edt_Csl = findViewById(R.id.edt_Csl);
		edt_Csh = findViewById(R.id.edt_Csh);
		edt_Hdl = findViewById(R.id.edt_Hdl);
		edt_Hdh = findViewById(R.id.edt_Hdh);
		edt_Fenqu = findViewById(R.id.edt_Fenqu);
		edt_Bsj = findViewById(R.id.edt_Bsj);
		edt_Ssj = findViewById(R.id.edt_Ssj);
		edt_Watchl =findViewById(R.id.edt_Watchl);
		edt_Watchh = findViewById(R.id.edt_Watchh);
		edt_Ifl = findViewById(R.id.edt_Ifl);
		edt_Ifh = findViewById(R.id.edt_Ifh);
		edt_CCF = findViewById(R.id.edt_CCF);

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
				MyUtils.LOGD("Ans", ans);
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

			        int Type = 0;
			        //判断哪些信息输入了
			        if(cb_Name.isChecked()){
			        	params.put(User.NAME, edt_Name.getText().toString().trim());
			        	Type+=2;
					}
			        if(cb_ISSN.isChecked()){
			        	params.put(User.ISSN,edt_ISSN.getText().toString().trim());
						Type+=1;
					}
			        if(cb_Press.isChecked()){
			        	params.put(User.PRESS,edt_Press.getText().toString().trim());
						Type+=4;
					}
			        if(cb_CiteScore.isChecked()){
			        	params.put(User.CSL,edt_Csl.getText().toString().trim());
						params.put(User.CSH,edt_Csh.getText().toString().trim());
						Type+=24;
					}
			        if(cb_Hindex.isChecked()){
			        	params.put(User.HDL,edt_Hdl.getText().toString().trim());
			        	params.put(User.HDH,edt_Hdh.getText().toString().trim());
						Type+=96;
					}
			        if(cb_SCI.isChecked())
					{
						params.put(User.FENQU,edt_Fenqu.getText().toString().trim());
						params.put(User.BSJ,edt_Bsj.getText().toString().trim());
						params.put(User.SSJ,edt_Ssj.getText().toString().trim());
						Type+=896;
					}
					if(cb_Watch.isChecked())
					{
						params.put(User.WATCHL,edt_Watchl.getText().toString().trim());
						params.put(User.WATCHH,edt_Watchh.getText().toString().trim());
						Type+=3072;
					}
			        if(cb_IF.isChecked())
					{
						params.put(User.IFL,edt_Ifl.getText().toString().trim());
						params.put(User.IFH,edt_Ifh.getText().toString().trim());
						Type+=12288;
					}
			        if(cb_CCF.isChecked())
					{
						params.put(User.CCF,edt_CCF.getText().toString().trim());
						Type+=16384;
					}
			        params.put(User.TYPE,String.valueOf(Type));
			        try {
			            //构造完整URL
			            String compeletedURL = HttpUtil.getURLWithParams(originAddress, params);
			            MyUtils.LOGD("compeletedURL", compeletedURL);
			            //发送请求..
			            HttpUtil.sendHttpRequest(compeletedURL, new HttpCallbackListener() {
			                @Override
			                public void onFinish(String response) {
								MyUtils.LOGD("res",response);
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
