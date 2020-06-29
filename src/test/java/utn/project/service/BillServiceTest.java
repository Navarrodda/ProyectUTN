package utn.project.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import utn.project.domain.Bill;
import utn.project.domain.City;
import utn.project.domain.PhoneLines;
import utn.project.domain.User;
import utn.project.domain.enums.UserType;
import utn.project.exceptions.UserException;
import utn.project.repository.BillRepository;
import utn.project.repository.UserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class BillServiceTest {

    @Mock
    BillRepository billRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    BillService billService;

    @Before
    public void setUp() {
        initMocks(this);
    }

    private User createUser(){
        return User.builder()
                .dni("2236211294")
                .password("123")
                .city(new City())
                .name("david")
                .surname("nava")
                .userType(UserType.CUSTOMER)
                .userName("navdda")
                .id(1)
                .active(Boolean.TRUE)
                .build();
    }

    private Bill createBill() {
        return Bill.builder()
                .id(1)
                .cantCall(20)
                .date(new Date())
                .costMin(new Float(20))
                .numberCall(new PhoneLines())
                .priceCost(new Float(20))
                .totalCost(new Float(20))
                .paymentData(new Date())
                .build();
    }

    @Test
    public void getBillsByUserOkTest() throws UserException {
        Integer id = 1;
        User u = createUser();
        List<Bill> list = new ArrayList<>();
        Bill bill = createBill();
        list.add(bill);
        when(this.userRepository.getById(id)).thenReturn(u);
        when(this.billRepository.getBillsByIdUser(id)).thenReturn(list);
        ResponseEntity<List<Bill>> response = this.billService.getBillsByIdUser(id);
        Assert.assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void getBillsByUserEmptyBillTest()throws UserException{
        Integer id = 1;
        User u = createUser();
        List<Bill> list = new ArrayList<>();
        when(this.userRepository.getById(id)).thenReturn(u);
        when(this.billRepository.getBillsByIdUser(id)).thenReturn(list);
        ResponseEntity<List<Bill>> response = this.billService.getBillsByIdUser(id);
        Assert.assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
    }

    @Test(expected = UserException.class)
    public void getBillsByUserNullTest()throws UserException{
        Integer id = 1;
        User u = createUser();
        List<Bill> list = new ArrayList<>();
        when(this.userRepository.getById(id)).thenReturn(null);
        this.billService.getBillsByIdUser(id);
    }

    @Test
    public void getBillsBtwDatesByIdUserOkTest() throws UserException {
        String startDate = "inicio";
        String finalDate = "final";
        Integer id = 1;
        User u = createUser();
        List<Bill> list = new ArrayList<>();
        Bill bill = createBill();
        list.add(bill);
        when(this.userRepository.getById(id)).thenReturn(u);
        when(this.billRepository.findBillsBtwDatesByIdUser(startDate,finalDate,id)).thenReturn(list);
        ResponseEntity<List<Bill>> response = this.billService.getBillsBetweenDatesByIdUser(startDate,finalDate,id);
        Assert.assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void getBillsBtwDatesByIdUserEmptyBillTest() throws UserException{
        String startDate = "inicio";
        String finalDate = "final";
        Integer id = 1;
        User u = createUser();
        List<Bill> list = new ArrayList<>();
        when(this.userRepository.getById(id)).thenReturn(u);
        when(this.billRepository.findBillsBtwDatesByIdUser(startDate,finalDate,id)).thenReturn(list);
        ResponseEntity<List<Bill>> response = this.billService.getBillsBetweenDatesByIdUser(startDate,finalDate,id);
        Assert.assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
    }

    @Test(expected = UserException.class)
    public void getBillsBtwDatesByIdUserNullTest() throws UserException{
        String startDate = "inicio";
        String finalDate = "final";
        Integer id = 1;
        User u = createUser();
        List<Bill> list = new ArrayList<>();
        when(this.userRepository.getById(id)).thenReturn(null);
        this.billService.getBillsBetweenDatesByIdUser(startDate,finalDate,id);
    }

    @Test
    public void getBillsBtwDatesOkTest(){
        String startDate = "inicio";
        String finalDate = "final";
        List<Bill> list = new ArrayList<>();
        Bill bill = createBill();
        list.add(bill);
        when(this.billRepository.findBillsBtwDates(startDate,finalDate)).thenReturn(list);
        ResponseEntity<List<Bill>> response = this.billService.getBillsBetweenDates(startDate,finalDate);
        Assert.assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void getBillsBtwDates(){
        String startDate = "inicio";
        String finalDate = "final";
        List<Bill> list = new ArrayList<>();
        when(this.billRepository.findBillsBtwDates(startDate,finalDate)).thenReturn(list);
        ResponseEntity<List<Bill>> response = this.billService.getBillsBetweenDates(startDate,finalDate);
        Assert.assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
    }

}
