package utn.project.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import utn.project.domain.PhoneLines;
import utn.project.domain.Type;
import utn.project.domain.User;
import utn.project.domain.enums.LineStatus;
import utn.project.dto.PhoneDto;
import utn.project.exceptions.LostException;
import utn.project.exceptions.PhoneNotExistsException;
import utn.project.exceptions.UserException;
import utn.project.exceptions.ValidationException;
import utn.project.service.PhoneService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PhoneControllerTest {

    @Mock
    PhoneService phoneServiceMock;


    @InjectMocks
    PhoneController phoneController;

    @Before
    public void setUp(){initMocks(this);}

    private PhoneDto createPhoneDto(){
        PhoneDto dto = new PhoneDto();
        dto.setStatus(LineStatus.ENABLED.toString());
        dto.setPhoneNumber("2234444");
        dto.setTypePhone(new Type());
        dto.setUser(new User());
        return dto;
    }
    private Type createLineType(){
        return Type.builder()
                .id(1)
                .type("celular")
                .build();
    }

    private PhoneLines createPhoneLine(){
        return PhoneLines.builder()
                .id(2)
                .phoneNumber("223-31222455")
                .user(new User())
                .typePhone(createLineType())
                .status(LineStatus.ENABLED)
                .build();
    }

    @Test
    public void addOkTest() throws ValidationException, UserException {
        PhoneDto dto = createPhoneDto();
        PhoneLines phoneLine = createPhoneLine();
        when(this.phoneServiceMock.add(dto)).thenReturn(phoneLine);
        ResponseEntity<PhoneLines> response = this.phoneController.add(dto);
        Assert.assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void changeStatusDisabled() throws PhoneNotExistsException, ValidationException, LostException {
        String phoneNumber = "22333232";
        String status = "ENABLED";
        PhoneLines phoneLine = createPhoneLine();
        PhoneLines phone = new PhoneLines();
        phoneLine.setStatus(LineStatus.ENABLED);
        when(this.phoneServiceMock.getByPhoneNumber(phoneNumber)).thenReturn(phoneLine);
        ResponseEntity<ResponseEntity<PhoneLines>> responseEntity = this.phoneController.changeStatus(phoneLine.getPhoneNumber(),"ENABLE");
        Assert.assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }

    @Test
    public void changeStatusEnableTest() throws LostException, PhoneNotExistsException, ValidationException {
        String phoneNumber = "22333232";
        String status = "enable";
        PhoneLines phoneLine = createPhoneLine();
        phoneLine.setStatus(LineStatus.DISABLED);
        when(this.phoneServiceMock.getByPhoneNumber(phoneNumber)).thenReturn(phoneLine);
        ResponseEntity<ResponseEntity<PhoneLines>> responseEntity = this.phoneController.changeStatus(phoneNumber,status);
        Assert.assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }

    @Test
    public void changeStatusSuspendTest() throws LostException, PhoneNotExistsException, ValidationException {
        String phoneNumber = "22333232";
        String status = "suspend";
        PhoneLines phoneLine = createPhoneLine();
        phoneLine.setStatus(LineStatus.SUSPENDED);
        when(this.phoneServiceMock.getByPhoneNumber(phoneNumber)).thenReturn(phoneLine);
        ResponseEntity<ResponseEntity<PhoneLines>> responseEntity = this.phoneController.changeStatus(phoneNumber,status);
        Assert.assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }


    @Test
    public void deleteOkTest() throws LostException, PhoneNotExistsException, ValidationException, UserException {
        Integer id = 1;
        PhoneLines phoneline = createPhoneLine();
        when(this.phoneServiceMock.phoneById(id)).thenReturn(String.valueOf(phoneline));
        when(this.phoneServiceMock.delete(id)).thenReturn(id);
        ResponseEntity response = this.phoneController.delete(id);
        Assert.assertEquals(HttpStatus.OK,response.getStatusCode());
    }


    @Test
    public void getPhoneLineByNumberTest() throws LostException, PhoneNotExistsException {
        String number = "123355552";
        PhoneLines phoneLine = createPhoneLine();
        when(this.phoneServiceMock.getByPhoneNumber(number)).thenReturn(phoneLine);
        ResponseEntity<PhoneLines> response = this.phoneController.getPhoneLineByNumber(number);
        Assert.assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void getPhoneLinesTest(){
        List<PhoneLines> list = new ArrayList<>();
        PhoneLines phoneLine = createPhoneLine();
        list.add(phoneLine);
        when(this.phoneServiceMock.getPhoneLines()).thenReturn(list);
        ResponseEntity<List<PhoneLines>> response = this.phoneController.getPhoneLines();
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
