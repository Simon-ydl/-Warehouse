package com.yjs.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class BusinessNumber {
    //生成业务流水号
    public static String getBusinessNumber() throws ParseException {
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String seconds = new SimpleDateFormat("HHmmss").format(new Date());
        //使用简单日期转换可以换成你想要的日期格式
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
//        long finaWay = sdf.parse(sdf.format(new Date())).getTime();
        String business = null;
        for (int i = 0; i < 10000; i++) {
//            System.out.println(date+"00001000"+getTwo()+"00"+seconds+getTwo());
//            business = date+getTwo()+seconds+getTwo();
            business = date + seconds + getTwo();
        }
        System.out.println(business);
		return business;
    }

    private static String getTwo() {
        Random rad = new Random();

        String result  = rad.nextInt(100) +"";
        if(result.length()==1){
            result = "0" + result;
        }
        return result;
    }
}
