package com.control;

import com.ShowUI.StatePanel;

/** µ¹¼ÆÊ± */
public class Countown {
	public static int time = 30;
	public static Thread thread;

	public Countown(int mTime) {
		// TODO Auto-generated constructor stub
		time = mTime;
	}

	public Countown() {
		// TODO Auto-generated constructor stub
	}

	public static void startTiming(int mTime) {
		time = mTime;
		thread = new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				while (time >= 0) {
					try {
						StatePanel.setTime(time--);
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
				}
			}
		};
		thread.start();
	}
}
