package com.control;

import com.chao.*;
import com.ShowUI.*;

/**
 * 人机对战
 * 
 * @author chaos
 * 
 */
public class GameRobot {

	public GameRobot() {
		GameCenter.reStart();
		GameCenter.setMode(GameCenter.MODE_ROBOT);
		Player.my.start();
		final PlayerAI playerAI = new PlayerAI();
		playerAI.start();
		playerAI.setGrade(Player.pe.getGrade());

		// 用户面板，更新用户信息
		UserPanel.setUserInfo(Player.my, UserPanel.left);
		UserPanel.setUserInfo(playerAI, UserPanel.right);

		// 产生一个线程，用于下棋
		new Thread() {
			public void run() {
				int color = playerAI.getColor();
				while (!GameCenter.isEnd()) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						System.out.println("robotThread 睡眠异常！");
					}
					if (color == TableData.getTheColor()) {
						Spot spot = playerAI.bestChess(color);
						ChessBroad.submitPaint(spot);
					}
				}
				System.out.println("电脑下棋线程已停止");
			};
		}.start();
	}
}
