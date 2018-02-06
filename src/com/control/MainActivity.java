package com.control;

import com.ShowUI.ShowUI;

/**
 * 主类，主函数类
 * 
 * @author chaos
 * 
 */
public class MainActivity {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ShowUI mainUI = new ShowUI();
		mainUI.setVisible(true);
		// 界面加载完后，加载数据
		ShowUI.init();
	}
}
