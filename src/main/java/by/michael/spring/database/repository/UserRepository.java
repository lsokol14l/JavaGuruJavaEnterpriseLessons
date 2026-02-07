package by.michael.spring.database.repository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class UserRepository {
  private String username;
  private int poolSize;
  private List<Object> args;
  private Map<String, Object> properties;
}
