package utn.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utn.project.domain.Country;
import utn.project.service.CountryService;

import java.util.List;

@RestController
@RequestMapping("/")
public class CountryController {
    private final CountryService countryService;

    @Autowired
    public CountryController(final CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/country")
    public List<Country> getPersona(){
        return countryService.getCountry();
    }

    @GetMapping("/{personaId}")
    public Country getPersonaById(@PathVariable Integer personaId){
        return new Country();
    }

    @PostMapping("/country")
    public void addCountry(@RequestBody  Country country){
        countryService.add(country);
    }

    //@GetMapping("/personaname/")
    //public List<Persona> getAll(@RequestParam(required = false)  String name){
    //    return  personaService.getAll(name);
   // }

    //@GetMapping("/projection")
    //public List<PersonaCant>getPersonaCant(){
      //  return personaService.getPersonaCant();
    //}
}
