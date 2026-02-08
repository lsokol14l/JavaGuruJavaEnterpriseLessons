package by.michael;

import java.util.Optional;

public class UserService {
  private final UserDao userDao = new UserDao();

  public Optional<UserDto> getUser(Long userId) {
    return userDao.findById(userId).map(it -> new UserDto(it.getName()));
  }
}
