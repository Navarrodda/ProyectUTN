package utn.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import utn.project.domain.Country;
import utn.project.service.CountryService;

import java.util.List;

@Controller
@RestController
@RequestMapping("/")
public class CountryController {
    private final CountryService countryService;

    @Autowired
    public CountryController(final CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/country")
    public List<Country> getCountry(){
        return countryService.getCountry();
    }

    @PostMapping("/country")
    public void addCountry(@RequestBody  Country country){
        countryService.add(country);
    }

}
