package utn.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.project.domain.Country;
import utn.project.repository.CountryRepository;
import java.util.List;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(final CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public void add(final Country country){
        countryRepository.save(country);

    }

    public List<Country> getCountry(){
        return countryRepository.findAll();
    }

}

