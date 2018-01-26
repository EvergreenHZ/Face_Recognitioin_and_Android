package com.example.huaizhi.xtpfinalhomework;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by huaizhi on 1/6/18.
 */

public class XTPLeancloud extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"mwORjkJ3JMiewYfCelSfEdqJ-gzGzoHsz","3bS3E5dp0mst4nOJcYDDENRk");
    }

}
