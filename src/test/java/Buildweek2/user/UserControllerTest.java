package Buildweek2.user;

import Buildweek2.authorization.AuthService;
import Buildweek2.user.payloads.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    private static final String ENDPOINT = "/users";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;
    private User user;
    private UserDTO userDTO;
    private UserDetails userDetails;

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


    //*************************** PROBLEMS HERE ****************************
    @Test
    public void getUserDetailsReturnCurrentUser() throws Exception {
//        Mockito.when(userService.getById(user.getId())).thenReturn(user);
        String requestBody = objectMapper.writeValueAsString(user);
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", user.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
