package utn.project.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import utn.project.domain.*;
import utn.project.dto.BroadcastCall;
import utn.project.exceptions.UserException;
import utn.project.exceptions.ValidationException;
import utn.project.projections.CallDate;
import utn.project.projections.CallMore;
import utn.project.projections.CallUser;
import utn.project.service.CallService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CallControllerTest {
    @Mock
    CallService callServiceMock;

    @InjectMocks
    CallController callController;

    @Before
    public void setUp(){initMocks(this);}

    private Call createCall() {
        return Call.builder()
                .id(1)
                .tariff(new Tariff())
                .destinyPhone(new PhoneLines())
                .destinyPhone(new PhoneLines())
                .date(new Date())
                .duration(new Float(20))
                .totalPrice(new Float(200))
                .build();
    }

    @Test
    public void getUserCallsOkTest() throws UserException {
        Integer id = 1;
        Call call = createCall();
        List<CallMore> list = new ArrayList<>();
        when(this.callServiceMock.getCallsMoreCity(id)).thenReturn(ResponseEntity.ok(list));
        ResponseEntity<List<CallUser>> response = this.callController.getCallsUser(id);
    }

    @Test
    public void getLocalitiesToByCallIdUserOkTest(){
        Integer id = 1;
        List<CallMore> list = new ArrayList<>();
        when(this.callServiceMock.getCallsMoreCity(id)).thenReturn(ResponseEntity.ok(list));
        ResponseEntity<List<CallMore>> response = this.callController.getCityToByCallIdUser(id);
        Assert.assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void getCallsBtwDatesByUserTest() throws UserException {
        String firstDate = "first";
        String secondDate = "second";
        Integer idUser = 1;
        Call call = createCall();
        List<CallDate> list = new ArrayList<>();
        when(this.callServiceMock.getCallsBtwDatesByUser(idUser,firstDate,secondDate)).thenReturn(ResponseEntity.ok(list));
        ResponseEntity<List<Call>> response = this.callController.getCallsBetweenDatesByUser(firstDate,secondDate,idUser);
    }

    @Test
    public void getCallsBtwDatesTest() throws UserException {
        String firstDate = "first";
        String secondDate = "second";
        Call call = createCall();
        List<Call> list = new ArrayList<>();
        list.add(call);
        when(this.callServiceMock.getCallsBetweenDates(firstDate,secondDate)).thenReturn(ResponseEntity.ok(list));
        ResponseEntity<List<Call>> response = this.callController.getCallsBetweenDates(firstDate,secondDate);
        Assert.assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void getCallsBtwDatesNullTest() throws UserException {
        String firstDate = "first";
        String secondDate = "second";
        Call call = createCall();
        List<Call> list = new ArrayList<>();
        list.add(call);
        when(this.callServiceMock.getCallsBetweenDates(null,null)).thenReturn(ResponseEntity.ok(list));
        ResponseEntity<List<Call>> response = this.callController.getCallsBetweenDates(firstDate,secondDate);
        Assert.assertEquals(null,response);

    }

    @Test
    public void addCallTest() throws ValidationException {
        BroadcastCall calls = new BroadcastCall();
        Call call = createCall();
        when(this.callServiceMock.addCall(calls)).thenReturn(ResponseEntity.ok(call));
        ResponseEntity<Call> response = this.callController.addCall(calls);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void addCallNullTest() throws ValidationException {
        BroadcastCall calls = new BroadcastCall();
        Call call = createCall();
        when(this.callServiceMock.addCall(null)).thenReturn(ResponseEntity.ok(call));
        ResponseEntity<Call> response = this.callController.addCall(calls);
        Assert.assertEquals(null, response);
    }






}