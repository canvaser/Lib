package com.summer.desktop.module.note.notedetail;

//by summer on 2017-06-06.

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.EditText;

import com.google.gson.Gson;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.summer.desktop.bean.dabean.GsonNoteBean;
import com.summer.desktop.bean.dabean.ImageNote;
import com.summer.desktop.bean.dabean.Note;
import com.summer.desktop.bean.dabean.NoteDetail;
import com.summer.lib.base.ope.BaseDAOpe;
import com.summer.lib.bean.databean.EventBean;
import com.summer.lib.util.GsonUtil;
import com.summer.lib.util.IntentUtil;
import com.summer.lib.util.LogUtil;
import com.summer.lib.util.NullUtil;

import java.io.File;
import java.util.ArrayList;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class NoteDetailDAOpe extends BaseDAOpe {


    GsonNoteBean bean;

    Note note;

    Gson gson = new Gson();
    EditText ed;


    public NoteDetailDAOpe(Context context) {
        super(context);
    }

    public void init(Fragment fragment) {
        note = (Note) fragment.getArguments().getSerializable("data");
        bean = gson.fromJson(note.getData(), GsonNoteBean.class);
        if (bean.getData() == null) {
            bean.setData(new ArrayList<NoteDetail>());
        }
    }

    public void update(final int[] i, final GsonNoteBean bean, final Note note) {
        if (bean.getData() == null) {
            return;
        }
        if (i[0] == 0) {
            Note n = new Note();
            n.setData(gson.toJson(bean));
            note.setData(gson.toJson(bean));
            n.update(note.getObjectId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {

                }
            });
        }
        if (bean.getData().size() <= i[0] && i[0] != 0) {
            Note n = new Note();
            n.setData(gson.toJson(bean));
            note.setData(gson.toJson(bean));
            n.update(note.getObjectId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {

                }
            });
            return;
        }
        if (bean.getData().size() == 0) {
            return;
        }
        if (bean.getData().get(i[0]).getType().equals(NoteDetail.IMAGE)) {
            final ImageNote imageNote = gson.fromJson(bean.getData().get(i[0]).getData(), ImageNote.class);
            if (NullUtil.isStrEmpty(imageNote.getSrc()) && !NullUtil.isStrEmpty(imageNote.getLocalSrc())) {
                java.io.File file = new java.io.File(imageNote.getLocalSrc().substring("file://".length(), imageNote.getLocalSrc().length()));
                file.exists();
                final BmobFile bmobFile = new BmobFile(file);
                bmobFile.uploadblock(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            imageNote.setSrc(bmobFile.getFileUrl());
                            bean.getData().get(i[0]).setData(gson.toJson(imageNote));
                            LogUtil.E(bmobFile.getFileUrl().toString());
                        }
                        i[0]++;
                        update(i, bean, note);
                    }

                    @Override
                    public void onProgress(Integer value) {
                        // 返回的上传进度（百分比）
                    }
                });
            } else {
                i[0]++;
                update(i, bean, note);
            }
        } else {
            i[0]++;
            update(i, bean, note);
        }
    }

    public void onresult(Intent data, GsonNoteBean bean) {
        if (data == null) {
            return;
        }
        ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
        for (int i = 0; images != null && i < images.size(); i++) {
            bean.getData().add(new NoteDetail(NoteDetail.IMAGE, gson.toJson(new ImageNote("file://" + images.get(i).path, images.get(i).width, images.get(i).height))));
        }
//        if (ed != null && bean.getData() != null && bean.getData().size() > 0) {
//            int i = (int) ed.getTag(R.id.position);
//            java.io.File file = new java.io.File(data.getDataString().substring("file://".length(), data.getDataString().length()));
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inJustDecodeBounds = true;
//            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath(), options);
//            bean.getData().add(i, new NoteDetail(NoteDetail.IMAGE, gson.toJson(new ImageNote(data.getDataString(), options.outWidth, options.outHeight))));
//        } else {
//            java.io.File file = new java.io.File(data.getDataString().substring("file://".length(), data.getDataString().length()));
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inJustDecodeBounds = true;
//            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath(), options);
//            bean.getData().add(new NoteDetail(NoteDetail.IMAGE, gson.toJson(new ImageNote(data.getDataString(), options.outWidth, options.outHeight))));
//        }
    }

    public void dealItemLongClick(Fragment fragment, EventBean msg, int index) {
        switch (index) {
            case 0:
                ArrayList<NoteDetail> data1 = (ArrayList<NoteDetail>) msg.msg;
                int position1 = (int) msg.msg2;
                ImageNote imageNote = GsonUtil.getInstance().fromJson(data1.get(position1).getData(), ImageNote.class);
                String url = imageNote.getLocalSrc().substring("file://".length(), imageNote.getLocalSrc().length());
                File file = new File(url);
                LogUtil.E(url + "------" + file.exists());
                IntentUtil.getInstance().shareImage(fragment, file.getPath());
                break;
            case 2:
                ArrayList<NoteDetail> data = (ArrayList<NoteDetail>) msg.msg;
                int position = (int) msg.msg2;
                data.remove(position);
                msg.onFinish();
                break;
        }
    }
}
