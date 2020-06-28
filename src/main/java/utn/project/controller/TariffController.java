package utn.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import utn.project.domain.Tariff;
import utn.project.exceptions.TariffNotExistsException;
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

    public Tariff getTariffForPhonesDesAndOrig(Integer idOrigin, Integer idDestiny){
        return tariffService.getTariffForPhonesDesAndOrig(idOrigin,idDestiny);};

    public ResponseEntity<Tariff> getTariffByLocalityFromTo(Integer idDestiny, Integer idOrigin) throws TariffNotExistsException {
        return ResponseEntity.ok(this.tariffService.getTariffByDestinyOriginFromTo(idDestiny, idOrigin));
    }

    public ResponseEntity<List<Tariff>> getTariffs(){
        return tariffService.getTariffs();
    }
}
