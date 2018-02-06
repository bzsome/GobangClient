package com.ShowUI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import com.Socket.*;
import com.chao.*;
import com.control.Countown;
import com.control.UserLogin;

/**
 * 界面显示 之 菜单栏
 */
public class MyJMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	JMenuItem chechIP, setInfo, exit, about, version, setTime, setLevel, login,
			register, admin, word;

	public MyJMenuBar() {
		UIManager.put("Menu.font", new Font("宋体", Font.BOLD, 22));
		UIManager.put("MenuItem.font", new Font("宋体", Font.BOLD, 22));

		JMenu menu = new JMenu("菜单(F)");
		menu.setMnemonic('f'); // 助记符

		JMenu my = new JMenu("登录(M)");
		my.setMnemonic('m'); // 助记符

		JMenu setting = new JMenu("设置(S)");
		setting.setMnemonic('s'); // 助记符

		JMenu help = new JMenu("帮助(H)");
		help.setMnemonic('h'); // 助记符

		login = new JMenuItem("登录账户");
		register = new JMenuItem("注册账户");
		admin = new JMenuItem("管理员后台");
		chechIP = new JMenuItem("查看本机IP");
		exit = new JMenuItem("退出");
		about = new JMenuItem("开发说明");
		version = new JMenuItem("软件版本");
		setInfo = new JMenuItem("我的信息");
		setLevel = new JMenuItem("难度设置");
		setTime = new JMenuItem("倒计时设置");
		word = new JMenuItem("游戏说明");
		menu.add(chechIP);
		menu.add(exit);
		my.add(login);
		my.add(register);
		my.add(admin);
		setting.add(setInfo);
		setting.add(setLevel);
		setting.add(setTime);
		help.add(about);
		help.add(version);
		help.add(word);

		this.add(menu);
		this.add(my);
		this.add(setting);
		this.add(help);
		addListener();
	}

	public void addListener() {
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ShowUI.close();
			}
		});
		about.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String function = "在线五子棋";
				String person = "\n软件编码：陈光超 1501511668\n智能算法：袁志强 1501511668\n软件测试：芶 钰 1501511668\n";
				String info = function + person + "\n" + ShowUI.TIME + "\n"
						+ ShowUI.COPR;
				JOptionPane.showMessageDialog(null, info, "软件开发说明",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		version.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String info = " 软件版本" + ShowUI.VER + "\n 开发日期 " + ShowUI.TIME
						+ "\n" + ShowUI.COPR + "\n" + ShowUI.UPDA;
				JOptionPane.showMessageDialog(null, info, "软件开发说明",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		chechIP.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String localIP = "本机所有IP地址:";
				ArrayList<String> res = new ArrayList<String>();
				res = MyIPTool.getAllLocalHostIP();
				for (String ip : res) {
					localIP += "\n" + ip;
				}
				JOptionPane.showMessageDialog(ShowUI.showUI, localIP, "查看本机IP",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		setTime.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String input = JOptionPane.showInputDialog("请输入超时时间(秒)", "30");
				try {
					int time = Integer.valueOf(input);
					Countown.startTiming(time);
				} catch (Exception e) {
				}
			}
		});
		setLevel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Object[] options = { "初级", "中级", "高级" };
				int m = JOptionPane
						.showOptionDialog(ShowUI.showUI, "请选择AI智能程度", "请选择",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options,
								options[0]);
				if (m == 0) {
					Algorithm.LEVEL = Algorithm.LEVEL_Low;
					return;
				}
				if (m == 1) {
					JOptionPane.showMessageDialog(ShowUI.showUI,
							"算法设计,AI智能：袁志强", "重要提示..",
							JOptionPane.INFORMATION_MESSAGE);
					Algorithm.LEVEL = Algorithm.LEVEL_Middle;
					return;
				}
				if (m == 2) {
					JOptionPane.showMessageDialog(ShowUI.showUI,
							"算法设计,AI智能：袁志强", "重要提示..",
							JOptionPane.INFORMATION_MESSAGE);
					Algorithm.LEVEL = Algorithm.LEVEL_Hight;
					return;
				}
			}
		});

		login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String name = JOptionPane.showInputDialog("正在登陆，请输入用户名", "");
				if (name == null) {
					return;
				}
				String pass = JOptionPane.showInputDialog("正在登陆，请输入密码", "");
				if (pass == null) {
					return;
				}

				new MyDataBase();
				int grade = MyDataBase.getGrade(name, pass);
				if (grade < 0) {
					JOptionPane.showMessageDialog(ShowUI.showUI, "登录失败！",
							"登录失败", JOptionPane.INFORMATION_MESSAGE);
				} else {
					System.out.println("cccc");
					Player.my.setName(name);
					Player.my.setGrade(grade);
					UserPanel.setUserInfo(Player.my, UserPanel.left);
					JOptionPane.showMessageDialog(ShowUI.showUI, name
							+ "欢迎回来！当前分数：" + grade + "\n在线一分钟可获得1积分！", "登录成功",
							JOptionPane.INFORMATION_MESSAGE);
					// 启动用户在线状态监控等线程
					new UserLogin(name, pass);
				}
			}
		});
		register.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String name = JOptionPane.showInputDialog("正在注册，请输入用户名", "");
				if (name == null) {
					return;
				}
				String pass = JOptionPane.showInputDialog("正在注册，请输入密码", "");
				if (pass == null) {
					return;
				}
				new MyDataBase();
				if (MyDataBase.setReg(name, pass)) {
					JOptionPane
							.showMessageDialog(ShowUI.showUI, "注册成功！",
									"注册成功，服务器已储存你的信息！",
									JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(ShowUI.showUI,
							"注册失败！可能是已注册！", "注册失败",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		admin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					java.net.URI uri = new java.net.URI(
							"http://gobang.bzchao.win/");
					java.awt.Desktop.getDesktop().browse(uri);
				} catch (Exception e) {
				}
			}
		});
		word.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane
						.showMessageDialog(
								ShowUI.showUI,
								"游戏名称：在线五子棋\n主要功能：双人游戏,人机对战,在线对战\n提供登陆账号服务，可储存你的积分\n下次登陆则加载你的积分\n在线一分钟可获得一积分",
								"游戏说明", JOptionPane.INFORMATION_MESSAGE);
			}

		});
	}
}
