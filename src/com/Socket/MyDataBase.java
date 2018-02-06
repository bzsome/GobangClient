package com.Socket;

import java.sql.*;

public class MyDataBase {
	private static final String url = "jdbc:mysql://gobangdata.bzchao.win/gobang?useUnicode=true&characterEncoding=UTF-8";
	private static final String name = "com.mysql.jdbc.Driver";
	private static final String user = "gobang";
	private static final String password = "1501511668";
	private static Connection conn = null;
	private static PreparedStatement pst = null;
	private static ResultSet res = null;

	public MyDataBase() {
		Thread thread = new Thread() {
			public void run() {
				try {
					Class.forName(name);// 指定连接类型
					conn = DriverManager.getConnection(url, user, password);// 获取连接
				} catch (Exception e) {
				}
			}
		};
		thread.start();
		try {
			thread.join(7000);
		} catch (InterruptedException e1) {
		}
		System.out.println("数据库连接超时！");
	}

	public static void close() {
		try {
			conn.close();
			pst.close();
		} catch (SQLException e) {
		}
	}

	/** 获得用户积分 */
	public static int getGrade(String name, String pass) {
		String sql = "select * from user where (name=?) and pass=?";
		try {// 执行语句，得到结果集
			pst = conn.prepareStatement(sql);// 准备执行语句
			pst.setString(1, name);
			pst.setString(2, pass);
			res = pst.executeQuery();
			while (res.next()) {
				int grade = res.getInt("grade");
				return grade;
			}
		} catch (Exception e) {
		}
		return -1;
	}

	/** 注册用户 */
	public static boolean setReg(String name, String pass) {
		String sql = "insert into user(name,pass)values(?,?);";
		try {// 执行语句，得到结果集
			pst = conn.prepareStatement(sql);// 准备执行语句
			pst.setString(1, name);
			pst.setString(2, pass);
			int count = pst.executeUpdate();
			if (count > 0) {
				System.out.println("setReg 注册用户 数据插入成功");
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			System.out.println("setReg 注册用户 插入数据失败！");
		}
		return false;
	}

	/** 向数据库发送表示，表示在线 */
	public static void setTip(String name, String pass) {
		String sql = "update user set tip=tip+1 where name=? and pass=?";
		try {// 执行语句，得到结果集
			pst = conn.prepareStatement(sql);// 准备执行语句
			pst.setString(1, name);
			pst.setString(2, pass);
			int count = pst.executeUpdate();
			if (count > 0) {
				// System.out.println("setTip 在线状态同步成功");
			}
		} catch (Exception e) {
			// System.out.println("setTip 在线状态同步失败！");
		}
	}

	/** 增加积分 */
	public static void addGrade(final String name, final String pass,
			final int add) {
		String sql = "update user set grade=grade+? where name=? and pass=?";
		try {// 执行语句，得到结果集
			pst = conn.prepareStatement(sql);// 准备执行语句
			pst.setInt(1, add);
			pst.setString(2, name);
			pst.setString(3, pass);
			pst.executeUpdate();
		} catch (Exception e) {
			System.out.println("addGrade 增加积分失败！");
		}

	}
}
