package utn.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import utn.project.domain.City;
import utn.project.domain.User;
import utn.project.domain.enums.UserType;
import utn.project.dto.LoginRequestDto;
import utn.project.dto.UserDto;
import utn.project.exceptions.UserAlreadyExistsException;
import utn.project.exceptions.UserException;
import utn.project.exceptions.UserNotFoundException;
import utn.project.exceptions.ValidationException;
import utn.project.projections.UserFilter;
import utn.project.projections.UserPhoneTypeLin;
import utn.project.repository.CityRepository;
import utn.project.repository.UserRepository;
import utn.project.tools.HashPassword;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final CityRepository cityRepository;

    @Autowired
    public UserService(final UserRepository userRepository, CityRepository cityRepository){
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
    }

    public List<User> getUsers(){
        return this.userRepository.findAll();
    }

    public User getUserById(Integer idUser) throws UserNotFoundException{
        return this.userRepository.findById(idUser).get();
    }

    public String getPassById(Integer idUser) {
        return this.userRepository.findIdWithPassword(idUser);
    }

    public User login(String username, String password) throws UserException {
        User user = userRepository.getByUsername(username, password);
        return Optional.ofNullable(user).orElseThrow(() -> new UserException("User does not exist"));
    }

    public User createUser(UserDto user) throws UserAlreadyExistsException, ValidationException {
        City city = cityRepository.getCityForId(user.getCity().getId());
        if(city == null){ return (User) Optional.ofNullable(null).orElseThrow(() -> new ValidationException("City does not exists")); }
        User save = new User();
        save.setId(user.getId());
        save.setCity(city);
        save.setName(user.getName());
        save.setSurname(user.getSurname());
        save.setUserName(user.getUsername());
        save.setPassword(user.getPassword());
        save.setDni(user.getDni());
        return userRepository.save(save);
    }

    public void delete(Integer idUser) throws UserException {
        userRepository.modify(idUser);
    }

    public void add(final User user) throws UserAlreadyExistsException {
        userRepository.save(user);
    }

    public List<UserFilter> getUser(){
        return userRepository.getUserFilter();
    }

    public List<UserPhoneTypeLin> getUserPhone(){
        return userRepository.getUserFilterPone();
    }

    public User getUserCity(Integer id){return userRepository.getUserCity(id);}

    public ResponseEntity<List<User>> getUsersLineActive(Integer id){
        List<User> users = this.userRepository.getUsersLineActive();
        List<User> newUser = new ArrayList<User>();
        for (User user: users) {
            if(user.getId() != id){ newUser.add(user); }
        }if(!newUser.isEmpty()){ return ResponseEntity.ok(newUser);
        } else{ return ResponseEntity.status(HttpStatus.NO_CONTENT).build();}
    }

    public ResponseEntity<List<User>> getUsersDisabled(){
        List<User> newUsersDisables = this.userRepository.getUsersLineDisabled();
        if(!newUsersDisables.isEmpty()){ return ResponseEntity.ok(newUsersDisables);
        }else{ return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); }
    }

    public ResponseEntity<List<User>> getUserSuspended(){
        List<User> newUsersDisables = this.userRepository.getUserSuspended();
        if(!newUsersDisables.isEmpty()){ return ResponseEntity.ok(newUsersDisables);
        }else{ return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); }
    }

    public User AdminUpdateAccount(Integer id, LoginRequestDto user) throws ValidationException {
        User newModify = this.userRepository.getById(id);
        HashPassword hash = new HashPassword();
        newModify.setUserName(user.getUserName());
        newModify.setPassword(hash.getHashPassword(user.getPassword()));
        return this.userRepository.save(newModify);
    }

}

