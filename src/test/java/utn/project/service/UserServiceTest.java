package utn.project.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import utn.project.domain.*;
import utn.project.domain.enums.UserType;
import utn.project.dto.LoginRequestDto;
import utn.project.dto.NewUserDto;
import utn.project.dto.UpdateUserDto;
import utn.project.exceptions.UserException;
import utn.project.exceptions.ValidationException;
import utn.project.repository.CityRepository;
import utn.project.repository.PhoneRepository;
import utn.project.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private PhoneRepository phoneRepository;

    private UserService userService;

    @Before
    public void setUp(){
        initMocks(this);
        this.userService = new UserService(userRepository,cityRepository,phoneRepository);
    }

    private UpdateUserDto createUpdateUserDto()
    {
        UpdateUserDto dto = new UpdateUserDto();
        dto.setUserType(UserType.CUSTOMER.toString());
        dto.setName("David");
        dto.setSurname("Navarro");
        dto.setCity(new City());
        return dto;
    }


    private NewUserDto createNewUserDto(Integer idLocality, String name, String surname, String username, String dni, String userType, Boolean active){
        NewUserDto dto = new NewUserDto();
        dto.setCity(new City());
        dto.setName(name);
        dto.setSurname(surname);
        dto.setUserName(username);
        dto.setDni(dni);
        dto.setUserType(userType);
        dto.setActive(active);
        return dto;
    }

    private City createLocality(List<City> list){
        return City.builder()
                .id(1)
                .state(new State())
                .name("city")
                .prefix("refix")
                .build();
    }
    private User createUser(){
        return User.builder()
                .dni("2236211294")
                .password("123")
                .city(new City())
                .name("david")
                .surname("nava")
                .userType(UserType.CUSTOMER)
                .userName("navdda")
                .id(1)
                .active(Boolean.TRUE)
                .build();
    }

    @Test()
    public void loginOk() throws UserException {
        User user = this.createUser();
        when(this.userRepository.getByUsername(user.getUserName(), user.getPassword())).thenReturn(user);
        User user1= this.userService.login(user.getUserName(), user.getPassword());
        Assert.assertEquals(user, user1);
    }

    @Test(expected = UserException.class)
    public void loginFail() throws UserException {
        this.userService.login("non", "non");
    }

    @Test
    public void addClientTest() throws ValidationException {
        Integer id = 1;
        NewUserDto dto = createNewUserDto(1, "Juli", "Nicota", "Manzo", "222222222",UserType.CUSTOMER.toString(), Boolean.TRUE);
        User user = createUser();
        List<Tariff> listTariff = new ArrayList<>();
        List<City> listCity = new ArrayList<>();
        City city = createLocality(listCity);
        when(this.cityRepository.getCityForId(id)).thenReturn(city);
        when(this.userRepository.save(user)).thenReturn(user);
        User test = new User();
        test.setUserType(UserType.CUSTOMER);
        test.setCity(city);
        Assert.assertEquals(city.getName(),test.getCity().getName());
        Assert.assertEquals(UserType.CUSTOMER,test.getUserType());
    }

    @Test
    public void addBackOfficeTest() throws ValidationException {
        Integer id = 1;
        NewUserDto dto = createNewUserDto(1, "Guillermo", "Na", "Gu22", "2222222",UserType.CUSTOMER.toString(), Boolean.TRUE);
        User user = createUser();
        List<Tariff> listTariff = new ArrayList<>();
        List<City> listCall = new ArrayList<>();
        City city = createLocality(listCall);
        when(this.cityRepository.getCityForId(id)).thenReturn(city);
        when(this.userRepository.save(user)).thenReturn(user);
        User test = new User();
        test.setUserType(UserType.ADMIN);
        test.setCity(city);
        Assert.assertEquals(city,test.getCity());
        Assert.assertEquals(UserType.ADMIN,test.getUserType());
    }

    @Test(expected = ValidationException.class)
    public void addLocalityNullTest() throws ValidationException {
        Integer id = 1;
        NewUserDto dto = createNewUserDto(1, "Guillermo", "Na", "Gu22", "2222222",UserType.CUSTOMER.toString(), Boolean.TRUE);
        when(this.cityRepository.getCityForId(id)).thenReturn(null);
        User test = this.userService.add(dto);
    }

    @Test(expected = ValidationException.class)
    public void addUserTypeNullTest() throws ValidationException {
        Integer id = 1;
        NewUserDto dto = createNewUserDto(1, "Guillermo", "Na", "Gu22", "2222222","jon", Boolean.TRUE);
        this.userService.add(dto);
    }

    @Test
    public void getUsersActiveOkTest(){
        List<User> listU = new ArrayList<>();
        User user = createUser();
        listU.add(user);
        when(this.userRepository.findAll()).thenReturn(listU);
        ResponseEntity<List<User>> response = this.userService.getUsersActive();
        Assert.assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void getUsersActiveEmptyTest(){
        List<User> listU = new ArrayList<>();
        when(this.userRepository.findAll()).thenReturn(listU);
        ResponseEntity<List<User>> response = this.userService.getUsersActive();
        Assert.assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
    }

    @Test
    public void getUsersDisabledOkTest(){
        List<User> listU = new ArrayList<>();
        User user = createUser();
        user.setActive(false);
        listU.add(user);
        when(this.userRepository.findAll()).thenReturn(listU);
        ResponseEntity<List<User>> response = this.userService.getUsersActive();
        Assert.assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
    }

    @Test
    public void getUsersDisabledEmptyTest(){
        List<User> listU = new ArrayList<>();
        when(this.userRepository.findAll()).thenReturn(listU);
        ResponseEntity<List<User>> response = this.userService.getUsersActive();
        Assert.assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
    }

    @Test
    public void getUserByIdOkTest() throws UserException {
        Integer id = 1;
        User user = createUser();
        when(this.userRepository.getById(id)).thenReturn(user);
        User test = this.userService.getUserById(id);
        Assert.assertEquals(user,test);

    }

    @Test(expected = UserException.class)
    public void getUserByIdNullTest() throws UserException {
        Integer id = 1;
        User user = createUser();
        when(this.userRepository.getById(id)).thenReturn(null);
        User test = this.userService.getUserById(id);

    }

    @Test
    public void deleteTest() throws UserException {
        Integer id = 1;
        doNothing().when(this.userRepository).delete(id);
        this.userService.delete(id);
    }

    @Test
    public void updateOkTest() throws ValidationException{
        Integer id = 2;
        User user = createUser();
        LoginRequestDto dto = new LoginRequestDto("Navarro", "4");
        List<Tariff> listTariff = new ArrayList<>();
        List<City> listCity = new ArrayList<>();
        City city = createLocality(listCity);
        when(this.cityRepository.getCityForId(id)).thenReturn(city);
        when(this.userRepository.findById(id)).thenReturn(ofNullable(user));
        when(this.cityRepository.getCityForId(id)).thenReturn(city);
        when(this.userRepository.save(user)).thenReturn(user);
    }

    @Test
    public void updateClientTest() throws ValidationException {
        Integer id = 1;
        User user = createUser();
        LoginRequestDto dto = new LoginRequestDto("name","cuca");
        when(this.userRepository.getById(id)).thenReturn(user);
        when(this.userRepository.save(user)).thenReturn(user);
        User test = this.userService.update(id,dto);
        Assert.assertEquals(user,test);
    }
}
