package utn.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import utn.project.domain.Call;
import utn.project.domain.User;
import utn.project.exceptions.UserException;
import utn.project.projections.CallDate;
import utn.project.projections.CallMore;
import utn.project.projections.CallUser;
import utn.project.repository.CallRepository;
import utn.project.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CallService {

    private final CallRepository callRepository;
    private final UserRepository userRepository;

    @Autowired
    public CallService(CallRepository callRepository, UserRepository userRepository) {
        this.callRepository = callRepository;
        this.userRepository = userRepository;
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

    public ResponseEntity<List<CallUser>> getCallsUser(Integer idCustomer) throws UserException {
        User user = new User();
        if((user = this.userRepository.getById(idCustomer)) == null){
            return (ResponseEntity<List<CallUser>>) Optional.ofNullable(null).orElseThrow(() -> new UserException("User not exists"));
        }
        List<CallUser> calls = new ArrayList<CallUser>();
        calls = this.callRepository.getCallUser(idCustomer);
        if(!calls.isEmpty()){ return ResponseEntity.ok(calls);
        }else{ return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); }
    }

    public ResponseEntity<List<Call>> getCallsBetweenDates(String firstDate, String secondDate){
        List<Call> calls = new ArrayList<Call>();
        calls = this.callRepository.getCallsBetweenDates(firstDate, secondDate);
        if(!calls.isEmpty()){ return ResponseEntity.ok(calls);
        }else{ return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); }
    }

    public ResponseEntity<List<Call>> getCallsBetweenDatesByUser(String firstDate, String secondDate,Integer idUser) throws UserException {
        User user = new User();
        if((user = this.userRepository.getById(idUser)) == null){
            return (ResponseEntity<List<Call>>) Optional.ofNullable(null).orElseThrow(() -> new UserException("User not exists"));
        }
        List<Call> calls = new ArrayList<Call>();
        calls = this.callRepository.getCallsBetweenDatesByUser(idUser, firstDate, secondDate);
        if(!calls.isEmpty()){
            return ResponseEntity.ok(calls);
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }
}
