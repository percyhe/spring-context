package demo;

import demo.service.User;
import demo.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    @SuppressWarnings("resources")
    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
        UserService userService = context.getBean(UserService.class);
        User user = userService.login("bob@example.com","password");
        System.out.println(user.getName());

        User reg_user = userService.register("percy2@percy.com","123456","percy2");
        System.out.println(reg_user.getName());
    }
}
