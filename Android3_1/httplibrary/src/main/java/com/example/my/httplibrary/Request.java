package com.example.my.httplibrary;

import java.util.HashMap;

/**
 * Created by my on 2016/6/21.
 */
  //请求的对象类
 abstract public  class Request <T>{
    //分别是请求的目标路径，请求类型，返回值
    private String url;
    private Method method;
    private Callback callback;
    //构造方法
    public Request( String url, Method method,Callback callback) {
        this.callback = callback;
        this.url = url;
        this.method = method;
    }
    //这里是枚举请求类型的方法，在下载部分用是否相同去判断method到底执行POST还是GET
    public enum Method{
        GET,POST;
    }
    //返回下载结果
    public Callback getCallback() {
        return callback;
    }

    public String getUrl() {
        return url;
    }
    //下面几个都是set，get属性，
    public void setUrl(String url) {
        this.url = url;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
    public void onError(Exception e) {
        callback.onEror(e);
    }
    //装在返回数据
    protected void onResponse(T res){
        System.out.println("装在");
        callback.onresponse(res);
    }
    //利用接口回调的方法，装在回调类型
    public interface Callback<T>{
        void onEror(Exception e);
        void onresponse(T response);
    }
    //POST在上传数值是要进行数据的优化
    public HashMap<String ,String> getPost(){
        return null;
    }
    //于图片下载相关的抽象方法，貌似也是返回？只是返回的是图片而已；
    abstract public void dispatchContent(byte[] content);
}
