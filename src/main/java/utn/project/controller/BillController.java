package utn.project.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import utn.project.domain.Bill;
import utn.project.service.BillService;

import java.util.List;

@Controller
@RestController
@RequestMapping("/bill")
public class BillController {

    private final BillService billService;

    @Autowired
    public BillController(final BillService billService) {
        this.billService = billService;
    }

    @GetMapping("/")
    public List<Bill> getBill(){
        return billService.getBill();
    }

    @PostMapping("/")
    public void addBill(@RequestBody Bill bill){
        billService.add(bill);
    }

}
