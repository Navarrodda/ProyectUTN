package utn.project.controller.customer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.project.domain.User;
import utn.project.exceptions.UserException;
import utn.project.projections.CallDate;
import utn.project.projections.CallMore;
import utn.project.projections.CallUser;
import utn.project.service.CallService;
import utn.project.session.SessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/call")
public class CallDController {

    private final CallService callService;
    private final SessionManager sessionManager;

    @Autowired
    public CallDController(CallService callService, SessionManager sessionManager) {
        this.callService = callService;
        this.sessionManager = sessionManager;
    }

    @GetMapping("/")
    public ResponseEntity<List<CallUser>> getCalls(@RequestHeader("Authorization") String sessionToken) throws UserException{
        User currentUser =  sessionManager.getCurrentUser(sessionToken);
        List<CallUser> calls = new ArrayList<CallUser>();
        calls = this.callService.getCallUser(currentUser.getId());
        if(!calls.isEmpty()){
            return ResponseEntity.ok(calls);
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @GetMapping("/most-places/")
    public ResponseEntity<List<CallMore>> getMost(@RequestHeader("Authorization") String sessionToken) throws UserException {
        User currentUser = getCurrentUser(sessionToken);
        List<CallMore> moreCity = new ArrayList<>();
        moreCity = this.callService.getCallsMoreCity(currentUser.getId());
        if(!moreCity.isEmpty()){
            return ResponseEntity.ok(moreCity);
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @GetMapping( "/between-dates/{firstDate}/{secondDate}")
    public ResponseEntity<List<CallDate>> getCallsBtwDates(@RequestHeader("Authorization") String sessionToken,
                                                           @PathVariable(value = "firstDate", required = true) @DateTimeFormat(pattern = "YYYY-MM-DD") String firstDate,
                                                           @PathVariable(value = "secondDate", required = true) @DateTimeFormat(pattern = "YYYY-MM-DD") String secondDate)
            throws UserException {
        User currentUser = sessionManager.getCurrentUser(sessionToken);
        List<CallDate> calls = new ArrayList<CallDate>();
        calls = this.callService.getCallsBtwDatesByUser(currentUser.getId(), firstDate, secondDate);
        if(!calls.isEmpty()){
            return ResponseEntity.ok(calls);
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    private User getCurrentUser(String sessionToken) throws UserException {
        return Optional.ofNullable(sessionManager.getCurrentUser(sessionToken)).orElseThrow(() -> new UserException("User not logged"));
    }
}
