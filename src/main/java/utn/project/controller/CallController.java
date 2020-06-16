package utn.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import utn.project.domain.Call;
import utn.project.service.CallService;

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

    @GetMapping("/")
    public List<Call> getCall(){
        return callService.getCall();
    }

    @PostMapping("/")
    public void addCall(@RequestBody Call call){
        call.



        callService.add(call);}

}
