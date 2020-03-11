package com.example.ceshi;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/*
 * 修改完成7
 * 修改完成7
 * 修改完成7
 * */

public class ChoiceBank extends AppCompatActivity {
    String[] s = new String[]{"中国工商银行", "中国农业银行","中国银行", "中国建设银行","交通银行", "招商银行","农村商业银行"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_bank);
        for(int i=0;i<s.length;i++) {
            TextView textView = new TextView(this);
            textView.setId(i);
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 80));
            textView.setPadding(10,0,0,0);
            textView.setText(s[i]);
            textView.setOnClickListener(new ReturnChoiceBankType());
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setTextColor(Color.BLACK);
            textView.setBackgroundResource(R.drawable.underline1);
            LinearLayout layout = (LinearLayout) findViewById(R.id.choicebank);
            layout.addView(textView);
        }
    }

   public class ReturnChoiceBankType implements View.OnClickListener{
        public void onClick(View v)
        {
            Intent intent = new Intent();
            intent.putExtra("bank", s[v.getId()]);  //返回银行结果
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
