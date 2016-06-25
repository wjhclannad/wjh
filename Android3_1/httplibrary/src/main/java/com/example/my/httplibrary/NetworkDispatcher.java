package com.example.my.httplibrary;

import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * Created by my on 2016/6/21.
 */
//Httputils的下载部分
public class NetworkDispatcher extends Thread{
    //初始化请求队列
    private BlockingQueue<Request> mResquest;
    //设定线程进行，flag为true
    public  boolean flag=false;
    //构造函数 把外界的请求队列放到构造函数里，
    NetworkDispatcher(BlockingQueue<Request> resquest) {
        mResquest = resquest;
    }
    //继承线程，重写run
    @Override
    public void run() {
        //线程相关
        System.out.println("执行线程");
       while(!flag&&!isInterrupted()){
           try {
               //从队列中拿出请求执行
                Request req= mResquest.take();
                byte[]  result=null;
               //执行判断进行哪一种网络行为：GET还是POST，并用String接收返回结果
                result=getNetworkResponse(req);
               //返回不为空，则放入接口中，等待回调使用
               if (result!=null){
                req.dispatchContent(result);
               }
           } catch (InterruptedException e) {
               e.printStackTrace();
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
    }
    //判断选择哪一种网络行为
    private byte[] getNetworkResponse(Request request) throws Exception {
        byte[] result_get = null;
        if (TextUtils.isEmpty(request.getUrl())){
            throw new Exception("url is null");
        }
        if (Request.Method.GET == request.getMethod()) {
            System.out.println("匹配"+(Request.Method.GET == request.getMethod()));
            result_get =getResponse(request);
        }
        if (Request.Method.POST == request.getMethod()) {
            result_get =postResponse(request);
        }
        System.out.println(result_get.length + "大小");
        return result_get;
    }
    //POST下载部分,这是进行上传，所以要进行字段编辑，就算上传之后还是会有返回值得，所以要进行out和In两种操作
    private byte[] postResponse(Request request) throws Exception {
        InputStream is=null;
        OutputStream os=null;
        HttpURLConnection mconn=null;
        URL url= new URL(request.getUrl());

        mconn= (HttpURLConnection) url.openConnection();
        mconn.setRequestMethod("POST");
        mconn.setConnectTimeout(5000);
        mconn.setDoOutput(true);

        String post=getPostEncodeString(request);
        byte[] content=null;
        if(post!=null){
            content=post.getBytes();
            mconn.setRequestProperty("content-length", "" + content.length);
        }
        os=mconn.getOutputStream();
        if (content!=null){
            os.write(content);
            os.flush();
        }
        int grc=mconn.getResponseCode();
        if (grc!=200){
           // Log.i(TAG,"getNetworkResponse() returned: response code error code="+grc);
            throw  new Exception("response is null" );
        }

        is = mconn.getInputStream();
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        byte[] buffer=new byte[1024];
        int len=0;
        while ((len=is.read(buffer))!=-1){
            baos.write(buffer,0,len);
        }
        is.close();
        os.close();
      return baos.toByteArray();
    }

    //这是下载部分
    private byte[] getResponse(Request request) throws IOException {
        ByteArrayOutputStream baos=null;
        InputStream is=null;
        HttpURLConnection mcon=null;
        URL url=null;
        try {
            url=new URL(request.getUrl());
            System.out.println(request.getUrl());
            mcon= (HttpURLConnection) url.openConnection();
            mcon.setRequestMethod("GET");
            mcon.setConnectTimeout(5000);
            int gr=mcon.getResponseCode();
            if (gr!= 200){
                  //  Log.d(TAG, "getNetworkResponse() returned: response code error code=" + gr);
                    throw new IllegalArgumentException("code eror");
                }
            is=mcon.getInputStream();
            baos=new ByteArrayOutputStream();
            byte[] buffer=new byte[1024];
            int len =0;
            while( (len=is.read(buffer))!=-1){
                    baos.write(buffer,0,len);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        is.close();
        return baos.toByteArray();
    }
    //上传字段再编译
    private String getPostEncodeString(Request request) {
        HashMap<String ,String > parms=request.getPost();
        StringBuffer sbr=new StringBuffer();
        if (parms!=null){
            Iterator<Map.Entry<String , String >> iterator=parms.entrySet().iterator();
            int i=0;
            while (iterator.hasNext()) {
                if (i > 0) {
                    sbr.append("&");
                }
                Map.Entry<String ,String > value=iterator.next();
                String str=value.getKey()+"="+value.getValue();
                sbr.append(str);
                i++;
            }
            return sbr.toString();

        }
        return null;
    }
}
