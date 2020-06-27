package utn.project.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import utn.project.domain.PhoneLines;
import utn.project.dto.PhoneDto;
import utn.project.exceptions.GoneLostException;
import utn.project.exceptions.PhoneNotExistsException;
import utn.project.exceptions.UserException;
import utn.project.exceptions.ValidationException;
import utn.project.service.CityService;
import utn.project.service.PhoneService;
import utn.project.service.UserService;
import java.util.List;
import java.util.Optional;

@Controller
@RestController
@RequestMapping("/")
public class PhoneController {


    private final PhoneService phoneService;

    @Autowired
    public PhoneController(final PhoneService phoneService, UserService userService, CityService cityService) {
        this.phoneService = phoneService;
    }

    public ResponseEntity<PhoneLines> getPhoneLineByNumber(String number) throws PhoneNotExistsException, GoneLostException{
        return ResponseEntity.ok(this.phoneService.getByPhoneNumber(number));
    }

    public ResponseEntity<List<PhoneLines>> getPhoneLines(){
        return ResponseEntity.ok(this.phoneService.getPhoneLines());
    }

    public ResponseEntity<PhoneLines> add(@RequestBody PhoneDto phoneLine) throws ValidationException, UserException{
        return ResponseEntity.ok(this.phoneService.add(phoneLine));
    }

    public ResponseEntity<ResponseEntity<PhoneLines>> changeStatus(String phoneNumber, String status) throws PhoneNotExistsException, GoneLostException, ValidationException {
        return  ResponseEntity.ok(this.phoneService.update(phoneNumber, status));
    }

    public ResponseEntity delete(Integer idPhone) throws PhoneNotExistsException, GoneLostException, ValidationException, UserException {
        return ResponseEntity.ok(this.phoneService.delete(idPhone));
    }


}
