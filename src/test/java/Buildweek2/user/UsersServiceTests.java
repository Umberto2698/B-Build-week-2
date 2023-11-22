package Buildweek2.user;

import Buildweek2.authorization.AuthService;
import Buildweek2.exceptions.BadRequestException;
import Buildweek2.exceptions.NotFoundException;
import Buildweek2.exceptions.UnauthorizedException;
import Buildweek2.user.payloads.UserDTO;
import Buildweek2.user.payloads.UserLoginDTO;
import Buildweek2.user.payloads.UserUpdateInfoDTO;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UsersServiceTests {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;
    @Autowired
    private Faker faker;
    private User user;
    private UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        userDTO = new UserDTO("Franck", "Johnson", "1234", "test@gmail.com", "Franco");
        user = authService.save(userDTO);
    }

    @AfterEach
    public void afterAll() {
        if (user != null) {
            userService.delete(user.getId());
        }
    }

    @Test
    public void userIsNotNull() {
        Assertions.assertNotNull(user);
    }

    @Test
    public void FindByEmailReturnNotFound() {
        Assertions.assertThrows(NotFoundException.class, () -> {
            userService.findByEmail("basdas@asdsd.com");
        });
    }

    @Test
    public void FindByIdReturnNotFound() {
        Assertions.assertThrows(NotFoundException.class, () -> userService.getById(-1L));
    }

    @Test
    public void FindByIdAndReturnCorrectUser() {
        User found = userService.getById(user.getId());
        Assertions.assertEquals(user, found);
    }

    @Test
    public void FindByIdAndUpdateReturnUpdatedUser() {
        UserUpdateInfoDTO updatedUser = new UserUpdateInfoDTO("Bob", "Bob", faker.internet().emailAddress(), "pic1", "1234");
        User updateUser = authService.update(user.getId(), updatedUser);
        Assertions.assertNotEquals(user, updateUser);
    }

    @Test
    public void DeleteByIdAndReturnNotFound() {
        userService.delete(user.getId());
        Assertions.assertThrows(NotFoundException.class, () -> userService.getById(user.getId()));
        user = null;
    }

    @Test
    public void RegisterUserUsingTheSameEmail() {
        Assertions.assertThrows(BadRequestException.class, () -> authService.save(userDTO));
    }

    @Test
    public void LoginUserWithInvalidPassword() {
        Assertions.assertThrows(UnauthorizedException.class, () -> authService.authenticateUser(new UserLoginDTO("test@gmail.com", "1")));
    }
}
