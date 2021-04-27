package com.bzchao.gobang.data;

/**
 * 算法类，只负责计算此点权重,用于计算应该出什么牌
 *
 * @author chaos
 */
public class Algorithm {
    /**
     * 储存游戏难度
     */
    public static int LEVEL = 0;
    /**
     * 游戏难度：初级
     */
    public final static int LEVEL_Low = 1;
    /**
     * 游戏难度：中级
     */
    public final static int LEVEL_Middle = 2;
    /**
     * 游戏难度：高级
     */
    public final static int LEVEL_High = 3;

    /**
     * 计算此点权重，根据游戏难度选择权重
     **/
    public static int getWeight(Spot spot, String mColor) {
        switch (LEVEL) {
            case LEVEL_Low:
                return getWeightLow(spot, mColor);
            case LEVEL_Middle:
                return getWeightMiddle(spot, mColor);
            case LEVEL_High:
                return getWeightHigh(spot, mColor);
            default:
                // 未设置游戏难度时，难度级别为中级
                return getWeightHigh(spot, mColor);
        }
    }

    /**
     * 计算权值，初级难度算法
     **/
    public static int getWeightLow(Spot spot, String mColor) {
        if (!Spot.blackChess.equals(mColor) && !spot.whiteChess.equals(mColor)) {
            return 0;
        }
        // 此点权，重默认为零
        int weight = 0;
        // 获取此点的行列
        int row = spot.getRow();
        int col = spot.getCol();
        int len[] = {10000, 8000, 5000, 2000, 1000};

        for (int i = 1; i < 5; i++) {

            // 该点有棋子，则不计算权值，默认为0
            if (TableData.getSpot(row, col).getColor() != Spot.notChess) {
                return 0;
            }

            // 下
            if (row + i < 19) {
                spot = TableData.getSpot(row + i, col);
                if (spot.getColor() == mColor)
                    weight += len[i - 1];
            }
            // 上
            if (row - i >= 0) {
                spot = TableData.getSpot(row - i, col);
                if (spot.getColor() == mColor)
                    weight += len[i - 1];
            }
            // 右
            if (col + i < 19) {
                spot = TableData.getSpot(row, col + i);
                if (spot.getColor() == mColor)
                    weight += len[i - 1];
            }
            // 左
            if (col - i >= 0) {
                spot = TableData.getSpot(row, col - i);
                if (spot.getColor() == mColor)
                    weight += len[i - 1];
            }
        }
        weight += (int) (Math.random() * 5000) + 1;
        return weight;
    }

    /**
     * 计算权值，中级难度算法
     **/
    public static int getWeightMiddle(Spot spot, String mColor) {
        if (!Spot.blackChess.equals(mColor) && !Spot.whiteChess.equals(mColor)) {
            return 0;
        }
        String x = Spot.notChess, y = Spot.notChess; // 定义判断颜色的变量
        if (Spot.blackChess.equals(mColor)) {
            x = Spot.blackChess;
            y = Spot.whiteChess;
        } else if (mColor == Spot.whiteChess) {
            x = Spot.whiteChess;
            y = Spot.blackChess;
        }
        int weight = 0;
        int row = spot.getRow();
        int col = spot.getCol();
        // int len[] = { 10000, 8000, 5000, 2000, 1000 };
        Spot spotData = TableData.getSpot(row, col);
        if (!Spot.notChess.equals(spotData.getColor())) {
            return 0;
        }
        int a[] = new int[8];
        int c = 0; // 用于存放a[0]到a[4]中最大的那个
        int i = 0, j = 0, k = 0, count = 1, m = 0, n = 0;
        while (count < 5) {
            if (j != 1 && m != 1) {
                if (row - count >= 0) {
                    spot = TableData.getSpot(row - count, col);
                    if (x.equals(spot.getColor()))
                        i++;
                    else if (y.equals(spot.getColor()))
                        j = 1;
                    else if (Spot.notChess.equals(spot.getColor()))
                        m = 1;
                }
            }
            if (k != 1 && n != 1) {
                if (row + count < 19) {
                    spot = TableData.getSpot(row + count, col);
                    if (x.equals(spot.getColor()))
                        i++;
                    else if (y.equals(spot.getColor()))
                        k = 1;
                    else if (Spot.notChess.equals(spot.getColor()))
                        n = 1;
                }
            }
            count++;
        }
        a[0] = i;
        a[4] = k + j;
        // ////////////////////////////////////////////1
        i = 0;
        j = 0;
        k = 0;
        m = 0;
        n = 0;
        count = 1;
        while (count < 5) {
            if (j != 1 && m != 1) {
                if (col - count >= 0) {
                    spot = TableData.getSpot(row, col - count);
                    if (x.equals(spot.getColor()))
                        i++;
                    else if (y.equals(spot.getColor()))
                        j = 1;
                    else if (Spot.notChess.equals(spot.getColor()))
                        m = 1;
                }
            }
            if (k != 1 && n != 1) {
                if (col + count < 19) {
                    spot = TableData.getSpot(row, col + count);
                    if (x.equals(spot.getColor()))
                        i++;
                    else if (y.equals(spot.getColor()))
                        k = 1;
                    else if (Spot.notChess.equals(spot.getColor()))
                        n = 1;
                }
            }
            count++;
        }
        a[1] = i;
        a[5] = k + j;
        // //////////////////////////////////////////////////2
        i = 0;
        j = 0;
        k = 0;
        m = 0;
        n = 0;
        count = 1;
        while (count < 5) {
            if (j != 1 && m != 1) {
                if (row - count >= 0 && col - count >= 0) {
                    spot = TableData.getSpot(row - count, col - count);
                    if (x.equals(spot.getColor()))
                        i++;
                    else if (y.equals(spot.getColor()))
                        j = 1;
                    else if (Spot.notChess.equals(spot.getColor()))
                        m = 1;
                }
            }
            if (k != 1 && n != 1) {
                if (row + count < 19 && col + count < 19) {
                    spot = TableData.getSpot(row + count, col + count);
                    if (x.equals(spot.getColor()))
                        i++;
                    else if (y.equals(spot.getColor()))
                        k = 1;
                    else if (Spot.notChess.equals(spot.getColor()))
                        n = 1;
                }
            }
            count++;
        }
        a[2] = i;
        a[6] = k + j;
        // ////////////////////////////////////////3
        i = 0;
        j = 0;
        k = 0;
        m = 0;
        n = 0;
        count = 1;
        while (count < 5) {
            if (j != 1 && m != 1) {
                if (row + count < 19 && col - count >= 0) {
                    spot = TableData.getSpot(row + count, col - count);
                    if (x.equals(spot.getColor()))
                        i++;
                    else if (y.equals(spot.getColor()))
                        j = 1;
                    else if (Spot.notChess.equals(spot.getColor()))
                        m = 1;
                }
            }
            if (k != 1 && n != 1) {
                if (row - count >= 0 && col + count < 19) {
                    spot = TableData.getSpot(row - count, col + count);
                    if (x.equals(spot.getColor()))
                        i++;
                    else if (y.equals(spot.getColor()))
                        k = 1;
                    else if (Spot.notChess.equals(spot.getColor()))
                        n = 1;
                }
            }
            count++;
        }
        a[3] = i;
        a[7] = k + j;
        // 下方代码为权值判定
        for (int b = 4; b <= 7; b++) {
            if (a[b] == 2)
                a[b - 4] = 0;
        }
        for (int b = 0; b < 4; b++) {
            if (a[b] > a[c])
                c = b;
        }
        for (int b = 0; b < 4; b++) {
            if (b != c && a[b] == a[c] && a[b + 4] < a[c + 4])
                c = b;
        }

        if (a[c] == 4) {
            weight = 10000;
            return weight;
        }
        if (a[c] == 3 && a[c + 4] == 0) {
            weight = 8000;
            return weight;
        }
        if (a[c] == 2 && a[c + 4] == 0) {
            weight = 5000;
            return weight;
        }
        if (a[c] == 3 && a[c + 4] == 1) {
            weight = 4000;
            return weight;
        }

        if (a[c] == 2 && a[c + 4] == 1) {
            weight = 3000;
            return weight;
        }
        if (a[c] == 1 && a[c + 4] == 0) {
            weight = 2000;
            return weight;
        }
        return weight + (int) (Math.random() * 500) + 1;
    }

    /**
     * 计算权值，高级难度算法
     **/
    public static int getWeightHigh(Spot spot, String mColor) {
        if (!Spot.blackChess.equals(mColor) && !Spot.whiteChess.equals(mColor)) {
            return 0;
        }
        String x = Spot.notChess, y = Spot.notChess; // 定义判断颜色的变量
        if (Spot.blackChess.equals(mColor)) {
            x = Spot.blackChess;
            y = Spot.whiteChess;
        } else if (mColor == Spot.whiteChess) {
            x = Spot.whiteChess;
            y = Spot.blackChess;
        }
        int weight = 0;
        int row = spot.getRow();
        int col = spot.getCol();
        // int len[] = { 10000, 8000, 5000, 2000, 1000 };
        Spot spotData = TableData.getSpot(row, col);
        if (!Spot.notChess.equals(spotData.getColor())) {
            return 0;
        }
        int a[] = new int[8];
        int c = 0, d = 0; // 用于存放a[0]到a[4]中最大的那个,d用于存放四个中第二大的那个
        int i = 0, j = 0, k = 0, count = 1, m = 0, n = 0;
        while (count < 5) {
            if (j != 1 && m != 1) {
                if (row - count >= 0) {
                    spot = TableData.getSpot(row - count, col);
                    if (x.equals(spot.getColor()))
                        i++;
                    else if (y.equals(spot.getColor()))
                        j = 1;
                    else if (Spot.notChess.equals(spot.getColor()))
                        m = 1;
                }
            }
            if (k != 1 && n != 1) {
                if (row + count < 19) {
                    spot = TableData.getSpot(row + count, col);
                    if (x.equals(spot.getColor()))
                        i++;
                    else if (y.equals(spot.getColor()))
                        k = 1;
                    else if (Spot.notChess.equals(spot.getColor()))
                        n = 1;
                }
            }
            count++;
        }
        a[0] = i;
        a[4] = k + j;
        // ////////////////////////////////////////////1
        i = 0;
        j = 0;
        k = 0;
        m = 0;
        n = 0;
        count = 1;
        while (count < 5) {
            if (j != 1 && m != 1) {
                if (col - count >= 0) {
                    spot = TableData.getSpot(row, col - count);
                    if (x.equals(spot.getColor()))
                        i++;
                    else if (y.equals(spot.getColor()))
                        j = 1;
                    else if (Spot.notChess.equals(spot.getColor()))
                        m = 1;
                }
            }
            if (k != 1 && n != 1) {
                if (col + count < 19) {
                    spot = TableData.getSpot(row, col + count);
                    if (x.equals(spot.getColor()))
                        i++;
                    else if (y.equals(spot.getColor()))
                        k = 1;
                    else if (Spot.notChess.equals(spot.getColor()))
                        n = 1;
                }
            }
            count++;
        }
        a[1] = i;
        a[5] = k + j;
        // //////////////////////////////////////////////////2
        i = 0;
        j = 0;
        k = 0;
        m = 0;
        n = 0;
        count = 1;
        while (count < 5) {
            if (j != 1 && m != 1) {
                if (row - count >= 0 && col - count >= 0) {
                    spot = TableData.getSpot(row - count, col - count);
                    if (x.equals(spot.getColor()))
                        i++;
                    else if (y.equals(spot.getColor()))
                        j = 1;
                    else if (Spot.notChess.equals(spot.getColor()))
                        m = 1;
                }
            }
            if (k != 1 && n != 1) {
                if (row + count < 19 && col + count < 19) {
                    spot = TableData.getSpot(row + count, col + count);
                    if (x.equals(spot.getColor()))
                        i++;
                    else if (y.equals(spot.getColor()))
                        k = 1;
                    else if (Spot.notChess.equals(spot.getColor()))
                        n = 1;
                }
            }
            count++;
        }
        a[2] = i;
        a[6] = k + j;
        // ////////////////////////////////////////3
        i = 0;
        j = 0;
        k = 0;
        m = 0;
        n = 0;
        count = 1;
        while (count < 5) {
            if (j != 1 && m != 1) {
                if (row + count < 19 && col - count >= 0) {
                    spot = TableData.getSpot(row + count, col - count);
                    if (x.equals(spot.getColor()))
                        i++;
                    else if (y.equals(spot.getColor()))
                        j = 1;
                    else if (Spot.notChess.equals(spot.getColor()))
                        m = 1;
                }
            }
            if (k != 1 && n != 1) {
                if (row - count >= 0 && col + count < 19) {
                    spot = TableData.getSpot(row - count, col + count);
                    if (x.equals(spot.getColor()))
                        i++;
                    else if (y.equals(spot.getColor()))
                        k = 1;
                    else if (Spot.notChess.equals(spot.getColor()))
                        n = 1;
                }
            }
            count++;
        }
        a[3] = i;
        a[7] = k + j;
        // 下方代码为权值判定
        for (int b = 4; b <= 7; b++) {
            if (a[b] == 2)
                a[b - 4] = 0;
        }
        for (int b = 0; b < 4; b++) {
            if (a[b] > a[c])
                c = b;
        }
        for (int b = 0; b < 4; b++) {
            if (b != c && a[b] == a[c] && a[b + 4] < a[c + 4])
                c = b;
        }
        d = (c + 1) % 4;
        for (int b = 0; b < 4; b++) {
            if (b != c) {
                if (a[b] > a[d])
                    d = b;
            }
        }
        for (int b = 0; b < 4; b++) {
            if (b != c && b != d && a[b] == a[d] && a[b + 4] < a[d + 4])
                d = b;
        }
        if (a[c] >= 4) {
            weight = 10000;
            return weight + (int) (Math.random() * 100) + 1;
        }
        if (col == 0 || col == 18 || row == 0 || row == 18) {
            weight = 0;
            return weight;
        }
        if (a[c] == 3 && a[c + 4] == 1 && a[d] == 2 && a[d + 4] == 0) {
            weight = 8000;
            return weight + (int) (Math.random() * 100) + 1;
        }
        if (a[c] == 3 && a[c + 4] == 1 && a[d] == 3 && a[d + 4] == 1) {
            weight = 8000;
            return weight + (int) (Math.random() * 100) + 1;
        }
        if (a[c] == 3 && a[c + 4] == 0 && a[d] == 3 && a[d + 4] >= 1) {
            weight = 8200;
            return weight + (int) (Math.random() * 100) + 1;
        }
        if (a[c] == 3 && a[c + 4] == 0) {
            weight = 8000;
            return weight + (int) (Math.random() * 100) + 1;
        }
        if (a[c] == 2 && a[c + 4] == 0 && a[d] == 2 && a[d + 4] == 0) {
            weight = 7000;
            return weight + (int) (Math.random() * 100) + 1;
        }
        if (a[c] == 2 && a[c + 4] == 0 && a[d] == 1 && a[d + 4] == 0) {
            weight = 6000;
            return weight + (int) (Math.random() * 100) + 1;
        }
        if (a[c] == 2 && a[c + 4] == 0) {
            weight = 5000;
            return weight + (int) (Math.random() * 100) + 1;
        }
        if (a[c] == 3 && a[c + 4] == 1 && a[d] == 1 && a[d + 4] == 0) {
            weight = 4000;
            return weight + (int) (Math.random() * 100) + 1;
        }
        if (a[c] == 3 && a[c + 4] == 1) {
            weight = 3000;
            return weight + (int) (Math.random() * 100) + 1;
        }
        if (a[c] == 1 && a[c + 4] == 0 && a[d] == 1 && a[d + 4] == 0) {
            weight = 2000;
            return weight + (int) (Math.random() * 100) + 1;
        }
        if (a[c] == 1 && a[c + 4] == 0) {
            weight = 1000;
            return weight + (int) (Math.random() * 100) + 1;
        }
        return weight + (int) (Math.random() * 100) + 1;
    }
}
