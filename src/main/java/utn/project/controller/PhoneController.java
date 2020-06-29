package utn.project.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import utn.project.domain.PhoneLines;
import utn.project.dto.PhoneDto;
import utn.project.exceptions.LostException;
import utn.project.exceptions.PhoneNotExistsException;
import utn.project.exceptions.UserException;
import utn.project.exceptions.ValidationException;
import utn.project.service.PhoneService;
import java.util.List;

@Controller
@RestController
@RequestMapping("/")
public class PhoneController {


    private final PhoneService phoneService;

    @Autowired
    public PhoneController(final PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    public ResponseEntity<PhoneLines> getPhoneLineByNumber(String number) throws PhoneNotExistsException, LostException {
        return ResponseEntity.ok(this.phoneService.getByPhoneNumber(number));
    }

    public ResponseEntity<List<PhoneLines>> getPhoneLines(){
        return ResponseEntity.ok(this.phoneService.getPhoneLines());
    }

    public ResponseEntity<PhoneLines> add(@RequestBody PhoneDto phoneLine) throws ValidationException, UserException{
        return ResponseEntity.ok(this.phoneService.add(phoneLine));
    }

    public ResponseEntity<ResponseEntity<PhoneLines>> changeStatus(String phoneNumber, String status) throws PhoneNotExistsException, LostException, ValidationException {
        return  ResponseEntity.ok(this.phoneService.update(phoneNumber, status));
    }

    public ResponseEntity delete(Integer idPhone) throws PhoneNotExistsException, LostException, ValidationException, UserException {
        return ResponseEntity.ok(this.phoneService.delete(idPhone));
    }


}
