package com.example.my.httplibrary;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by my on 2016/6/22.
 */
//请求队列的类，主要用于有序进行网络线程下载
public class RequestQueue {
    //创建阻塞队列
    private BlockingQueue<Request> mquest=new LinkedBlockingQueue<>();
    //自定义线程最大数量为3
    private final int MAX_THREADS=3;
    //加载线程之后的网络工程分发器，
    private NetworkDispatcher[] nder=new NetworkDispatcher[MAX_THREADS];
    //构造函数和初始化设定
    public RequestQueue() {
        ini();
    }
    //都是初始化设定，开启线程池
    private void ini() {
        for (int i=0;i<nder.length;i++){
            nder[i]=new NetworkDispatcher(mquest);
            nder[i].start();
        }
    }
    //添加请求到队列当中
    public void addRequest (Request request){
        mquest.add(request);
    }
    //停止线程
    private void  stop(){
        for (int i = 0; i < nder.length; i++) {
            nder[i].flag = false;
            nder[i].interrupt();
        }
    }
}
