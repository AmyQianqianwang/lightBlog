package in.wenwen.util;

import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.jiucheng.util.DefaultPropertiesUtil;

public class EmailUtil {
    public static void send(String to, String subject, String msg) {
        try {
            SimpleEmail simpleEmail = new SimpleEmail();
            simpleEmail.setHostName(DefaultPropertiesUtil.getString(ConstUtil.SMTP_HOST));
            simpleEmail.setSmtpPort(Integer.parseInt(DefaultPropertiesUtil.getString(ConstUtil.SMTP_PORT)));
            simpleEmail.setAuthentication(DefaultPropertiesUtil.getString(ConstUtil.SMTP_USERNAME), DefaultPropertiesUtil.getString(ConstUtil.SMTP_PASSWORD));
            simpleEmail.setCharset(DefaultPropertiesUtil.getString(ConstUtil.SMTP_CHARSET));
            simpleEmail.setFrom(DefaultPropertiesUtil.getString(ConstUtil.SMTP_FR), DefaultPropertiesUtil.getString(ConstUtil.SMTP_NICK));
            simpleEmail.addTo(to);
            simpleEmail.setSubject(subject);
            simpleEmail.setMsg(msg);
            simpleEmail.send();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void sendHtml(String to, String subject, String msg) {
        try {
            HtmlEmail simpleEmail = new HtmlEmail();
            simpleEmail.setHostName(DefaultPropertiesUtil.getString(ConstUtil.SMTP_HOST));
            simpleEmail.setSmtpPort(Integer.parseInt(DefaultPropertiesUtil.getString(ConstUtil.SMTP_PORT)));
            simpleEmail.setAuthentication(DefaultPropertiesUtil.getString(ConstUtil.SMTP_USERNAME), DefaultPropertiesUtil.getString(ConstUtil.SMTP_PASSWORD));
            simpleEmail.setCharset(DefaultPropertiesUtil.getString(ConstUtil.SMTP_CHARSET));
            simpleEmail.setFrom(DefaultPropertiesUtil.getString(ConstUtil.SMTP_FR), DefaultPropertiesUtil.getString(ConstUtil.SMTP_NICK));
            simpleEmail.addTo(to);
            simpleEmail.setSubject(subject);
            simpleEmail.setHtmlMsg(msg);
            simpleEmail.send();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
