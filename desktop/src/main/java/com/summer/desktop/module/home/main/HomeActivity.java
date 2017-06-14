package com.summer.desktop.module.home.main;

//by summer on 2017-06-07.

import android.os.Bundle;
import android.view.View;

import com.summer.desktop.R;
import com.summer.desktop.util.FragList;
import com.summer.lib.base.activity.BaseUIActivity;
import com.summer.lib.base.listener.DoubleClickListener;
import com.summer.lib.base.ope.BaseOpes;
import com.summer.lib.view.bottommenu.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class HomeActivity extends BaseUIActivity<HomeUIOpe, HomeDAOpe> implements IPostion, DoubleClickListener, View.OnClickListener {


    @Override
    public BaseOpes<HomeUIOpe, HomeDAOpe> createOpes() {
        return new BaseOpes<>(new HomeUIOpe(activity), new HomeDAOpe(activity));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getOpes().getUiOpe().initViewPager(activity);
        getOpes().getUiOpe().initBottom(this, this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMesage(MessageEvent event) {
        getOpes().getUiOpe().selectPager(event.position);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public int getNowPostion() {
        return getOpes().getUiOpe().getUiBean().getHomeViewpager().getCurrentItem();
    }

    @Override
    public void onBackPressed() {
        if (getNowPostion() % 3 == 2) {
            //Toast.makeText(getApplicationContext(), "double kill", Toast.LENGTH_SHORT).show();
            FragList.getInstance().removeTop(activity);
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
