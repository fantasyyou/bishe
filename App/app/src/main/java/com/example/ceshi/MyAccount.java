package com.example.ceshi;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.service.WebService;
import com.tool.Address;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.graphics.Color;
import android.view.Gravity;
import java.util.ArrayList;

/*
 * 修改完成3
 * 修改完成3
 * 修改完成3
 * */

public class MyAccount extends AppCompatActivity {
    public static String bankcard="";  //用户绑定的银行卡信息
    ArrayList card_list=new ArrayList();
    ArrayList type_list=new ArrayList();
    ArrayList balance_list=new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getRecord();
        setContentView(R.layout.activity_my_account);

        try{
            JSONArray result_json=new JSONArray(bankcard);
            //加载银行卡数据
            for (int i = 0; i < result_json.length(); i++) {
                JSONObject object=result_json.getJSONObject(i);
                LinearLayout operation = new LinearLayout(this);
                TextView card_type = new TextView(this);
                TextView card_balance = new TextView(this);
                TextView query_details = new TextView(this);
                TextView transaction = new TextView(this);
                card_list.add(object.getString("cardnumber"));
                type_list.add(object.getString("type"));
                balance_list.add(object.getString("balance"));
                //设置卡号信息
                card_type.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                card_type.setText(object.getString("type")+"\n"+object.getString("cardnumber"));
                card_type.setTextColor(Color.BLACK);
                card_type.setPadding(0, 0, 0, 5);
                card_type.setBackgroundResource(R.drawable.underline);
                //设置余额信息
                card_balance.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                card_balance.setText("人民币余额："+object.getString("balance"));
                card_balance.setTextColor(Color.BLACK);
                card_balance.setPadding(0, 0, 0, 5);
                card_balance.setBackgroundResource(R.drawable.underline);
                //设置操作布局
                operation.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                //设置查询明细操作
                query_details.setId(i);
                query_details.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
                query_details.setGravity(Gravity.CENTER);
                query_details.setText("查询明细");
                query_details.setClickable(true);
                query_details.setOnClickListener(new QueryDetails());
                query_details.setTextSize(15);
                query_details.setBackgroundResource(R.drawable.underline);
                //设置交易转账操作
                transaction.setId(i);
                transaction.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
                transaction.setGravity(Gravity.CENTER);
                transaction.setText("交易转账");
                transaction.setClickable(true);
                String a=object.getString("cardnumber");
                transaction.setOnClickListener(new TransferAccounts());
                transaction.setTextSize(15);
                transaction.setBackgroundResource(R.drawable.underline);
                operation.addView(query_details);
                operation.addView(transaction);
                //添加到布局当中
                LinearLayout layout = (LinearLayout) findViewById(R.id.main_layout);
                layout.addView(card_type);
                layout.addView(card_balance);
                layout.addView(operation);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(400,60);
            layoutParams.setMargins(0,40,0,0);
            layoutParams.gravity=Gravity.CENTER_HORIZONTAL;
            TextView addcard = new TextView(this);
            addcard.setLayoutParams(layoutParams);
            addcard.setGravity(Gravity.CENTER);
            addcard.setText("+添加银行卡");
            addcard.setClickable(true);
            addcard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    //调到绑定银行卡界面
                    Intent intent=new Intent(MyAccount.this,AddBankCard.class);
                    startActivity(intent);
                }

            });
            addcard.setTextColor(Color.RED);
            addcard.setBackgroundResource(R.drawable.line);
            TextView tips = new TextView(this);
            tips.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,60));
            tips.setGravity(Gravity.CENTER);
            tips.setText("添加本人本行或其他银行账户");
            tips.setTextColor(Color.BLACK);
            LinearLayout layout = (LinearLayout) findViewById(R.id.main_layout);
            layout.addView(addcard);
            layout.addView(tips);
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
                    String path = Address.ip+Address.bankcardinformation+"phone="+Login.phone;
                    System.out.println(path);
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

    public class QueryDetails implements View.OnClickListener{
        public void onClick(View v)
        {   System.out.println("测试");
            Intent intent=new Intent(MyAccount.this,MySchedule.class);//新建信使对象
            intent.putExtra("cardnumber",card_list.get(v.getId()).toString());
            intent.putExtra("type",type_list.get(v.getId()).toString());
            intent.putExtra("balance",balance_list.get(v.getId()).toString());
            startActivity(intent);//打开新的activity*/MyAccount
        }
    }

    public class TransferAccounts implements View.OnClickListener{
        public void onClick(View v)
        {
            Intent intent=new Intent(MyAccount.this,TransferAccount.class);//新建信使对象
            intent.putExtra("cardnumber",card_list.get(v.getId()).toString());
            startActivity(intent);//打开新的activity
        }
    }
}