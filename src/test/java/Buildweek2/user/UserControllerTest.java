package Buildweek2.user;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(UserController.class)
public class UserControllerTest {


    //    @Test
//    public void FindByIdAndUpdateThrowBadRequest() throws IOException {
//        UserUpdateInfoDTO updatedUser = new UserUpdateInfoDTO("Bob", "Bob", "notAnEmail", "pic1", "1234");
//        Assertions.assertThrows(BadRequestException.class, () -> authService.update(user.getId(), updatedUser));
//    }
}
