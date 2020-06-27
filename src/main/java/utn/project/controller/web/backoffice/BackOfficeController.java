package utn.project.controller.web.backoffice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.project.controller.*;
import utn.project.domain.Call;
import utn.project.domain.PhoneLines;
import utn.project.domain.Tariffs;
import utn.project.domain.User;
import utn.project.dto.LoginRequestDto;
import utn.project.dto.NewUserDto;
import utn.project.dto.PhoneDto;
import utn.project.dto.UpdateUserDto;
import utn.project.exceptions.*;
import utn.project.projections.CallUser;
import utn.project.session.SessionManager;

import java.util.List;

@RestController
@RequestMapping("/office")
public class BackOfficeController {

    private final UserController userController;
    private final PhoneController phoneController;
    private final TariffController tariffController;
    private final CallController callController;
    private final BillController billController;
    private final SessionManager sessionManager;

    @Autowired
    public BackOfficeController(final UserController userController, final PhoneController phoneController, final SessionManager sessionManager, TariffController tariffController, CallController callController, BillController billController){
        this.userController = userController;
        this.phoneController = phoneController;
        this.sessionManager = sessionManager;
        this.tariffController = tariffController;
        this.callController = callController;
        this.billController = billController;
    }

    /**Users and Account*/

    @GetMapping("/")
    public ResponseEntity<User> getInfo(@RequestHeader("Authorization") String sessionToken) throws UserException {
        User currentUser = sessionManager.getCurrentUser(sessionToken);
        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/users/active")
    public ResponseEntity<List<User>> getUsersActive(@RequestHeader("Authorization") String sessionToken) throws UserException {
        User operator = sessionManager.getCurrentUser(sessionToken);
        return this.userController.getUsersLineActive(operator.getId());
    }

    @GetMapping("/users/disabled")
    public ResponseEntity<List<User>> getUsersDisabled(@RequestHeader("Authorization") String sessionToken) throws UserException {
        sessionManager.getCurrentUser(sessionToken);
        return this.userController.getUsersDisabled();
    }


    @GetMapping("/users/suspended")
    public ResponseEntity<List<User>> getUserSuspended(@RequestHeader("Authorization") String sessionToken) throws UserException {
        sessionManager.getCurrentUser(sessionToken);
        return this.userController.getUserSuspended();
    }

   @PutMapping("/update/account")
    public ResponseEntity<User> AdminUpdateAccount(@RequestHeader("Authorization") String sessionToken,
                                       @RequestBody LoginRequestDto user) throws ValidationException, UserException {
        User currentUser = sessionManager.getCurrentUser(sessionToken);
        return this.userController.AdminUpdateAccount(currentUser.getId(), user);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById (@RequestHeader("Authorization") String sessionToken,
                                             @PathVariable(value = "id", required = true)Integer id) throws UserException {
        sessionManager.getCurrentUser(sessionToken);
        return this.userController.getUserById(id);
    }

    @PostMapping("/users")
    public ResponseEntity addUser(@RequestHeader("Authorization") String sessionToken, @RequestBody NewUserDto userDto) throws UserException, UserAlreadyExistsException, ValidationException {
        sessionManager.getCurrentUser(sessionToken);
        return this.userController.add(userDto);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateClient(@RequestHeader("Authorization") String sessionToken,
                                             @PathVariable(value = "id", required = true) Integer id,
                                             @RequestBody UpdateUserDto updateUserDto) throws ValidationException, UserException {
        User currentUser = sessionManager.getCurrentUser(sessionToken);
        return this.userController.update(id, updateUserDto,currentUser.getId());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@RequestHeader("Authorization") String sessionToken,
                                     @PathVariable(value = "id", required = true) Integer id) throws UserException {
        User currentUser = sessionManager.getCurrentUser(sessionToken);
        this.userController.deleteUser(id,currentUser.getId());
        return ResponseEntity.ok().build();
    }

    /**Consulta Phone*/

    @GetMapping("/phone/{number}")
    public ResponseEntity<PhoneLines> getPhoneLine(@RequestHeader("Authorization") String sessionToken,
                                                   @PathVariable(value = "number", required = true) String number) throws UserException, PhoneNotExistsException, GoneLostException{
        sessionManager.getCurrentUser(sessionToken);
        return this.phoneController.getPhoneLineByNumber(number);
    }

    @GetMapping("/phone")
    public ResponseEntity<List<PhoneLines>> getPhoneLines(@RequestHeader("Authorization") String sessionToken) throws UserException, PhoneNotExistsException, GoneLostException {
        sessionManager.getCurrentUser(sessionToken);
        return this.phoneController.getPhoneLines();
    }
    @PostMapping("/phone")
    public ResponseEntity<PhoneLines> addPhoneLine(@RequestHeader("Authorization") String sessionToken,
                                                  @RequestBody PhoneDto phoneLine) throws UserException, UserAlreadyExistsException, ValidationException, ValidationException {
        sessionManager.sessionRemove(sessionToken);
        return this.phoneController.add(phoneLine);
    }

    @PutMapping("/phone/{number}/status={status}")
    public ResponseEntity<ResponseEntity<PhoneLines>> disablePhoneLine (@RequestHeader("Authorization") String sessionToken,
                                                                        @PathVariable(value = "number", required = true) String number,
                                                                        @PathVariable(value = "status", required = true) String status) throws ValidationException, UserException, PhoneNotExistsException, GoneLostException {
        sessionManager.getCurrentUser(sessionToken);
        return this.phoneController.changeStatus(number, status);
    }

    @DeleteMapping("/phone/{idPhone}")
    public ResponseEntity deletePhoneLine (@RequestHeader("Authorization") String sessionToken,
                                           @PathVariable(value = "idPhone", required = true) Integer idPhone) throws ValidationException, UserException, PhoneNotExistsException, GoneLostException {
        sessionManager.getCurrentUser(sessionToken);
        return this.phoneController.delete(idPhone);
    }

    /**Consulta Tariff*/

    @GetMapping("/tariffs")
    public ResponseEntity<List<Tariffs>> getTariffs(@RequestHeader("Authorization") String sessionToken) throws UserException {
        sessionManager.getCurrentUser(sessionToken);
        return this.tariffController.getTariffs();
    }

    @GetMapping("/tariffs/destiny={idDestiny}/origin={idOrigin}")
    public ResponseEntity<Tariffs> getTariff(@RequestHeader("Authorization") String sessionToken,
                                             @PathVariable(value = "idDestiny", required = true) Integer idDestiny,
                                             @PathVariable(value = "idOrigin", required = true) Integer idOrigin) throws UserException, TariffNotExistsException {
        sessionManager.getCurrentUser(sessionToken);
        return this.tariffController.getTariffByLocalityFromTo(idDestiny, idOrigin);
    }

    /**Consulta llamadas*/

    @GetMapping("/calls/user/{id}")
    public ResponseEntity<List<CallUser>> getCallsByUser(@RequestHeader("Authorization") String sessionToken,
                                                         @PathVariable(value = "id", required = true) Integer id) throws UserException {
        sessionManager.getCurrentUser(sessionToken);
        return this.callController.getCallsUser(id);
    }

    @GetMapping( "/calls/between-dates/{firstDate}/{secondDate}")
    public ResponseEntity<List<Call>> getCallsBtwDates(@RequestHeader("Authorization") String sessionToken,
                                                       @PathVariable(value = "firstDate", required = true) @DateTimeFormat(pattern = "YYYY-MM-DD") String firstDate,
                                                       @PathVariable(value = "secondDate", required = true) @DateTimeFormat(pattern = "YYYY-MM-DD") String secondDate)
            throws UserException {
        sessionManager.getCurrentUser(sessionToken);
        return this.callController.getCallsBetweenDates(firstDate, secondDate);
    }

    @GetMapping( "/calls/user/{id}/between-dates/{firstDate}/{secondDate}")
    public ResponseEntity<List<Call>> getCallsBetweenDatesByUser(@RequestHeader("Authorization") String sessionToken,
                                                             @PathVariable(value = "firstDate", required = true) @DateTimeFormat(pattern = "YYYY-MM-DD") String firstDate,
                                                             @PathVariable(value = "secondDate", required = true) @DateTimeFormat(pattern = "YYYY-MM-DD") String secondDate,
                                                             @PathVariable(value = "id", required = true) Integer id) throws UserException {
        sessionManager.getCurrentUser(sessionToken);
        return this.callController.getCallsBetweenDatesByUser(firstDate, secondDate, id);
    }

    /**Consulta Facturas*/



}
