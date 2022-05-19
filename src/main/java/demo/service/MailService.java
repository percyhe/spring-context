package demo.service;

import org.slf4j.LoggerFactory;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class MailService {
    private ZoneId zoneId = ZoneId.systemDefault();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void setZoneId(ZoneId zoneId) {
        this.zoneId = zoneId;
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
        System.err.println(msg);
    }

    public void sendRegistrationMail(User user) {
        String msg = String.format("Welcome, %s!", user.getName());
        logger.info(msg, getClass().getSimpleName());
        System.err.println(msg);

    }
}
