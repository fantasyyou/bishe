package com.example.ceshi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import static com.example.ceshi.MySchedule.record;

/*
 * 修改完成5
 * 修改完成5
 * 修改完成5
 * */

public class AccountDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        try {
            TextView textview1=findViewById(R.id.cost1);
            TextView textview2=findViewById(R.id.time);
            TextView textview3=findViewById(R.id.myaccount);
            TextView textview4=findViewById(R.id.summary);
            TextView textview5=findViewById(R.id.place);
            TextView textview6=findViewById(R.id.cost);
            TextView textview7=findViewById(R.id.currency);
            TextView textview8=findViewById(R.id.balance);
            TextView textview9=findViewById(R.id.counteraccount);
            TextView textview10=findViewById(R.id.accountname);
            JSONObject object = new JSONArray(record).getJSONObject(Integer.valueOf(getIntent().getStringExtra("id")));
            textview1.setText(object.getString("cost"));
            textview2.setText(object.getString("time"));
            textview3.setText(object.getString("myaccount"));
            textview4.setText(object.getString("summary"));
            textview5.setText(object.getString("place"));
            textview6.setText(object.getString("cost").replace("-","").replace("+",""));
            textview7.setText(object.getString("currency"));
            textview8.setText(object.getString("balance"));
            textview9.setText(object.getString("counteraccount"));
            textview10.setText(object.getString("accountname"));

        } catch (JSONException e) {

        }
    }
}
