package com.bzchao.gobang.view;

import com.bzchao.gobang.connect.DataSocket;
import com.bzchao.gobang.connect.MyIPTool;
import com.bzchao.gobang.connect.MySocket;
import com.bzchao.gobang.data.Player;
import com.bzchao.gobang.data.Spot;
import com.bzchao.gobang.game.GameOnline;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义对话框
 */
public class MyDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    JButton button;
    JLabel jLabel, upLabel;
    JPanel p1, p2, p3;
    JFrame jFrame;
    private static MyDialog my;
    // 储存玩家上次输入的IP地址
    private static String address = "192.168.118.135";

    MyDialog(JFrame jFrame, String title) {
        super(jFrame, title);
        // 关闭上一个自定义窗口
        try {
            my.dispose();
        } catch (Exception e) {
        }
        my = this;
        this.jFrame = jFrame;

        jFrame.setEnabled(false);
        p1 = new JPanel();
        p2 = new JPanel();
        p3 = new JPanel();
        upLabel = new JLabel();// 提示内容，静态文本，默认不显示
        upLabel.setVisible(false);
        jLabel = new JLabel("自定义窗口,未设置内容....");// 提示内容，动态文本
        jLabel.setFont(new Font("宋体", Font.BOLD, 16));
        upLabel.setFont(new Font("宋体", Font.BOLD, 16));
        button = new JButton("取消");
        p1.add(upLabel);
        p2.add(jLabel);
        p3.add(button);

        this.setSize(300, 300);
        this.add(BorderLayout.NORTH, p1);
        this.add("Center", p2);
        this.add("South", p3);
        int sWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int sHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setLocation((sWidth - WIDTH) / 2, (sHeight - HEIGHT) / 2);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // this.pack();
        this.setVisible(true);
        addListener();
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            jFrame.setEnabled(true);
            dispose();
        }
    }

    public void setUpText(String str) {
        upLabel.setVisible(!"".equals(str));
        upLabel.setText("<html>" + str + "<html>");
    }

    public void setText(String str) {
        jLabel.setText("<html>" + str + "<html>");
    }

    public void setBtText(String str) {
        button.setText(str);
    }

    private void addListener() {
        button.addActionListener(event -> {
            jFrame.setEnabled(true);
            dispose();
        });
    }

    /**
     * 对方下线窗口
     */
    public static void monitor() {
        final MyDialog myDialog = new MyDialog(MainFrame.mainFrame, "连接断开");
        myDialog.setVisible(true);
        myDialog.setText("连接已经断开<br>我方或对方已下线，请重新游戏!");
        myDialog.setBtText("确定");
    }

    /**
     * 选择联机对战方式窗体
     */
    public static void online() {
        Object[] options = {"创建房间", "加入房间"};
        int m = JOptionPane.showOptionDialog(null, "请选择联机对战方式", "联机对战",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                options, options[0]);
        if (m == 0) {
            createRoom();
        }

        if (m == 1) {
            joinRoom();
        }
    }

    /**
     * 创建房间窗体
     */
    public static void createRoom() {
        // 加入房间则我第一个出棋
        Player.myPlayer = new Player();
        Player.myPlayer.setName("房主(我方)");
        Player.myPlayer.start(Spot.blackChess);
        UserPanel.setUserInfo(Player.myPlayer, UserPanel.left);

        Player.otherPlayer = new Player();
        Player.otherPlayer.setName("玩家1");
        Player.otherPlayer.start(Spot.whiteChess);
        UserPanel.setUserInfo(Player.otherPlayer, UserPanel.right);

        final MyDialog myDialog = new MyDialog(MainFrame.mainFrame, "创建房间");

        final Thread thread = new Thread(() -> {
            try {
                System.out.println("正在创建房间，等待玩家...");
                MySocket.startServer();
                // 启动监控线程(监控对方是否下线 )
                GameOnline.monitor();
                GameOnline.getOtherAddress();
                // DataSocket.send(Player.my);
                DataSocket.send("Hello,欢迎你加入房间");
            } catch (Exception e) {
            }
        });
        thread.start();

        List<String> res = MyIPTool.getAllLocalHostIP();
        String allIpAddress = res.stream().collect(Collectors.joining("<br/>\t"));

        final String createRoomMsg = "其他玩家输入以下IP加入房间<br/>" + allIpAddress;

        Thread thread2 = new Thread(() -> {
            while (true) {
                if (!thread.isAlive()) {
                    // 创建房间线程server已停止
                    if (MySocket.isStart) {
                        // 创建房间线程正常终止，即对方加入房间成功
                        myDialog.setText("玩家已加入房间！开始游戏。");
                        myDialog.setBtText("确定，开始游戏");
                    } else {
                        // 加入房间线程异常终止，即加入房间失败！
                        myDialog.setText("创建房间失败！可能是端口被占用！");
                        myDialog.setBtText("确定");
                    }
                    return;
                }

                // 创建房间窗体未关闭
                if (!myDialog.isDisplayable()) {
                    // 执行到这里，则说明，线程1未终止，窗口却关闭了，
                    // 说明取消房间创建,则需要终止server
                    try {
                        System.out.print("等待窗口被手动关闭，强行终止accept！");
                        MySocket.server.close();
                        System.out.println(" 终止成功！");
                        // 等待thread线程结束，用于后面判断线程状态！
                        thread.join(100);
                        break;
                    } catch (Exception e2) {
                    }
                } else {// 窗口未关闭
                    try {
                        myDialog.setUpText(createRoomMsg);
                        myDialog.setBtText("取消等待!");
                        myDialog.setText("正在等待玩家加入.");
                        Thread.sleep(1000);
                        myDialog.setText("正在等待玩家加入..");
                        Thread.sleep(1000);
                        myDialog.setText("正在等待玩家加入...");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }
            }

            System.out.println("createRoom() 创建房间线程线程状态:" + thread.getState());
        });
        thread2.start();
    }

    /**
     * 加入房间窗体
     */
    public static void joinRoom() {
        // 加入房间则我第二个出棋
        Player.otherPlayer = new Player();
        Player.otherPlayer.setName("房主");
        Player.otherPlayer.start(Spot.blackChess);
        UserPanel.setUserInfo(Player.otherPlayer, UserPanel.right);

        Player.myPlayer = new Player();
        Player.myPlayer.setName("玩家1(我方)");
        Player.myPlayer.start(Spot.whiteChess);
        UserPanel.setUserInfo(Player.myPlayer, UserPanel.left);

        address = JOptionPane.showInputDialog("请输入房主IP地址", address);
        if (address == null) {
            return;
        }
        final MyDialog myDialog = new MyDialog(MainFrame.mainFrame, "加入房间");
        myDialog.setBtText("取消加入");
        myDialog.setText("正在加入房间，请等待..");

        // 加入房间线程
        final Thread thread = new Thread(() -> {
            try {
                System.out.println("正在加入房间，等待加入连接...");
                MySocket.getSocket(address);
                // 加入房间成功，启动监控线程(监控对方是否下线 )
                GameOnline.monitor();
                GameOnline.getOtherAddress();
                // DataSocket.send(Player.my);
                DataSocket.send("HI,我已加入你的房间");
            } catch (Exception e1) {
            }
        });
        thread.start();

        // 加入房间窗体线程
        Thread thread2 = new Thread(() -> {
            while (true) {
                if (!thread.isAlive()) {
                    // 加入房间线程getSocket已停止
                    if (MySocket.isStart) {
                        // 加入房间线程正常终止，即加入房间成功
                        myDialog.setText("房间加入成功！开始游戏。");
                        myDialog.setBtText("确定，开始游戏");
                    } else {
                        // 加入房间线程异常终止，即加入房间失败！
                        myDialog.setText("加入房间超时!!<br>可能是IP输入错误，请重新输入！");
                        myDialog.setBtText("确定");
                    }
                    return;
                }

                // 加入房间线程未停止
                if (!myDialog.isDisplayable()) {// 窗体被关闭
                    // 执行到这里，则说明，线程1未终止，窗口却关闭了，
                    // 即窗口被手动关闭，加入房间,则需要终止socket
                    try {
                        System.out.print("等待窗口被手动关闭，强行终止socket！");
                        MySocket.socket.close();
                        System.out.println(" 终止成功！");
                        // 等待线程结束，用于后面判断线程状态！
                        thread.join(100);
                        break;
                    } catch (Exception e2) {
                    }
                } else {// 窗口未关闭
                    try {

                        myDialog.setBtText("取消等待!");
                        myDialog.setText("正在加入房间，请等待.");
                        Thread.sleep(1000);
                        myDialog.setText("正在加入房间，请等待..");
                        Thread.sleep(1000);
                        myDialog.setText("正在加入房间，请等待...");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }
            }
            System.out.println("joinRoom() 加入房间线程状态:" + thread.getState());
        });
        thread2.start();
    }
}
