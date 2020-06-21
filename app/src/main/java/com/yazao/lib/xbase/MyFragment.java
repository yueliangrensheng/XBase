package com.yazao.lib.xbase;

import android.view.Gravity;
import android.view.View;

import com.yazao.lib.toast.XToast;
import com.yazao.lib.xbase.demo.R;
import com.yazao.lib.base.BaseFragment;

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
}
