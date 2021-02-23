package com.yazao.lib.xbase;

import android.view.Gravity;

import com.yazao.lib.net.NetUtil;
import com.yazao.lib.toast.XToast;
import com.yazao.lib.xbase.demo.R;
import com.yazao.lib.xbase.demo.databinding.ActivityMainDataBindingBinding;

public class MainDBActivity extends BaseActivity<ActivityMainDataBindingBinding> {

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main_data_binding;
    }

    @Override
    protected void initData() {
        mDataBinding.setTextContent("DataBinding Activity");
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
