package Buildweek2.user;

import Buildweek2.exceptions.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  public User getById(Long id) {
    return userRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
  }
}
