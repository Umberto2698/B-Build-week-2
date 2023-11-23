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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

@WebMvcTest(UserController.class)
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
    private User savedUser;
    private UserDTO userDTO;
    private UserLoginDTO userLoginDTO;
    private String token;

    @BeforeEach
    public void setUp() {
        userDTO = new UserDTO("admin", "admin", "1234", "a@gmail.com", "admin");
        User user = User.builder().name("Franck").surname("Johnson").email("test@gmail.com").username("Franco").password("1234").build();
        userLoginDTO = new UserLoginDTO("a@gmail.com", "1234");
        when(userRepository.save(user)).thenReturn(user);
        when(authService.save(userDTO)).thenReturn(user);
        when(authService.authenticateUser(userLoginDTO)).thenReturn(token);
    }

    @AfterEach
    public void afterAll() {
        if (savedUser != null) {
            userService.delete(savedUser.getId());
        }
    }

    @Test
    public void userIsNotNull() {
        User savedUser = authService.save(userDTO);
        Assertions.assertNotNull(savedUser);
    }


    //*************************** PROBLEMS HERE ****************************
    @Test
    public void getUserDetailsReturnCurrentUser() throws Exception {
        User savedUser = authService.save(userDTO);
        String token = authService.authenticateUser(userLoginDTO);
        System.out.println(token);
        when(userService.getById(savedUser.getId())).thenReturn(savedUser);
        String requestBody = objectMapper.writeValueAsString(savedUser);
        mockMvc.perform(MockMvcRequestBuilders.get("/users/" + savedUser.getId()).content(requestBody).header("Authorization", "Bearer" + token))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
