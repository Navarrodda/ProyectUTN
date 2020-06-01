package utn.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.project.domain.City;
import utn.project.projections.CityMoreStateProjections;
import utn.project.repository.CityRepository;
import java.util.List;

@Service
public class CityService {

    private final CityRepository cityRepository;

    @Autowired
    public CityService(final CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public void add(final City city){
        cityRepository.save(city);
    }

    public List<CityMoreStateProjections>getCity(){
        return cityRepository.getCityMoreStateProjections();
    }
}
