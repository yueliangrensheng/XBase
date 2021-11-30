package com.yazao.demo;

import android.content.Intent;
import android.view.View;

import com.yazao.lib.xbase.BaseActivity;
import com.yazao.lib.xbase.demo.R;
import com.yazao.lib.xlog.Log;

public class MainActivity extends BaseActivity {

    //当前页面不需要使用 DataBinding，因此 继承 BaseActivity类时候，不需要指定泛型
    @Override
    protected boolean isFitDataBinding() {
        return false;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main_list_layout;
    }

    @Override
    protected void initViewsAndEvents() {
        super.initViewsAndEvents();
        Log.e("----initViewsAndEvents ");
    }

    @Override
    protected void initData() {
        Log.e("----initData ");

        findViewById(R.id.main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, com.yazao.demo.xbase.MainActivity.class));
            }
        });
    }
}
