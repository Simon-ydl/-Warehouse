package com.tensquare.sms;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * 消费者频道
 */
@Component
@RabbitListener(queues = "itcast")
public class SmsListener {
    //注入阿里云的API
    @Autowired
    private SmsUtil smsUtil;

    @Value("${aliyun.sms.template_code}")
    private String templateCode;

    @Value("${aliyun.sms.sign_name}")
    private String signName;

    //信息处理
    @RabbitHandler
    public void handlermsg(Map<String ,String> map, Channel channel, Message message) throws IOException {
        //打印从中间件获取的信息
        System.out.println("手机号码:"+ map.get("mobile"));
        System.out.println("验证码:"+ map.get("code"));
        //调用API方法
        try {
            //使用阿里云发送短信
            SendSmsResponse sendSmsResponse = smsUtil.sendSms(map.get("mobile"),templateCode,signName,"{\"code\":\""+map.get("code")+"\"}");

            //判断是否成功
            if(sendSmsResponse.getCode().equals("OK")){
                System.out.println("发送成功:"+sendSmsResponse.getCode());
                //确认消息是否启用,返回确认消息
                //通知服务器收到这条消息，可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉，后续还会在发
                channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            }else{
                System.out.println("发送失败:"+sendSmsResponse.getCode());
            }
        } catch (ClientException e) {
            e.printStackTrace();
            //丢弃这条消息
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
        }


    }
}
