package com.example.ceshi;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.service.WebService;
import com.tool.Address;

import java.net.URLEncoder;

public class Verification extends AppCompatActivity {
    public String cardnumber="";
    public String bindphone="";
    public String result="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cardnumber=getIntent().getStringExtra("cardnumber");
        bindphone=getIntent().getStringExtra("bindphone");
        setContentView(R.layout.activity_verification);
        TextView textview1=findViewById(R.id.cardnumber);
        TextView textview2=findViewById(R.id.bindphone);
        textview1.setText(cardnumber);
        textview2.setText(bindphone);
    }

    public void binding(View view) {
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        EditText editText1 = (EditText) findViewById(R.id.code);
                        String code = editText1.getText().toString();
                        EditText editText2 = (EditText) findViewById(R.id.verificationcode);
                        String verificationcode = editText2.getText().toString();
                        String path = Address.ip + Address.addbankcard + "cardnumber=" + cardnumber +
                                       "&verificationcode=" + verificationcode + "&code=" + code +
                                       "&phone=" + Login.phone + "&bindphone=" + bindphone+"&type=" + URLEncoder.encode("绑定", "utf-8");
                        String mode = "POST";
                        result=new WebService().executeHttpGet(path,mode);
                    } catch(Exception e) { }
                }
            });
            thread.start();
            thread.join();
            if(result.equals("绑定成功")) {
                startActivity(new Intent(this, MyAccount.class));
            }
            else { System.out.println(result);}

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCode(View view) {
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String path = Address.ip + Address.provingidentitya + "phone=" + bindphone + "&type=" + URLEncoder.encode("绑定", "utf-8");
                        String mode = "GET";
                        new WebService().executeHttpGet(path, mode);
                    }catch (Exception e){}
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
