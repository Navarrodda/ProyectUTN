package utn.project.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.project.controller.CallController;
import utn.project.domain.User;
import utn.project.dto.BroadcastCall;
import utn.project.exceptions.ValidationException;
import utn.project.session.SessionManager;

import java.text.ParseException;

/**Usuario especial*/

@RestController
@RequestMapping("/broadcast")
public class BroadcastController {

    private final CallController callController;

    @Autowired
    public BroadcastController(CallController callController, SessionManager sessionManager) {
        this.callController = callController;
    }

    @PostMapping("/")
    public ResponseEntity addCall(@RequestHeader("Authorization") String sessionToken, @RequestBody BroadcastCall call) throws ParseException, ValidationException {
          return this.callController.addCall(call);
    }

}
