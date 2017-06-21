package com.summer.lib.view.image.imagepager;
import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.summer.lib.base.ope.BaseUIBean;
import com.summer.lib.databinding.ActivityImagesVpBinding;

import java.util.ArrayList;

public class ImagePagerUIOpe extends BaseUIBean<ActivityImagesVpBinding> {


    public ImagePagerUIOpe(Context context) {
        super(context);
    }

    public void initPager(FragmentManager fragmentManager, ArrayList<String> strs, int position) {
        viewDataBinding.vpVp.setAdapter(new ImagePagerAdaper(fragmentManager, context, strs));
        viewDataBinding.vpVp.setCurrentItem(position);

    }

}