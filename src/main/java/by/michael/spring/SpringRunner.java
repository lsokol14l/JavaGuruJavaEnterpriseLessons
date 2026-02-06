package by.michael.spring;

import by.michael.spring.database.repository.UserRepository;
import by.michael.spring.dto.UserDto;
import by.michael.spring.ioc.Container;
import by.michael.spring.mapper.UserMapper;
import by.michael.spring.service.UserService;

public class SpringRunner {
  public static void main(String[] args) {
    //    UserDto userDto = new UserDto();
    //    UserMapper userMapper = new UserMapper(userDto);
    //    UserRepository userRepository = new UserRepository();

    //    UserService userService = new UserService(userMapper, userRepository);

    Container container = new Container();
    // Наш контейнер в методе get проанализирует наш класс, увидит 2 поля, найдет объекты этих
    // классов, их заsetит сам в UserService и отдаст нам сразу бин с проинициализированными полями
    UserService userService = container.get(UserService.class);
  }
}
