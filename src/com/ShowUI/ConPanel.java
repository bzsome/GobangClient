package com.ShowUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import com.Socket.*;
import com.chao.*;
import com.control.*;

/**
 * 主界面的控制按钮面板
 */
public class ConPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	public static ConPanel my;
	private JButton btShowChess, btAgain, btChessAI, btTest, btCoupe, btOnline,
			btRobot;
	int i = 0;

	public ConPanel() {
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.setBackground(new Color(220, 220, 220, 220));
		btAgain = new JButton("重新游戏");
		btCoupe = new JButton("双人对战");
		btRobot = new JButton("人机对战");
		btOnline = new JButton("在线对战");
		btChessAI = new JButton("智能AI下棋");
		btShowChess = new JButton("显示所有棋子");
		btTest = new JButton("备用测试按钮");

		this.add(btAgain);
		this.add(btCoupe);
		this.add(btRobot);
		this.add(btOnline);
		this.add(btChessAI);
		this.add(btShowChess);
		this.add(btTest);

		my = this;
		addListener();
	}

	public static void init() {
		my.repaint();
	}

	/**
	 * 添加监听事件
	 */
	private void addListener() {

		btShowChess.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				GameCenter.showChess();
			}
		});

		btAgain.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				GameCenter.reStart();
				ChessBroad.my.repaint();
				MySocket.close();
				btCoupe.setEnabled(true);
				btRobot.setEnabled(true);
				btOnline.setEnabled(true);
				btChessAI.setEnabled(true);
				try {
					MySocket.socket.close();
				} catch (Exception e) {
				}
			}
		});

		btChessAI.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (GameCenter.isEnd()) {
					JOptionPane.showMessageDialog(null, "游戏未开始，无法使用AI下棋",
							"提示信息", JOptionPane.CANCEL_OPTION);
					return;
				}
				new AutoChess();
			}
		});

		btTest.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ChessBroad.my.repaint();
				System.out.println("----------黑棋权值-------");
				GameCenter.showWeight(Spot.blackChess);

				System.out.println("----------白棋权值-------");
				GameCenter.showWeight(Spot.whiteChess);
			}
		});

		btCoupe.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new GameCoupe();
				btCoupe.setEnabled(false);
				btRobot.setEnabled(false);
				btOnline.setEnabled(false);
			}
		});

		btRobot.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new GameRobot();
				btCoupe.setEnabled(false);
				btRobot.setEnabled(false);
				btOnline.setEnabled(false);
			}
		});

		btOnline.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				// new 一定要在前面，否则数据被重置！
				new GameOnline();
				MyDialogx.online();

				btCoupe.setEnabled(false);
				btRobot.setEnabled(false);
				btOnline.setEnabled(false);
				btChessAI.setEnabled(false);
			}
		});
	}
}
