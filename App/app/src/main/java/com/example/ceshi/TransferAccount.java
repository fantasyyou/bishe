package com.example.ceshi;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.service.ProvingIdentity;
import com.service.WebService;
import com.tool.Address;
import java.net.URLEncoder;

/*
 * 修改完成11
 * 修改完成11
 * 修改完成11
 * */

public class TransferAccount extends AppCompatActivity {
    public static String myaccount="";   //用户账户
    public static String cardnumber="";  //银行卡号
    public static String bank="";        //银行类型
    public static String name="张三";    //用户姓名
    public static String money="";       //交易金额
    public String balance="";
    public String result="";      //验证结果

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_account);
        TextView choicebank=findViewById(R.id.choicebank);
        choicebank.setOnClickListener(new ChoiceBankType());
        ImageView photograph=findViewById(R.id.photograph);
        photograph.setOnClickListener(new IdentifyingPictures());
        TextView myaccount=findViewById(R.id.myaccount);
        myaccount.setOnClickListener(new ChoiceMyAccount());
        TextView balance=findViewById(R.id.balance);
        balance.setOnClickListener(new ChoiceMyAccount());
    }

    public void pay(View view) {
        EditText editText2 = (EditText) findViewById(R.id.cardnumber);
        cardnumber = editText2.getText().toString();
        EditText editText3 = (EditText) findViewById(R.id.money);
        money = editText3.getText().toString();
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String path = Address.ip + Address.addbankcard +
                                "cardnumber=" + cardnumber + "&bank=" + URLEncoder.encode(bank, "utf-8") + "&name=" + URLEncoder.encode(name, "utf-8");
                        String mode = "GET";
                        result=new WebService().executeHttpGet(path,mode);
                    } catch(Exception e) { }
                }
            });
            thread.start();
            thread.join();
            System.out.println(result);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if(!result.equals("银行卡不存在"))
        {
            if((!myaccount.equals("")&&!myaccount.equals(" "))&&(!money.equals("")&&!money.equals(" ")))
            {
                if(Float.valueOf(money)<Float.valueOf(balance)) {
                    PayDialogFragment payDialogFragment = new PayDialogFragment();
                    payDialogFragment.show(getSupportFragmentManager(), "payFragment");
                }
                else {System.out.println("余额不足，请重新输入");}
            }
            else {System.out.println("请输入转账金额并选择交易账户");}
        }
        else { System.out.println("请输入正确的银行卡号信息");}
    }

    public String transfer()
    {
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String path = Address.ip+Address.transfer+"myaccount="+TransferAccount.myaccount
                            +"&cardnumber="+TransferAccount.cardnumber+"&money="+TransferAccount.money;
                    String mode = "POST";
                    result = new ProvingIdentity().executeHttpGet(path,mode);
                }
            });
            thread.start();
            thread.join();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
     return result;
    }

    public class ChoiceBankType implements View.OnClickListener{
        public void onClick(View v)
        {
            Intent intent = new Intent(TransferAccount.this, ChoiceBank.class);
            startActivityForResult(intent, 1);
        }
    }

    public class IdentifyingPictures implements View.OnClickListener{
        public void onClick(View v)
        {
            Intent intent = new Intent(TransferAccount.this, Custom.class);
            startActivityForResult(intent, 2);
        }
    }

    public class ChoiceMyAccount implements View.OnClickListener{
        public void onClick(View v)
        {
            Intent intent = new Intent(TransferAccount.this, ChoiceAccount.class);
            startActivityForResult(intent, 3);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1&&data!=null) {
            bank = data.getStringExtra("bank");
            TextView textView = findViewById(R.id.choicebank);
            textView.setText(bank);
        }

        if(requestCode==2&&data!=null) {
            String cardnumber = data.getStringExtra("cardnumber");
            cardnumber.replace("_","");
            TextView textView = findViewById(R.id.cardnumber);
            textView.setText(cardnumber);
        }

        if(requestCode==3&&data!=null)
        {
            myaccount = data.getStringExtra("myaccount");
            TextView textView1 = findViewById(R.id.myaccount);
            textView1.setText(myaccount);
            balance = data.getStringExtra("balance");
            TextView textView2 = findViewById(R.id.balance);
            textView2.setText(balance);
        }
    }
}