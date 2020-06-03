package utn.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import utn.project.domain.Call;
import utn.project.domain.City;
import utn.project.projections.CityMoreState;
import utn.project.service.CallService;
import utn.project.service.CityService;

import java.util.List;

@Controller
@RestController
@RequestMapping("/call")
public class CallController {

    private final CallService callService;
    private final Call cal = new Call();

    @Autowired
    public CallController(final CallService callService) {
        this.callService = callService;
    }

    @GetMapping("/city")
    public List<Call> getCall(){
        return callService.getCall();
    }

    @PostMapping("/city/start")
    public void addCall(@RequestBody Call call){



        callService.add(call);
    }

    
   /* @PostMapping("/city/end")
    public void addCall(@RequestBody Call call){ callService.add(call);
    }*/


   /* public int minuteCounter(){

        return 1;
    }*/
}
