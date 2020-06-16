package utn.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.project.domain.Tariff;
import utn.project.repository.TariffRepository;

import java.util.List;

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

    public List<Tariff> getTariffForPhonesDesAndOrig(){
        return tariffRepository.findAll();
    }
}
