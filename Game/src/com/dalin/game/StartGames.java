package com.dalin.game;

//用于绘图,awt也拥有相同的效果
import javax.swing.*;

/**
 * 绘制静态窗口:
 *      使用JFrame类
 */
public class StartGames {
    public static void main(String[] args) {

        //1.绘制静态窗口 JFrame
        JFrame jFrame = new JFrame("贪吃蛇");
        //编制界面大小
        //设置界面大小
        jFrame.setBounds(10,10,900,720);
        //设置窗口大小不能改变
        jFrame.setResizable(false);
        //设置关闭事件,用于关闭游戏
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //2.画板Jpanel 可以加入到JFrame
        jFrame.add(new GamePanel());

        //展示窗口
        jFrame.setVisible(true);
    }
}
