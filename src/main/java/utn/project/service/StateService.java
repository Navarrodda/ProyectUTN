package utn.project.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.project.domain.State;
import utn.project.repository.StateRepository;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class StateService {

    private final StateRepository stateRepository;

    @Autowired
    public StateService(final StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    public void add(final State state){
        stateRepository.save(state);
    }

    public List<State> getState(){
        return stateRepository.findAll();
    }

    public List<State> getAll(String name) {
        if(isNull(name)) {
            return stateRepository.findAll();
        }
        return stateRepository.findByName(name);
    }

    public List<State> getCountCountry(Integer id) {
        return stateRepository.findByCountCountry(id);
    }


}
