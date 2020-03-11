package com.tool;

public class MyThread extends Thread{
    public byte[] data=null;
    public MyThread(byte[] data){
        this.data=data;
    }

    @Override
    public void run(){
        new Ceshi().sendMessage(data);
    }
}
