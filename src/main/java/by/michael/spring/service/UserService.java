package by.michael.spring.service;

import by.michael.spring.database.repository.UserRepository;
import by.michael.spring.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public class UserService {
  private final UserMapper userMapper;
  private final UserRepository userRepository;
}
