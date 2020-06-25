package utn.project.controller.web.customer;


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
@RequestMapping("/api")
public class CustomerController {

    private final UserService userService;
    private final SessionManager sessionManager;

    public CustomerController(UserService userService, SessionManager sessionManager) {
        this.userService = userService;
        this.sessionManager = sessionManager;
    }

    public User login(String username, String password, SessionManager sessionManager) throws UserException, ValidationException, InvalidLoginException {
        if ((username != null) && (password != null)) {
            User user = userService.login(username, password);
            if(sessionManager.theUserIsLogged(user)){
                return (User) Optional.ofNullable(null).orElseThrow(() -> new InvalidLoginException("This user is already logged"));
            } else { return user; }
        } else { return (User) Optional.ofNullable(null).orElseThrow(() -> new ValidationException("Username and password must have a value")); }
    }

    @GetMapping("/user")
    public ResponseEntity<User> getInfo(@RequestHeader("Authorization") String sessionToken) throws UserException {
        User currentUser = sessionManager.getCurrentUser(sessionToken);
        return ResponseEntity.ok(currentUser);
    }

    private User getCurrentUser(String sessionToken) throws UserException {
        return Optional.ofNullable(sessionManager.getCurrentUser(sessionToken))
                .orElseThrow(() -> new UserException("User not Logged"));
    }
}

