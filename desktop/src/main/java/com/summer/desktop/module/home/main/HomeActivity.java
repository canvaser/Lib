package com.summer.desktop.module.home.main;

//by summer on 2017-06-07.

import android.os.Bundle;
import android.view.View;

import com.summer.desktop.R;
import com.summer.lib.base.activity.BaseUIActivity;
import com.summer.lib.base.listener.DoubleClickListener;
import com.summer.lib.util.FragmentUtil;

public class HomeActivity extends BaseUIActivity<HomeUIOpe, HomeDAOpe> implements IPostion, DoubleClickListener, View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getOpes().getUiOpe().init(this, this, this);
    }


    @Override
    public int getNowPostion() {
        return getOpes().getUiOpe().getUiBean().getHomeViewpager().getCurrentItem();
    }

    @Override
    public void onBackPressed() {
        if (getNowPostion() % 3 == 2) {
            //Toast.makeText(getApplicationContext(), "double kill", Toast.LENGTH_SHORT).show();
            FragmentUtil.getInstance().removeTop(activity);
        } else {
            //super.onBackPressed();
        }
    }

    @Override
    public void onDouble(View view) {
        onBackPressed();
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag(R.id.position);
        if ((position - 2) >= 0) {
            getOpes().getUiOpe().getUiBean().getBottomViewpager().setCurrentItem((position - 2));
        }
    }
}
