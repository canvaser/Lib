package com.summer.lib.appthread;


import com.summer.lib.base.interf.OnFinishWithObjI;

/**
 * Created by ${viwmox} on 2016-07-22.
 */
public class AppThread extends Thread {

    private static AppThread instance;
    private static OnFinishWithObjI o;
    public boolean stop = true;
    public boolean pause = true;
    public long sleepTime = 20000;
    int count = 0;

    private AppThread() {

    }

    public static AppThread getInstance(OnFinishWithObjI o) {
        AppThread.o = o;
        if (instance == null) {
            instance = new AppThread();
        }
        return instance;
    }


    public AppThread init() {
        stop = false;
        pause = false;
        count = 0;
        return instance;
    }

    @Override
    public void run() {
        super.run();
        while (!stop) {
            if (!pause) {
                doThing(count);
                try {
                    Thread.sleep(sleepTime);
                    count++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void doThing(int count) {
        if (o != null) {
            o.onNetFinish(count);
        }
    }
}
