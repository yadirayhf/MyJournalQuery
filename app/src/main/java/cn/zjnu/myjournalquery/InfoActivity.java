package cn.zjnu.myjournalquery;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
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

	private TextView itv_Number;
	private TextView itv_Rank;
	private TextView itv_Name;
	private TextView itv_ISSN;
	private TextView itv_Press;
	private TextView itv_Citescore;
	private TextView itv_Hindex;
	private TextView itv_Fenqu;
	private TextView itv_Bsj;
	private TextView itv_Ssj;
	private TextView itv_Watch;
	private TextView itv_Ifavg;
	private TextView itv_If2016;
	private TextView itv_If2017;
	private TextView itv_If2018;
	private TextView itv_CCF;
	private Button btn_down;
	private Button btn_up;

	private int currentRecord = 1;
	private int sum = 0;
	
	private String ans = null;
	private String[] strs;

	private ArrayList<Journal> journals;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);

		Intent intent = getIntent();
		ans = intent.getStringExtra("cn.zjnu.myjuornamquery.ans");

		init();
	}

	private void init() {

		initview();
		parseEasyJson(ans);
		Show();
		btn_down.setOnClickListener(new DownOnClickListener());
		btn_up.setOnClickListener(new UpOnClickListener());
	}

	private void initview() {
		itv_Number = findViewById(R.id.itv_Number);
		itv_Rank = findViewById(R.id.itv_Rank);
		itv_Name = findViewById(R.id.itv_Name);
		itv_ISSN = findViewById(R.id.itv_ISSN);
		itv_Press = findViewById(R.id.itv_Press);
		itv_Citescore = findViewById(R.id.itv_CiteScore);
		itv_Hindex = findViewById(R.id.itv_Hindex);
		itv_Fenqu = findViewById(R.id.itv_Fenqu);
		itv_Bsj = findViewById(R.id.itv_Bsj);
		itv_Ssj = findViewById(R.id.itv_Ssj);
		itv_Watch = findViewById(R.id.itv_Watch);
		itv_Ifavg = findViewById(R.id.itv_Ifavg);
		itv_If2016 = findViewById(R.id.itv_If2016);
		itv_If2017 = findViewById(R.id.itv_If2017);
		itv_If2018 = findViewById(R.id.itv_If2018);
		itv_CCF = findViewById(R.id.itv_CCF);

		btn_down = findViewById(R.id.btn_down);
		btn_up = findViewById(R.id.btn_up);
	}

	private void parseEasyJson(String json){
		journals = new ArrayList<>();

        try{
        		JSONArray jsonArray = new JSONArray(json);
				for(int i = 0;i < jsonArray.length();i++) {
					JSONObject jsonObject = (JSONObject) jsonArray.get(i);
					Journal journal = new Journal();
					journal.setName(jsonObject.getString("name"));
					journal.setIssn(jsonObject.getString("issn"));
					journal.setPress(jsonObject.getString("press"));
					journal.setCitescore(jsonObject.getString("citescore"));
					journal.setHindex(jsonObject.getString("hindex"));
					journal.setFenqu(jsonObject.getString("fenqu"));
					journal.setBsj(jsonObject.getString("bsj"));
					journal.setSsj(jsonObject.getString("ssj"));
					journal.setWatch(jsonObject.getString("watch"));
					journal.setIf2016(jsonObject.getString("if2016"));
					journal.setIf2017(jsonObject.getString("if2017"));
					journal.setIf2018(jsonObject.getString("if2018"));
					journal.setIfavg(jsonObject.getString("ifavg"));
					journal.setCcf(jsonObject.getString("ccf"));
					journal.setRank(jsonObject.getString("rank"));
					sum++;
					journals.add(journal);
				}
            } catch (JSONException e1) { e1.printStackTrace();}
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
		if(sum==0)
		{
			itv_Number.setText("共0条记录");
        	itv_Rank.setText(null);
        	itv_Name.setText(null);
        	itv_ISSN.setText(null);
        	itv_Press.setText(null);
        	itv_Citescore.setText(null);
			itv_Hindex.setText(null);
			itv_Fenqu.setText(null);
			itv_Bsj.setText(null);
			itv_Ssj.setText(null);
			itv_Watch.setText(null);
			itv_Ifavg.setText(null);
			itv_If2016.setText(null);
			itv_If2017.setText(null);
			itv_If2018.setText(null);
			itv_CCF.setText(null);
		}else{
			itv_Number.setText(String.format("共%s条 当前第%s条", sum,currentRecord));
			itv_Rank.setText("当前期刊排名:"+journals.get(currentRecord-1).getRank());
			itv_Name.setText("期刊名:"+journals.get(currentRecord-1).getName());
			itv_ISSN.setText("ISSN:"+journals.get(currentRecord-1).getIssn());
			itv_Press.setText("出版社:"+journals.get(currentRecord-1).getPress());
			itv_Citescore.setText("Citescore:"+journals.get(currentRecord-1).getCitescore());
			itv_Hindex.setText("h-index:"+journals.get(currentRecord-1).getHindex());
			itv_Fenqu.setText("中科院分区号:"+journals.get(currentRecord-1).getFenqu());
			itv_Bsj.setText("大类学科:"+journals.get(currentRecord-1).getBsj());
			itv_Ssj.setText("小类学科:"+journals.get(currentRecord-1).getSsj());
			itv_Watch.setText("查看数:"+journals.get(currentRecord-1).getWatch());
			itv_Ifavg.setText("IF指数三年平均值:"+journals.get(currentRecord-1).getIfavg());
			itv_If2016.setText("2016：:"+journals.get(currentRecord-1).getIf2016());
			itv_If2017.setText("2017:"+journals.get(currentRecord-1).getIf2017());
			itv_If2018.setText("2018:"+journals.get(currentRecord-1).getIf2018());
			itv_CCF.setText("CCF中分类:"+journals.get(currentRecord-1).getCcf());
		}
	}

}
