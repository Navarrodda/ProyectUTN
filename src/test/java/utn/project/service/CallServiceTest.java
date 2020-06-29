package utn.project.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import utn.project.domain.*;
import utn.project.domain.enums.UserType;
import utn.project.dto.BroadcastCall;
import utn.project.dto.UpdateUserDto;
import utn.project.exceptions.UserException;
import utn.project.exceptions.ValidationException;
import utn.project.projections.CallDate;
import utn.project.projections.CallMore;
import utn.project.projections.CallUser;
import utn.project.repository.CallRepository;
import utn.project.repository.UserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CallServiceTest {

    @Mock
    CallRepository callRepositoryMock;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    CallService callService;

    @Before
    public void setUp() {
        initMocks(this);
    }

    private User createUser() {
        return User.builder()
                .dni("388312")
                .password("123")
                .city(new City())
                .name("david")
                .surname("fuimos")
                .userType(UserType.CUSTOMER)
                .userName("navarrodda")
                .id(1)
                .build();
    }

    private Call createCall() {
        return Call.builder()
                .id(1)
                .totalPrice(new Float(200))
                .duration(new Float(2000))
                .destinyPhone(new PhoneLines())
                .phoneLines(new PhoneLines())
                .date(new Date())
                .tariff(new Tariff())
                .build();
    }

    private BroadcastCall createBroadcastCall() {
        BroadcastCall broad = new BroadcastCall();
        broad.setDuration(new Float(20));
        broad.setOriginPhone("55555555");
        broad.setPhoneNumberDestiny("22222222");
        return broad;
    }

    @Test
    public void getUserCallsOkTest() throws UserException {
        Integer id = 1;
        User u = createUser();
        Call call = createCall();
        List<CallUser> list = new ArrayList<>();
        when(this.userRepository.getById(id)).thenReturn(u);
        when(this.callRepositoryMock.getCallUser(id)).thenReturn(list);
        ResponseEntity<List<CallUser>> response = this.callService.getCallsUser(id);
        Assert.assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
    }

    @Test
    public void getUserCallsNoContentTest() throws UserException {
        Integer id = 1;
        User user = createUser();
        List<CallUser> list = new ArrayList<>();
        when(this.userRepository.getById(id)).thenReturn(user);
        when(this.callRepositoryMock.getCallUser(id)).thenReturn(list);
        ResponseEntity<List<CallUser>> response = this.callService.getCallsUser(id);
        Assert.assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
    }

    @Test(expected = UserException.class)
    public void getUserCallsNullTest() throws UserException {
        Integer id = 1;
        User u = createUser();
        List<Call> list = new ArrayList<>();
        when(this.userRepository.getById(id)).thenReturn(null);
        ResponseEntity<List<CallUser>> response = this.callService.getCallsUser(id);
    }

    @Test
    public void getLocalitiesToByCallIdUserOkTest(){
        Integer id = 1;
        City city = new City();
        List<CallUser> list = new ArrayList<>();
        when(this.callRepositoryMock.getCallUser(id)).thenReturn(list);
        ResponseEntity<List<CallMore>> response = this.callService.getCallsMoreCity(id);
        Assert.assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
    }

    @Test
    public void getLocalitiesToByCallIdUserNoContentTest() throws UserException {
        Integer id = 1;
        List<CallUser> list = new ArrayList<>();
        when(this.callRepositoryMock.getCallUser(id)).thenReturn(list);
    }

    @Test
    public void getCallsBtwDatesByUserOkTest() throws UserException {
        String startDate = "inicio";
        String finalDate = "fin";
        Integer id = 1;
        User u = createUser();
        Call call = createCall();
        List<CallDate> list = new ArrayList<>();
        when(this.userRepository.getById(id)).thenReturn(u);
        when(this.callRepositoryMock.getCallsBtwDatesByUser(id, startDate, finalDate)).thenReturn(list);
        ResponseEntity<List<CallDate>> response = this.callService.getCallsBtwDatesByUser(id, startDate, finalDate);
        Assert.assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
    }

    @Test
    public void getCallsBtwDatesByUserNoContentTest() throws UserException {
        String startDate = "inicio";
        String finalDate = "fin";
        Integer id = 1;
        User u = createUser();
        Call call = createCall();
        List<CallDate> list = new ArrayList<>();
        when(this.userRepository.getById(id)).thenReturn(u);
        when(this.callRepositoryMock.getCallsBtwDatesByUser(id, startDate, finalDate)).thenReturn(list);
        ResponseEntity<List<CallDate>> response = this.callService.getCallsBtwDatesByUser(id, startDate, finalDate);
        Assert.assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
    }


    @Test
    public void getCallsBtwDatesOkTest(){
        String startDate = "inicio";
        String finalDate = "fin";
        Call call = createCall();
        List<Call> list = new ArrayList<>();
        list.add(call);
        when(this.callRepositoryMock.getCallsBetweenDates(startDate, finalDate)).thenReturn(list);
        ResponseEntity<List<Call>> response = this.callService.getCallsBetweenDates(startDate, finalDate);
        Assert.assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void getCallsBtwDatesNoContentTest(){
        String startDate = "inicio";
        String finalDate = "fin";
        List<Call> list = new ArrayList<>();
        when(this.callRepositoryMock.getCallsBetweenDates(startDate, finalDate)).thenReturn(list);
        ResponseEntity<List<Call>> response = this.callService.getCallsBetweenDates(startDate, finalDate);
        Assert.assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
    }

    @Test
    public void addCallOkTest() throws ValidationException {
        Call call = createCall();
        BroadcastCall call2 = createBroadcastCall();
        when(this.callRepositoryMock.save(call)).thenReturn(call);
    }

}
