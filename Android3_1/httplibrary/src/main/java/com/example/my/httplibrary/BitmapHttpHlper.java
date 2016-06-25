package com.example.my.httplibrary;

/**
 * Created by my on 2016/6/22.
 */
//网路助手工具
public class BitmapHttpHlper {
    //单例化应用.
    private static BitmapHttpHlper instance;
    private RequestQueue mqueue;
    private static BitmapHttpHlper getinstance(){
        if (instance==null){
            synchronized (BitmapHttpHlper.class){
                if (instance==null){
                    instance=new BitmapHttpHlper();
                }
            }
        }
        return instance;
    }
    private BitmapHttpHlper(){
        mqueue=new RequestQueue();
    }
    public static void  addhResquest(Request request){
    getinstance().mqueue.addRequest(request);

    }
}
