package com.shenyutao.freeuicheck;

import android.app.Application;
import android.content.Context;

import com.shenyutao.mylibrary.IgnoreUiUpdateCheck;

public class MyApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        IgnoreUiUpdateCheck.freeReflection(base);
    }
}
