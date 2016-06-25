package com.example.my.httplibrary;

/**
 * Created by my on 2016/6/22.
 */
//网路助手工具
public class HttpHlper {
    //单例化应用.
    private static  HttpHlper  instance;
    private RequestQueue mqueue;
    private static HttpHlper getinstance(){
        if (instance==null){
            synchronized (HttpHlper.class){
                if (instance==null){
                    instance=new HttpHlper();
                }
            }
        }
        return instance;
    }
    private HttpHlper(){
        mqueue=new RequestQueue();
    }
    public static void  addhResquest(Request request){
    getinstance().mqueue.addRequest(request);

    }
}
