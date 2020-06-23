package utn.project.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.project.domain.Bill;
import utn.project.domain.Call;
import utn.project.repository.BillRepository;

import java.util.List;

@Service
public class BillService {

    private final BillRepository billRepository;

    @Autowired
    public BillService(final BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public void add(final Bill bill){
        billRepository.save(bill);
    }

    public List<Bill> getBill(){
        return billRepository.findAll();
    }
}
