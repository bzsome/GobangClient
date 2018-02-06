package com.ShowUI;

import com.Socket.*;
import com.chao.*;
import com.control.UserLogin;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * 界面显示 之 棋桌面板，显示棋盘，绘制棋子
 ** 
 * 当界面重新被加载时，无法正常绘制出所有棋子！(加入线程后解决问题！)
 ** 
 * 同样的，棋桌间断性地绘制失败(加入线程后解决问题！)
 ** 
 * 将胜负判断加在绘制棋子里面则导致，游戏结束后，界面重新绘制时，导致多次判断胜负
 ** 
 * 因此，新增一个函数，用于判断提交的绘制请求是否符合要求
 ** 
 * 绘制圆形fillOval,指定的位置是左顶点的位置！
 ** 
 * 新增绘制五子连珠情况
 ** 
 * 主界面重新绘制后，棋盘绘制不完整(加入线程,等待棋盘绘制完成,等待棋子绘制完成,再绘制结果)
 */
public class ChessBroad extends JPanel {

	private static final long serialVersionUID = 1L;

	/** 棋子大小 */
	protected static int chessSize;
	public static ChessBroad my;
	/** 线程，解决棋盘覆盖掉棋子 */
	static Thread thread, thread2;

	public ChessBroad() {
		this.setVisible(true);
		// 设置鼠标形状为手型
		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.setBackground(new Color(249, 214, 91));
		this.addListener();
		my = this;
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		paintTable();
		painAllChess();
		paintResult();
	}

	/**
	 * 界面显示，控件加载完毕后执行
	 */
	public static void init() {
		chessSize = my.getWidth() / 19;
		paintTable();
		my.repaint();
	}

	public static void addGrade() {
		if (Player.my.getColor() != TableData.getTheColor()) {
			if (UserLogin.getOnline()) {
				UserLogin.addGrade(100);// 已更新玩家积分！
				int grade = Player.my.getGrade();
				UserPanel.setGrade(grade, UserPanel.left);
			}
			int grade = Player.my.addGrade(100);
			UserPanel.setGrade(grade, UserPanel.left);
		} else {
			int grade = Player.pe.addGrade(100);
			UserPanel.setGrade(grade, UserPanel.right);
		}
	}

	/**
	 * 添加监听事件
	 */
	private void addListener() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				// TODO Auto-generated method stub
				int cx = e.getX();
				int cy = e.getY();
				System.out.print("点击坐标" + cx + ":" + cy);

				// 根据坐标，获得行列

				int row = Coordinate.yToRow(cy);
				int col = Coordinate.xToCol(cx);
				Spot spot = new Spot(row - 1, col - 1, TableData.getTheColor());
				System.out.println("， 点击行列" + row + ":" + col);

				if (TableData.isSpot(row - 1, col - 1)) {
					System.out.println("ChessBroad 此点已有棋子！");
					return;
				}

				if (GameCenter.getMode() == GameCenter.MODE_ONLINE) {
					if (Player.my.getColor() == TableData.getTheColor()) {
						DataSocket.send(spot);
					} else {
						JOptionPane.showMessageDialog(ShowUI.showUI,
								"局域网对战中，请先等待对方下棋", "请等待..",
								JOptionPane.CANCEL_OPTION);
						System.out.println(Player.my.getColor() + ":"
								+ TableData.getTheColor());
						return;
					}
				}

				submitPaint(spot);
			}
		});
	}

	/**
	 * 接收绘制棋子请求，先验证，后绘制
	 * 
	 * @param spot
	 */
	public static void submitPaint(Spot spot) {
		if (spot != null) {
			{
				// 判断符合下棋要求
				if (GameCenter.isEnd()) {
					JOptionPane.showMessageDialog(null,
							"游戏未开始，或已结束！\n请选择游戏模式，以开始游戏，", "游戏未开始",
							JOptionPane.CANCEL_OPTION);
					return;
				}

				paintChess(spot);
				TableData.putDownChess(spot);
			}
		}

		if (TableData.isOver()) {
			addGrade();// 增加用户积分
			GameCenter.setMode(GameCenter.MODE_END);
			paintResult();
			if (TableData.getTheColor() == Spot.whiteChess) {
				// 当前应该下白棋，则黑棋获胜
				JOptionPane.showMessageDialog(ShowUI.showUI, "黑棋获胜！增加100积分！",
						"游戏结束", JOptionPane.CANCEL_OPTION);
			} else {
				JOptionPane.showMessageDialog(ShowUI.showUI, "白棋获胜！增加100积分！",
						"游戏结束", JOptionPane.CANCEL_OPTION);
			}
		}
	}

	/**
	 * 绘制单颗棋子,执行则绘制棋子，不判断棋子的正确性
	 * 
	 * @param spot
	 */
	private static void paintChess(Spot spot) {
		if (spot != null) {
			int row = spot.getRow() + 1;
			int col = spot.getCol() + 1;

			int cx = Coordinate.colToX(col);
			int cy = Coordinate.rowToY(row);
			Graphics g = my.getGraphics();
			int color = spot.getColor();
			switch (color) {
			case Spot.blackChess:
				g.setColor(Color.black);
				break;
			case Spot.whiteChess:
				g.setColor(Color.white);
				break;
			default:
				return;
			}
			g.fillOval(cx - chessSize / 2, cy - chessSize / 2, chessSize,
					chessSize);
		}
	}

	/**
	 * 绘制棋桌
	 * 
	 * @param g
	 */
	private static void paintTable() {
		final Graphics g = my.getGraphics();
		g.setFont(new Font("黑体", Font.BOLD, 20));
		// 在线程中绘制棋盘
		thread = new Thread() {
			public void run() {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
				}
				for (int i = 0; i < 19; i++) {
					g.drawString("" + (i + 1), 0, 20 + chessSize * i);
					g.drawLine(chessSize / 2, chessSize / 2 + chessSize * i,
							chessSize / 2 + chessSize * 18, chessSize / 2
									+ chessSize * i);

					g.drawString("" + (i + 1), chessSize * i + 8, 15);
					g.drawLine(chessSize / 2 + chessSize * i, chessSize / 2,
							chessSize / 2 + chessSize * i, chessSize / 2
									+ chessSize * 18);
				}
			};
		};
		thread.start();
	}

	/**
	 * 绘制所有棋子
	 */
	private static void painAllChess() {
		// 绘制所有棋子线程，没有线程时棋子可能绘制失败！
		thread2 = new Thread() {
			@Override
			public void run() {
				try {// 等待棋桌绘制完成
					thread.join();
				} catch (InterruptedException e) {
				}
				for (int i = 0; i < 19; i++) {
					for (int j = 0; j < 19; j++) {
						Spot spot = TableData.getSpot(i, j);
						paintChess(spot);
					}
				}
			}
		};
		thread2.start();
	}

	/**
	 * 绘制赢棋后的五子连珠状况
	 */
	private static void paintResult() {
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				try {
					thread2.join();
				} catch (InterruptedException e) {
				}
				if (GameCenter.isEnd()) {
					if (TableData.endRow + TableData.endCol < 5) {
						return;
					}

					System.out.println("赢棋起始位置：" + TableData.indexRow + " "
							+ TableData.indexCol);
					System.out.println("赢棋终止位置：" + TableData.endRow + " "
							+ TableData.endCol);

					Graphics2D g = (Graphics2D) my.getGraphics();
					int indexX = Coordinate.colToX(TableData.indexCol + 1);
					int indexY = Coordinate.rowToY(TableData.indexRow + 1);
					int endX = Coordinate.colToX(TableData.endCol + 1);
					int endY = Coordinate.rowToY(TableData.endRow + 1);
					g.setColor(Color.yellow);
					g.setStroke(new BasicStroke(5.0f));
					g.setFont(new Font("黑体", Font.BOLD, 150));
					g.drawLine(indexX, indexY, endX, endY);
				}
			}
		}.start();

	}
}

class Coordinate {
	/** 将行转换为Y坐标 */
	public static int rowToY(int row) {
		return ChessBroad.chessSize * (row - 1) + ChessBroad.chessSize / 2;
	}

	/** 将列转换为X坐标 */
	public static int colToX(int col) {
		return ChessBroad.chessSize * (col - 1) + ChessBroad.chessSize / 2;
	}

	/** Y坐标转换为行 */
	public static int yToRow(int y) {
		return (y + ChessBroad.chessSize) / ChessBroad.chessSize;
	}

	/** 将X坐标转换为行列 */
	public static int xToCol(int x) {
		return (x + ChessBroad.chessSize) / ChessBroad.chessSize;
	}
}
