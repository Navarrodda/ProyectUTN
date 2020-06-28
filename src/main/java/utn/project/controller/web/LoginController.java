package utn.project.controller.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.project.controller.UserController;
import utn.project.domain.User;
import utn.project.dto.LoginRequestDto;
import utn.project.exceptions.InvalidLoginException;
import utn.project.exceptions.UserException;
import utn.project.exceptions.ValidationException;
import utn.project.session.SessionManager;
import utn.project.tools.HashPassword;

@RestController
@RequestMapping("/")
public class LoginController {

    UserController userController;
    SessionManager sessionManager;

    @Autowired
    public LoginController(UserController userController, SessionManager sessionManager) {
        this.userController = userController;
        this.sessionManager = sessionManager;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDto loginRequestDto) throws ValidationException, UserException, InvalidLoginException {
        ResponseEntity response;
        HashPassword hash= new HashPassword();
        String newPassword = hash.getHashPassword(loginRequestDto.getPassword());
        User user = userController.login(loginRequestDto.getUserName(), newPassword,  sessionManager);
        String token = sessionManager.createSession(user);
        response = ResponseEntity.ok().headers(createHeaders(token)).build();
        return response;
    }

    @PostMapping("/logout")
    public ResponseEntity logout(@RequestHeader("Authorization") String token) {
        sessionManager.sessionRemove(token);
        return ResponseEntity.ok().build();
    }

    private HttpHeaders createHeaders(String token) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization", token);
        return responseHeaders;
    }
}
