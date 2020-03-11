package com.example.ceshi;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import com.tool.Ceshi;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/*
 * 修改完成6
 * 修改完成6
 * 修改完成6
 * */

public class Custom extends Activity implements SurfaceHolder.Callback{
    public String card;
    private Camera mCamera;
    private SurfaceView mPreview;
    private SurfaceHolder mHolder;
    private int cameraId=0;//声明cameraId属性，ID为1调用前置摄像头，为0调用后置摄像头。此处因有特殊需要故调用前置摄像头
    //定义照片保存并显示的方法
    private Camera.PictureCallback mpictureCallback=new Camera.PictureCallback(){
        @Override
        public void onPictureTaken(byte[] data,Camera camera){
            File tempfile=new File("/sdcard/emp.png");//新建一个文件对象tempfile，并保存在某路径中
            try{
                final byte[] image=data;
                FileOutputStream fos =new FileOutputStream(tempfile);
                fos.write(data);//将照片放入文件中
                fos.close();//关闭文件
                String bank_card=new Ceshi().ceshi("/sdcard/emp.png");
                Intent intent = new Intent();
                intent.putExtra("cardnumber",bank_card);
                setResult(RESULT_OK, intent);
                Custom.this.finish();//关闭现有界面
            }
            catch (IOException e){e.printStackTrace();}
        }
    };

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom);
        Button choicepicture = (Button) findViewById(R.id.choicepicture);
        choicepicture.setOnClickListener(new ChoicePicture());
        //判断6.0大于等于.当前权限时，直接运行，超过6.0的话动态调取权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1001);
            }
        } else {
        }
        mPreview=findViewById(R.id.preview);//初始化预览界面
        mHolder=mPreview.getHolder();
        mHolder.addCallback(this);
        //点击预览界面聚焦
        mPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.autoFocus(null);
            }
        });
    }
    //定义“拍照”方法
    public void capture(View view){
        Camera.Parameters parameters=mCamera.getParameters();
        parameters.setPictureFormat(ImageFormat.JPEG);//设置照片格式
        parameters.setPreviewSize(800,400);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        //摄像头聚焦
        mCamera.autoFocus(new Camera.AutoFocusCallback(){
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if(success){mCamera.takePicture(null,null, mpictureCallback);}
            }
        });

    }
    //activity生命周期在onResume是界面应是显示状态
    @Override
    protected void onResume() {
        super.onResume();
        if (mCamera==null){//如果此时摄像头值仍为空
            if (Build.VERSION.SDK_INT >= 23) {
                int REQUEST_CODE_CONTACT = 101;
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                //验证是否许可权限
                for (String str : permissions) {
                    if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                        //申请权限
                        this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    }
                }
            }
            mCamera=getCamera();//则通过getCamera()方法开启摄像头
            if(mHolder!=null){
                setStartPreview(mCamera,mHolder);//开启预览界面
            }
        }
    }
    //activity暂停的时候释放摄像头
    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }
    //onResume()中提到的开启摄像头的方法
    private Camera getCamera(){
        Camera camera;//声明局部变量camera
        try{
            camera=Camera.open(cameraId);}//根据cameraId的设置打开前置摄像头
        catch (Exception e){
            camera=null;
            e.printStackTrace(); }
        return camera;
    }
    //开启预览界面
    private void setStartPreview(Camera camera,SurfaceHolder holder){
        try{
            camera.setPreviewDisplay(holder);
            Configuration mConfiguration = this.getResources().getConfiguration(); //获取设置的配置信息
            int ori = mConfiguration.orientation; //获取屏幕方向
            if (ori == mConfiguration.ORIENTATION_PORTRAIT) {
                camera.setDisplayOrientation(90);//如果没有这行你看到的预览界面就会是水平的
            }
            camera.startPreview();}
        catch (Exception e){
            e.printStackTrace(); }
    }
    //定义释放摄像头的方法
    private void releaseCamera(){
        if(mCamera!=null){//如果摄像头还未释放，则执行下面代码
            mCamera.stopPreview();//1.首先停止预览
            mCamera.setPreviewCallback(null);//2.预览返回值为null
            mCamera.release(); //3.释放摄像头
            mCamera=null;//4.摄像头对象值为null
        }
    }
    //定义新建预览界面的方法
    @Override
    public void surfaceCreated(SurfaceHolder holder) { setStartPreview(mCamera,mHolder); }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mCamera.stopPreview();//如果预览界面改变，则首先停止预览界面
        setStartPreview(mCamera,mHolder);//调整再重新打开预览界面
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();//预览界面销毁则释放相机
    }

    public class ChoicePicture implements View.OnClickListener{
        public void onClick(View v)
        {
            //打开本地相册
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            //设定结果返回
            startActivityForResult(intent, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            //获取返回的数据，这里是android自定义的Uri地址
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            //获取选择照片的数据视图
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            //从数据视图中获取已选择图片的路径
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            String bank_card=new Ceshi().ceshi(picturePath);
            Intent intent = new Intent();
            intent.putExtra("cardnumber",bank_card);
            setResult(RESULT_OK, intent);
            Custom.this.finish();//关闭现有界面
        }
    }
}

