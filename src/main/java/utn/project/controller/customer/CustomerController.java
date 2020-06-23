package utn.project.controller.customer;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utn.project.domain.User;
import utn.project.exceptions.InvalidLoginException;
import utn.project.exceptions.UserException;
import utn.project.exceptions.ValidationException;
import utn.project.service.UserService;
import utn.project.session.SessionManager;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class CustomerController {

    private final UserService userService;
    private final SessionManager sessionManager;

    public CustomerController(UserService userService, SessionManager sessionManager) {
        this.userService = userService;
        this.sessionManager = sessionManager;
    }

    public User login(String username, String password) throws UserException, ValidationException, InvalidLoginException{
        if ((username != null) && (password != null)) {

            User u = userService.login(username, password);

            if(sessionManager.theUserIsLogged(u)){
                throw new InvalidLoginException("This user is already logged");
            }
            else {
                return u;
            }
        } else {
            throw new ValidationException("Username and password must have a value");
        }
    }


    @GetMapping("/")
    public ResponseEntity<User> getInfo(@RequestHeader("Authorization") String sessionToken) throws UserException {
        User currentUser = sessionManager.getCurrentUser(sessionToken);
        return ResponseEntity.ok(currentUser);
    }

    private User getCurrentUser(String sessionToken) throws UserException {
        return Optional.ofNullable(sessionManager.getCurrentUser(sessionToken))
                .orElseThrow(() -> new UserException("User not Logged"));
    }
}

