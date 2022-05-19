package demo;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import demo.service.User;
import demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.util.Properties;
import java.util.stream.Collectors;

@Configuration
@ComponentScan
@PropertySource("app.properties")  //读取配置文件
public class AppConfig {

  /*  @Value("${app.zone:Z}")
    String zoneId;*/

    @Autowired
    private MySqlConfig mySqlConfig;


    @SuppressWarnings("resource")
    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        UserService userService = context.getBean(UserService.class);
        User user = userService.login("bob@example.com", "password");
        System.out.println(user.getName());

        AppService appService = context.getBean(AppService.class);
        appService.printLogo();

//        User reg_user = userService.register("percy3@percy.com","123456","percy3");
//        System.out.println(reg_user.getName());

    }

    /**
     * 使用第三方Bean
     *
     * @return
     */
    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        HikariConfig config = mySqlConfig.getConfig();
        DataSource ds = new HikariDataSource(config);
        return ds;
    }

    //创建一个Bean Primary
/*    @Bean
    @Primary //指定为主要Bean
    @Qualifier("z")
    //别名
    ZoneId createZoneOfZ() {
        return ZoneId.of("Z");
    }

    @Bean
    @Qualifier("utc8")
    ZoneId createZoneOfUTC8() {
        return ZoneId.of("UTC+08:00");
    }*/

    /*    @Bean
        ZoneId createZoneId() {
            return ZoneId.of(zoneId);
        }*/
    @Bean
    @Primary //指定为主要Bean
    ZoneId createZoneId(@Value("${app.zone:Z}") String zoneId) {
        return ZoneId.of(zoneId);
    }


}
