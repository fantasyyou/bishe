package com.tool;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Base64;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.Arrays;

public class Ceshi {
    public static String bank_card="测试失败";

    public static void sendMessage(byte[] request) {
        byte[] res = null;
        Socket socket = null;
        InputStream is = null;
        OutputStream os = null;
        try {
            socket = new Socket("192.168.1.103", 8000);
            os = socket.getOutputStream();
            os.write(request);
            os.flush();
            is = socket.getInputStream();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int count = 0;
            do {
                count = is.read(buffer);
                bos.write(buffer, 0, count);
            } while (is.available() != 0);
            System.out.println("结果:"+bos.toString());
            bank_card=bos.toString();
            res = bos.toByteArray();
            os.close();
            is.close();
            socket.close();
        } catch (Exception ex) {
            try {
                if (is != null) {
                    is.close();
                }
                if (socket != null)
                    socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public String ceshi(String path)
    {
        try {
            new Ceshi().compressBitmapToFile(path);
            //BitmapFactory.Options options =new BitmapFactory.Options();
            //options.inSampleSize=2;
            //options.inJustDecodeBounds =true;
            Bitmap photo = BitmapFactory.decodeFile("/sdcard/emp1.png");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG,30,stream);
            byte[] b1 = stream.toByteArray();
            /*byte[] b2 = Base64.getEncoder().encode(b1);
            byte[] b3 = Base64.getDecoder().decode(b2);
            MyThread mythread=new MyThread(b3);
            mythread.start();
            mythread.join();*/
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bank_card;
    }

    public static void compressBitmapToFile(String path){
        // 尺寸压缩倍数,值越大，图片尺寸越小
        int ratio = 4;
        // 压缩Bitmap到对应尺寸
        Bitmap photo = BitmapFactory.decodeFile(path);
        Bitmap result = Bitmap.createBitmap(photo.getWidth() / ratio, photo.getHeight() / ratio, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Rect rect = new Rect(0, 0, photo.getWidth() / ratio, photo.getHeight() / ratio);
        canvas.drawBitmap(photo, null, rect, null);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 把压缩后的数据存放到baos中
        result.compress(Bitmap.CompressFormat.JPEG, 100 ,baos);
        try {
            FileOutputStream fos = new FileOutputStream("/sdcard/emp1.png");
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
