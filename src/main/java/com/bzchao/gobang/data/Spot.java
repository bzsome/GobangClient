package com.bzchao.gobang.data;

/**
 * 棋子类
 *
 * @author chaos
 */
public class Spot {
    /**
     * 棋子颜色：没有棋子
     */
    public final static String notChess = "none";
    /**
     * 棋子颜色：黑棋
     */
    public final static String blackChess = "black";
    /**
     * 棋子颜色：白棋
     */
    public final static String whiteChess = "white";

    private String color;
    private int row;
    private int col;

    public Spot() {
    }

    /**
     * 取值范围0-18
     */
    public Spot(int row, int col, String color) {
        this.color = color;
        this.row = row;
        this.col = col;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    /**
     * 获得相反颜色棋子的值
     */
    public static String getBackColor(String mColor) {
        return Spot.blackChess.equals(mColor) ? Spot.whiteChess : Spot.blackChess;
    }

    /**
     * 返回棋子颜色字符串
     */
    public static String getColorString(String mColor) {
        if (Spot.notChess.equals(mColor)) {
            return "未得到棋子";
        }
        if (Spot.blackChess.equals(mColor)) {
            return "黑棋";
        }
        if (Spot.whiteChess.equals(mColor)) {
            return "白棋";
        }
        return "getColorString() 传入参数错误！" + mColor;
    }
}
