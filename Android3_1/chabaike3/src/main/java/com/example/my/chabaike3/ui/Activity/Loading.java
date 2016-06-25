package com.example.my.chabaike3.ui.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.my.chabaike3.MainActivity;
import com.example.my.chabaike3.R;

public class Loading extends Activity {
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Loading.this, MainActivity.class);
                boolean bn=getfirstopenflag();
                if (bn) {
                    intent.setClass(Loading.this, Welcome.class);
                }
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

    public boolean getfirstopenflag() {
        boolean bn=false;
        SharedPreferences Pref_Utils = getSharedPreferences("chabaike", Context.MODE_PRIVATE);
        if (Pref_Utils.getBoolean("first", true)) {
            editor = Pref_Utils.edit();
            bn=true;
            editor.putBoolean("first", false);
            editor.commit();
        }
        return bn;
    }
}
