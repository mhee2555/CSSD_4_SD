package com.phc.cssd;

import android.content.Context;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //Firebase.setAndroidContext(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //MultiDex.install(this);
    }
}
