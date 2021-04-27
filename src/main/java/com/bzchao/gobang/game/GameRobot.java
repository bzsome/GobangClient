package com.bzchao.gobang.game;

import com.bzchao.gobang.data.*;
import com.bzchao.gobang.view.ChessBroad;
import com.bzchao.gobang.view.UserPanel;

/**
 * 人机对战
 *
 * @author chaos
 */
public class GameRobot {
    private GameRobot() {
    }

    public static void reStart() {
        GameCenter.reStart();
        GameCenter.setMode(GameCenter.MODE_ROBOT);
        Player.myPlayer.start(Spot.blackChess);
        final PlayerAI playerAI = new PlayerAI();
        playerAI.start(Spot.whiteChess);
        playerAI.setGrade(Player.otherPlayer.getGrade());

        // 用户面板，更新用户信息
        UserPanel.setUserInfo(Player.myPlayer, UserPanel.left);
        UserPanel.setUserInfo(playerAI, UserPanel.right);

        // 产生一个线程，用于下棋
        new Thread(() -> {
            String color = playerAI.getColor();
            while (!GameCenter.isEnd()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("robotThread 睡眠异常！");
                }
                if (TableData.getNowColor().equals(color)) {
                    Spot spot = playerAI.getBestChess(color);
                    ChessBroad.submitPaint(spot);
                }
            }
            System.out.println("电脑下棋线程已停止");
        }).start();
    }
}
