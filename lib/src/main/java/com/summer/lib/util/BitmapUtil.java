package com.summer.lib.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;


/**
 * 图像处理的工具类
 */
public class BitmapUtil {

    private static BitmapUtil instance;

    private BitmapUtil() {

    }

    /**
     * 图像处理工具类单例模式
     */
    public static BitmapUtil getInstance() {
        if (instance == null) {
            instance = new BitmapUtil();
        }
        return instance;
    }

    /**
     * 设置imageview的src
     */
    public void setImageSrc(Context context, ImageView imageView, String imageSrc) {
        if (imageView == null) {
            LogUtil.E("imageview is null");
            return;
        }
        if (NullUtil.isStrEmpty(imageSrc)) {
            LogUtil.E("imagesrc is null");
        }
        Glide.with(context).load(imageSrc).into(imageView);
    }


    public ArrayList<File> getThumbnails(ArrayList<File> files) {
        if (files == null) {
            return new ArrayList<File>();
        }
        ArrayList<File> newFiles = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            if (files.get(i) == null || files.get(i).getPath() == null || files.get(i).getPath().equals("")) {
                continue;
            }
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            //开始读入图片，此时把options.inJustDecodeBounds 设回true了
            newOpts.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeFile(files.get(i).getPath(), newOpts);//此时返回bm为空
            newOpts.inJustDecodeBounds = false;
            int w = newOpts.outWidth;
            int h = newOpts.outHeight;
            //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
            float hh = 800f;//这里设置高度为800f
            float ww = 480f;//这里设置宽度为480f
            //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
            int be = 1;//be=1表示不缩放
            if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
                be = (int) (newOpts.outWidth / ww);
            } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
                be = (int) (newOpts.outHeight / hh);
            }
            if (be <= 0)
                be = 1;
            newOpts.inSampleSize = be;//设置缩放比例
            //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
            bitmap = BitmapFactory.decodeFile(files.get(i).getPath(), newOpts);
            try {
                File file = new File(IntentUtil.getInstance().getPhotoShortFileFolder(), files.get(i).getName());
                newFiles.add(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 60, new FileOutputStream(file));
                bitmap.recycle();
                bitmap = null;
                System.gc();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return newFiles;
    }
}
