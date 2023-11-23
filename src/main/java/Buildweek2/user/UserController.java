package Buildweek2.user;

import Buildweek2.authorization.AuthService;
import Buildweek2.exceptions.BadRequestException;
import Buildweek2.user.payloads.RoleUpdateDTO;
import Buildweek2.user.payloads.UserUpdateInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
  @Autowired
  private UserService userService;

  @Autowired
  private AuthService authService;

  @GetMapping("")
  @PreAuthorize("hasAuthority('ADMIN')")
  public Page<User> getUsers(@RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id") String orderBy) {
    return userService.getUsers(page, size, orderBy);
  }

  @GetMapping("/all")
  @PreAuthorize("hasAuthority('ADMIN')")
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public User getUser(@PathVariable long id) {
    return userService.getById(id);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteUser(@PathVariable long id) {
    userService.delete(id);
  }

  @PatchMapping("/upload/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public User updateUserPicture(@RequestParam("avatar") MultipartFile body, @PathVariable long id) throws IOException {
    return userService.uploadPicture(body, id);
  }

  @PutMapping("/role/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public User updateRole(@PathVariable long id, @RequestBody @Validated RoleUpdateDTO body, BindingResult validation) {
    if (validation.hasErrors()) {
      throw new BadRequestException("", validation.getAllErrors());
    } else {
      return authService.updateRole(id, body);
    }
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public User updateUserInfo(@PathVariable long id, @RequestBody @Validated UserUpdateInfoDTO body,
      BindingResult validation) {
    if (validation.hasErrors()) {
      throw new BadRequestException("", validation.getAllErrors());
    } else {
      return authService.update(id, body);
    }
  }

  @PutMapping("/me")
  public UserDetails updateProfile(@AuthenticationPrincipal User currentUser, @RequestBody UserUpdateInfoDTO body) {
    return authService.update(currentUser.getId(), body);
  }

  @GetMapping("/me")
  @ResponseStatus(HttpStatus.OK)
  public User getProfile(@AuthenticationPrincipal User currentUser) {
    return currentUser;
  }

  @DeleteMapping("/me")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteProfile(@AuthenticationPrincipal User currentUser) {
    userService.delete(currentUser.getId());
  }

  @PatchMapping("/upload/me")
  @ResponseStatus(HttpStatus.OK)
  public User updateProfilePicture(@RequestParam("avatar") MultipartFile body,
      @AuthenticationPrincipal User currentUser) throws IOException {
    return userService.uploadPicture(body, currentUser.getId());
  }
}
