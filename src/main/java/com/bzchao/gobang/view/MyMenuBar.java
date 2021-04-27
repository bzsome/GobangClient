package com.bzchao.gobang.view;

import com.bzchao.gobang.connect.MyIPTool;
import com.bzchao.gobang.data.Algorithm;
import com.bzchao.gobang.game.CountDown;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 界面显示 之 菜单栏
 */
public class MyMenuBar extends JMenuBar {
    private static final long serialVersionUID = 1L;
    JMenuItem checkIP, setInfo, exit, about, version, setTime, setLevel, login,
            register, admin, word, helpOnline;

    public MyMenuBar() {
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
        checkIP = new JMenuItem("查看本机IP");
        exit = new JMenuItem("退出");
        about = new JMenuItem("开发说明");
        version = new JMenuItem("软件版本");
        setInfo = new JMenuItem("我的信息");
        setLevel = new JMenuItem("难度设置");
        setTime = new JMenuItem("倒计时设置");
        word = new JMenuItem("游戏说明");
        helpOnline = new JMenuItem("在线帮助");

        menu.add(checkIP);
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
        help.add(helpOnline);

        this.add(menu);
        this.add(my);
        this.add(setting);
        this.add(help);
        addListener();
    }

    public void addListener() {
        exit.addActionListener(event -> MainFrame.close());
        about.addActionListener(event -> {
            String function = "在线五子棋";
            String person = "\n软件编码：陈光超 1501511668\n智能算法：袁志强 1501511668\n软件测试：芶 钰 1501511668\n";
            String info = function + person + "\n开发时间：" + MainFrame.BUILD_TIME + "\n"
                    + MainFrame.COPYRIGHT;
            JOptionPane.showMessageDialog(null, info, "软件开发说明",
                    JOptionPane.INFORMATION_MESSAGE);
        });
        version.addActionListener(event -> {
            String info = " 软件版本：" + MainFrame.VERSION + "\n 开发日期 " + MainFrame.BUILD_TIME + "\n" + MainFrame.COPYRIGHT;
            JOptionPane.showMessageDialog(null, info, "软件开发说明",
                    JOptionPane.INFORMATION_MESSAGE);
        });
        checkIP.addActionListener(event -> {
            String localIP = "本机所有IP地址:";
            List<String> res = MyIPTool.getAllLocalHostIP();
            String allIp = res.stream().collect(Collectors.joining("\n"));
            localIP = localIP + "\n" + allIp;

            JOptionPane.showMessageDialog(MainFrame.mainFrame, localIP, "查看本机IP", JOptionPane.INFORMATION_MESSAGE);
        });
        setTime.addActionListener(event -> {
            String input = JOptionPane.showInputDialog("请输入超时时间(秒)", "30");
            try {
                int time = Integer.valueOf(input);
                CountDown.startTiming(time);
            } catch (Exception e) {
            }
        });
        setLevel.addActionListener(event -> {
            Object[] options = {"初级", "中级", "高级"};
            int m = JOptionPane
                    .showOptionDialog(MainFrame.mainFrame, "请选择AI智能程度", "请选择",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, options,
                            options[0]);
            if (m == 0) {
                Algorithm.LEVEL = Algorithm.LEVEL_Low;
                return;
            }
            if (m == 1) {
                JOptionPane.showMessageDialog(MainFrame.mainFrame, "算法设计,AI智能：袁志强", "重要提示..",
                        JOptionPane.INFORMATION_MESSAGE);
                Algorithm.LEVEL = Algorithm.LEVEL_Middle;
                return;
            }
            if (m == 2) {
                JOptionPane.showMessageDialog(MainFrame.mainFrame, "算法设计,AI智能：袁志强", "重要提示..",
                        JOptionPane.INFORMATION_MESSAGE);
                Algorithm.LEVEL = Algorithm.LEVEL_High;
                return;
            }
        });

        login.addActionListener(event -> {
            JOptionPane.showMessageDialog(MainFrame.mainFrame, "离线版不支持登录，如需登录保存账号积分，请下载在线版！", "提示",
                    JOptionPane.INFORMATION_MESSAGE);

        });
        register.addActionListener(event -> {
            JOptionPane.showMessageDialog(MainFrame.mainFrame, "离线版不支持注册，如需登录保存账号积分，请下载在线版！", "提示",
                    JOptionPane.INFORMATION_MESSAGE);

        });
        admin.addActionListener(event -> {
            JOptionPane.showMessageDialog(MainFrame.mainFrame, "离线版不支持用户管理，如需管理用户，请下载在线版！", "提示",
                    JOptionPane.INFORMATION_MESSAGE);
        });
        word.addActionListener(event -> JOptionPane
                .showMessageDialog(
                        MainFrame.mainFrame,
                        "游戏名称：在线五子棋\n主要功能：双人游戏,人机对战,联机对战\n提供登陆账号服务，可储存你的积分\n下次登陆则加载你的积分\n在线一分钟可获得一积分",
                        "游戏说明", JOptionPane.INFORMATION_MESSAGE));
        helpOnline.addActionListener(event -> {
            try {
                java.net.URI uri = new java.net.URI("http://www.bzchao.com/");
                Desktop.getDesktop().browse(uri);
            } catch (Exception e) {
            }
        });
    }
}
