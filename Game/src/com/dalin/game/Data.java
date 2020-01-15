package com.dalin.game;

import javax.swing.*;
import java.net.URL;

//存放外部数据
public class Data {
    // URL:定位图片地址
    // ImageIcon:图片绘制都窗口界面
    //标题
    public static URL headerURL = Data.class.getResource("SourceMaterial/header.png");
    public static ImageIcon header = new ImageIcon(headerURL);
    //头部:上,下,左,右
    public static URL upURL = Data.class.getResource("SourceMaterial/up.png");
    public static ImageIcon up = new ImageIcon(upURL);
    public static URL downURL = Data.class.getResource("SourceMaterial/down.png");
    public static ImageIcon down = new ImageIcon(downURL);
    public static URL leftURL = Data.class.getResource("SourceMaterial/left.png");
    public static ImageIcon left = new ImageIcon(leftURL);
    public static URL rightURL = Data.class.getResource("SourceMaterial/right.png");
    public static ImageIcon right = new ImageIcon(rightURL);
    //身体
    public static URL bodyURL = Data.class.getResource("SourceMaterial/body.png");
    public static ImageIcon body = new ImageIcon(bodyURL);
    //食物
    public static URL foodURL = Data.class.getResource("SourceMaterial/food.png");
    public static ImageIcon food = new ImageIcon(foodURL);
}
