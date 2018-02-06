package com.chao;

/**
 * 棋桌数据类，储存处理棋桌数据，19*19个点
 * 
 * @author chaos
 * 
 */
public class TableData {
	// 游戏模式
	public static int gameModel;
	// 游戏模式常量
	public final static int MODE_END = 0;
	public final static int MODE_COUPE = 1;
	public final static int MODE_ROBOT = 2;
	public final static int MODE_ONLINE = 3;
	// 储存当前应该下棋玩家棋色
	private static int color = Spot.notChess;
	// 游戏是否结束
	private static boolean flagOver = false;
	private static Spot[][] spots = new Spot[19][19];

	// 储存游戏结束时，五子连珠起始位置
	public static int indexRow = 0;
	public static int indexCol = 0;
	public static int endRow = 0;
	public static int endCol = 0;

	protected TableData() {
		// TODO Auto-generated constructor stub
		// 初始化棋桌数据
		for (int i = 0; i < spots.length; i++) {
			for (int j = 0; j < spots[0].length; j++) {
				spots[i][j] = new Spot(i, j, 0);
			}
		}
		flagOver = false;
		color = Spot.notChess;
		indexRow = indexCol = endRow = endCol = 0;
		System.out.println("已初始化棋盘数据");
	}

	/**
	 * 根据行列下棋，范围0-18,调用则更新棋盘数据！
	 */
	public static void putDownChess(Spot spot) {
		int mColor = spot.getColor();
		int row = spot.getRow();
		int col = spot.getCol();
		if (flagOver) {// 游戏已结束则直接返回
			return;
		}
		if (color != mColor) {
			System.out.println("putChess() 不属于此玩家操作" + mColor);
			return;
		}

		if (isSpot(row, col)) {
			System.out.println("此位置已有棋子" + row + ":" + col);
			return;
		}

		color = Spot.getBackColor(color);
		spots[row][col].setColor(mColor);
	}

	/**
	 * 判读玩家是否获胜
	 */
	public static boolean isOver() {
		if (flagOver) {// 游戏已结束则直接返回结果
			return true;
		}

		for (int i = 0; i < spots.length; i++) {
			for (int j = 0; j < spots[0].length; j++) {
				int coutRow = 0;
				int coutCol = 0;
				int coutCR = 0;
				int coutCRS = 0;
				indexRow = i;
				indexCol = j;

				Spot spot = spots[i][j];

				// 获取当前点的棋子颜色
				int color = spot.getColor();

				// 判断此点是否有棋子
				if (color == Spot.notChess) {
					continue;
				}
				// 判断每行
				if (j <= 14) {
					for (int m = 1; m <= 4; m++) {
						int colorm = spots[i][j + m].getColor();
						if (colorm == color) {
							coutRow++;
						}
					}
				}

				// 判断每列
				if (i <= 14)
					for (int m = 1; m <= 4; m++) {
						int colorm = spots[i + m][j].getColor();
						if (colorm == color) {
							coutCol++;
						}
					}

				// 判断正对角线
				if (i <= 14 && j <= 14)
					for (int m = 1; m <= 4; m++) {
						int colorm = spots[i + m][j + m].getColor();
						if (colorm == color) {
							coutCR++;
						}
					}

				// 判断逆对角线
				if (i <= 14 && j >= 4)
					for (int m = 1; m <= 4; m++) {
						int colorm = spots[i + m][j - m].getColor();
						if (colorm == color) {
							coutCRS++;
						}
					}

				// 得到五子连珠的起始行列值
				if (coutRow == 4) {
					endRow = indexRow;
					endCol = indexCol + 4;
					flagOver = true;
				}
				if (coutCol == 4) {
					endRow = indexRow + 4;
					endCol = indexCol;
					flagOver = true;
				}
				if (coutCR == 4) {
					endRow = indexRow + 4;
					endCol = indexCol + 4;
					flagOver = true;
				}
				if (coutCRS == 4) {
					endRow = indexRow + 4;
					endCol = indexCol - 4;
					flagOver = true;
				}
				if (flagOver) {
					return flagOver;
				}
			}
		}
		return false;
	}

	/**
	 * 开始游戏，向玩家分配棋色
	 */
	public static int start() {
		if (color == Spot.notChess) {
			color = Spot.blackChess;
			return Spot.blackChess;
		} else {
			return Spot.whiteChess;
		}
	}

	public static Spot getSpot(int row, int col) {
		return spots[row][col];
	}

	public static int getTheColor() {
		return color;
	}

	public static boolean isSpot(int row, int col) {
		Spot spot = spots[row][col];
		int color = spot.getColor();
		return color != Spot.notChess;
	}
}