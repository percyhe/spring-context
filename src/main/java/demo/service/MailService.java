package demo.service;

import org.slf4j.LoggerFactory;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class MailService {
    //如果找到就注入 找不到就忽略
    @Autowired(required = false)
    //@Qualifier("z")
    //@Qualifier("utc8")
    private ZoneId zoneId = ZoneId.systemDefault();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void setZoneId(ZoneId zoneId) {
        this.zoneId = zoneId;
    }

    //#表示从Bean读取属性
    @Value("#{smtpConfig.host}")
    private String smtpHost;

    @Value("#{smtpConfig.port}")
    private  int smtpPort;

    @PostConstruct
    public void  init(){
        String msg = "Init mail service with zoneId = " + this.zoneId;
        logger.info(msg, getClass().getSimpleName());
        System.out.println(msg);
    }

    @PreDestroy
    public void shutdown(){
        String msg = "Shutdown mail service";
        logger.info(msg, getClass().getSimpleName());
        System.out.println(msg);
    }

    public String getTime() {
        //ZonedDateTime.now(this.zoneId).format(DateTimeFormatter.ISO_ZONED_DATE_TIME)
        ZonedDateTime date = ZonedDateTime.now();
        String time = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm:ss").format(date);
        return time;

    }

    public void sendLoginMail(User user) {
        String msg = String.format("Hi, %s! You are logged in at %s", user.getName(), getTime());
        logger.info(msg, getClass().getSimpleName());
        System.err.println(msg + " host:"+ smtpHost +":" + smtpPort);
    }

    public void sendRegistrationMail(User user) {
        String msg = String.format("Welcome, %s!", user.getName());
        logger.info(msg, getClass().getSimpleName());
        System.err.println(msg);

    }
}
