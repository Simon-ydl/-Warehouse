package com.dalin.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

//画板功能
public class GamePanel extends JPanel implements KeyListener , ActionListener{

    int lenth;//蛇的长度
    int[] snakeX = new int[600];//蛇的坐标X
    int[] snakeY = new int[500];//蛇的坐标Y

    String fx;//"R":右,"L":左,"U":上,"D":下

    boolean inStart;//判断游戏是否开始

    Timer timer = new Timer(100, this);//监听频率(毫米值),监听的方法

    int foodX;int foodY;Random random = new Random();//食物坐标,坐标的随机数

    boolean isFall = false;//死亡失败,默认不失败

    int score = 0;//积分

    //构造器
    public GamePanel(){
        init();
        //获取键盘的监听事件
        this.setFocusable(true);//聚集焦点在游戏上
        this.addKeyListener(this);//启动键盘监听,实现键盘监听类
        timer.start();//定时器:让时间动起来
    }

    //初始化
    public void init(){
        lenth = 3;
        snakeX[0] = 100;snakeY[0] = 100;//头部坐标
        snakeX[1] = 75;snakeY[1] = 100;//身体坐标
        snakeX[2] = 50;snakeY[2] = 100;//身体坐标
        fx = "R";//方向
        inStart = false;//开始控制
        foodX = 25 + 25*random.nextInt(32);//食物X坐标
        foodY = 75 + 25*random.nextInt(22);//食物Y坐标

    }

    //绘制界面:画板功能实现,Graphics:画笔,super.paintComponent(g):清屏
    @Override
    protected void paintComponent(Graphics g) {
        //清屏
        super.paintComponent(g);
        //设置背板颜色
        this.setBackground(Color.lightGray);
        //绘制头部:获取Data数据绘制
        Data.header.paintIcon(this,g,25,11);
        //绘制游戏区域
        //fill+方法():用于填充,要计算好要填充的区域
        g.fillRect(25,75,850,600);

        // 静态小蛇
        // Data.right.paintIcon(this,g,snakeX[0],snakeY[0]);//头部
        // Data.body.paintIcon(this,g,snakeX[1],snakeY[1]);//身体
        // Data.body.paintIcon(this,g,snakeX[2],snakeY[2]);//身体

        //动态的蛇
        if (fx.equals("R")){
            Data.right.paintIcon(this,g,snakeX[0],snakeY[0]);
        }else if (fx.equals("L")){
            Data.left.paintIcon(this,g,snakeX[0],snakeY[0]);
        }else if (fx.equals("U")){
            Data.up.paintIcon(this,g,snakeX[0],snakeY[0]);
        }else if (fx.equals("D")){
            Data.down.paintIcon(this,g,snakeX[0],snakeY[0]);
        }

        for (int i = 1; i < lenth; i++) {
            Data.body.paintIcon(this,g,snakeX[i],snakeY[i]);
        }

        //画积分
        g.setColor(Color.white);//设置画笔的颜色
        g.setFont(new Font("微软雅黑",Font.BOLD,18));//设置字体
        g.drawString("长度:"+lenth,750,35);//确定位置内容
        g.drawString("分数:"+score,750,50);//确定位置内容

        //画食物
        Data.food.paintIcon(this,g,foodX,foodY);

        //游戏提示,是否开始
        if(inStart == false){
            //画文字,String
            g.setColor(Color.white);//设置画笔的颜色
            g.setFont(new Font("微软雅黑",Font.BOLD,40));//设置字体
            g.drawString("按下空格开始游戏",300,300);//确定位置内容
        }

        //失败提醒
        if(isFall){
            //画文字,String
            g.setColor(Color.red);//设置画笔的颜色
            g.setFont(new Font("微软雅黑",Font.BOLD,40));//设置字体
            g.drawString("游戏结束,按下空格重新开始",200,300);//确定位置内容
        }
    }

    //监听键盘操作,键盘按下未释放
    @Override
    public void keyPressed(KeyEvent e) {
        //获取按下的键盘是那个键
        int keyCode = e.getKeyCode();
        //如果按下的是空格键
        if (keyCode == KeyEvent.VK_SPACE){
            //失败重新开始
            if (isFall){
                isFall = false;
                init();//重新初始化游戏
            }else{
                //暂停游戏
                inStart = !inStart;
                repaint();//刷新界面
            }
        }
        //如果按下的是up键
        if (keyCode == KeyEvent.VK_UP){
            fx = "U";
            repaint();//刷新界面
        }else if (keyCode == KeyEvent.VK_DOWN){
            fx = "D";//如果按下的是down键
            repaint();//刷新界面
        }else if (keyCode == KeyEvent.VK_RIGHT){
            fx = "R";//如果按下的是right键
            repaint();//刷新界面
        }else if (keyCode == KeyEvent.VK_LEFT){
            fx = "L";//如果按下的是left键
            repaint();//刷新界面
        }
    }

    //定时器:监听事件.帧;执行定时操作
    @Override
    public void actionPerformed(ActionEvent e) {
        //如果游戏启动状态并且游戏没有失败
        if(inStart && isFall == false){
            //右移动:坐标的移动snakeX[0]=snakeX[0]+25
            for (int i = lenth-1; i > 0 ; i--) {//除了头部,身体向前移动
                snakeX[i]=snakeX[i-1];
                snakeY[i]=snakeY[i-1];
            }
            //通过控制方向让头部移动
            if (fx.equals("U")){
                snakeY[0]=snakeY[0]-25;//头部向上出发
                if (snakeY[0] < 75){ snakeY[0] = 650; }//边界判断
            }else if (fx.equals("D")){
                snakeY[0]=snakeY[0]+25;//头部向下出发
                if (snakeY[0] > 650){ snakeY[0] = 75; }//边界判断
            }else if (fx.equals("L")){
                snakeX[0]=snakeX[0]-25;//头部向左出发
                if (snakeX[0] < 25){ snakeX[0] = 850; }//边界判断
            }else if (fx.equals("R")){
                snakeX[0]=snakeX[0]+25;//头部向右出发
                if (snakeX[0] > 850){ snakeX[0] = 25; }//边界判断
            }
            //吃食物操作
            if (snakeX[0] == foodX && snakeY[0] ==foodY){
                //增加长度
                lenth++;
                //获取分数
                score = score + 10;
                //重新生成食物
                foodX = 25 + 25*random.nextInt(34);//食物X坐标
                foodY = 75 + 25*random.nextInt(24);//食物Y坐标
            }
            
            //结束判断,撞自己
            for (int i = 1; i < lenth; i++) {
                if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]){
                    isFall = true;
                }
            }
            //刷新界面
            repaint();
        }
        //定时器:让时间动起来
        timer.start();
    }





























    //按下且释放键盘
    @Override
    public void keyTyped(KeyEvent e) {}
    //释放键盘
    @Override
    public void keyReleased(KeyEvent e) {}
}

