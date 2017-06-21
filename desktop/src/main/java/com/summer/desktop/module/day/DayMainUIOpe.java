package com.summer.desktop.module.day;

//by summer on 2017-06-13.

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.summer.desktop.R;
import com.summer.desktop.bean.dabean.Note;
import com.summer.desktop.bean.dabean.TimeBean;
import com.summer.desktop.databinding.FragDayMainBinding;
import com.summer.desktop.module.home.main.HomeActivity;
import com.summer.desktop.module.note.noteslist.NoteViewPagerFrag;
import com.summer.lib.base.ope.BaseUIBean;
import com.summer.lib.util.FragmentUtil;

import java.util.ArrayList;

public class DayMainUIOpe extends BaseUIBean<FragDayMainBinding> {


    public DayMainUIOpe(Context context) {
        super(context);
    }


    public void initList(DayView.OnlongClickWithHM listener) {
//        dayMainAdapter = new DayMainAdapter(context);
//        getUiBean().initRecylce(dayMainAdapter);
//        dayMainAdapter.setOnLongClickListener(listener);
        viewDataBinding.dayview.setLongClickListener(listener);

    }

    public void addTimes(ArrayList<TimeBean> times) {
//        dayMainAdapter.setTimes(times);
//        dayMainAdapter.notifyDataSetChanged();
        viewDataBinding.dayview.setTimes(times);
    }

    public void deleteTime(int h, int m) {
        viewDataBinding.dayview.delete(h, m);
    }

    public void goToNote(FragmentActivity activity, String parentid) {

        NoteViewPagerFrag noteListssFrag = new NoteViewPagerFrag();
        Bundle bundle = new Bundle();
        Note note = new Note(Note.NOTEBOOK, parentid);
        ArrayList<Note> notes = new ArrayList<>();
        notes.add(note);
        note.setObjectId(parentid);
        bundle.putBoolean("first", true);
        bundle.putSerializable("data", notes);
        noteListssFrag.setArguments(bundle);
        FragmentUtil.getInstance().add(activity, R.id.root_note, noteListssFrag);
        if (activity instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) activity;
            homeActivity.getOpes().getUi().viewDataBinding.homeViewpager.setCurrentItem(2);
        }
    }
}
