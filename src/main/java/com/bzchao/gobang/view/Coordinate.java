package com.bzchao.gobang.view;

public class Coordinate {
    /**
     * 将行转换为Y坐标
     */
    public static int rowToY(int row) {
        return ChessBroad.chessSize * (row - 1) + ChessBroad.chessSize / 2;
    }

    /**
     * 将列转换为X坐标
     */
    public static int colToX(int col) {
        return ChessBroad.chessSize * (col - 1) + ChessBroad.chessSize / 2;
    }

    /**
     * Y坐标转换为行
     */
    public static int yToRow(int y) {
        return (y + ChessBroad.chessSize) / ChessBroad.chessSize;
    }

    /**
     * 将X坐标转换为行列
     */
    public static int xToCol(int x) {
        return (x + ChessBroad.chessSize) / ChessBroad.chessSize;
    }
}
