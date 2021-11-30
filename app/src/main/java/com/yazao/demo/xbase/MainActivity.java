package com.yazao.demo.xbase;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;

import com.yazao.lib.toast.XToast;
import com.yazao.lib.xbase.BaseActivity;
import com.yazao.lib.xbase.demo.R;
import com.yazao.lib.xbase.demo.databinding.ActivityMainDataBindingBinding;
import com.yazao.lib.xnet.observer.NetUtil;

public class MainActivity extends BaseActivity {

    //当前页面不需要使用 DataBinding ，因此 继承 BaseActivity类时候，不需要指定泛型
    @Override
    protected boolean isFitDataBinding() {
        return false;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        findViewById(R.id.textview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                XToast.show("hello world");
                startActivity(new Intent(MainActivity.this, MainDBActivity.class));
            }
        });
    }

    @Override
    protected void onNetWorkDisConnected() {
        XToast.show("网络已断开，请检查网络", Gravity.CENTER);
    }


    @Override
    protected void onNetWorkConnected(NetUtil.NetType type) {
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
