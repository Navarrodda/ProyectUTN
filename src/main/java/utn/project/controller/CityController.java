package utn.project.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import utn.project.domain.City;
import utn.project.projections.CityMoreState;
import utn.project.service.CityService;

import java.util.List;

@Controller
@RestController
@RequestMapping("/")
public class CityController {

    private final CityService cityService;

    @Autowired
    public CityController(final CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/city")
    public List<CityMoreState>getCity(){
        return cityService.getCity();
    }

    @PostMapping("/city")
    public void addCity(@RequestBody City city){
        cityService.add(city);
    }
}
