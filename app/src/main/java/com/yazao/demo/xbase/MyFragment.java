package com.yazao.demo.xbase;

import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.yazao.lib.toast.XToast;
import com.yazao.lib.xbase.BaseFragment;
import com.yazao.lib.xbase.demo.R;

public class MyFragment extends BaseFragment {
    @Override
    protected int getLayoutID() {
        return R.layout.fragment_layout;
    }

    @Override
    protected void initViewsAndEvents() {

        // view is the fragment layout root view
        view.findViewById(R.id.textview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XToast.show("I'm fragment", Gravity.CENTER);
            }
        });
    }

    @Override
    protected boolean isFitDataBinding() {
        return false;
    }

    @Override
    protected void requestNetData() {

    }

    @Override
    protected void onFirstUserVisible() {
        super.onFirstUserVisible();
        Log.e("yazao", " onFirstUserVisible");
    }

    @Override
    protected void onFirstUserInvisible() {
        super.onFirstUserInvisible();
        Log.e("yazao", " onFirstUserInvisible");
    }

    @Override
    protected void onUserVisible() {
        super.onUserVisible();
        Log.e("yazao", " onUserVisible");
    }

    @Override
    protected void onUserInvisible() {
        super.onUserInvisible();
        Log.e("yazao", " onUserInvisible");

    }
}
