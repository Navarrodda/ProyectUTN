package utn.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import utn.project.domain.Call;
import utn.project.domain.City;
import utn.project.domain.Tariffs;
import utn.project.exceptions.UserException;
import utn.project.projections.CallUser;
import utn.project.service.CallService;
import utn.project.service.CityService;
import utn.project.service.PhoneService;
import utn.project.service.TariffService;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Controller
@RestController
@RequestMapping("/call")
public class CallController {

    private final CallService callService;
    private final TariffService tariffService;
    private final PhoneService phoneService;
    private final CityService cityService;

    private final Call cal = new Call();

    @Autowired
    public CallController(final CallService callService, TariffService tariffService, PhoneService phoneService, CityService cityService) {
        this.callService = callService;
        this.tariffService = tariffService;
        this.phoneService = phoneService;
        this.cityService = cityService;
    }

    @GetMapping("/")
    public List<Call> getCall(){
        return callService.getCall();
    }

   @PostMapping("/")
    public void addCall(@RequestBody Call call) {
       //Traemos los TeleFonos de origen y destino.
       if (call != null) {
           String prefixOrigin = this.phoneByIdPrefix(call.getPhoneLines().getId());
           String prefixDestiny = this.phoneByIdPrefix(call.getDestinyPhone().getId());
           //Hora a trabes del prefijo extraemos el id de la ciudad origen y destino.
           City origin = cityService.getCityByPrefix(prefixOrigin);
           City destiny = cityService.getCityByPrefix(prefixDestiny);
           //Paso siguiente al tener la ciudad origin y destino extraemos la tarifa.
           Tariffs tariffs = tariffService.getTariffForPhonesDesAndOrig(origin.getId(), destiny.getId());
           //al tener la tarifa calculamos el total de esos minutos.
           Float totalPrice = tariffs.getMinutePrice() * call.getDuration();
           call.setTotalPrice(totalPrice);
           Timestamp date =  this.getDateNow();
           call.setDate(date);
           call.setTariffs(tariffs);
           callService.add(call);
       }
   }

    public String phoneByIdPrefix(Integer id){
        if(id != null)
        {
            String prefix = phoneService.phoneById(id);
            prefix = prefix.substring(0,prefix.length() -8);
            return  prefix;
        }
        return  null;
        }
        
        public Timestamp getDateNow(){
            Date utilDate = new java.util.Date(); //fecha actual
            long lnMilisegundos = utilDate.getTime();
            Date sqlDate = new java.sql.Date(lnMilisegundos);
            Time sqlTime = new java.sql.Time(lnMilisegundos);
            Timestamp sqlTimestamp = new java.sql.Timestamp(lnMilisegundos);
            return sqlTimestamp;
        }

    public ResponseEntity<List<CallUser>> getCallsUser(Integer idCustomer) throws UserException {
        return this.callService.getCallsUser(idCustomer);
    }

    public ResponseEntity<List<Call>> getCallsBetweenDates(String firstDate, String secondDate) throws UserException {
        return this.callService.getCallsBetweenDates(firstDate, secondDate);
    }

    public ResponseEntity<List<Call>> getCallsBetweenDatesByUser(String firstDate, String secondDate, Integer idUser) throws UserException {
        return this.callService.getCallsBetweenDatesByUser(firstDate, secondDate, idUser);
    }

}
