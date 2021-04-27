package com.bzchao.gobang.data;

/**
 * 游戏中心类，控制游戏数据
 *
 * @author chaos
 */
public class GameCenter {

    /**
     * 储存游戏模式
     */
    private static int gameModel;
    /**
     * 游戏模式 ：游戏结束
     */
    public final static int MODE_END = 0;
    /**
     * 游戏模式 ：双人对战
     */
    public final static int MODE_COUPE = 1;
    /**
     * 游戏模式 ：人机对战
     */
    public final static int MODE_ROBOT = 2;
    /**
     * 游戏模式 ：联机对战
     */
    public final static int MODE_ONLINE = 3;

    /**
     * 重新游戏
     */
    public static void reStart() {
        // 初始化棋桌数据
        TableData.reset();
        gameModel = MODE_END;
    }

    /**
     * 仅供调试，显示所有棋子
     */
    public static void showChess() {
        System.out.println("显示棋桌： 游戏是否结束： isOver() " + TableData.isOver());
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 19; j++) {
                String color = TableData.getSpot(i, j).getColor();
                System.out.print(color + ", ");
            }
            System.out.println();
        }
    }

    /**
     * 仅供调试，无实际功能，显示所有点的权值
     */
    public static void showWeight(String mColor) {
        if (Spot.notChess.equals(mColor)) {
            System.out.println("showWeight 颜色设置错误！");
            return;
        }
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 19; j++) {
                Spot spot = TableData.getSpot(i, j);
                int n = Algorithm.getWeight(spot, mColor);
                System.out.print(n + ",\t");
            }
            System.out.println();
        }
    }

    public static int getMode() {
        return gameModel;
    }

    public static void setMode(int mode) {
        gameModel = mode;
    }

    public static boolean isEnd() {
        return gameModel == MODE_END;
    }
}
