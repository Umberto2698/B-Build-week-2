package Buildweek2.BillsTests;

import Buildweek2.authorization.AuthService;
import Buildweek2.bill.Bill;
import Buildweek2.bill.BillService;
import Buildweek2.bill.BillState;
import Buildweek2.bill.Payloads.BillDTO;
import Buildweek2.client.BusinessName;
import Buildweek2.client.Client;
import Buildweek2.client.ClientService;
import Buildweek2.client.payloads.NewClientDTO;
import Buildweek2.user.User;
import Buildweek2.user.UserRole;
import Buildweek2.user.UserService;
import Buildweek2.user.payloads.UserDTO;
import Buildweek2.user.payloads.UserLoginDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
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
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class BillsControllerTest {
  @Autowired
  ClientService clientService;
  @Autowired
  AuthService authService;
  @Autowired
  UserService userService;
  @Autowired
  BillService billService;
  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  MockMvc mockMvc;
  private UserDTO userDTO;
  private User user;
  private BillDTO billDTO;
  private Client client;
  private Bill bill;

  private String token;

  @BeforeEach
  public void setUp() {
    userDTO = new UserDTO("Franck", "Johnson", "1234", "user123456789@gmail.com", "Franco");
    user = authService.save(userDTO);
    user.setRole(UserRole.ADMIN);
    userService.save(user);
    UserLoginDTO userLoginDTO = new UserLoginDTO("user123456789@gmail.com", "1234");
    token = authService.authenticateUser(userLoginDTO);

    client = authService.save(new NewClientDTO("clientName", "clientSurname",
            "client12233@gmail.com", "3456780900", "3456780900"
            , new Date(), LocalDate.now(), "companyName", "129312L", "companyName3@gmail.com"
            , 8978L, "dasdsad@pec.it", "logo", BusinessName.SAS.name()));
    billDTO = new BillDTO(29923L, client.getId(), BillState.UNPAID.name());
    bill = billService.save(user.getId(), billDTO);
  }

  @AfterEach
  public void afterAll() {

    List<Bill> bills = billService.getBillsListForClient(client.getId());
    bills.forEach(bill1 -> billService.findAndDeleteById(bill1.getId()));
    clientService.removeClient(client.getId());
    userService.delete(user.getId());
  }

  //-------------------------POST-------------------------------
  @Test
  public void testEndPointPostReturn200OK() throws Exception {
    String body = objectMapper.writeValueAsString(billDTO);
    mockMvc.perform(MockMvcRequestBuilders.post("/bills")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body)
                    .header("Authorization", "Bearer " + token))
            .andExpect(MockMvcResultMatchers.status().isCreated()).andDo(print());
  }

  @Test
  public void testEndPointPostReturn404() throws Exception {
    billDTO = new BillDTO(29923L, -2L, BillState.UNPAID.name());
    String body = objectMapper.writeValueAsString(billDTO);
    mockMvc.perform(MockMvcRequestBuilders.post("/bills")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body)
                    .header("Authorization", "Bearer " + token))
            .andExpect(MockMvcResultMatchers.status().isNotFound()).andDo(print());
  }

  @Test
  public void testEndPointPostReturn401() throws Exception {
    billDTO = new BillDTO(29923L, client.getId(), BillState.UNPAID.name());
    String body = objectMapper.writeValueAsString(billDTO);
    mockMvc.perform(MockMvcRequestBuilders.post("/bills")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body)
                    .header("Authorization", "Bearer "))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized()).andDo(print());
  }

  @Test
  public void testEndPointPostReturn400() throws Exception {
    billDTO = new BillDTO(29923L, client.getId(), "");
    String body = objectMapper.writeValueAsString(billDTO);
    mockMvc.perform(MockMvcRequestBuilders.post("/bills")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body)
                    .header("Authorization", "Bearer " + token))
            .andExpect(MockMvcResultMatchers.status().isBadRequest()).andDo(print());
  }

  //-------------------------GET BILLS-------------------------------
  @Test
  public void testEndPointGetBillsPAgeReturn200Ok() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/bills")
                    .header("Authorization", "Bearer " + token))
            .andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
  }

  @Test
  public void testEndPointGetBillsPAgeReturn401() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/bills")
                    .header("Authorization", "Bearer "))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized()).andDo(print());
  }

  //-------------------------GET PAGE BIlls BY ID-------------------------------
  @Test
  public void testgetPagesByClientIdReturn200Ok() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/bills/clientBill/" + client.getId())
                    .header("Authorization", "Bearer " + token))
            .andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
  }

  @Test
  public void testgetPagesByClientIdReturn404() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/bills/clientbill/" + -2L)
                    .header("Authorization", "Bearer " + token))
            .andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
  }

  @Test
  public void testgetPagesByClientIdReturn401() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/bills/clientbill/" + client.getId())
                    .header("Authorization", "Bearer "))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized()).andDo(print());
  }


  //-------------------------GET LIST BIllS BY ID-------------------------------
  @Test
  public void testGetBillsListByIdReturn200Ok() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/bills/clientBillsList/" + client.getId())
                    .header("Authorization", "Bearer " + token))
            .andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
  }

  @Test
  public void testGetBillsListByClientIDreturn404() throws Exception {
    billDTO = new BillDTO(29923L, -1L, "");
    mockMvc.perform(MockMvcRequestBuilders.get("/bills/clientbilllist/" + client.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer " + token))
            .andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
  }

  @Test
  public void testGetBillsListByClientIDreturn401() throws Exception {
    billDTO = new BillDTO(29923L, -1L, "");
    mockMvc.perform(MockMvcRequestBuilders.get("/bills/clientbilllist/" + client.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer "))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized()).andDo(print());
  }

  //-------------------------GET PAID AND UNPAID BIlls-------------------------------

  @Test
  public void testgetListPaidBillsReturn200() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/bills/paidBills")
                    .header("Authorization", "Bearer " + token))
            .andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
  }

  @Test
  public void testgetListPaidBillsReturn401() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/bills/paidBills")
                    .header("Authorization", "Bearer "))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized()).andDo(print());
  }

  @Test
  public void testgetListUnpaidBillsReturn200() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/bills/unpaidBills")
                    .header("Authorization", "Bearer " + token))
            .andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
  }

  @Test
  public void testgetListUnpaidBillsReturn401() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/bills/unpaidBills")
                    .header("Authorization", "Bearer "))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized()).andDo(print());
  }

  //-------------------------GET BILLs BY DATE-------------------------------
  @Test
  public void testgetBillsperdateReturn200() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/bills/billsperdate")
                    .header("Authorization", "Bearer " + token))
            .andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
  }

  @Test
  public void testgetBillsperdateReturn401() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/bills/billsperdate")
                    .header("Authorization", "Bearer "))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized()).andDo(print());
  }

  //-------------------------DELETE BILL BY ID-------------------------------
  @Test
  public void testDELETEBillReturn204() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete("/bills/" + bill.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer "))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized()).andDo(print());
  }

  @Test
  public void testDELETEBillReturn404() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete("/bills/" + -2L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer "))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized()).andDo(print());
  }

  @Test
  public void testDELETEBillReturn401() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete("/bills/" + bill.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer "))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized()).andDo(print());
  }

}
