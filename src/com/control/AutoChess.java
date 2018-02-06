package com.control;

import com.chao.*;
import com.ShowUI.ChessBroad;

/**
 * AI辅助出棋
 */
public class AutoChess {

	public AutoChess() {
		// 单独实例化一个AI，防止影响人机对战
		PlayerAI ai = new PlayerAI();
		// AI系统算得最佳下棋位置,一定要传入player的颜色，AI默认无颜色！
		int color = TableData.getTheColor();
		Spot spot = ai.bestChess(color);

		int row = spot.getRow();
		int col = spot.getCol();
		// 玩家下棋，返回所下的棋子
		spot = new Spot(row, col, color);
		// 提交绘制该棋子
		ChessBroad.submitPaint(spot);

		System.out.println("智能AI生成位置：" + ",  " + (row + 1) + ":" + (col + 1));
	}
}
