package utn.project.controller.web;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import utn.project.controller.CallController;
import utn.project.domain.Bill;
import utn.project.domain.Call;
import utn.project.domain.Type;
import utn.project.domain.User;
import utn.project.domain.enums.LineStatus;
import utn.project.dto.BroadcastCall;
import utn.project.dto.PhoneDto;
import utn.project.exceptions.UserException;
import utn.project.exceptions.ValidationException;
import utn.project.service.BillService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class BroadcastControllerTest {

    @Mock
    CallController callControllerMock;


    private BroadcastCall createBroadcast(){
        BroadcastCall call = new BroadcastCall();
        call.setPhoneNumberDestiny("2222");
        call.setOriginPhone("5555");
        call.setDuration(new Float(222));
        return call;
    }

    @Test(expected = NullPointerException.class)
    public void addCallTest() throws ParseException, ValidationException {
        BroadcastCall call = createBroadcast();
        when(this.callControllerMock.addCall(call));
        ResponseEntity<Call> response = this.callControllerMock.addCall(call);
        Assert.assertEquals(null,response);
    }
}
