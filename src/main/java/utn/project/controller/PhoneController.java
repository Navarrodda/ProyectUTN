package utn.project.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import utn.project.domain.Country;
import utn.project.domain.Phone_lines;
import utn.project.service.CountryService;
import utn.project.service.PhoneService;

import java.util.List;

@Controller
@RestController
@RequestMapping("/")
public class PhoneController {


    private final PhoneService phoneService;

    @Autowired
    public PhoneController(final PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @GetMapping("/phone")
    public List<Phone_lines> getPhone(){
        return phoneService.getPhone();
    }

    @PostMapping("/phone")
    public void addPhone(@RequestBody Phone_lines phone){
        phoneService.add(phone);
    }
}
