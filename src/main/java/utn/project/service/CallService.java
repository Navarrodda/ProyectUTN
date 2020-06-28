package utn.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import utn.project.domain.*;
import utn.project.dto.BroadcastCall;
import utn.project.exceptions.UserException;
import utn.project.exceptions.ValidationException;
import utn.project.projections.CallDate;
import utn.project.projections.CallMore;
import utn.project.projections.CallUser;
import utn.project.repository.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CallService {

    private final CallRepository callRepository;
    private final PhoneRepository phoneRepository;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final TariffRepository tariffRepository;

    @Autowired
    public CallService(CallRepository callRepository, PhoneRepository phoneRepository, UserRepository userRepository, CityRepository cityRepository, TariffRepository tariffRepository) throws ValidationException {
        this.callRepository = callRepository;
        this.phoneRepository = phoneRepository;
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
        this.tariffRepository = tariffRepository;
    }

    public void add(final Call call){
        callRepository.save(call);
    }

    public List<Call> getCall(){
        return callRepository.findAll();
    }



    public ResponseEntity<List<CallDate>> getCallsBtwDatesByUser(Integer id,String firstDate, String secondDate){
        List<CallDate> calls = new ArrayList<CallDate>();
        calls = this.callRepository.getCallsBtwDatesByUser(id,firstDate,secondDate);
        if(!calls.isEmpty()){
            return ResponseEntity.ok(calls);
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    public ResponseEntity<List<CallMore>> getCallsMoreCity(Integer id){
        List<CallMore> moreCity = new ArrayList<>();
        moreCity = callRepository.getCallsMoreCity(id);
        if(!moreCity.isEmpty()){
            return ResponseEntity.ok(moreCity);
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    public ResponseEntity<List<CallUser>> getCallsUser(Integer idCustomer) throws UserException {
        User user = new User();
        if((user = this.userRepository.getById(idCustomer)) == null){
            return (ResponseEntity<List<CallUser>>) Optional.ofNullable(null).orElseThrow(() -> new UserException("User not exists"));
        }
        List<CallUser> calls = new ArrayList<CallUser>();
        calls = this.callRepository.getCallUser(idCustomer);
        if(!calls.isEmpty()){ return ResponseEntity.ok(calls);
        }else{ return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); }
    }

    public ResponseEntity<List<Call>> getCallsBetweenDates(String firstDate, String secondDate){
        List<Call> calls = new ArrayList<Call>();
        calls = this.callRepository.getCallsBetweenDates(firstDate, secondDate);
        if(!calls.isEmpty()){ return ResponseEntity.ok(calls);
        }else{ return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); }
    }

    public ResponseEntity<List<Call>> getCallsBetweenDatesByUser(String firstDate, String secondDate, Integer idUser) throws UserException {
        User user = new User();
        if((user = this.userRepository.getById(idUser)) == null){
            return (ResponseEntity<List<Call>>) Optional.ofNullable(null).orElseThrow(() -> new UserException("User not exists"));
        }
        List<Call> calls = new ArrayList<Call>();
        calls = this.callRepository.getCallsBetweenDatesByUser(idUser, firstDate, secondDate);
        if(!calls.isEmpty()){ return ResponseEntity.ok(calls);
        }else{ return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    public ResponseEntity<Call> addCall(BroadcastCall call) throws ValidationException {
        try {
            Call savedCall = new Call();
            PhoneLines phoneOrigin = new PhoneLines();
            PhoneLines phoneDestiny = new PhoneLines();
            List<PhoneLines> phoneLines = phoneRepository.getByPhoneNumber();
            if (phoneLines != null) {
                int OriginCon = call.getOriginPhone().indexOf("-");
                if (OriginCon != -1) {
                    String[] newNumber = call.getOriginPhone().split("-");
                    for (PhoneLines phoneLine : phoneLines) {
                        String[] arrSplit = phoneLine.getPhoneNumber().split("-");
                        if (arrSplit[1].equals(newNumber[1])) {
                            phoneOrigin = phoneLine;
                        }
                    }
                } else {
                    String ferPhone1 = call.getOriginPhone().substring(2, 9);
                    if (phoneLines != null) {
                        for (PhoneLines phoneLine : phoneLines) {
                            String[] arrSplit = phoneLine.getPhoneNumber().split("-");
                            if (arrSplit[1].equals(ferPhone1)) {
                                phoneOrigin = phoneLine;
                            }
                        }
                    }
                }
                int DestinyCon = call.getPhoneNumberDestiny().indexOf("-");
                if (DestinyCon != -1) {
                    String[] newNumberDestiny = call.getPhoneNumberDestiny().split("-");
                    for (PhoneLines phoneLine : phoneLines) {
                        String[] arrSplit = phoneLine.getPhoneNumber().split("-");
                        if (arrSplit[1].equals(newNumberDestiny[1])) {
                            phoneDestiny = phoneLine;
                        }
                    }
                } else {
                    String ferPhone2 = call.getPhoneNumberDestiny().substring(2, 9);
                    if (phoneLines != null) {
                        for (PhoneLines phoneLine : phoneLines) {
                            String[] arrSplit = phoneLine.getPhoneNumber().split("-");
                            if (arrSplit[1].equals(ferPhone2)) {
                                phoneDestiny = phoneLine;
                            }
                        }
                    }
                }
            }
            String prefixOrigin = this.phoneByIdPrefix(phoneOrigin.getId());
            String prefixDestiny = this.phoneByIdPrefix(phoneDestiny.getId());
            //Hora a trabes del prefijo extraemos el id de la ciudad origen y destino.
            City origin = cityRepository.getCityPrefixByPrefix(prefixOrigin);
            City destiny = cityRepository.getCityPrefixByPrefix(prefixDestiny);
            //Paso siguiente al tener la ciudad origin y destino extraemos la tarifa.
            Tariff tariff = tariffRepository.getTariffByDestinyOriginFromTo(origin.getId(), destiny.getId());
            //al tener la tarifa calculamos el total de esos minutos.
            Float totalPrice = tariff.getMinutePrice() * call.getDuration();
            Timestamp date = this.getDateNow();
            Call saved = new Call();
            saved.setPhoneLines(phoneOrigin);
            saved.setDestinyPhone(phoneDestiny);
            saved.setTariff(tariff);
            saved.setDate(date);
            saved.setDuration(call.getDuration());
            saved.setTotalPrice(totalPrice);
            return ResponseEntity.ok(saved = callRepository.save(saved));
        }
        catch(Exception e){
            return (ResponseEntity<Call>) Optional.ofNullable(null).orElseThrow(() -> new ValidationException("Error. Check: One of the numbers inserted do not exists " +
                        "Or there are no numbers entered"));
        }
    }

    public Timestamp getDateNow(){
        Date utilDate = new java.util.Date(); //fecha actual
        long lnMilliseconds = utilDate.getTime();
        Date sqlDate = new java.sql.Date(lnMilliseconds);
        Time sqlTime = new java.sql.Time(lnMilliseconds);
        Timestamp sqlTimestamp = new java.sql.Timestamp(lnMilliseconds);
        return sqlTimestamp;
    }

    public String phoneByIdPrefix(Integer id){
        if(id != null)
        {
            String prefix = phoneRepository.phoneById(id);
            prefix = prefix.substring(0,prefix.length() -8);
            return  prefix;
        }
        return  null;
    }


}
