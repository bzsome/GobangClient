package com.bzchao.gobang.view;

import com.bzchao.gobang.data.Algorithm;
import com.bzchao.gobang.data.GameCenter;
import com.bzchao.gobang.data.Spot;
import com.bzchao.gobang.data.TableData;

import javax.swing.*;
import java.awt.*;

/**
 * 界面显示 之 游戏状态面板,显示游戏模式和出棋情况
 */
public class StatePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static JLabel colorLabel, modeLabel, timeJLabel, levelLabel;
    public static StatePanel my;

    public StatePanel() {
        modeLabel = new JLabel("显示游戏模式");
        levelLabel = new JLabel("难度级别:默认中级");
        timeJLabel = new JLabel("倒计时:暂未设置");
        colorLabel = new JLabel("玩家:黑棋先下");
        this.setBackground(new Color(200, 200, 198));
        this.setLayout(new GridLayout(0, 1));
        this.add(modeLabel);
        this.add(levelLabel);
        this.add(timeJLabel);
        this.add(colorLabel);
        my = this;
        // 重视，注释此代码，导致UserPanel内容无法显示，原因未知(原因是UserPanel 显示数据后未重新绘制）
        updateInfo();
    }

    /**
     * 使用线程动态更新状态面板
     */
    private static void updateInfo() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    colorLabel.setText("此面板显示异常！");
                }
                String color = TableData.getNowColor();
                if (Spot.blackChess.equals(color)) {
                    colorLabel.setText("上手白棋，请下黑棋");
                } else if (Spot.whiteChess.equals(color)) {
                    colorLabel.setText("上手黑棋，请下白棋");
                } else {
                    colorLabel.setText("玩家:黑棋先下");
                }

                switch (GameCenter.getMode()) {
                    case GameCenter.MODE_END:
                        modeLabel.setText("游戏模式:未开始游戏");
                        break;
                    case GameCenter.MODE_COUPE:
                        modeLabel.setText("游戏模式:双人对战");
                        break;
                    case GameCenter.MODE_ROBOT:
                        modeLabel.setText("游戏模式:人机对战");
                        break;
                    case GameCenter.MODE_ONLINE:
                        modeLabel.setText("游戏模式:联机对战");
                        break;
                    default:
                        break;
                }

                switch (Algorithm.LEVEL) {
                    case Algorithm.LEVEL_Low:
                        levelLabel.setText("游戏难度:初级难度");
                        break;
                    case Algorithm.LEVEL_Middle:
                        levelLabel.setText("游戏难度:中级难度");
                        break;
                    case Algorithm.LEVEL_High:
                        levelLabel.setText("游戏难度:高级难度");
                        break;
                    default:
                        break;
                }

            }
        }).start();
    }

    public static void setTime(int mTime) {
        timeJLabel.setText("剩余时间:" + mTime + "秒");
    }
}
