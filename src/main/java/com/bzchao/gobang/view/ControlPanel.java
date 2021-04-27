package com.bzchao.gobang.view;

import com.bzchao.gobang.connect.MySocket;
import com.bzchao.gobang.data.GameCenter;
import com.bzchao.gobang.data.Spot;
import com.bzchao.gobang.game.AutoChess;
import com.bzchao.gobang.game.GameCoupe;
import com.bzchao.gobang.game.GameOnline;
import com.bzchao.gobang.game.GameRobot;

import javax.swing.*;
import java.awt.*;

/**
 * 主界面右边的控制按钮面板
 */
public class ControlPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    public static ControlPanel my;
    private JButton btnShowChess, btnAgain, btnChessAI, btnTest, btnCoupe, btnOnline, btnRobot;

    public ControlPanel() {
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.setBackground(new Color(220, 220, 220, 220));
        btnAgain = new JButton("重新游戏");
        btnCoupe = new JButton("双人对战");
        btnRobot = new JButton("人机对战");
        btnOnline = new JButton("联机对战");
        btnChessAI = new JButton("智能AI下棋");
        btnShowChess = new JButton("显示所有棋子");
        btnTest = new JButton("备用测试按钮");

        this.add(btnAgain);
        this.add(btnCoupe);
        this.add(btnRobot);
        this.add(btnOnline);
        this.add(btnChessAI);
        this.add(btnShowChess);
        this.add(btnTest);

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
        btnShowChess.addActionListener(event -> GameCenter.showChess());

        btnAgain.addActionListener(event -> {
            GameCenter.reStart();
            ChessBroad.myBroad.repaint();
            MySocket.close();
            btnCoupe.setEnabled(true);
            btnRobot.setEnabled(true);
            btnOnline.setEnabled(true);
            btnChessAI.setEnabled(true);
            try {
                MySocket.socket.close();
            } catch (Exception e) {
            }
        });

        btnChessAI.addActionListener(event -> {
            if (GameCenter.isEnd()) {
                JOptionPane.showMessageDialog(null, "游戏未开始，无法使用AI下棋", "提示信息", JOptionPane.CANCEL_OPTION);
            } else {
                new AutoChess();
            }
        });

        btnTest.addActionListener(event -> {
            ChessBroad.myBroad.repaint();
            System.out.println("----------黑棋权值-------");
            GameCenter.showWeight(Spot.blackChess);

            System.out.println("----------白棋权值-------");
            GameCenter.showWeight(Spot.whiteChess);
        });

        btnCoupe.addActionListener(event -> {
            GameCoupe.reStart();
            btnCoupe.setEnabled(false);
            btnRobot.setEnabled(false);
            btnOnline.setEnabled(false);
        });

        btnRobot.addActionListener(event -> {
            GameRobot.reStart();
            btnCoupe.setEnabled(false);
            btnRobot.setEnabled(false);
            btnOnline.setEnabled(false);
        });

        btnOnline.addActionListener(event -> {
            // new 一定要在前面，否则数据被重置！
            GameOnline.reStart();
            MyDialog.online();

            btnCoupe.setEnabled(false);
            btnRobot.setEnabled(false);
            btnOnline.setEnabled(false);
            btnChessAI.setEnabled(false);
        });
    }
}
