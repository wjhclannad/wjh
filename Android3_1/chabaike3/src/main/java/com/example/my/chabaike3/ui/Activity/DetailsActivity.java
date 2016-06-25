package com.example.my.chabaike3.ui.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.widget.TextView;

import com.example.my.chabaike3.R;
import com.example.my.chabaike3.beans.Info;
import com.example.my.httplibrary.HttpHlper;
import com.example.my.httplibrary.Request;
import com.example.my.httplibrary.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailsActivity extends AppCompatActivity {
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_details);
        textView= (TextView) findViewById(R.id.details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.logo);
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle("Title");
        toolbar.setSubtitle("Subtitle");
        toolbar.inflateMenu(R.menu.details);
        setSupportActionBar(toolbar);
        Long path_id= getIntent().getLongExtra("info",0);
        String str="http://www.tngou.net/api/lore/show?id="+path_id;
        System.out.println(str);
        StringRequest sr=new StringRequest(str, Request.Method.GET, new Request.Callback<String>() {
            @Override
            public void onEror(Exception e) {

            }

            @Override
            public void onresponse(String response) {
                try {
                    JSONObject object= new JSONObject(response);
                    Info info=parseJson2List(object);
                    System.out.println("详情:"+info.getDescription());
                    textView.setText(info.getDescription());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        HttpHlper.addhResquest(sr);
    }

    private Info parseJson2List(JSONObject jsonObject) throws JSONException {
        JSONObject obj = jsonObject;
        Info info =null;
            info = new Info();
            info.setDescription(obj.optString("message"));
        return info;
    }
}
