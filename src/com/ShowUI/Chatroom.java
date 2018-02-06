package com.ShowUI;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.Socket.*;

public class Chatroom extends JPanel {
	private static final long serialVersionUID = 1L;
	private static JTextArea jArea;
	private static JTextField jText;
	private static JButton btClear, btSend;
	public static Chatroom my;
	/** 我的信息 */
	public final static int myText = 0;
	/** 对方的信息 */
	public final static int peText = 1;

	public Chatroom() {
		// TODO Auto-generated constructor stub
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		jArea = new JTextArea(7, 19);
		jText = new JTextField(14);
		btClear = new JButton("清空");
		btSend = new JButton("发送");
		jArea.setEnabled(false);
		// 自动换行
		jArea.setLineWrap(true);
		jArea.setText("聊天室内容：");
		jArea.setFont(new Font("宋体", Font.BOLD, 14));
		JScrollPane jsp = new JScrollPane(jArea);
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(jsp);
		this.add(jText);
		this.add(btClear);
		this.add(btSend);
		addListener();
		my = this;
	}

	public void addListener() {
		btClear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				jArea.setText("聊天室内容：");
			}
		});
		btSend.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (MySocket.isStart) {
					String text = jText.getText();
					if (text.length() > 0) {
						addText(text, myText);
						DataSocket.send(text);
					}
				} else {
					JOptionPane.showMessageDialog(ShowUI.showUI,
							"消息发送失败！未有玩家连接！", "发送失败",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}

	public static void addText(String text, int who) {
		String str = jArea.getText();
		switch (who) {
		case myText:
			text = "我说:" + text;
			break;
		case peText:
			text = "对方说:" + text;
			break;
		default:
			break;
		}
		jArea.setText(str + "\n" + text);
	}
}
