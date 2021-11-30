package com.yazao.demo;

import com.yazao.lib.toast.XToast;
import com.yazao.lib.xbase.WBaseApplication;

public class BaseApplication extends WBaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        //init xtoast
        XToast.init(this);
    }
}
