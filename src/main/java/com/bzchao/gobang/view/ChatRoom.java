package com.bzchao.gobang.view;

import com.bzchao.gobang.connect.DataSocket;
import com.bzchao.gobang.connect.MySocket;

import javax.swing.*;
import java.awt.*;

/**
 * 聊天室面板
 */
public class ChatRoom extends JPanel {
    private static final long serialVersionUID = 1L;
    private static JTextArea jArea;
    private static JTextField jText;
    private static JButton btClear, btSend;
    public static ChatRoom myRoom;
    /**
     * 我的信息
     */
    public final static int myText = 0;
    /**
     * 对方的信息
     */
    public final static int peText = 1;

    public ChatRoom() {
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
        myRoom = this;
    }

    public void addListener() {
        btClear.addActionListener(event -> jArea.setText("聊天室内容："));
        btSend.addActionListener(event -> {
            if (MySocket.isStart) {
                String text = jText.getText();
                if (text.length() > 0) {
                    addText(text, myText);
                    DataSocket.send(text);
                }
            } else {
                JOptionPane.showMessageDialog(MainFrame.mainFrame,
                        "消息发送失败！未有玩家连接！", "发送失败",
                        JOptionPane.WARNING_MESSAGE);
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
