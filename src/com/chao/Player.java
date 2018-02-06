package com.chao;

/**
 * 玩家类
 * 
 * @author chaos
 * 
 */
public class Player {
	protected String name = "默认玩家";
	protected String address = "无IP地址(本地玩家)";
	protected int grade = 100;
	protected int color = Spot.notChess;
	protected static int i = 1;
	// 储存对战玩家，我方，对方
	public static Player my, pe;

	public Player() {
		this.name = "默认玩家" + i;
		i++;
	}

	public Player(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
	}

	/** 初始化两玩家，主要用于在线对战 */
	public static void init() {
		my = new Player();
		pe = new Player();
	}

	/**
	 * 创建下棋对象
	 */
	public Spot createChess(int row, int col) {
		return new Spot(row, col, color);
	}

	/**
	 * 开始游戏,得到棋色
	 */
	public void start() {
		// 返回得到的棋色
		color = TableData.start();
		System.out.println(name + " 获得棋色 " + getColorString());
	}

	public int getColor() {
		return color;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getColorString() {
		return Spot.getColorString(color);
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int addGrade(int add) {
		grade += add;
		return grade;
	}
}
