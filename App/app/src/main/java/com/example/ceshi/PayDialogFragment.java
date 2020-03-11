package com.example.ceshi;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import com.service.ProvingIdentity;
import com.tool.Address;

/*
 * 修改完成8
 * 修改完成8
 * 修改完成8
 * */

public class PayDialogFragment extends DialogFragment implements PwdEditText.OnTextInputListener {

    private static final String TAG = "PayDialogFragment";
    public String code="";    //用户输入的支付密码
    public String result="";  //返回数据
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        //去掉dialog的标题，需要在setContentView()之前
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        View view = inflater.inflate(R.layout.layout_pay_dialog, null);
        ImageView exitImgView = view.findViewById(R.id.iv_exit);
        exitImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayDialogFragment.this.dismiss();
            }
        });
        final PwdEditText editText = view.findViewById(R.id.et_input);
        editText.setOnTextInputListener(this);
        PwdKeyboardView keyboardView = view.findViewById(R.id.key_board);
        keyboardView.setOnKeyListener(new PwdKeyboardView.OnKeyListener() {
            @Override
            public void onInput(String text) {
                Log.d(TAG, "onInput: text = " + text);
                editText.append(text);
                String content = editText.getText().toString();
                Log.d(TAG, "onInput: content = " + content);
            }

            @Override
            public void onDelete() {
                Log.d(TAG, "onDelete: ");
                String content = editText.getText().toString();
                if (content.length() > 0) {
                    editText.setText(content.substring(0, content.length() - 1));
                }


            }
        });
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.windowAnimations = R.style.DialogFragmentAnimation;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //设置dialog的位置在底部
        lp.gravity = Gravity.BOTTOM;
        window.setAttributes(lp);
        //去掉dialog默认的padding
//        window.getDecorView().setPadding(0, 0, 0, 0);

        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    @Override
    public void onComplete(String code) {
        //Log.d(TAG, "onComplete: result = " + result);
        this.code=code;
        verifyCode();  //检测密码输入是否正确
        if(result.equals("密码正确")) {
            result=new TransferAccount().transfer();
            Toast.makeText(getContext(),result, Toast.LENGTH_SHORT).show();
            PayDialogFragment.this.dismiss();  //关闭窗口
        }
        else {
            Toast.makeText(getContext(),"密码错误，请重新输入", Toast.LENGTH_SHORT).show();
        }
    }

    public void verifyCode() {
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String path = Address.ip+Address.transfer+"myaccount="+TransferAccount.myaccount+"&code="+code;
                    String mode = "GET";
                    result = new ProvingIdentity().executeHttpGet(path,mode);
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
