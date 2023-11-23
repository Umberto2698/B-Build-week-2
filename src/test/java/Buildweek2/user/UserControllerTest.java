package Buildweek2.user;

import Buildweek2.authorization.AuthService;
import Buildweek2.security.JWTTools;
import Buildweek2.user.payloads.UserDTO;
import Buildweek2.user.payloads.UserLoginDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


//@WebMvcTest(UserController.class)
@SpringBootTest
@AutoConfigureMockMvc

public class UserControllerTest {
    private static final String ENDPOINT = "/users";
    @MockBean
    private JWTTools jwtTools;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private AuthService authService;
    @MockBean
    private UserRepository userRepository;
    private User user;
    private UserDTO userDTO;
    private UserLoginDTO userLoginDTO;
    private String token;

    @BeforeEach
    public void setUp() {
        userDTO = new UserDTO("Franck", "Johnson", "1234", "test@gmail.com", "Franco");
        user = authService.save(userDTO);
        System.out.println(user.getId());
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


    //*************************** PROBLEMS HERE ****************************
    @Test
    public void getUserDetailsReturnCurrentUser() throws Exception {
//        Mockito.when(userService.getById(user.getId())).thenReturn(user);
        String requestBody = objectMapper.writeValueAsString(user);
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", user.getId()).header("Authorization", "Bearer "))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
