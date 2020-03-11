package com.example.ceshi;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.service.WebService;
import com.tool.Address;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * 修改完成4
 * 修改完成4
 * 修改完成4
 * */

public class MySchedule extends AppCompatActivity {
    public static String record="";  //用户的交易记录
    public String cardnumber="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cardnumber=getIntent().getStringExtra("cardnumber");
        getRecord();
        setContentView(R.layout.activity_my_schedule);
        TextView textview1=findViewById(R.id.type);
        TextView textview2=findViewById(R.id.balance);
        textview1.setText(getIntent().getStringExtra("type")+"\n"+getIntent().getStringExtra("cardnumber"));
        textview2.setText("人民币余额："+getIntent().getStringExtra("balance"));

        try
        {
            JSONArray result_json=new JSONArray(record);
            for (int i = 0; i < result_json.length(); i++) {
                JSONObject object=result_json.getJSONObject(i);
                TextView textview = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,120 ));
                if(i==0)
                    layoutParams.setMargins(0,20,0,0);
                textview.setLayoutParams(layoutParams);
                textview.setId(i);
                textview.setOnClickListener(new QueryAccountDetails());
                textview.setText(object.getString("summary")+"          "+object.getString("cost")+"\n"+object.getString("place"));
                textview.setPadding(10,0,0,0);
                textview.setLineSpacing(10,1);
                textview.setGravity(Gravity.CENTER_VERTICAL);
                textview.setTextColor(Color.BLACK);
                textview.setBackgroundResource(R.drawable.underline1);
                LinearLayout layout = (LinearLayout) findViewById(R.id.my_schedule);
                layout.addView(textview);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getRecord() {
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String path = Address.ip + Address.transferrecord + "myaccount=" + cardnumber;
                    System.out.println(path);
                    String mode = "GET";
                    record = new WebService().executeHttpGet(path, mode);
                }
            });
            thread.start();
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class QueryAccountDetails implements View.OnClickListener{
        public void onClick(View v)
        {
            Intent intent=new Intent(MySchedule.this,AccountDetails.class);//新建信使对象
            intent.putExtra("id",String.valueOf(v.getId()));
            startActivity(intent);//打开新的activity
        }
    }
}
