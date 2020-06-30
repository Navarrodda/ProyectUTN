package utn.project.controller;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import utn.project.domain.City;
import utn.project.domain.User;
import utn.project.domain.enums.UserType;
import utn.project.dto.LoginRequestDto;
import utn.project.dto.NewUserDto;
import utn.project.dto.UpdateUserDto;
import utn.project.dto.UserDto;
import utn.project.exceptions.*;
import utn.project.service.UserService;
import utn.project.session.SessionManager;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserControllerTest {

    @Mock
    UserService userServiceMockito;

    @Mock
    SessionManager sessionManagerMockito;

    @InjectMocks
    UserController userController;

    @Before
    public void setUp(){initMocks(this);}

    private NewUserDto createNewUserDto(String name, String surname, String username, String dni, String userType, Boolean active){
        City city = new City();
        NewUserDto dto = new NewUserDto();
        dto.setCity(city);
        dto.setName(name);
        dto.setSurname(surname);
        dto.setUserName(username);
        dto.setDni(dni);
        dto.setUserType(userType);
        dto.setActive(active);
        return dto;
    }

    private SessionManager createSessionManager(User user){
        SessionManager manager = new SessionManager();
        manager.createSession(user);
        return manager;
    }

    private User createUser(){
        return User.builder()
                .dni("38831211")
                .password("123")
                .city(new City())
                .name("Lautaro")
                .surname("Crespo")
                .userType(UserType.CUSTOMER)
                .userName("Crespo")
                .id(1)
                .build();
    }

    private UpdateUserDto createDto(String name,String surname, String userType){
        City city = new City();
        UpdateUserDto dto = new UpdateUserDto();
        dto.setCity(city);
        dto.setName(name);
        dto.setSurname(surname);
        dto.setUserType(userType);
        return dto;
    }

    private LoginRequestDto createLoginRequestDto(String username, String password){
        LoginRequestDto dto = new LoginRequestDto(username,password);
        return dto;
    }

    @Test
    public void testLoginOk() throws UserNonsexistException, ValidationException, UserException, InvalidLoginException{
        City city = new City();
        User loggedUser = new User(1, city,"Nombre", "surname", "dni", "username","password", UserType.CUSTOMER, true);
        //Cuando llame al mock service.login devuelvo el logged user
        when(userServiceMockito.login("user", "pwd")).thenReturn(loggedUser);
        User returnedUser = userController.login("user", "pwd",sessionManagerMockito);

        //Hacemos los assert
        assertEquals(loggedUser.getId(), returnedUser.getId());
        assertEquals(loggedUser.getUserName(), returnedUser.getUserName());
        verify(userServiceMockito, times(1)).login("user", "pwd");
    }


    @Test(expected = ValidationException.class)
    public void testLoginInvalidData() throws UserNonsexistException, ValidationException, InvalidLoginException, UserException {
        userController.login(null, "pwd",sessionManagerMockito);
    }


    @Test
    public void loginOkTest() throws UserException, ValidationException, InvalidLoginException {
        User user = createUser();
        SessionManager manager = new SessionManager();
        when(this.userServiceMockito.login(user.getUserName(),user.getPassword())).thenReturn(user);
        User userLogin = this.userController.login(user.getUserName(),user.getPassword(),manager);
        Assert.assertEquals(user,userLogin);
    }
    @Test(expected = InvalidLoginException.class)
    public void loginAlreadyTest() throws UserException, ValidationException, InvalidLoginException {
        User user = createUser();
        SessionManager manager = createSessionManager(user);
        when(this.userServiceMockito.login(user.getUserName(),user.getPassword())).thenReturn(user);
        this.userController.login(user.getUserName(),user.getPassword(),manager);
    }

    @Test(expected = ValidationException.class)
    public void loginNullTest() throws UserException, ValidationException, InvalidLoginException {
        User user = new User();
        SessionManager manager = new SessionManager();
        when(this.userServiceMockito.login(user.getUserName(),user.getPassword())).thenReturn(user);
        this.userController.login(user.getUserName(),user.getPassword(),manager);
    }

    @Test
    public void updateTestOk() throws ValidationException {
        Integer id = 1;
        City city = new  City();
        UpdateUserDto dto = createDto("economic","joe",UserType.CUSTOMER.toString());
        User user = new User();
        when(this.userServiceMockito.update(1,dto,1)).thenReturn(user);
        ResponseEntity<User> response = this.userController.update(1,dto,1);
        Assert.assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void updateTestOk2() throws ValidationException {
        Integer id = 1;
        LoginRequestDto dto = createLoginRequestDto("Nava","joe");
        User user = createUser();
        when(this.userServiceMockito.update(id,dto)).thenReturn(user);
        ResponseEntity<User> response = this.userController.update(id,dto);
        Assert.assertEquals(HttpStatus.OK,response.getStatusCode());
    }


    @Test
    public void getUserByIdOkTest() throws ValidationException, UserException {
        Integer id = 1;
        User user = createUser();
        when(this.userServiceMockito.getUserById(id)).thenReturn(user);
        ResponseEntity<User> response = this.userController.getUserById(id);
        Assert.assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void getUsersActiveTest(){
        List<User> list = new ArrayList<>();
        User user = createUser();
        list.add(user);
        when(this.userServiceMockito.getUsersLineActive(1)).thenReturn(ResponseEntity.ok(list));
        ResponseEntity<List<User>> response = this.userController.getUsersLineActive(1);
        Assert.assertEquals(list.size(),response.getBody().size());
        Assert.assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void getUsersDisabledTest(){
        List<User> list = new ArrayList<>();
        User user = createUser();
        list.add(user);
        when(this.userServiceMockito.getUsersDisabled()).thenReturn(ResponseEntity.ok(list));
        ResponseEntity<List<User>> response = this.userController.getUsersDisabled();
        Assert.assertEquals(list.size(),response.getBody().size());
        Assert.assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void addOkTest() throws ValidationException, DataIntegrityViolationException {
        User newUser = new User();
        NewUserDto user = createNewUserDto("lla","loco","dda","123","CUSTOMER",true);
        when(this.userServiceMockito.add(user)).thenReturn(newUser);
    }

    @Test
    public void  getUserSuspendedTest() throws  ValidationException, NullPointerException{
        ResponseEntity<List<User>> list = null;
        when(this.userServiceMockito.getUserSuspended()).thenReturn(list);
        ResponseEntity<List<User>> response = this.userController.getUserSuspended();
        Assert.assertEquals(null,response);
    }


    @Test
    public void getAdminUpdateAccountTest() throws ValidationException {
        List<User> list = new ArrayList<>();
        LoginRequestDto dto = new  LoginRequestDto("lau","123");
        User user = createUser();
        list.add(user);
        when(this.userServiceMockito.AdminUpdateAccount(1,dto)).thenReturn(user);
        User  response = this.userServiceMockito.AdminUpdateAccount(1,dto);
        Assert.assertEquals(user,response);
    }
}
