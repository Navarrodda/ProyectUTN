package utn.project.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import utn.project.domain.Bill;
import utn.project.exceptions.UserException;
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


    public ResponseEntity<List<Bill>> getBillsByIdUser(Integer idUser) throws UserException {
        return this.billService.getBillsByIdUser(idUser);
    }

    public ResponseEntity<List<Bill>> getBillsBetweenDatesByIdUser(String firstDate, String secondDate, Integer idUser ) throws UserException {
        return this.billService.getBillsBetweenDatesByIdUser(firstDate, secondDate, idUser);
    }

    public ResponseEntity<List<Bill>> getBillsBetweenDates(String firstDate, String secondDate){
        return this.billService.getBillsBetweenDates(firstDate, secondDate);
    }

}
