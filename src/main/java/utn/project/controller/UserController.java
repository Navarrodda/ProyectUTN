package utn.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import utn.project.domain.User;
import utn.project.service.UserService;

import java.util.List;

@Controller
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public List<User> getUser(){
        return userService.getUser();
    }

    @PostMapping("/")
    public void addUser(@RequestBody User user){
        userService.add(user);
    }



}
