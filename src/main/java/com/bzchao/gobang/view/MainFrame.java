package com.bzchao.gobang.view;

import com.bzchao.gobang.data.GameCenter;
import com.bzchao.gobang.data.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

/**
 * 主界面UI 版本0.3 2017.06.23
 *
 * @author chaos
 */
public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public static final String VERSION = "1.1.4 正式版";
    public static final String BUILD_TIME = "2017.06.26.23";
    public static final String COPYRIGHT = "自主设计开发  拥有本软件所有版权";
    // 窗体大小
    private int width = 730;
    private int height = 760;

    // 三个主面板
    public static MainFrame mainFrame;

    public MainFrame() {
        mainFrame = this;
        UIManager.put("Label.font", new Font("宋体", Font.BOLD, 15));
        UIManager.put("Button.font", new Font("宋体", Font.PLAIN, 20));

        this.setTitle(" 在线五子棋  " + BUILD_TIME);
        this.setSize(width, height);
        this.setResizable(false);
        this.setLayout(null);

        int sWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int sHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setLocation((sWidth - width) / 2, (sHeight - height) / 2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            java.net.URL imgURL = this.getClass().getResource("/images/goBang.png");
            ImageIcon imgIcon = new ImageIcon(imgURL);
            this.setIconImage(imgIcon.getImage());
        } catch (Exception e) {
            System.out.println("图标设置失败！");
        }

        addWidget();
    }

    /**
     * 添加控件和菜单栏
     */
    private void addWidget() {
        int x = 5;
        int y = 2;
        int mWidth = width / 4 * 3;
        int mHeight = width / 5;

        this.setJMenuBar(new MyMenuBar());

        getContentPane().add(new UserPanel());
        UserPanel.userPanel.setBounds(x, y, mWidth, mHeight);

        getContentPane().add(new ChessBroad());
        ChessBroad.myBroad.setBounds(x, mHeight + 2 * y, mWidth, height - mHeight - 2
                * y);

        getContentPane().add(new StatePanel());
        StatePanel.my.setBounds(mWidth + 2 * x, y, width - mWidth - 2 * x,
                mHeight);

        getContentPane().add(new ChatRoom());
        ChatRoom.myRoom.setBounds(mWidth + 2 * x, mHeight + 2 * y, width - mWidth
                - 2 * x, (height - mHeight - 2) / 2 * y);

        getContentPane().add(new ControlPanel());
        ControlPanel.my.setBounds(mWidth + 2 * x, 340, width - mWidth - 2 * x,
                height - mHeight - 2 * y);
    }

    // 界面显示，控件加载完毕后执行(向控件加载数据等)
    public static void init() {
        GameCenter.reStart();
        ChessBroad.init();
        UserPanel.init();
        ControlPanel.init();
        Player.init();
        mainFrame.repaint();
    }

    // 窗口关闭事件
    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            close();
        } else {
            super.processWindowEvent(e); // 该语句会执行窗口事件的默认动作(如：隐藏)
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        ControlPanel.init();
    }

    public static void close() {
        int i = JOptionPane.showConfirmDialog(null, "确定要退出系统吗？", "正在退出五子棋...",
                JOptionPane.YES_NO_OPTION);
        if (i == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
