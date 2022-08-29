package com.itCar.base.tools;

import lombok.extern.slf4j.Slf4j;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.Properties;

/**
 * @ClassName: EmailUtil 
 * @Description: TODO 发送邮件
 * @author: liuzg
 * @Date: 2021/7/22 9:16
 * @Version: v1.0
 */
@Slf4j
public class EmailUtil {

    /**
     * 工具类发送邮件的方法里，发件邮箱的信息是固定的
     *
     * @param map     ：收件地址和发送类型 (地址作 key)
     * @param subject : 邮件的主题
     * @param content ：邮件的正文
     */
    public static void sendMail(Map<String, String> map, String subject, String content) {
        try {
            //1.配置发送邮件的属性
            Properties properties = new Properties();
            properties.setProperty(InitSysLoad.HOST, InitSysLoad.COM); //设置协议主机
            properties.setProperty(InitSysLoad.STMP, InitSysLoad.ISITCERTIFIED); //设置smtp是否需要认证
            //2.根据属性获得一个会话
            Session session = Session.getInstance(properties);
            //3.设置会话是debug模式(会打印更多相关信息,生产环境可设为false)
            session.setDebug(true);
            //4.创建邮件主题信息对象
            MimeMessage message = new MimeMessage(session);
            //5.设置发件人
            message.setFrom(new InternetAddress(InitSysLoad.SENDER));
            //6.设置邮件主题
            message.setSubject(subject);
            //7.设置邮件正文
            message.setText(content);
            //8.设置收件人: TO-发送    CC-抄送   BCC-密送
            for (Map.Entry<String, String> me : map.entrySet()) {
                String email = me.getKey(); //邮件地址
                String type = me.getValue(); //邮件类型
                if ("to".equalsIgnoreCase(type)) {
                    //发送
                    message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
                } else if ("cc".equalsIgnoreCase(type)) {
                    //抄送
                    message.setRecipient(Message.RecipientType.CC, new InternetAddress(email));
                } else if ("bcc".equalsIgnoreCase(type)) {
                    //密送
                    message.setRecipient(Message.RecipientType.BCC, new InternetAddress(email));
                }
            }
            //9.获取发送器对象:提供指定的协议
            Transport transport = session.getTransport("smtp");
            //10.设置发件人的信息
//        transport.connect("smtp.163.com", "amnesiaclover@163.com", "ddoextzstojibjfi");
            //                         协议主机             用户名                            用户登录的授权码
            transport.connect(InitSysLoad.COM, InitSysLoad.SENDER, InitSysLoad.AUTHORIZATIONCODE);
            //11.发送邮件

            transport.sendMessage(message, message.getAllRecipients());
            //12.关闭资源
            transport.close();
            log.info("发送成功");
        } catch (MessagingException e) {
            e.printStackTrace();
            log.info("发送失败");
        }

    }
}
