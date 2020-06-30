package utn.project.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import utn.project.domain.City;
import utn.project.domain.PhoneLines;
import utn.project.domain.Type;
import utn.project.domain.User;
import utn.project.domain.enums.LineStatus;
import utn.project.exceptions.LostException;
import utn.project.exceptions.PhoneNotExistsException;
import utn.project.repository.CityRepository;
import utn.project.repository.PhoneRepository;
import utn.project.repository.TypeRepository;
import utn.project.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PhoneServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PhoneRepository phoneRepository;

    @Mock
    private TypeRepository typeRepository;

    @Mock
    private PhoneService phoneService;

    @Mock
    private CityRepository cityRepository;

    @Before
    public void setUp(){
        initMocks(this);
        this.phoneService = new PhoneService(phoneRepository, typeRepository, userRepository, cityRepository);
    }

    @Test()
    public void getPhoneLinesOk(){
        City city = new City();
        User user = new User();
        Type type = new Type();
        PhoneLines phoneLine = PhoneLines.builder()
                .id(1)
                .phoneNumber("223")
                .typePhone(type)
                .user(user)
                .status(LineStatus.ENABLED)
                .build();
        List<PhoneLines> phoneLineList = new ArrayList<PhoneLines>();
        phoneLineList.add(phoneLine);
        when(this.phoneRepository.findAll()).thenReturn(phoneLineList);
        List<PhoneLines> phoneLineList1 = this.phoneService.getPhoneLines();
        Assert.assertEquals(phoneLineList.size() , phoneLineList1.size());
    }

    @Test()
    public void getByPhoneNumberFails1() throws LostException, PhoneNotExistsException {
        City city = new City();
        User user = new User();
        Type type = new Type();
        PhoneLines phoneLine = PhoneLines.builder()
                .id(1)
                .phoneNumber("223")
                .typePhone(type)
                .user(user)
                .status(LineStatus.ENABLED)
                .build();
        this.phoneService.getByPhoneNumber("223");
    }

    @Test()
    public void getByPhoneNumberFails2() throws PhoneNotExistsException, LostException {
        this.phoneService.getByPhoneNumber("222-2222222222");
    }

}
