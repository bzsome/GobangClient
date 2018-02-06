package com.Socket;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class MySocket {
	public static Socket socket;
	public static ServerSocket server;
	public final static int port = 8000;
	/** 是否有玩家连接，主要用于窗口提示,对方是否掉线 */
	public static boolean isStart = false;
	public static String peAddress;

	/**
	 * 创建Socket服务，如果是创建，则保存连接到的socket
	 * 
	 * @throws IOException
	 */
	public static void startServer() throws IOException {
		server = new ServerSocket(port);
		MySocket.socket = server.accept();
		getData();// 有玩家加入房间则准备接收数据
		isStart = true;
		peAddress = socket.getInetAddress().getHostAddress();
		System.out.println("startServer() 对方加入连接成功！准备接收对方数据");
	}

	/**
	 * 连接指定IP地址的Socket服务，并保存连接到的socket
	 * 
	 * @throws Exception
	 */
	public static void getSocket(final String address) throws Exception {
		socket = new Socket();
		// socket.setSoTimeout(5000);
		socket.connect(new InetSocketAddress(address, port));
		getData();// 成功加入房间则接收数据
		isStart = true;
		peAddress = socket.getInetAddress().getHostAddress();
		System.out.println("getSocket() 加入对方连接成功,准备接收对方数据");
	}

	/**
	 * 发送数据，保护方法，禁止非网络数据处理包调用！
	 * 
	 * @param object
	 */
	protected static void putData(final Object object) {
		new Thread() {
			public void run() {
				try {
					ObjectOutputStream os = new ObjectOutputStream(
							socket.getOutputStream());
					os.writeObject(object);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("putData() 数据发送异常！终止发送");
					isStart = false;
					return;
				}
			}
		}.start();
	}

	/**
	 * 接收数据,保护方法，禁止非网络数据处理包调用！
	 */
	protected static void getData() {
		new Thread() {
			public void run() {
				while (true) {
					try {
						ObjectInputStream in = new ObjectInputStream(
								MySocket.socket.getInputStream());
						Object object = in.readObject();
						if (object instanceof ArrayList) {
							@SuppressWarnings("unchecked")
							ArrayList<String> list = (ArrayList<String>) object;
							DataSocket.recieve(list);
						}
					} catch (Exception e) {
						System.out.println("getData() 数据接收异常，终止接收");
						isStart = false;
						return;
					}
				}
			}
		}.start();
	}

	public static void close() {
		try {
			server.close();
			isStart = false;
		} catch (Exception e) {
		}
		try {
			socket.close();
			isStart = false;
		} catch (Exception e) {
		}
	}
}