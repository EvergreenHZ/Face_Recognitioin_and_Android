package com.example.huaizhi.xtpfinalhomework;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent_service = new Intent(MainActivity.this, XTPGetDataService.class);
                startService(intent_service);

                Intent intent_to_main = new Intent(MainActivity.this, XTPMain.class);
                startActivity(intent_to_main);
                MainActivity.this.finish();
            }
        },2000);
    }
}
