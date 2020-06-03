package utn.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.project.domain.Call;
import utn.project.repository.CallRepository;

import java.util.List;

@Service
public class CallService {

    private final CallRepository callRepository;

    @Autowired
    public CallService(final CallRepository callRepository) {
        this.callRepository = callRepository;
    }

    public void add(final Call call){
        callRepository.save(call);
    }

    public List<Call> getCall(){
        return callRepository.findAll();
    }
}
