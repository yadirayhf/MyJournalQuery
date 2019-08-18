package cn.zjnu.myjournalquery;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class InfoActivity extends AppCompatActivity {

	private TextView tv_rank;
	private TextView tv_id;
	private TextView tv_shortName;
	private TextView tv_fullName;
	private TextView tv_publishHouse;
	private TextView tv_number;
	private Button btn_down;
	private Button btn_up;
	
	private int number = 0;
	private int infoKind = 5;
	private int currentRecord = 0;
	private int sum = 0;
	
	private String ans = null;
	private String[] strs;

	private ArrayList<Journal> journals;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		
		tv_number = (TextView)findViewById(R.id.tv_number);
		tv_id = (TextView)findViewById(R.id.tv_id);
		tv_rank = (TextView) findViewById(R.id.tv_rank);
		tv_shortName= (TextView)findViewById(R.id.tv_shortName);
		tv_fullName = (TextView)findViewById(R.id.tv_fullName);
		tv_publishHouse = (TextView)findViewById(R.id.tv_publishHouse);
		btn_down = (Button)findViewById(R.id.btn_down);
		btn_up = (Button)findViewById(R.id.btn_up);
		
		Intent intent = getIntent();
		ans = intent.getStringExtra("cn.zjnu.myjuornamquery.ans");
		parseEasyJson(ans);
		System.out.print(sum);
//		Show();
//		btn_down.setOnClickListener(new DownOnClickListener());
//        btn_up.setOnClickListener(new UpOnClickListener());
	}
	
	@SuppressLint("DefaultLocale")
	private void parseEasyJson(String json){
		journals = new ArrayList<>();

        try{
//        	JSONObject js = JSONObject.
//					/*
//
//
//
//
//					 */
////			sum = (int)js.get("num");

            for(int i = 1;i <= sum; i++){
            	Journal journal = new Journal();
//				journal.setId(js.getString(String.format("%d-id",i)).toString());
//				journal.setName(js.getString(String.format("%d-name",i)).toString());
//				journal.setIssn(js.getString(String.format("%d-issn",i)).toString());
//				journal.setPress(js.getString(String.format("%d-press",i)).toString());
//				journal.setCitescore(js.getString(String.format("%d-citescore",i)).toString());
//				journal.setHindex(js.getString(String.format("%d-hindex",i)).toString());
//				journal.setFenqu(js.getString(String.format("%d-fenqu",i)).toString());
//				journal.setBsj(js.getString(String.format("%d-bsj",i)).toString());
//				journal.setSsj(js.getString(String.format("%d-ssj",i)).toString());
//				journal.setWatch(js.getString(String.format("%d-watch",i)).toString());
//				journal.setIf2016(js.getString(String.format("%d-if2016",i)).toString());
//				journal.setIf2017(js.getString(String.format("%d-if2017",i)).toString());
//				journal.setIf2018(js.getString(String.format("%d-if2018",i)).toString());
//				journal.setIfavg(js.getString(String.format("%d-ifavg",i)).toString());
//				journal.setCcf(js.getString(String.format("%d-ccf",i)).toString());
//				journal.setRank(js.getString(String.format("%d-rank",i)).toString());
				System.out.println(journal.getName());
                journals.add(journal);
            }
            currentRecord = 1;
        }catch (Exception e){e.printStackTrace();}
    }
	
	private class DownOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(currentRecord==sum)
			{
				Toast.makeText(InfoActivity.this, "当前已是最后一条记录", Toast.LENGTH_SHORT).show();
			}else{
				currentRecord++;
				Show();
			}
			
		}
		
	}
	private class UpOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(currentRecord==1)
			{
				Toast.makeText(InfoActivity.this, "当前已是第一条记录", Toast.LENGTH_SHORT).show();
			}else{
				currentRecord--;
				Show();
			}
			
		}
		
	}
	private void Show()
	{
		/*
		//sum表示查询出的总记录数,num表示当前是第几条记录
		if(sum==0)
		{
			tv_number.setText("共0条记录");
        	tv_id.setText(null);
        	tv_rank.setText(null);
        	tv_shortName.setText(null);
        	tv_fullName.setText(null);
        	tv_publishHouse.setText(null);
		}else{
			int strLen = strs[currentRecord].length();
        	String subStr = strs[currentRecord].substring(1, strLen-1);//把括号去掉
        	String[] strsInfo = subStr.split(",");
        	
        	tv_number.setText(String.format("共%s条 当前第%s条", sum,currentRecord));
        	tv_id.setText("id:"+strsInfo[0]);
        	tv_rank.setText("排名:"+strsInfo[1]);
        	tv_shortName.setText("简称:"+strsInfo[2].substring(1, strsInfo[2].length()-1));
        	tv_fullName.setText("全称:"+strsInfo[3].substring(1, strsInfo[3].length()-1));
        	tv_publishHouse.setText("出版社:"+strsInfo[4].substring(1, strsInfo[4].length()-1));*/
		//sum表示查询出的总记录数,num表示当前是第几条记录
		if(sum==0)
		{
			tv_number.setText("共0条记录");
        	tv_id.setText(null);
        	tv_rank.setText(null);
        	tv_shortName.setText(null);
        	tv_fullName.setText(null);
        	tv_publishHouse.setText(null);
		}else{
        	tv_number.setText(String.format("共%s条 当前第%s条", sum,currentRecord));
        	tv_id.setText("id:"+journals.get(currentRecord-1).getId());
        	tv_rank.setText("排名:"+journals.get(currentRecord-1).getRank());
        	tv_fullName.setText("全称:"+journals.get(currentRecord-1).getName());
        	tv_publishHouse.setText("出版社:"+journals.get(currentRecord-1).getPress());
		}
	}

}
