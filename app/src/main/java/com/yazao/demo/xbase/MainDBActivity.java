package com.yazao.demo.xbase;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;

import com.yazao.lib.toast.XToast;
import com.yazao.lib.xbase.BaseActivity;
import com.yazao.lib.xbase.demo.R;
import com.yazao.lib.xbase.demo.databinding.ActivityMainDataBindingBinding;
import com.yazao.lib.xnet.observer.NetUtil;

/**
 * 使用 DataBinding
 */
public class MainDBActivity extends BaseActivity<ActivityMainDataBindingBinding> {

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main_data_binding;
    }

    @Override
    protected String getLayoutIDString() {
        return "activity_main_data_binding";
    }

    @Override
    protected void initData() {
        mDataBinding.setTextContent("DataBinding Activity");
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainDBActivity.this, MainDBActivityKt.class));
            }
        });
    }

    @Override
    protected void onNetWorkDisConnected() {
        XToast.show("网络已断开，请检查网络", Gravity.CENTER);
    }

    @Override
    protected boolean isFitDataBinding() {
        return true;
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
