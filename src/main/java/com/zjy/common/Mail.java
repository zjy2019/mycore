package com.zjy.common;





import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

/**
 * @author zjy
 * @des
 * @date 2019/6/4
 */
public class Mail {

    public static boolean sendMail(List<String> listto, String subject, String content) {
        boolean isFinish = true;
        try {
            String uname="";
            String pwd="";
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.qiye.163.com");
            props.put("mail.smtp.starttls.enable", "true");//使用 STARTTLS安全连接
            //props.put("mail.smtp.port", "25");             //google使用465或587端口
            props.put("mail.smtp.auth", "true");        // 使用验证
            //props.put("mail.debug", "true");
            Session mailSession = Session.getInstance(props, new MyAuthenticator(uname,pwd));

            MimeMessage message = new MimeMessage(mailSession);
            InternetAddress fromAddress = new InternetAddress(uname);
            message.setFrom(fromAddress);

            for (String cc : listto) {
                InternetAddress toAddress = new InternetAddress(cc);
                message.addRecipient(MimeMessage.RecipientType.TO, toAddress);
            }
            message.setSentDate(Calendar.getInstance().getTime());
            message.setSubject(subject);
            message.setContent(content, "text/html;charset=UTF-8");
            // 第三步：发送消息
            Transport transport = mailSession.getTransport("smtp");
            transport.connect("smtp.qiye.163.com", uname, pwd);
            transport.send(message, message.getRecipients(MimeMessage.RecipientType.TO));

        } catch (Exception e) {
            e.getStackTrace();
            System.out.println("send error" + "  " + MyUtils.getException(e));

        }

        return isFinish;
    }
}
class MyAuthenticator extends Authenticator {
    String userName="";
    String password="";
    public MyAuthenticator(){

    }
    public MyAuthenticator(String userName,String password){
        this.userName=userName;
        this.password=password;
    }
    protected PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication(userName, password);
    }
}
