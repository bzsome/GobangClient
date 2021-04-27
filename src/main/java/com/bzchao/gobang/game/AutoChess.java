package com.bzchao.gobang.game;

import com.bzchao.gobang.view.ChessBroad;
import com.bzchao.gobang.data.PlayerAI;
import com.bzchao.gobang.data.Spot;
import com.bzchao.gobang.data.TableData;

/**
 * AI辅助出棋
 */
public class AutoChess {

    public AutoChess() {
        // 单独实例化一个AI，防止影响人机对战
        PlayerAI aiPlayer = new PlayerAI();
        // AI系统算得最佳下棋位置,一定要传入player的颜色，AI默认无颜色！
        String color = TableData.getNowColor();
        //获得最适合的下棋位置
        Spot spot = aiPlayer.getBestChess(color);

        int row = spot.getRow();
        int col = spot.getCol();
        // 玩家下棋，返回所下的棋子
        spot = new Spot(row, col, color);

        // 提交绘制该棋子
        ChessBroad.submitPaint(spot);

        System.out.println("智能AI生成位置：" + ",  " + (row + 1) + ":" + (col + 1));
    }
}
