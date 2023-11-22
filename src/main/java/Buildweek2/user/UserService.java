package Buildweek2.user;

import Buildweek2.exceptions.NotFoundException;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private Cloudinary cloudinary;

  public User save(User user) {
    return userRepository.save(user);
  }

  public Page<User> getUsers(int page, int size, String orderBy) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
    return userRepository.findAll(pageable);
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public User findByEmail(String email) {
    return userRepository.findByEmail(email)
            .orElseThrow(() -> new NotFoundException(email));
  }

  public User getById(long id) {
    return userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
  }

  public void delete(long id) {
    User found = this.getById(id);
    userRepository.delete(found);
  }

  public User uploadPicture(MultipartFile file, long id) throws IOException {
    User found = this.getById(id);
    String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
    found.setAvatarUrl(url);
    return userRepository.save(found);
  }
}
