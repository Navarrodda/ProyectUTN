package utn.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import utn.project.domain.City;
import utn.project.domain.PhoneLines;
import utn.project.domain.User;
import utn.project.domain.enums.UserType;
import utn.project.dto.LoginRequestDto;
import utn.project.dto.NewUserDto;
import utn.project.dto.UpdateUserDto;
import utn.project.dto.UserDto;
import utn.project.exceptions.UserAlreadyExistsException;
import utn.project.exceptions.UserException;
import utn.project.exceptions.ValidationException;
import utn.project.projections.UserFilter;
import utn.project.projections.UserPhoneTypeLin;
import utn.project.repository.CityRepository;
import utn.project.repository.PhoneRepository;
import utn.project.repository.UserRepository;
import utn.project.tools.HashPassword;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final PhoneRepository phoneRepository;

    @Autowired
    public UserService(final UserRepository userRepository, CityRepository cityRepository, PhoneRepository phoneRepository){
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
        this.phoneRepository = phoneRepository;
    }

    public List<User> getUsers(){
        return this.userRepository.findAll();
    }


    public User getUserById(Integer id) throws UserException {
        User user = this.userRepository.getById(id);
        return Optional.ofNullable(user).orElseThrow(() -> new UserException("User not exists"));
    }

    public String getPassById(Integer idUser) {
        return this.userRepository.findIdWithPassword(idUser);
    }

    public User login(String username, String password) throws UserException {
        User user = userRepository.getByUsername(username, password);
        return Optional.ofNullable(user).orElseThrow(() -> new UserException("User not exists"));
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

    public User add (NewUserDto user) throws ValidationException{
        if(!user.getUserType().equals(UserType.ADMIN.toString())) {
            if (!user.getUserType().equals(UserType.CUSTOMER.toString())) {
                return (User) Optional.ofNullable(null).orElseThrow(() -> new ValidationException("User type is not valid"));
            }
        }
        City city = cityRepository.getCityForId(user.getCity().getId());
        if(city == null){
            return (User) Optional.ofNullable(null).orElseThrow(() -> new ValidationException("The City does not exist"));
        }
        User save = new User();
        HashPassword hash = new HashPassword();
        save.setCity(city);
        save.setName(user.getName());
        save.setSurname(user.getSurname());
        save.setUserName(user.getUserName());
        save.setDni(user.getDni());
        save.setPassword(hash.getHashPassword(user.getDni()));
        save.setActive(user.getActive());
        if(user.getUserType().equals(UserType.ADMIN) ){
            save.setUserType(UserType.ADMIN);
        } else{ save.setUserType(UserType.CUSTOMER);
        }
        User saved = new User();
        try {
            saved = userRepository.save(save);
        }catch(DataIntegrityViolationException e){
            return  (User) Optional.ofNullable(null).orElseThrow(() -> new ValidationException("Id number already exists"));
        }
        return saved;
    }

    public User update(Integer id, UpdateUserDto userDto, Integer currentId) throws ValidationException {
        if (id != currentId) {
            User previous = this.userRepository.findById(id).get();
            if (!userDto.getUserType().equals(UserType.ADMIN.toString())) {
                if (!userDto.getUserType().equals(UserType.CUSTOMER.toString())) {
                    return (User) Optional.ofNullable(null).orElseThrow(() -> new ValidationException("User type is not valid"));
                }
            }
            if(userDto.getCity() != null)
            {
                City city = cityRepository.getCityForId(userDto.getCity().getId());
                if (city == null) {
                    return (User) Optional.ofNullable(null).orElseThrow(() -> new ValidationException("The City does not exist"));
                }
                previous.setCity(city);
            }
            if(userDto.getName() != null) { previous.setName(userDto.getName()); }
            if(userDto.getSurname() != null) { previous.setSurname(userDto.getSurname()); }
            if(userDto.getActive() != null) { previous.setActive(userDto.getActive()); }
            if (userDto.getUserType().equals(UserType.ADMIN)) {
                previous.setUserType(UserType.ADMIN);
            } else {
                previous.setUserType(UserType.CUSTOMER);
            }
            return this.userRepository.save(previous);
        }
        return (User) Optional.ofNullable(null).orElseThrow(() -> new ValidationException("The entered id is your id"));
    }

    public User update(Integer idClient, LoginRequestDto user) throws ValidationException {
        User save = this.userRepository.getById(idClient);
        HashPassword hash = new HashPassword();
        save.setUserName(user.getUserName());
        save.setPassword(hash.getHashPassword(user.getPassword()));
        return this.userRepository.save(save);
    }

    public void deleteUser(Integer id,Integer currentId){
        if(id != currentId) {
            PhoneLines phoneLines =  phoneRepository.phoneByIdUser(id);
            if(phoneLines != null)
            { phoneRepository.deleteUser(id); }
            userRepository.delete(id);
        }
    }
}

