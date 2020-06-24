package utn.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import utn.project.domain.State;
import utn.project.service.StateService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RestController
@RequestMapping("/")
public class StateController {

    private final StateService stateService;

    @Autowired
    public StateController(final StateService stateService) {
        this.stateService = stateService;
    }

    @GetMapping("/state")
    public List<State> getState(){
        return stateService.getState();
    }

    @PostMapping("/state")
    public void addCountry(@RequestBody State state){
        stateService.add(state);
    }

    @GetMapping("/state/{id}/")
    public List<State> getCountryCant(@PathVariable Integer id){
        return stateService.getCountCountry(id);
    }


    ////Prueba;
    @GetMapping("/state/more/city")
    public ResponseEntity<List<State>> getMoreCity() {
        List<State> cant = new ArrayList<>();
        cant = this.stateService.getMoreCity();
        if (!cant.isEmpty()) {
                return ResponseEntity.ok(cant);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        }

}
