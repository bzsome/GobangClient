package com.chao;

/**
 * 棋子类
 * 
 * @author chaos
 * 
 */
public class Spot {
	/** 棋子颜色：没有棋子 */
	public final static int notChess = 0;
	/** 棋子颜色：黑棋 */
	public final static int blackChess = 1;
	/** 棋子颜色：白棋 */
	public final static int whiteChess = 2;

	private int color;
	private int row;
	private int col;

	public Spot() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 取值范围0-18
	 * 
	 * @param row
	 * @param col
	 * @param color
	 */
	public Spot(int row, int col, int color) {
		this.color = color;
		this.row = row;
		this.col = col;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
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
	 * 
	 * @param mColor
	 * @return
	 */
	public static int getBackColor(int mColor) {
		return mColor == Spot.blackChess ? Spot.whiteChess : Spot.blackChess;
	}

	/**
	 * 返回棋子颜色字符串
	 */
	public static String getColorString(int mColor) {
		if (mColor == Spot.notChess) {
			return "未得到棋子";
		}
		if (mColor == Spot.blackChess) {
			return "黑棋";
		}
		if (mColor == Spot.whiteChess) {
			return "白棋";
		}
		return "getColorString() 传入参数错误！" + mColor;
	}
}
