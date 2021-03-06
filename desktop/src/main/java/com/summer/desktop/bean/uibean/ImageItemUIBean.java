package com.summer.desktop.bean.uibean;

//by summer on 2017-06-06.

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.summer.desktop.R;
import com.summer.lib.bean.uibean.BaseUIBean;

import butterknife.BindView;

public class ImageItemUIBean extends BaseUIBean {


    @BindView(R.id.image)
    ImageView image;

    public ImageItemUIBean(Context context, ViewGroup parent) {
        super(context, parent, R.layout.item_note_image);
    }

    public ImageView getImage() {
        return image;
    }
}
