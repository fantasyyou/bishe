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
import java.util.ArrayList;

/*
 * 修改完成12
 * 修改完成12
 * 修改完成12
 * */

public class ChoiceAccount extends AppCompatActivity {
    public static String bankcard="";  //用户绑定的银行卡信息
    ArrayList card_list=new ArrayList();
    ArrayList balance_list=new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getRecord();
        setContentView(R.layout.activity_choice_account);
        try{
            JSONArray result_json=new JSONArray(bankcard);
            for (int i = 0; i < result_json.length(); i++) {
                JSONObject object=result_json.getJSONObject(i);
                card_list.add(object.getString("cardnumber"));
                balance_list.add(object.getString("balance"));
                TextView textView = new TextView(this);
                textView.setId(i);
                textView.setText(object.getString("type")+"\n"+object.getString("cardnumber"));
                textView.setOnClickListener(new ChoiceBankAccount());
                textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 80));
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setPadding(10, 0, 0, 0);
                textView.setTextColor(Color.BLACK);
                textView.setBackgroundResource(R.drawable.underline1);
                LinearLayout layout = (LinearLayout) findViewById(R.id.choiceaccount);
                layout.addView(textView);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class ChoiceBankAccount implements View.OnClickListener{
        public void onClick(View v)
        {
            Intent intent = new Intent();
            intent.putExtra("myaccount", card_list.get(v.getId()).toString());
            intent.putExtra("balance", balance_list.get(v.getId()).toString());
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    public void getRecord() {
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String path = Address.ip+Address.bankcardinformation+"phone="+Login.phone;
                    String mode = "GET";
                    bankcard = new WebService().executeHttpGet(path,mode);
                }
            });
            thread.start();
            thread.join();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
