package com.summer.desktop.module.day.main;

//by summer on 2017-06-13.

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.summer.desktop.R;
import com.summer.desktop.bean.dabean.TimeBean;
import com.summer.desktop.module.note.circlemenu.CircleMenuFrag;
import com.summer.lib.base.fragment.BaseUIFrag;
import com.summer.lib.base.interf.OnFinishListener;
import com.summer.lib.base.ope.BaseOpes;
import com.summer.lib.constant.ValueConstant;
import com.summer.lib.util.data.DateFormatUtil;
import com.summer.lib.util.file.TimePickUtil;

import java.util.ArrayList;

public class DayFrag extends BaseUIFrag<DayMainUIOpe, DayMainDAOpe> {

    @Override
    public BaseOpes<DayMainUIOpe, DayMainDAOpe> createOpes() {
        return new BaseOpes<>(new DayMainUIOpe(activity), new DayMainDAOpe(activity));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getOpes().getUiOpe().initList(this);
        BroadcastReceiver receiver = new DayBroadCase();
        IntentFilter filter = new IntentFilter();
        filter.addAction(activity.getPackageName() + ValueConstant.ACITON_GLOB_CAST);
        activity.registerReceiver(receiver, filter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onLongClick(View v) {
        CircleMenuFrag circleMenuFrag = new CircleMenuFrag();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.homeroot, circleMenuFrag);
        transaction.commit();
        circleMenuFrag.setOnFinishListener(new OnFinishListener() {
            @Override
            public void onFinish(Object o) {
                switch ((int) o) {
                    case 0:
                        TimePickUtil.getInstance().showTimePickDialogHMHM(activity, getFragmentManager(), new TimePickUtil.TimeDate() {
                            @Override
                            public void getTimeData(ArrayList<Long> time) {
                                getOpes().getDaOpe().getTimes().add(new TimeBean(DateFormatUtil.getHour(time.get(0)), DateFormatUtil.getMinute(time.get(0)), DateFormatUtil.getHour(time.get(1)), DateFormatUtil.getMinute(time.get(1))));
                                getOpes().getUiOpe().addTimes(getOpes().getDaOpe().getTimes());
                            }
                        });
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
            }
        });
        return true;
    }

    public class DayBroadCase extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            getOpes().getUiOpe().getUiBean().getRecycle().getAdapter().notifyDataSetChanged();
        }
    }
}