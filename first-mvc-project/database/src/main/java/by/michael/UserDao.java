package by.michael;

import java.util.Optional;

public class UserDao {
  public Optional<User> findById(Long id) {
    User user = new User("Anatolyi");

    return Optional.of(user);
  }
}
