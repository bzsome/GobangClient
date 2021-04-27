package com.bzchao.gobang.connect;

import com.bzchao.gobang.data.Player;
import com.bzchao.gobang.data.Spot;
import com.bzchao.gobang.view.ChatRoom;
import com.bzchao.gobang.view.ChessBroad;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据发送方案,数据接收，对外接口
 *
 * @author chaos
 */
public class DataSocket {
    public final static String TYPE_chat = "chat";
    public final static String TYPE_spot = "spot";
    public final static String TYPE_player = "player";

    /**
     * 发送数据，处理数据后发送
     */
    public static void send(Object object) {
        List<String> list = new ArrayList<>();

        //下棋数据
        if (object instanceof Spot) {
            list.add(TYPE_spot);
            int row = ((Spot) object).getRow();
            int col = ((Spot) object).getCol();

            if (row < 10) {
                list.add("0" + row);
            } else {
                list.add("" + row);
            }

            if (col < 10) {
                list.add("0" + col);
            } else {
                list.add("" + col);
            }
            MySocket.sendData(list);
        }
        //玩家信息
        if (object instanceof Player) {
            Player player = (Player) object;
            list.add(TYPE_player);
            list.add(player.getName());
            list.add("" + player.getGrade());
            MySocket.sendData(list);
        }
        //聊天内容
        if (object instanceof String) {
            list.add(TYPE_chat);
            list.add((String) object);
            MySocket.sendData(list);
        }
    }

    /**
     * 接收数据，数据处理后显示
     */
    public static void receive(List<String> list) {
        String str = list.get(0);
        switch (str) {
            case TYPE_chat:
                ChatRoom.addText(list.get(1), ChatRoom.peText);
                break;
            case TYPE_spot:
                int row = Integer.valueOf(list.get(1));
                int col = Integer.valueOf(list.get(2));
                String color = Player.otherPlayer.getColor();
                ChessBroad.submitPaint(new Spot(row, col, color));
                break;
            case TYPE_player:
                String name = list.get(1);
                int grade = Integer.valueOf(list.get(2));
                Player.otherPlayer.setName(name);
                Player.otherPlayer.setGrade(grade);
                break;
            default:
                System.out.println("DataSocket 收到未知数据！" + str);
                break;
        }
    }
}
