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
import utn.project.exceptions.*;
import utn.project.projections.UserFilter;
import utn.project.projections.UserPhoneTypeLin;
import utn.project.service.UserService;
import utn.project.session.SessionManager;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Controller
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(final UserService userService){
        this.userService = userService;
    }

    public User login(String username, String password, SessionManager sessionManager) throws UserException, ValidationException, InvalidLoginException {
        if ((username != null) && (password != null)) {
            User user = userService.login(username, password);
            if(sessionManager.theUserIsLogged(user)){
                return (User) Optional.ofNullable(null).orElseThrow(() -> new InvalidLoginException("This user is already logged"));
            } else { return user; }
        } else { return (User) Optional.ofNullable(null).orElseThrow(() -> new ValidationException("Username and password must have a value")); }
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
        return ResponseEntity.created(getURI(this.userService.add(user))).build();
    }

    public ResponseEntity<User> update(Integer id, UpdateUserDto user,Integer currentId) throws ValidationException{
        return ResponseEntity.ok(this.userService.update(id, user, currentId));
    }

    public ResponseEntity<User> update(Integer idClient, LoginRequestDto user) throws ValidationException{
        return ResponseEntity.ok(this.userService.update(idClient, user));
    }

    public void deleteUser(Integer id, Integer currentId) throws UserException {
        this.userService.deleteUser(id, currentId);
    }

    private URI getURI(User user) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}/")
                .buildAndExpand(user.getId())
                .toUri();
    }

}
