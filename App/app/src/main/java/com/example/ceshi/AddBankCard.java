package com.example.ceshi;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.service.WebService;
import com.tool.Address;
import java.net.URLEncoder;

public class AddBankCard extends AppCompatActivity {
    public String cardnumber="";  //银行卡号
    public String bank="";        //银行类型
    public String name="";        //用户姓名
    public String result="";      //验证结果

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank_card);
        TextView choicebank=findViewById(R.id.choicebank);
        choicebank.setOnClickListener(new ChoiceBankType());
        ImageView photograph=findViewById(R.id.photograph);
        photograph.setOnClickListener(new IdentifyingPictures());
        TextView textView=findViewById(R.id.name);
        textView.setText(Login.name);
    }

    public void next(View view) //跳转到明细表
    {
        EditText editText1 = (EditText) findViewById(R.id.cardnumber);
        cardnumber = editText1.getText().toString();
        query();  //查询卡号及用户名等信息是否存在
        if(!result.equals("银行卡不存在"))
        {
            Intent intent=new Intent(AddBankCard.this,Verification.class);//新建信使对象
            intent.putExtra("cardnumber",cardnumber);
            intent.putExtra("bindphone",result);
            startActivity(intent);//打开新的activity*/MyAccount
        }
        else { System.out.println("请输入正确的银行卡号信息");}
    }

    public class IdentifyingPictures implements View.OnClickListener{
        public void onClick(View v)
        {
            Intent intent = new Intent(AddBankCard.this, Custom.class);
            startActivityForResult(intent, 1);
        }
    }

    public class ChoiceBankType implements View.OnClickListener{
        public void onClick(View v)
        {
            Intent intent = new Intent(AddBankCard.this, ChoiceBank.class);
            startActivityForResult(intent, 2);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1&&data!=null) {
            cardnumber = data.getStringExtra("cardnumber");
            cardnumber.replace("_","");
            TextView textView = findViewById(R.id.cardnumber);
            textView.setText(cardnumber);
        }

        if(requestCode==2&&data!=null) {
            bank = data.getStringExtra("bank");
            TextView textView = findViewById(R.id.choicebank);
            textView.setText(bank);
        }
    }

    public void query() {
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String path = Address.ip + Address.addbankcard +
                                "cardnumber=" + cardnumber + "&bank=" + URLEncoder.encode(bank, "utf-8") + "&name=" + URLEncoder.encode(Login.name, "utf-8");
                        String mode = "GET";
                        result=new WebService().executeHttpGet(path,mode);
                    } catch(Exception e) { }
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
