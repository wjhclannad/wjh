package com.example.my.chabaike3.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.my.chabaike3.R;
import com.example.my.chabaike3.beans.Info;
import com.example.my.httplibrary.BitmapHttpHlper;
import com.example.my.httplibrary.BitmapRequest;
import com.example.my.httplibrary.Request;

import java.util.List;

/**
 * Created by my on 2016/6/22.
 */
public class Lv_adapter extends BaseAdapter{
    private List<Info> infoLists;
    private Context context;
    public Lv_adapter(List<Info> infoLists) {
       this.infoLists=infoLists;
        this.context=context;
    }

    @Override
    public int getCount() {
        return infoLists==null?0:infoLists.size();
    }

    @Override
    public Object getItem(int position) {
        return infoLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Helder helder=new Helder();
        if(convertView==null){
            convertView=View.inflate(parent.getContext(),R.layout.fragment_item,null);
            helder.image_item= (ImageView) convertView.findViewById(R.id.image_item);
            helder.text_item= (TextView) convertView.findViewById(R.id.text_item);
            helder.text_read= (TextView) convertView.findViewById(R.id.text_read);
            helder.text_time= (TextView) convertView.findViewById(R.id.text_time);
            convertView.setTag(helder);
        }else{
            helder= (Helder) convertView.getTag();
        }
        Info info_lv=infoLists.get(position);
        helder.text_time.setText(info_lv.getTime());
//        helder.text_read.setText(info_lv.getRcount());
        helder.text_item.setText(info_lv.getDescription());
        helder.image_item.setImageResource(R.mipmap.firstdefault);
        LoadImage("http://tnfs.tngou.net/image" + info_lv.getImg() + "_100x100", helder.image_item);
        return convertView;
    }

        private void LoadImage(final String url, final ImageView view) {
            view.setTag(url);
            BitmapRequest br=new BitmapRequest(url, Request.Method.GET, new Request.Callback<Bitmap>() {


                @Override
                public void onEror(Exception e) {

                }
                @Override
                public void onresponse(final Bitmap response) {
                    if (response!=null&&view!=null&&((String)view.getTag()).equals(url)){
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                view.setImageBitmap(response);
                            }
                        });
                    }

                }
                });
           BitmapHttpHlper.addhResquest(br);
        }

    class Helder{
        private TextView text_read,text_item,text_time;
        private ImageView image_item;
    }

}
