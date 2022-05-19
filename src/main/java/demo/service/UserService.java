package demo.service;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.*;
import java.util.List;

import java.util.ArrayList;

@Component
public class UserService {

    @Autowired
    private MailService mailService;

    @Autowired
    private DataSource dataSource;



    public void setMailService(@Autowired MailService mailService) {

        this.mailService = mailService;
    }

  /*  public void setDataSource(@Autowired DataSource dataSource) {
        this.dataSource = dataSource;
    }*/

    private List<User> users = new ArrayList<>(
            List.of(
                    new User(1, "bob@example.com", "password", "Bob"), // bob
                    new User(2, "alice@example.com", "password", "Alice"), // alice
                    new User(3, "tom@example.com", "password", "Tom")
            )
    );

    /**
     * 用户登录逻辑
     *
     * @param email
     * @param password
     * @return
     */
    public User login(String email, String password) {

        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("select  * from user where email=? and password=?");
            ps.setObject(1, email);
            ps.setObject(2, password);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                String emails = rs.getString("email");
                String passwords = rs.getString("password");
                String name = rs.getString("name");
                User user = new User(id, emails, passwords, name);
                mailService.sendLoginMail(user);
                return user;

            }
        } catch (SQLException e) {

        }

        throw new RuntimeException("login failed");

    }

    /**
     * 根据ID获取用户信息
     *
     * @param id
     * @return
     */
    public User getUser(long id) {
        return this.users.stream().filter(user -> user.getId() == id).findFirst().orElseThrow();
    }

    /**
     * 用户注册逻辑
     *
     * @param email
     * @param password
     * @param name
     * @return
     */
    public User register(String email, String password, String name) {
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("select  * from user where email=?");
            ps.setObject(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                throw new RuntimeException("email exist.");
            }
            PreparedStatement pss = conn.prepareStatement(
                    "INSERT INTO user (email, password, name) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            pss.setObject(1, email);
            pss.setObject(2, password);
            pss.setObject(3, name);
            int n = pss.executeUpdate();
            ResultSet rss = pss.getGeneratedKeys();
            if (rss.next()) {
                long dbId = rss.getLong(1);
                User user = new User(dbId, email, password, name);
                mailService.sendRegistrationMail(user);
                return user;
            }


        } catch (SQLException e) {

        }

        throw new RuntimeException("register error");

    }


}
