package by.michael.spring;

import by.michael.spring.database.repository.UserRepository;
import by.michael.spring.dto.UserDto;
import by.michael.spring.ioc.Container;
import by.michael.spring.mapper.UserMapper;
import by.michael.spring.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeUnit;

public class SpringRunner {
  public static void main(String[] args) {
    var context = new ClassPathXmlApplicationContext("application.xml");
    var repo1 = context.getBean("repo1");
    var repo2 = context.getBean("repo2");
    System.out.println(repo1);
    System.out.println(repo2);

    var userService = context.getBean(UserService.class);
    System.out.println(userService);
  }
}
