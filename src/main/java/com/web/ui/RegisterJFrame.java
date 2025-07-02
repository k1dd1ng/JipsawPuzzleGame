package com.web.ui;

import javax.swing.*;

/**
 * @author cjw
 */
public class RegisterJFrame extends JFrame{

    //注册界面
    public RegisterJFrame(){

        //设置屏幕宽高
        this.setSize(488,430);
        //设置界面标题
        this.setTitle("注册界面");
        //设置界面置顶
        this.setAlwaysOnTop(true);
        //设置界面居中
        this.setLocationRelativeTo(null);
        //设置游戏关闭模式
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //显示界面
        this.setVisible(true);
    }


}
