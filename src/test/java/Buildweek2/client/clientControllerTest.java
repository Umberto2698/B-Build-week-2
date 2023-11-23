package Buildweek2.client;

import Buildweek2.authorization.AuthService;
import Buildweek2.client.payloads.ChangeClientInfoDTO;
import Buildweek2.client.payloads.NewClientDTO;
import Buildweek2.user.User;
import Buildweek2.user.UserService;
import Buildweek2.user.payloads.UserDTO;
import Buildweek2.user.payloads.UserLoginDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
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

import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class clientControllerTest {
    private static final String ENDPOINT = "/clients";
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AuthService authService;
    @Autowired
    private ClientService clientService;

    @Autowired
    private UserService userService;
    @Autowired
    private Faker faker;
    private Client client;
    private User user;
    private String token;
    private ChangeClientInfoDTO changes;

    @BeforeEach
    public void setUp() {
        UserDTO userDTO = new UserDTO("bob", "bob", "bob", "bob@gmail.com", "bob");
        user = authService.save(userDTO);
        UserLoginDTO userLoginDTO = new UserLoginDTO("bob@gmail.com", "bob");
        token = authService.authenticateUser(userLoginDTO);
        NewClientDTO randomClient = new NewClientDTO("client", "client", "client@gmail.com", faker.phoneNumber().phoneNumber(), faker.phoneNumber().phoneNumber()
                , new Date(), LocalDate.now(), "companyName", String.valueOf(new Random().nextLong(10000000000L, 100000000000L)), "company@gmail.com"
                , new Random().nextLong(10000000L, 100000000000L), "pec@pec.it", faker.company().logo(), BusinessName.SAS.name());
        client = authService.save(randomClient);
        changes = new ChangeClientInfoDTO("client", "client", "client@gmail.com", faker.phoneNumber().phoneNumber(), faker.phoneNumber().phoneNumber()
                , "company11@gmail.com"
                , "pec@pec.it");
    }

    @AfterEach
    public void afterAll() {
        userService.delete(user.getId());
        if (client != null) {
            clientService.removeClient(client.getId());
        }
    }

    @Test
    public void clientIsNotNull() {
        Assertions.assertNotNull(client);
    }

    @Test
    public void getClientByIdReturnOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/{id}", client.getId()).header("Authorization", "Bearer " + token)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
    }

    @Test
    public void getClientByIdReturnNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/{id}", -1L).header("Authorization", "Bearer " + token)
        ).andExpect(MockMvcResultMatchers.status().isNotFound()).andDo(print());
    }

    @Test
    public void getClientByIdReturnUnauthorize() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/{id}", client.getId()).header("Authorization", "Bearer " + "ccc")
        ).andExpect(MockMvcResultMatchers.status().isUnauthorized()).andDo(print());
    }

    @Test
    public void removeClientByIdReturnNoContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(ENDPOINT + "/{id}", client.getId()).header("Authorization", "Bearer " + token)
        ).andExpect(MockMvcResultMatchers.status().isNoContent()).andDo(print());
        client = null;
    }

    @Test
    public void removeClientByIdReturnNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(ENDPOINT + "/{id}", -1L).header("Authorization", "Bearer " + token)
        ).andExpect(MockMvcResultMatchers.status().isNotFound()).andDo(print());
        client = null;
    }

    @Test
    public void removeClientByIdReturnUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(ENDPOINT + "/{id}", client.getId()).header("Authorization", "Bearer " + "token")
        ).andExpect(MockMvcResultMatchers.status().isUnauthorized()).andDo(print());
        client = null;
    }

    @Test
    public void updateClientByIdReturnOk() throws Exception {
        String requestBody = objectMapper.writeValueAsString(changes);
        mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT + "/changeclientinfo/{id}", client.getId()).header("Authorization", "Bearer " + token)
                .content(requestBody).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
    }

    @Test
    public void updateClientByIdReturnNotFound() throws Exception {
        String requestBody = objectMapper.writeValueAsString(changes);
        mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT + "/changeclientinfo/{id}", -1L).header("Authorization", "Bearer " + token)
                .content(requestBody).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound()).andDo(print());
    }

    @Test
    public void updateClientByIdReturnUnauthorized() throws Exception {
        String requestBody = objectMapper.writeValueAsString(changes);
        mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT + "/changeclientinfo/{id}", client.getId()).header("Authorization", "Bearer " + "token")
                .content(requestBody).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isUnauthorized()).andDo(print());
    }
}

