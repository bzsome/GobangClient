package com.Socket;

import java.util.ArrayList;

import com.ShowUI.*;
import com.chao.*;

/**
 * 数据发送方案,数据接收，对外接口
 * 
 * @author chaos
 * 
 */
public class DataSocket {
	public final static String TYPE_chat = "chat";
	public final static String TYPE_spot = "spot";
	public final static String TYPE_player = "player";

	/**
	 * 发送数据，处理数据后发送
	 * 
	 * @param object
	 */
	public static void send(Object object) {
		ArrayList<String> list = new ArrayList<>();

		if (object instanceof Spot) {
			list.add("" + TYPE_spot);
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
			MySocket.putData(list);
		}

		if (object instanceof String) {
			list.add("" + TYPE_chat);
			list.add((String) object);
			MySocket.putData(list);
		}

		if (object instanceof Player) {
			Player player = (Player) object;
			list.add("" + TYPE_player);
			list.add(player.getName());
			list.add("" + player.getGrade());
			MySocket.putData(list);
		}
	}

	/**
	 * 接收数据，数据处理后显示
	 * 
	 * @param list
	 */
	public static void recieve(ArrayList<String> list) {
		String str = list.get(0);
		switch (str) {
		case TYPE_chat:
			Chatroom.addText(list.get(1), Chatroom.peText);
			break;
		case TYPE_spot:
			int row = Integer.valueOf(list.get(1));
			int col = Integer.valueOf(list.get(2));
			int color = Player.pe.getColor();
			ChessBroad.submitPaint(new Spot(row, col, color));
			break;
		case TYPE_player:
			String name = list.get(1);
			int grade = Integer.valueOf(list.get(2));
			Player.pe.setName(name);
			Player.pe.setGrade(grade);
			break;
		default:
			System.out.println("DataSocket 收到未知数据！");
			break;
		}
	}
}
