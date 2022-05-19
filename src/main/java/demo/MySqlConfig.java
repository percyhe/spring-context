package demo;

import com.zaxxer.hikari.HikariConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 读取数据库配置类
 */
@Configuration
@PropertySource("jdbc.properties")
@Component
public class MySqlConfig {


    @Value("${account}")
    String username;
    @Value("${jdbcUrl}")
    String jdbcUrl;
    @Value("${password}")
    String password;
    @Value("${connectionTimeout}")
    String connectionTimeout;
    @Value("${idleTimeout}")
    String idleTimeout;
    @Value("${maximumPoolSize}")
    String maximumPoolSize;
    @Value("${autoCommit}")
    String autoCommit;

    public HikariConfig getConfig() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.addDataSourceProperty("connectionTimeout", connectionTimeout); // 连接超时：1秒
        config.addDataSourceProperty("idleTimeout", idleTimeout); // 空闲超时：60秒
        config.addDataSourceProperty("maximumPoolSize", maximumPoolSize); // 最大连接数：10
        config.addDataSourceProperty("autoCommit", autoCommit); // 最大连接数：10
        return config;
    }

}
