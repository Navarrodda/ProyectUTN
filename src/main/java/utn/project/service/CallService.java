package utn.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import utn.project.domain.Call;
import utn.project.projections.CallDate;
import utn.project.projections.CallMore;
import utn.project.projections.CallUser;
import utn.project.repository.CallRepository;

import java.util.ArrayList;
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

    public ResponseEntity<List<CallUser>> getCallUser(Integer id){
        List<CallUser> calls = new ArrayList<CallUser>();
        calls = this.callRepository.getCallUser(id);
        if(!calls.isEmpty()){
            return ResponseEntity.ok(calls);
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    public ResponseEntity<List<CallDate>> getCallsBtwDatesByUser(Integer id,String firstDate, String secondDate){
        List<CallDate> calls = new ArrayList<CallDate>();
        calls = this.callRepository.getCallsBtwDatesByUser(id,firstDate,secondDate);
        if(!calls.isEmpty()){
            return ResponseEntity.ok(calls);
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    public ResponseEntity<List<CallMore>> getCallsMoreCity(Integer id){
        List<CallMore> moreCity = new ArrayList<>();
        moreCity = callRepository.getCallsMoreCity(id);
        if(!moreCity.isEmpty()){
            return ResponseEntity.ok(moreCity);
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }
}
