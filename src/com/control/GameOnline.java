package com.control;

import com.ShowUI.MyDialogx;
import com.ShowUI.UserPanel;
import com.Socket.MySocket;
import com.chao.*;

/**
 * 在线对战类
 * 
 * @author chaos
 * 
 */
public class GameOnline {

	public GameOnline() {
		GameCenter.reStart();
		GameCenter.setMode(GameCenter.MODE_ONLINE);
	}

	/** 启动监控线程(监控对方是否下线 ) */
	public static void monitor() {
		new Thread() {// 用于检测游戏是否结束
			public void run() {
				while (MySocket.isStart) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
				}
				MyDialogx.monitor();
				System.out.println("在线监控线程 已终止！");
			};
		}.start();
	}

	/** 相互发送接收玩家信息 */
	public static void getPeAddress() {
		new Thread() {// 用于检测游戏是否结束
			public void run() {
				try {
					Thread.sleep(1000);
					if (MySocket.isStart) {
						Player.pe.setAddress(MySocket.peAddress);
						UserPanel.setUserInfo(Player.pe, UserPanel.right);
					}
				} catch (InterruptedException e) {
				}

			};
		}.start();
	}

}
