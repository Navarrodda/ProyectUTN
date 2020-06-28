package utn.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import utn.project.domain.Call;
import utn.project.domain.City;
import utn.project.dto.BroadcastCall;
import utn.project.exceptions.UserException;
import utn.project.exceptions.ValidationException;
import utn.project.projections.CallMore;
import utn.project.projections.CallUser;
import utn.project.service.CallService;
import utn.project.service.PhoneService;
import utn.project.service.TariffService;
import java.util.List;

@Controller
@RestController
@RequestMapping("/call")
public class CallController {

    private final CallService callService;
    private final TariffService tariffService;
    private final PhoneService phoneService;

    private final Call cal = new Call();

    @Autowired
    public CallController(final CallService callService, TariffService tariffService, PhoneService phoneService) {
        this.callService = callService;
        this.tariffService = tariffService;
        this.phoneService = phoneService;
    }


    public ResponseEntity<List<CallUser>> getCallsUser(Integer idCustomer) throws UserException {
        return this.callService.getCallsUser(idCustomer);
    }

    public ResponseEntity<List<CallMore>> getCityToByCallIdUser(Integer idUser) {
        return this.callService.getCallsMoreCity(idUser);
    }
    public ResponseEntity<List<Call>> getCallsBetweenDates(String firstDate, String secondDate) throws UserException {
        return this.callService.getCallsBetweenDates(firstDate, secondDate);
    }

    public ResponseEntity<List<Call>> getCallsBetweenDatesByUser(String firstDate, String secondDate, Integer idUser) throws UserException {
        return this.callService.getCallsBetweenDatesByUser(firstDate, secondDate, idUser);
    }

    public ResponseEntity<Call> addCall(BroadcastCall call) throws ValidationException {
        return this.callService.addCall(call);
    }

}
