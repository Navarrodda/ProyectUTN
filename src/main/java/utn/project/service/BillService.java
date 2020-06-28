package utn.project.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import utn.project.domain.Bill;
import utn.project.domain.User;
import utn.project.exceptions.UserException;
import utn.project.repository.BillRepository;
import utn.project.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BillService {

    private final BillRepository billRepository;
    private final UserRepository userRepository;

    @Autowired
    public BillService(final BillRepository billRepository, UserRepository userRepository) {
        this.billRepository = billRepository;
        this.userRepository = userRepository;
    }

    public void add(final Bill bill){
        billRepository.save(bill);
    }

    public List<Bill> getBill(){
        return billRepository.findAll();
    }


    public ResponseEntity<List<Bill>> getBillsByIdUser(Integer idUser) throws UserException {
        User user = new User();
        if((user = this.userRepository.getById(idUser)) == null){
            return (ResponseEntity<List<Bill>>) Optional.ofNullable(null).orElseThrow(() -> new UserException("User not exists"));
        }
        List<Bill> bills = new ArrayList<Bill>();
        bills = this.billRepository.getBillsByIdUser(idUser);
        if (!bills.isEmpty()) {
            return ResponseEntity.ok(bills);
        } else { return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); }
    }

    public ResponseEntity<List<Bill>> getBillsBetweenDatesByIdUser(String firstDate, String secondDate, Integer idUser) throws UserException {
        User u = new User();

        if((u = this.userRepository.getById(idUser)) == null){
            return (ResponseEntity<List<Bill>>) Optional.ofNullable(null).orElseThrow(() -> new UserException("User not exists"));
        }
        List<Bill> bills = new ArrayList<Bill>();
        bills =this.billRepository.findBillsBtwDatesByIdUser(firstDate, secondDate, idUser);
        if(!bills.isEmpty()){
            return ResponseEntity.ok(bills);
        }else{ return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); }
    }

    public ResponseEntity<List<Bill>> getBillsBetweenDates(String firstDate, String secondDate){
        List<Bill> bills = new ArrayList<Bill>();
        bills = this.billRepository.findBillsBtwDates(firstDate, secondDate);
        if(!bills.isEmpty()){
            return ResponseEntity.ok(bills);
        }else{ return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); }
    }

}
