package utn.project.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import utn.project.domain.Bill;
import utn.project.exceptions.UserException;
import utn.project.service.BillService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class BillControllerTest {

    @Mock
    BillService billServiceMock;

    @InjectMocks
    BillController billController;

    @Before
    public void setUp(){initMocks(this);}

    @Test
    public void getBillsByIdUserTest() throws UserException {
        Integer id = 1;
        List<Bill> list = new ArrayList<>();
        list.add(new Bill());
        when(this.billServiceMock.getBillsByIdUser(id)).thenReturn(ResponseEntity.ok(list));
        ResponseEntity<List<Bill>> response = this.billController.getBillsByIdUser(id);
        Assert.assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void getBillsBtwDatesByIdUserTest() throws UserException {
        Integer idUser = 1;
        String firstDate = "first";
        String secondDate = "second";
        List<Bill> list = new ArrayList<>();
        list.add(new Bill());
        when(this.billServiceMock.getBillsBetweenDatesByIdUser(firstDate,secondDate,idUser)).thenReturn(ResponseEntity.ok(list));
        ResponseEntity<List<Bill>> response = this.billController.getBillsBetweenDatesByIdUser(firstDate,secondDate,idUser);
        Assert.assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void getBillsBtwDatesTest(){
        String firstDate = "first";
        String secondDate = "second";
        List<Bill> list = new ArrayList<>();
        list.add(new Bill());
        when(this.billServiceMock.getBillsBetweenDates(firstDate,secondDate)).thenReturn(ResponseEntity.ok(list));
        ResponseEntity<List<Bill>> response = this.billController.getBillsBetweenDates(firstDate,secondDate);
        Assert.assertEquals(HttpStatus.OK,response.getStatusCode());
    }
}
