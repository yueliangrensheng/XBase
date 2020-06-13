package com.yazao.lib.xbase;

import android.view.Gravity;
import android.view.View;

import com.yazao.lib.base.BaseActivity;
import com.yazao.lib.net.NetUtil;
import com.yazao.lib.toast.XToast;
import com.yazao.lib.xbase.demo.R;

import static com.yazao.lib.net.NetUtil.*;

public class MainActivity extends BaseActivity {

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        findViewById(R.id.textview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                XToast.show("hello world");
            }
        });
    }

    @Override
    protected void onNetWorkDisConnected() {
        XToast.show("网络已断开，请检查网络", Gravity.CENTER);
    }

    @Override
    protected void onNetWorkConnected(NetType type) {
        switch (type) {
            case NONE:// none
                break;
            case WIFI:// wifi
                XToast.show("当前处于Wifi网络", Gravity.CENTER);
                break;
            default:
                //移动网络
                XToast.show("当前处于移动网络", Gravity.CENTER);
                break;
        }

    }
}
