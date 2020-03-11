package com.example.ceshi;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.service.ProvingIdentity;
import com.tool.Address;
import java.net.URLEncoder;

/*
 * 修改完成2
 * 修改完成2
 * 修改完成2
 * */

public class Register extends AppCompatActivity {
    public static String phone="";        //用户输入的手机号
    public String code="";                //用户输入的密码
    public static String result="";        //验证的结果

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void getCode(View view) {   //用户接受验证码
        try {
            EditText editText1 = (EditText) findViewById(R.id.phone);
            phone = editText1.getText().toString();
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String path = Address.ip+Address.provingidentitya+"phone="+phone+"&type="+ URLEncoder.encode("注册", "utf-8");
                        String mode = "GET";
                        result = new ProvingIdentity().executeHttpGet(path, mode);
                        System.out.println("发送验证码的结果:" + result);
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

    public void register(View view) {  //用户登录
        try {
            EditText editText1 = (EditText) findViewById(R.id.phone);
            phone = editText1.getText().toString();
            EditText editText2 = (EditText) findViewById(R.id.code);
            code = editText2.getText().toString();
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String path = Address.ip+Address.register+"phone="+phone+"&type="+URLEncoder.encode("注册", "utf-8")+"&code="+code;
                        String mode = "GET";
                        result = new ProvingIdentity().executeHttpGet(path, mode);
                        System.out.println("注册的结果:" + result);
                    }catch (Exception e){}
                }
            });
            thread.start();
            thread.join();
            if(result.equals("注册成功")) {
                startActivity(new Intent(this, Login.class));
            } else{ }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
