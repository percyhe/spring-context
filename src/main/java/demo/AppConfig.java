package demo;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import demo.service.User;
import demo.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ComponentScan
public class AppConfig {
    @SuppressWarnings("resource")
    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        UserService userService = context.getBean(UserService.class);
        User user = userService.login("bob@example.com", "password");
        System.out.println(user.getName());

//        User reg_user = userService.register("percy3@percy.com","123456","percy3");
//        System.out.println(reg_user.getName());

    }

    /**
     * 使用第三方Bean
     * @return
     */
    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        config.setUsername("percy");
        config.setPassword("percy");
        config.addDataSourceProperty("connectionTimeout", "10000"); // 连接超时：1秒
        config.addDataSourceProperty("idleTimeout", "60000"); // 空闲超时：60秒
        config.addDataSourceProperty("maximumPoolSize", "10"); // 最大连接数：10
        config.addDataSourceProperty("autoCommit", "true"); // 最大连接数：10
        DataSource ds = new HikariDataSource(config);
        return ds;
    }
}
