package Buildweek2.BillsTests;

import Buildweek2.authorization.AuthService;
import Buildweek2.bill.Bill;
import Buildweek2.bill.BillService;
import Buildweek2.bill.BillState;
import Buildweek2.bill.Payloads.BillDTO;
import Buildweek2.bill.Payloads.BillPachDTO;
import Buildweek2.client.BusinessName;
import Buildweek2.client.Client;
import Buildweek2.client.ClientService;
import Buildweek2.client.payloads.NewClientDTO;
import Buildweek2.exceptions.NotFoundException;
import Buildweek2.user.User;
import Buildweek2.user.UserRole;
import Buildweek2.user.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class BillsServiceTest {
  @Autowired
  ClientService cs;
  @Autowired
  AuthService ats;
  @Autowired
  UserService us;

  @Autowired
  BillService bs;
  private Client client;
  private User user;
  private BillDTO billDTO;
  private Bill bill;

  @BeforeEach
  public void setUp() {
    user = new User();
    user.setName("Admin");
    user.setSurname("Admin");
    user.setEmail("admin@gmai.com");
    user.setAvatarUrl("img");
    user.setPassword("1234");
    user.setRole(UserRole.ADMIN);
    us.save(user);
    client = ats.save(new NewClientDTO("clientName", "clientSurname", "as@gmail.com", "3287239805", "3287239805"
            , new Date(), LocalDate.now(), "companyName", "11111111111", "as2@gmail.com"
            , 129120291L, "pec@pec.it", "logo", BusinessName.SAS.name()));
    billDTO = new BillDTO(20000, client.getId(), BillState.PAID.name());
    bill = bs.save(user.getId(), billDTO);
  }

  @AfterEach
  public void afterAll() {
    bs.findAndDeleteById(bill.getId());
    cs.removeClient(client.getId());
    us.delete(user.getId());
  }

  @Test
  public void testBillNotNUll() {
    Assertions.assertNotNull(bill);
  }

  @Test
  public void testSaveBillReturnClientNotFound() {
    Assertions.assertThrows(NotFoundException.class, () -> {
      billDTO = new BillDTO(20000, 32423L, BillState.PAID.name());
      bs.save(user.getId(), billDTO);
    });
  }

  @Test
  public void testFindBillByidReturnNotFound() {
    Assertions.assertThrows(NotFoundException.class, () -> {
      bs.findById(234232L);
    });
  }

  @Test
  public void testFindBillByidReturnCorrectBill() {
    Bill found = bs.findById(bill.getId());
    Assertions.assertEquals(bill, found);
  }


  @Test
  public void testBillChangeState() {
    BillPachDTO billPachDTO = new BillPachDTO(BillState.UNPAID.name());
    Bill updatedBill = bs.changeStateBill(bill.getId(), billPachDTO);
    Assertions.assertEquals(BillState.UNPAID, updatedBill.getState());
  }

  @Test
  public void testBillChangeStateReturnNotFound() {
    BillPachDTO billPachDTO = new BillPachDTO(BillState.UNPAID.name());
    Assertions.assertThrows(NotFoundException.class, () -> {
      Bill updatedBill = bs.changeStateBill(2323L, billPachDTO);
    });
  }

  @Test
  public void testgetBillsByDate() {
    List<Bill> billList = bs.getBillsByDate(LocalDate.of(2020, 2, 2), LocalDate.now().plusYears(1));
    System.out.println(bill + "mia");
    billList.forEach(System.out::println);
    Assertions.assertTrue(billList.contains(bill));
  }

  @Test
  public void testgetBillsByYear() {
    List<Bill> billList = bs.getBillsByDate(LocalDate.of(2020, 2, 2), LocalDate.now().plusYears(1));
    System.out.println(bill + "mia");
    billList.forEach(System.out::println);
    Assertions.assertTrue(billList.contains(bill));
  }

  @Test
  public void testFindByRangeAmountReturnBill() {
    List<Bill> billList = bs.findByRangeAmount(100L, 200009L);
    System.out.println(bill + "mia");
    billList.forEach(System.out::println);
    Assertions.assertTrue(billList.contains(bill));
  }

  @Test
  public void testFindByPartialCompanyNameDTOReturnNotFound() {
    Assertions.assertThrows(NotFoundException.class, () -> {
      bs.findByPartialCompanyName("YASd");
    });
  }
}
