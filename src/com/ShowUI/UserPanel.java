package com.ShowUI;

import java.awt.*;

import javax.swing.*;
import com.chao.Player;

/**
 * 界面显示 之 玩家信息面板，显示玩家昵称，地址等信息
 */
public class UserPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	static JPanel myPanel, pePanel;
	private static JLabel myName, myAddress, myColor, myGrade;
	private static JLabel peName, peAddress, peColor, peGrade;
	static UserPanel my;
	/** 用户信息面板，左面版(我的信息) */
	public static final int left = 0;
	/** 用户信息面板，右面版(对方信息) */
	public static final int right = 1;

	public UserPanel() {
		this.setVisible(true);
		this.setLayout(null);
		this.setBackground(new Color(180, 180, 180));

		myPanel = new JPanel();
		pePanel = new JPanel();
		myPanel.setLayout(new GridLayout(0, 1));
		pePanel.setLayout(new GridLayout(0, 1));

		myName = new JLabel("玩家昵称: ");
		myAddress = new JLabel("玩家地址: ");
		myColor = new JLabel("玩家棋色: ");
		myGrade = new JLabel("玩家分数: ");

		peName = new JLabel("玩家昵称: ");
		peAddress = new JLabel("玩家地址: ");
		peColor = new JLabel("玩家棋色: ");
		peGrade = new JLabel("玩家分数: ");

		myPanel.add(myName);
		myPanel.add(myAddress);
		myPanel.add(myColor);
		myPanel.add(myGrade);

		pePanel.add(peName);
		pePanel.add(peAddress);
		pePanel.add(peColor);
		pePanel.add(peGrade);

		this.add(myPanel);
		this.add(pePanel);
		my = this;
	}

	/**
	 * 界面显示，控件加载完毕后执行
	 */
	public static void init() {

		myPanel.setBounds(2, 2, my.getWidth() / 2 - 2, my.getHeight() - 5);
		pePanel.setBounds(my.getWidth() / 2 + 2, 2, my.getWidth() / 2 - 2,
				my.getHeight() - 5);

		setUserInfo(null, left);
		setUserInfo(null, right);
		my.repaint();
	}

	/**
	 * 在用户信息版面，显示用户信息
	 * 
	 * @param player
	 * @param position
	 */
	public static void setUserInfo(Player player, int position) {
		if (player == null) {
			player = new Player();
		}
		if (position == left) {
			myName.setText("玩家昵称: " + player.getName());
			myAddress.setText("玩家地址: " + player.getAddress());
			myColor.setText("玩家棋色: " + player.getColorString());
			myGrade.setText("玩家分数: " + player.getGrade());
		} else {
			peName.setText("玩家昵称: " + player.getName());
			peAddress.setText("玩家地址: " + player.getAddress());
			peColor.setText("玩家棋色: " + player.getColorString());
			peGrade.setText("玩家分数: " + player.getGrade());
		}
	}

	/**
	 * 设置信息
	 */
	public static void setGrade(int grade, int position) {
		if (position == left) {
			myGrade.setText("玩家分数: " + grade);
		} else {
			peGrade.setText("玩家分数: " + grade);
		}
	}
}
