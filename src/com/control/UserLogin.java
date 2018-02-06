package com.control;

import com.ShowUI.MyDialogx;
import com.ShowUI.UserPanel;
import com.Socket.MyDataBase;
import com.chao.Player;

public class UserLogin {

	private static String name;
	private static String pass;
	private static boolean isOnline = false;
	private static Thread addThread, onThread;

	public UserLogin(String mName, String mPass) {
		name = mName;
		pass = mPass;
		addOnline();
	}

	/** 增加积分，更改数据积分，本地数据积分，不更新UI */
	public static void addGrade(int add) {
		MyDataBase.addGrade(name, pass, add);
		int grade = MyDataBase.getGrade(name, pass);
		Player.my.setGrade(grade);
	}

	/** 向数据库发送标签，表示用户在线 */
	public static void isOnline() {
		onThread = new Thread() {
			public void run() {
				while (isOnline) {
					try {
						MyDataBase.setTip(name, pass);
						Thread.sleep(3000);
					} catch (Exception e) {
					}
				}
			};
		};
		onThread.start();
	}

	/** 在线获得1分钟获得1积分 */
	public static void addOnline() {
		System.out.println("ADDONLINE");
		addThread = new Thread() {// 用于检测游戏是否结束
			public void run() {
				try {
					// 等待上一个线程结束
					if (isOnline) {
						isOnline = false;
						Thread.sleep(61000);
					}
					isOnline = true;
					isOnline();
				} catch (InterruptedException e1) {
				}
				while (isOnline) {
					try {
						MyDataBase.addGrade(name, pass, 1);
						updatePanelGrade();
						Thread.sleep(60000);
					} catch (Exception e) {
					}
				}
				MyDialogx.monitor();
				System.out.println("在线监控线程 已终止！");
			};
		};
		addThread.start();
	}

	/** 更新玩家面板玩家积分 */
	public static void updatePanelGrade() {
		int grade = MyDataBase.getGrade(name, pass);
		UserPanel.setGrade(grade, UserPanel.left);
	}

	public static String getName() {
		return name;
	}

	public static String getPass() {
		return pass;
	}

	public static boolean getOnline() {
		return isOnline;
	}
}
