package com.ShowUI;

import com.Socket.Record;
import com.chao.*;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

/**
 * 主界面UI 版本0.3 2017.06.23
 * 
 * @author chaos
 */
public class ShowUI extends JFrame {

	private static final long serialVersionUID = 1L;

	public static final String VER = "1.1.2  正式版";
	public static final String TIME = "2017.06.26.23(正式版)";
	public static final String COPR = "自主设计开发  拥有本软件所有版权";
	public static final String UPDA = "在线对战禁用智能AI。";
	// 窗体大小
	int width = 730;
	int hight = 760;

	// 三个主面板
	public static ShowUI showUI;

	public ShowUI() {
		showUI = this;
		UIManager.put("Label.font", new Font("宋体", Font.BOLD, 15));
		UIManager.put("Button.font", new java.awt.Font("宋体", 0, 20));

		// TODO Auto-generated constructor stub
		this.setTitle(" 在线五子棋  " + TIME);
		this.setSize(width, hight);
		this.setResizable(false);
		this.setLayout(null);

		int sWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int sHight = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation((sWidth - width) / 2, (sHight - hight) / 2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		try {
			java.net.URL imgURL = getClass().getResource("/raw/goBang.png");
			ImageIcon imgIcon = new ImageIcon(imgURL);
			this.setIconImage(imgIcon.getImage());
		} catch (Exception e) {
			System.out.println("图标设置失败！");
		}

		addWidget();
	}

	/**
	 * 添加控件和菜单栏
	 */
	private void addWidget() {

		int x = 5;
		int y = 2;
		int mWidth = width / 4 * 3;
		int mHight = width / 5;

		this.setJMenuBar(new MyJMenuBar());

		getContentPane().add(new UserPanel());
		UserPanel.my.setBounds(x, y, mWidth, mHight);

		getContentPane().add(new ChessBroad());
		ChessBroad.my.setBounds(x, mHight + 2 * y, mWidth, hight - mHight - 2
				* y);

		getContentPane().add(new StatePanel());
		StatePanel.my.setBounds(mWidth + 2 * x, y, width - mWidth - 2 * x,
				mHight);

		getContentPane().add(new Chatroom());
		Chatroom.my.setBounds(mWidth + 2 * x, mHight + 2 * y, width - mWidth
				- 2 * x, (hight - mHight - 2) / 2 * y);

		getContentPane().add(new ConPanel());
		ConPanel.my.setBounds(mWidth + 2 * x, 340, width - mWidth - 2 * x,
				hight - mHight - 2 * y);
	}

	// 界面显示，控件加载完毕后执行(向控件加载数据等)
	public static void init() {
		GameCenter.reStart();
		ChessBroad.init();
		UserPanel.init();
		ConPanel.init();
		Player.init();
		showUI.repaint();
		new Record();
	}

	// 窗口关闭事件
	@Override
	protected void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			close();
			return; // 直接返回，阻止默认动作，阻止窗口关闭
		}
		super.processWindowEvent(e); // 该语句会执行窗口事件的默认动作(如：隐藏)
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		ConPanel.init();
	}

	public static void close() {
		int i = JOptionPane.showConfirmDialog(null, "确定要退出系统吗？", "正在退出五子棋...",
				JOptionPane.YES_NO_OPTION);
		if (i == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}
}