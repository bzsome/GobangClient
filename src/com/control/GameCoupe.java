package com.control;

import com.ShowUI.UserPanel;
import com.chao.*;

/**
 * 双人对战
 * 
 * @author chaos
 * 
 */
public class GameCoupe {

	public GameCoupe() {
		GameCenter.reStart();
		GameCenter.setMode(GameCenter.MODE_COUPE);
		Player.my.start();
		Player.pe.start();

		// 用户面板，更新用户信息
		UserPanel.setUserInfo(Player.my, UserPanel.left);
		UserPanel.setUserInfo(Player.pe, UserPanel.right);
	}

}
