package utn.project.controller.web.backoffice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.project.controller.*;
import utn.project.domain.User;
import utn.project.exceptions.UserException;
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
}
