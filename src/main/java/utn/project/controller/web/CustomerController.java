package utn.project.controller.web;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.project.controller.BillController;
import utn.project.controller.CallController;
import utn.project.controller.UserController;
import utn.project.domain.Bill;
import utn.project.domain.Call;
import utn.project.domain.User;
import utn.project.dto.LoginRequestDto;
import utn.project.exceptions.UserException;
import utn.project.exceptions.ValidationException;
import utn.project.projections.CallDate;
import utn.project.projections.CallMore;
import utn.project.projections.CallUser;
import utn.project.session.SessionManager;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CustomerController {

    private final UserController userController;
    private final CallController callController;
    private final BillController billController;
    private final SessionManager sessionManager;

    public CustomerController(UserController userController, CallController callController, BillController billController, SessionManager sessionManager) {
        this.userController = userController;
        this.callController = callController;
        this.billController = billController;
        this.sessionManager = sessionManager;
    }

    private User getCurrentUser(String sessionToken) throws UserException {
        return Optional.ofNullable(sessionManager.getCurrentUser(sessionToken)).orElseThrow(() -> new UserException("User not logged"));
    }

    /**Consulta de clientes*/

    @GetMapping("/user")
    public ResponseEntity<User> getInfo(@RequestHeader("Authorization") String sessionToken) throws UserException {
        User currentUser = sessionManager.getCurrentUser(sessionToken);
        return ResponseEntity.ok(currentUser);
    }

    @PutMapping("/info/update")
    public ResponseEntity<User> update(@RequestHeader("Authorization") String sessionToken,
                                       @RequestBody LoginRequestDto client) throws UserException, ValidationException {
        User currentUser = getCurrentUser(sessionToken);
        return  this.userController.update(currentUser.getId(), client);
    }

    @GetMapping("/calls")
    public ResponseEntity<List<CallUser>> getCalls(@RequestHeader("Authorization") String sessionToken) throws UserException{
        User currentUser =  sessionManager.getCurrentUser(sessionToken);
        return callController.getCallsUser(currentUser.getId());}


    @GetMapping("/calls/most-places/")
    public ResponseEntity<List<CallMore>> getMost(@RequestHeader("Authorization") String sessionToken) throws UserException {
        User currentUser = getCurrentUser(sessionToken);
        return callController.getCityToByCallIdUser(currentUser.getId());
    }

    /**Consulta de llamadas de cleintes entre fechas*/

    @GetMapping( "/calls/between-dates/{firstDate}/{secondDate}")
    public ResponseEntity<List<Call>> getCallsBtwDates(@RequestHeader("Authorization") String sessionToken,
                                                       @PathVariable(value = "firstDate", required = true) @DateTimeFormat(pattern = "YYYY-MM-DD") String firstDate,
                                                       @PathVariable(value = "secondDate", required = true) @DateTimeFormat(pattern = "YYYY-MM-DD") String secondDate)
            throws UserException {
        User currentUser = sessionManager.getCurrentUser(sessionToken);
        return callController.getCallsBetweenDatesByUser(firstDate, secondDate, currentUser.getId());
    }

    @GetMapping("/bills")
    public ResponseEntity<List<Bill>> getBills(@RequestHeader("Authorization") String sessionToken) throws UserException {
        User currentUser = getCurrentUser(sessionToken);
        return this.billController.getBillsByIdUser(currentUser.getId());
    }

    @GetMapping("/bills/between-dates/{firstDate}/{secondDate}")
    public ResponseEntity<List<Bill>> getBillsBtwDates(@RequestHeader("Authorization") String sessionToken,
                                                       @PathVariable(value = "firstDate", required = true) String firstDate,
                                                       @PathVariable(value = "secondDate", required = true) String secondDate) throws UserException {
        User currentUser = getCurrentUser(sessionToken);
        return this.billController.getBillsBetweenDatesByIdUser( firstDate, secondDate, currentUser.getId());
    }

}

