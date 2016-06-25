package com.example.my.chabaike3.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.my.chabaike3.R;
import com.example.my.chabaike3.adapter.Lv_adapter;
import com.example.my.chabaike3.beans.Info;
import com.example.my.chabaike3.ui.Activity.DetailsActivity;
import com.example.my.httplibrary.HttpHlper;
import com.example.my.httplibrary.Request;
import com.example.my.httplibrary.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by my on 2016/6/22.
 */
public class Fragment_cbk extends Fragment{
    private ListView list_fragment;
    private List<Info> infoLists=new ArrayList<>();
    private int class_id;
    private Lv_adapter lvadapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view=View.inflate(getActivity(),R.layout.fragment_cbk,null);
        list_fragment= (ListView) view .findViewById(R.id.list_frag);
        class_id=getArguments().getInt("id");
        System.out.println(class_id + "种类ID");
        if(bundle!=null){
            Parcelable[] pa=bundle.getParcelableArray("cbk");
            Info[] ifs=(Info[])bundle.getParcelableArray("cbk");
            if (ifs!=null&&ifs.length!=0){
                System.out.println("储存中提取");
                infoLists= Arrays.asList(ifs);
                lvadapter=new Lv_adapter(infoLists);
            }else {
                System.out.println("网络下载，但bundle不为空");
                getNetworkdata();
            }
        }else  {
            System.out.println("网络下载");
            getNetworkdata();
        }
        list_fragment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getActivity(), infoLists.get(position).getId()+"",Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(getActivity(), DetailsActivity.class);
                    intent.putExtra("info", infoLists.get(position).getId());
                    startActivity(intent);
            }
        });
        return  view ;
    }

    public void getNetworkdata() {
        String url = "http://www.tngou.net/api/info/list?id="+class_id;
        System.out.println(url);
        StringRequest sr=new StringRequest(url, Request.Method.GET, new Request.Callback<String>(){
            @Override
            public void onEror(Exception e) {

            }
            @Override
            public void onresponse(String response) {
                try {
                    //System.out.println(response.length()+"反馈");
                    JSONObject jsonObject = new JSONObject(response);
                    List<Info> listinfo = parseJson2List(jsonObject);
                    System.out.println(listinfo != null);
                    if (listinfo != null){
                        infoLists.clear();
                        infoLists=listinfo;
                        if (lvadapter == null){
                            lvadapter = new Lv_adapter(infoLists);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    list_fragment.setAdapter(lvadapter);
                                }
                            });
                        }else {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    lvadapter.notifyDataSetChanged();
                                }
                            });

                        }
                    }
                } catch (JSONException e){
                    e.printStackTrace();

                }
            }
        });
        HttpHlper.addhResquest(sr);
    }

    private List<Info> parseJson2List(JSONObject jsonObject) throws JSONException {
        System.out.println((jsonObject==null)+"为空？");
        if (jsonObject == null)return  null;

        JSONArray array = jsonObject.getJSONArray("tngou");

        if (array== null ||array.length() ==0)return null;

        List<Info> list = new ArrayList<>();
        int len = array.length();
        JSONObject obj = null;
        Info info =null;
        for (int i = 0; i <len ; i++) {
            obj = array.getJSONObject(i);
            info = new Info();
            info.setDescription(obj.optString("description"));
            info.setRcount(obj.optInt("rcount"));
            long time = obj.getLong("time");
            String str = new SimpleDateFormat("yyyyMMdd:hhmmss").format(time);
            info.setTime(str);
            info.setImg(obj.optString("img"));
            info.setId(obj.optInt("id"));
            list.add(info);
        }
        return list;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (infoLists==null||infoLists.size()==0)return;
        Info[] parms= new Info[infoLists.size()];
        infoLists.toArray(parms);
        outState.putParcelableArray("cbk",parms);
    }
}
