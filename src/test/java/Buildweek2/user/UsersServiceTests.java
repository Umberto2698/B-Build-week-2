package Buildweek2.user;

import Buildweek2.authorization.AuthService;
import Buildweek2.exceptions.NotFoundException;
import Buildweek2.user.payloads.UserDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UsersServiceTests {
    @Autowired
    UserService us;
    @Autowired
    AuthService acs;
    private User user;
    private UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        userDTO = new UserDTO("Franck", "Johnson", "1234", "test@gmail.com", "Franco");
        user = acs.save(userDTO);
    }

    @AfterEach
    public void afterAll() {
        us.delete(user.getId());
    }

    @Test
    public void userIsNotNull() {
        Assertions.assertNotNull(user);
    }

    @Test
    public void FindByEmailReturnNotFound() throws Exception {
        Assertions.assertThrows(NotFoundException.class, () -> {
            us.findByEmail("basdas@asdsd.com");
        });
    }

//  @Test
//  public void FindByIdAndUpdateReturnUpdatedUser(){
//    U
//  }
}
