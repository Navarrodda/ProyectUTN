package utn.project.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import utn.project.domain.City;
import utn.project.domain.Phone_lines;
import utn.project.domain.User;
import utn.project.projections.PhonesUsers;
import utn.project.service.CityService;
import utn.project.service.PhoneService;
import utn.project.service.UserService;
import java.util.List;

@Controller
@RestController
@RequestMapping("/")
public class PhoneController {


    private final PhoneService phoneService;
    private  final UserService userService;
    private  final CityService cityService;

    @Autowired
    public PhoneController(final PhoneService phoneService, UserService userService, CityService cityService) {
        this.phoneService = phoneService;
        this.userService = userService;
        this.cityService = cityService;
    }


    @GetMapping("/phone")
    public List<PhonesUsers> getPhone(){
        return phoneService.getPhone();
    }

    @PostMapping("/phone")
    public void addPhone(@RequestBody Phone_lines phone){
        String ferPhone = phone.getPhoneNumber();
        ferPhone = ferPhone.substring(2,9);
        User user = userService.getUserCity(phone.getUser().getId());
        City city = cityService.getCityPrefix(user.getCity().getId());
        phone.setPhoneNumber(city.getPrefix()+"-"+ferPhone);
        phoneService.add(phone);
    }

}
