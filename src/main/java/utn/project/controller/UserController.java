package utn.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import utn.project.domain.User;
import utn.project.exceptions.UserAlreadyExistsException;
import utn.project.exceptions.UserException;
import utn.project.exceptions.UserNotFoundException;
import utn.project.exceptions.ValidationException;
import utn.project.projections.UserPhoneTypeLin;
import utn.project.service.UserService;

import java.util.List;

@Controller
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(final UserService userService){
        this.userService = userService;
    }

    public User login(String username, String password) throws UserException, ValidationException {
        if ((username != null) && (password != null)) {
            return userService.login(username, password);
        } else {
            throw new ValidationException("Username or password cannot be empty");
        }
    }

    @GetMapping("/")
    public List<User> getUsers(){
        return this.userService.getUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById (@PathVariable(value = "id", required = true)Integer idUser) throws UserNotFoundException {

        return ResponseEntity.ok(this.userService.getUserById(idUser));
    }

    @GetMapping("/{id}/pass}")
    public String getPassById(@PathVariable(value = "id", required = true)Integer idUser)
    {
        return this.userService.getPassById(idUser);
    }

    @PostMapping("/")
    public void addUser(@RequestBody User user) throws UserAlreadyExistsException {
        userService.add(user);
    }

    @GetMapping("/phone")
    public List<UserPhoneTypeLin> getUserPhone(){
        return userService.getUserPhone();
    }


    public ResponseEntity<List<User>> getUsersLineActive(Integer id){
        return this.userService.getUsersLineActive(id);
    }

}
