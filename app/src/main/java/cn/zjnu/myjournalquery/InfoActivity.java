package cn.zjnu.myjournalquery;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

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
	private int sum = -1;
	
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
		//ans="[{\"id\":\"1\",\"rank\":\"1\",\"abbreviation\":\"TOCS\",\"fullname\":\"ACM Transactions on Computer Systems\",\"press\":\"ACM\"},{\"id\":\"2\",\"rank\":\"2\",\"abbreviation\":\"TOC\",\"fullname\":\"IEEE Transactions on Computers\",\"press\":\"TEEE\"}]";
		/*
	    strs = ans.split("#");
        String subStr = null;
        sum = Integer.parseInt(strs[0]);//�õ���¼������
        currentRecord = 1;
        Show();
        btn_down.setOnClickListener(new DownOnClickListener());
        btn_up.setOnClickListener(new UpOnClickListener());*/
		parseEasyJson(ans);
		
		Show();
		btn_down.setOnClickListener(new DownOnClickListener());
        btn_up.setOnClickListener(new UpOnClickListener());
	}
	
	private void parseEasyJson(String json){
		journals = new ArrayList<Journal>();
		
        try{
            JSONArray jsonArray = new JSONArray(json);
            for(int i = 0;i < jsonArray.length();i++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Journal journal = new Journal();
                journal.setId(jsonObject.getString("id"));
                journal.setRank(jsonObject.getString("rank"));
                journal.setAbbreviation(jsonObject.getString("abbreviation"));
                journal.setFullname(jsonObject.getString("fullname"));
                journal.setPress(jsonObject.getString("press"));
               
                sum++;
                journals.add(journal);
            }
        }catch (Exception e){e.printStackTrace();}
    }
	
	private class DownOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(currentRecord==sum)
			{
				Toast.makeText(InfoActivity.this, "��ǰ�������һ����¼", Toast.LENGTH_SHORT).show();
			}else{
				currentRecord++;
				Show();
			}
			
		}
		
	}
	private class UpOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(currentRecord==0)
			{
				Toast.makeText(InfoActivity.this, "��ǰ���ǵ�һ����¼", Toast.LENGTH_SHORT).show();
			}else{
				currentRecord--;
				Show();
			}
			
		}
		
	}
	private void Show()
	{
		/*
		//sum��ʾ��ѯ�����ܼ�¼��,num��ʾ��ǰ�ǵڼ�����¼
		if(sum==0)
		{
			tv_number.setText("��0����¼");
        	tv_id.setText(null);
        	tv_rank.setText(null);
        	tv_shortName.setText(null);
        	tv_fullName.setText(null);
        	tv_publishHouse.setText(null);
		}else{
			int strLen = strs[currentRecord].length();
        	String subStr = strs[currentRecord].substring(1, strLen-1);//������ȥ��
        	String[] strsInfo = subStr.split(",");
        	
        	tv_number.setText(String.format("��%s�� ��ǰ��%s��", sum,currentRecord));
        	tv_id.setText("id:"+strsInfo[0]);
        	tv_rank.setText("����:"+strsInfo[1]);
        	tv_shortName.setText("���:"+strsInfo[2].substring(1, strsInfo[2].length()-1));
        	tv_fullName.setText("ȫ��:"+strsInfo[3].substring(1, strsInfo[3].length()-1));
        	tv_publishHouse.setText("������:"+strsInfo[4].substring(1, strsInfo[4].length()-1));*/
		//sum��ʾ��ѯ�����ܼ�¼��,num��ʾ��ǰ�ǵڼ�����¼
		if(sum==-1)
		{
			tv_number.setText("��0����¼");
        	tv_id.setText(null);
        	tv_rank.setText(null);
        	tv_shortName.setText(null);
        	tv_fullName.setText(null);
        	tv_publishHouse.setText(null);
		}else{
        	tv_number.setText(String.format("��%s�� ��ǰ��%s��", sum+1,currentRecord+1));
        	tv_id.setText("id:"+journals.get(currentRecord).getId());
        	tv_rank.setText("����:"+journals.get(currentRecord).getRank());
        	tv_shortName.setText("���:"+journals.get(currentRecord).getAbbreviation());
        	tv_fullName.setText("ȫ��:"+journals.get(currentRecord).getFullname());
        	tv_publishHouse.setText("������:"+journals.get(currentRecord).getPress());	
		}
	}

}
