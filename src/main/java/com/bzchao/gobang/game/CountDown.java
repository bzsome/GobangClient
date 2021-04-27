package com.bzchao.gobang.game;

import com.bzchao.gobang.view.StatePanel;

/**
 * 倒计时
 */
public class CountDown {
    public static int time = 30;
    public static Thread thread;

    public static void startTiming(int mTime) {
        time = mTime;
        thread = new Thread(() -> {
            while (time >= 0) {
                try {
                    StatePanel.setTime(time--);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }
        });
        thread.start();
    }
}
