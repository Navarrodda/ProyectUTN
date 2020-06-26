package utn.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import utn.project.domain.User;
import utn.project.dto.LoginRequestDto;
import utn.project.dto.NewUserDto;
import utn.project.dto.UpdateUserDto;
import utn.project.exceptions.UserAlreadyExistsException;
import utn.project.exceptions.UserException;
import utn.project.exceptions.UserNotFoundException;
import utn.project.exceptions.ValidationException;
import utn.project.projections.UserFilter;
import utn.project.projections.UserPhoneTypeLin;
import utn.project.service.UserService;

import java.net.URI;
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


    @GetMapping("/{id}/pass}")
    public String getPassById(@PathVariable(value = "id", required = true)Integer idUser)
    {
        return this.userService.getPassById(idUser);
    }

    @GetMapping("/phone")
    public List<UserPhoneTypeLin> getUserPhone(){
        return userService.getUserPhone();
    }


    public ResponseEntity<List<User>> getUsersLineActive(Integer id){
        return this.userService.getUsersLineActive(id);
    }

    public ResponseEntity<List<User>> getUsersDisabled(){ return this.userService.getUsersDisabled(); }

    public ResponseEntity<List<User>> getUserSuspended(){ return this.userService.getUserSuspended(); }

    public ResponseEntity<User> AdminUpdateAccount(Integer id, LoginRequestDto user) throws ValidationException{
        return ResponseEntity.ok(this.userService.AdminUpdateAccount(id, user));
    }

    public ResponseEntity<User> getUserById (Integer id) throws UserException {
        return ResponseEntity.ok(this.userService.getUserById(id));
    }

    public ResponseEntity<User> add(NewUserDto user) throws ValidationException {
        return ResponseEntity.created(getLocation(this.userService.add(user))).build();
    }

    public ResponseEntity<User> update(Integer id, UpdateUserDto user,Integer currentId) throws ValidationException{
        return ResponseEntity.ok(this.userService.update(id, user, currentId));
    }

    public void deleteUser(Integer id, Integer currentId) throws UserException {
        this.userService.deleteUser(id, currentId);
    }

    private URI getLocation(User user) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}/")
                .buildAndExpand(user.getId())
                .toUri();
    }

}
