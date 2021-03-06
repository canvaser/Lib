package com.summer.desktop.bean.uibean;

//by summer on 2017-06-06.

import android.content.Context;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.summer.desktop.R;
import com.summer.lib.bean.uibean.BaseUIBean;

import butterknife.BindView;

public class MainActUIBean extends BaseUIBean {


    @BindView(R.id.toptitle)
    TextView toptitle;
    @BindView(R.id.root)
    RelativeLayout root;

    public MainActUIBean(Context context, ViewGroup parent) {
        super(context, parent, R.layout.act_note_main);
    }

    public TextView getToptitle() {
        return toptitle;
    }

    public RelativeLayout getRoot() {
        return root;
    }
}
