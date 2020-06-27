package utn.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import utn.project.domain.Tariffs;
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

    public void add(final Tariffs tariffs){
        tariffRepository.save(tariffs);
    }

    public List<Tariffs> getTariff(){
        return tariffRepository.findAll();
    }

    public Tariffs getTariffForPhonesDesAndOrig(Integer idOrigin, Integer idDestiny){
        return tariffRepository.getTariffForOriginDestiny(idOrigin,idDestiny);
    }

    public ResponseEntity<List<Tariffs>> getTariffs(){
        List<Tariffs> tariffs = new ArrayList<Tariffs>();
        tariffs = tariffRepository.findAll();
        if(!tariffs.isEmpty()){
            return ResponseEntity.ok(tariffs);
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    public Tariffs getTariffByDestinyOriginFromTo(Integer idDestiny, Integer idOrigin) throws TariffNotExistsException {
        Tariffs tariffs = new Tariffs();
        tariffs = tariffRepository.getTariffByDestinyOriginFromTo(idDestiny, idOrigin);
        return  Optional.ofNullable(tariffs).orElseThrow(() -> new TariffNotExistsException("Tariff do not exists"));
    }
}
