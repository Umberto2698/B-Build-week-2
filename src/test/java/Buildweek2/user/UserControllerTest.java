package Buildweek2.user;

import Buildweek2.authorization.AuthService;
import Buildweek2.user.payloads.RoleUpdateDTO;
import Buildweek2.user.payloads.UserDTO;
import Buildweek2.user.payloads.UserLoginDTO;
import Buildweek2.user.payloads.UserUpdateInfoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
  private static final String ENDPOINT = "/users";
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private UserService userService;
  @Autowired
  private AuthService authService;
  private User user;
  private UserDTO userDTO;
  private UserLoginDTO userLoginDTO;
  private UserUpdateInfoDTO userUpdateInfoDTO;

  private RoleUpdateDTO roleUpdateDTO;
  private String token;

  @BeforeEach
  public void setUp() {
    userDTO = new UserDTO("Franck", "Johnson", "1234", "test@gmail.com", "Franco");
    user = authService.save(userDTO);
    userLoginDTO = new UserLoginDTO("test@gmail.com", "1234");
    userUpdateInfoDTO = new UserUpdateInfoDTO("gigi", "gigi", "gigi@gmail.com", "gigi", "gigi");
    roleUpdateDTO = new RoleUpdateDTO(UserRole.USER.name());
    token = authService.authenticateUser(userLoginDTO);
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
  public void getUserDetailsReturnOk() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/{id}", user.getId()).header("Authorization", "Bearer " + token))
            .andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
  }

  @Test
  public void getUserDetailsReturnNotFound() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/{id}", -1L).header("Authorization", "Bearer " + token))
            .andExpect(MockMvcResultMatchers.status().isNotFound()).andDo(print());
  }

  @Test
  public void deleteUserReturnNotFound() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete(ENDPOINT + "/{id}", -1L).header("Authorization", "Bearer " + token))
            .andExpect(MockMvcResultMatchers.status().isNotFound()).andDo(print());
  }

  @Test
  public void deleteUserReturnNoContent() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete(ENDPOINT + "/{id}", user.getId()).header("Authorization", "Bearer " + token))
            .andExpect(MockMvcResultMatchers.status().isNoContent()).andDo(print());
    user = null;
  }

  @Test
  public void getCurrentProfileReturnOk() throws Exception {
    String requestBody = objectMapper.writeValueAsString(user);
    mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/me").header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON).content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
  }

  @Test
  public void getCurrentProfileReturnUnauthorized() throws Exception {
    String requestBody = objectMapper.writeValueAsString(user);
    mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/me").header("Authorization", "Bearer " + "cicici")
                    .contentType(MediaType.APPLICATION_JSON).content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized()).andDo(print());
  }

  @Test
  public void deleteCurrentProfileReturnUnauthorized() throws Exception {
    String requestBody = objectMapper.writeValueAsString(user);
    mockMvc.perform(MockMvcRequestBuilders.delete(ENDPOINT + "/me").header("Authorization", "Bearer " + "cicici")
                    .contentType(MediaType.APPLICATION_JSON).content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized()).andDo(print());
  }

  @Test
  public void uploadCurrentProfilePictureReturnUnauthorized() throws Exception {
    String requestBody = objectMapper.writeValueAsString(user);
    mockMvc.perform(MockMvcRequestBuilders.delete(ENDPOINT + "/me").header("Authorization", "Bearer " + "cicici")
                    .contentType(MediaType.APPLICATION_JSON).content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized()).andDo(print());
  }

  @Test
  public void updateCurrentProfileReturnOk() throws Exception {
    String requestBody = objectMapper.writeValueAsString(userUpdateInfoDTO);
    System.out.println(user);
    mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT + "/me").header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON).content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
  }

  @Test
  public void updateCurrentProfileReturnUnauthorized() throws Exception {
    String requestBody = objectMapper.writeValueAsString(userUpdateInfoDTO);
    System.out.println(user);
    mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT + "/me").header("Authorization", "Bearer " + "cicic")
                    .contentType(MediaType.APPLICATION_JSON).content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized()).andDo(print());
  }

  @Test
  public void updateUserInfoReturnUnauthorized() throws Exception {
    String requestBody = objectMapper.writeValueAsString(userUpdateInfoDTO);
    System.out.println(user);
    mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT + "/{id}", user.getId()).header("Authorization", "Bearer " + "cicic")
                    .contentType(MediaType.APPLICATION_JSON).content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized()).andDo(print());
  }

  @Test
  public void updateUserInfoReturnOk() throws Exception {
    String requestBody = objectMapper.writeValueAsString(userUpdateInfoDTO);
    System.out.println(user);
    mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT + "/{id}", user.getId()).header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON).content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
  }

  @Test
  public void updateUserInfoReturnNotFound() throws Exception {
    String requestBody = objectMapper.writeValueAsString(userUpdateInfoDTO);
    System.out.println(user);
    mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT + "/{id}", -1L).header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON).content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isNotFound()).andDo(print());
  }

  @Test
  public void updateUserRoleReturnUnauthorized() throws Exception {
    String requestBody = objectMapper.writeValueAsString(roleUpdateDTO);
    System.out.println(user);
    mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT + "/{id}", user.getId()).header("Authorization", "Bearer " + "cicic")
                    .contentType(MediaType.APPLICATION_JSON).content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized()).andDo(print());
  }

  @Test
  public void updateUserRoleReturnOk() throws Exception {
    String requestBody = objectMapper.writeValueAsString(roleUpdateDTO);
    System.out.println(user);
    mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT + "/role/{id}", user.getId()).header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON).content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
  }

  @Test
  public void updateUserRoleReturnNotFound() throws Exception {
    String requestBody = objectMapper.writeValueAsString(roleUpdateDTO);
    System.out.println(user);
    mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT + "/role/{id}", -1L).header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON).content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isNotFound()).andDo(print());
  }
}
