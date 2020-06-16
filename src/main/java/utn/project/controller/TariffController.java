package utn.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import utn.project.domain.Tariff;
import utn.project.service.TariffService;

import java.util.List;

@Controller
@RestController
@RequestMapping("/tariff")
public class TariffController {

    private final TariffService tariffService;

    @Autowired
    public TariffController(final TariffService tariffService) {
        this.tariffService = tariffService;
    }

    @GetMapping("/")
    public List<Tariff> getTariff(){
        return tariffService.getTariff();
    }

    @PostMapping("/")
    public void addTariff(@RequestBody Tariff tariff){
        tariffService.add(tariff);
    }

}
