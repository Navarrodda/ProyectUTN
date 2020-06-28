package utn.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import utn.project.domain.Tariff;
import utn.project.exceptions.TariffNotExistsException;
import utn.project.repository.TariffRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TariffService {

    private final TariffRepository tariffRepository;

    @Autowired
    public TariffService(final TariffRepository tariffRepository) {
        this.tariffRepository = tariffRepository;
    }

    public void add(final Tariff tariff){
        tariffRepository.save(tariff);
    }

    public List<Tariff> getTariff(){
        return tariffRepository.findAll();
    }

    public Tariff getTariffForPhonesDesAndOrig(Integer idOrigin, Integer idDestiny){
        return tariffRepository.getTariffForOriginDestiny(idOrigin,idDestiny);
    }

    public ResponseEntity<List<Tariff>> getTariffs(){
        List<Tariff> tariffs = new ArrayList<Tariff>();
        tariffs = tariffRepository.findAll();
        if(!tariffs.isEmpty()){
            return ResponseEntity.ok(tariffs);
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    public Tariff getTariffByDestinyOriginFromTo(Integer idDestiny, Integer idOrigin) throws TariffNotExistsException {
        Tariff tariff = new Tariff();
        tariff = tariffRepository.getTariffByDestinyOriginFromTo(idDestiny, idOrigin);
        return  Optional.ofNullable(tariff).orElseThrow(() -> new TariffNotExistsException("Tariff do not exists"));
    }
}
