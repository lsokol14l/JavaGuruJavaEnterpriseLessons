package by.michael.spring.mapper;

import by.michael.spring.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class UserMapper {
  private final UserDto userDto;
}
