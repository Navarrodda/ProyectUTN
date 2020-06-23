package utn.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.project.domain.Call;
import utn.project.projections.CallDate;
import utn.project.projections.CallMore;
import utn.project.projections.CallUser;
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

    public List<CallUser> getCallUser(Integer id){
        return callRepository.getCallUser(id);
    }

    public List<CallDate> getCallsBtwDatesByUser(Integer id,String startDate, String finalDate){
        return callRepository.getCallsBtwDatesByUser(id,startDate,finalDate);
    }

    public List<CallMore> getCallsMoreCity(Integer id){
        return callRepository.getCallsMoreCity(id);
    }
}
