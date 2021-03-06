package com.summer.desktop.module.note.notelist;

//by summer on 2017-06-06.

import android.content.Context;

import com.summer.desktop.bean.dabean.GsonNoteBean;
import com.summer.desktop.bean.dabean.Note;
import com.summer.desktop.bean.dabean.TimeBean;
import com.summer.lib.base.interf.OnFinishListener;
import com.summer.lib.base.ope.BaseDAOpe;
import com.summer.lib.util.GsonUtil;
import com.summer.lib.util.LogUtil;
import com.summer.lib.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DeleteBatchListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class NoteListDAOpe extends BaseDAOpe {


    int i = -1;

    public NoteListDAOpe(Context context) {
        super(context);
    }

    public void createNote(String parentid, final OnFinishListener onFinishListener) {
        Note note = new Note(Note.NOTE, "新建笔记" + RandomUtil.getInstance().nextFloat() * 100);
        GsonNoteBean gsonNoteBean = new GsonNoteBean();
        gsonNoteBean.setType(GsonNoteBean.TYPE_NOTE);
        note.setData(GsonUtil.getInstance().toJson(gsonNoteBean));
        note.setParentId(parentid);
        note.save(new SaveListener<String>() {

            @Override
            public void done(String objectId, BmobException e) {
                onFinishListener.onFinish(objectId);
            }
        });
    }

    public void createGallery(String parentid, final OnFinishListener onFinishListener) {
        Note note = new Note(Note.NOTE, "相册集" + RandomUtil.getInstance().nextFloat() * 100);
        GsonNoteBean gsonNoteBean = new GsonNoteBean();
        gsonNoteBean.setType(GsonNoteBean.TYPE_GALLERY);
        note.setData(GsonUtil.getInstance().toJson(gsonNoteBean));
        note.setParentId(parentid);
        note.save(new SaveListener<String>() {

            @Override
            public void done(String objectId, BmobException e) {
                onFinishListener.onFinish(objectId);
            }
        });
    }

    public void createTodayNote(final TimeBean timeBean, final String parentid, String[] name, final String finalname, final OnFinishListener onFinishListener) {
        i = -1;
        todayNoteBookExist(parentid, name, new OnFinishListener() {
            @Override
            public void onFinish(final Object o) {
                Note note = new Note(Note.NOTE, finalname);
                GsonNoteBean gsonNoteBean = new GsonNoteBean();
                gsonNoteBean.setType(GsonNoteBean.TYPE_NOTE_DAY);
                gsonNoteBean.setTimeDetail(GsonUtil.getInstance().toJson(timeBean));
                note.setData(GsonUtil.getInstance().toJson(gsonNoteBean));
                note.setParentId((String) o);
                note.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId, BmobException e) {
                        onFinishListener.onFinish((String) o);
                    }
                });
            }
        });
    }


    public void getTodayNotes(final String parentid, String[] name, final OnFinishListener onFinishListener) {
        i = -1;
        todayNoteBookExist(parentid, name, new OnFinishListener() {
            @Override
            public void onFinish(final Object o) {
                BmobQuery<Note> query = new BmobQuery<Note>();
                query.addWhereEqualTo("parentId", (String) o);
                query.findObjects(new FindListener<Note>() {
                    @Override
                    public void done(List<Note> list, BmobException e) {
                        Object[] objects = new Object[]{o, list};
                        onFinishListener.onFinish(objects);
                    }
                });
            }
        });
    }

    public void todayNoteBookExist(final String parentid, final String[] name, final OnFinishListener onFinishListener) {
        i++;
        if (name.length <= i) {
            onFinishListener.onFinish(parentid);
            return;
        }
        BmobQuery<Note> query = new BmobQuery<>();
        query.addWhereEqualTo("parentId", parentid);
        query.addWhereEqualTo("name", name[i]);
        query.findObjects(new FindListener<Note>() {
            @Override
            public void done(List<Note> list, BmobException e) {
                if (e != null) {
                    onFinishListener.onFinish(null);
                    return;
                }
                if (list == null) {
                    list = new ArrayList<Note>();
                }
                if (list.size() == 0) {
                    createNoteBookWithName(parentid, name[i], new OnFinishListener() {
                        @Override
                        public void onFinish(Object o) {
                            todayNoteBookExist((String) o, name, onFinishListener);
                        }
                    });
                } else {
                    todayNoteBookExist(list.get(0).getObjectId(), name, onFinishListener);
                }
            }
        });
    }


    public void createNoteBook(String parentid, final OnFinishListener onFinishListener) {
        Note note = new Note(Note.NOTEBOOK, "新建笔记本" + RandomUtil.getInstance().nextFloat() * 100);
        note.setParentId(parentid);
        note.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                onFinishListener.onFinish(objectId);
            }
        });
    }

    public void createNoteBookWithName(String parentid, String name, final OnFinishListener onFinishListener) {
        Note note = new Note(Note.NOTEBOOK, name);
        note.setParentId(parentid);
        note.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                onFinishListener.onFinish(objectId);
            }
        });
    }

    public void deleteNote(Note n, final OnFinishListener onFinishListener) {
        if (n == null) {
            return;
        }
        Note note = new Note();
        note.setObjectId(n.getObjectId());
        note.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                onFinishListener.onFinish(e);
            }
        });
    }


    public void deleteFile(String[] urls) {
        //此url必须是上传文件成功之后通过bmobFile.getUrl()方法获取的。
        BmobFile.deleteBatch(urls, new DeleteBatchListener() {

            @Override
            public void done(String[] failUrls, BmobException e) {
                if (e == null) {
                    LogUtil.E("全部删除成功");
                } else {
                    if (failUrls != null) {
                        LogUtil.E(("删除失败个数：" + failUrls.length + "," + e.toString()));
                    } else {
                        LogUtil.E(("全部文件删除失败：" + e.getErrorCode() + "," + e.toString()));
                    }
                }
            }
        });
    }


    public void renameNote(String s, Note n, final OnFinishListener onFinishListener) {
        if (s.equals("")) {
            return;
        }
        if (n == null) {
            return;
        }
        Note note = new Note();
        note.setObjectId(n.getObjectId());
        note.setName(s);
        note.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                onFinishListener.onFinish(e);
            }
        });
    }
}
