package utn.project.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import utn.project.domain.City;
import utn.project.domain.PhoneLines;
import utn.project.domain.Type;
import utn.project.domain.User;
import utn.project.domain.enums.LineStatus;
import utn.project.dto.PhoneDto;
import utn.project.exceptions.GoneLostException;
import utn.project.exceptions.PhoneNotExistsException;
import utn.project.exceptions.UserException;
import utn.project.exceptions.ValidationException;
import utn.project.projections.PhonesUsers;
import utn.project.repository.CityRepository;
import utn.project.repository.PhoneRepository;
import utn.project.repository.TypeRepository;
import utn.project.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PhoneService {

    private final PhoneRepository phoneRepository;
    private final TypeRepository typeRepository;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;

    @Autowired
    public PhoneService(final PhoneRepository phoneRepository, TypeRepository typeRepository, UserRepository userRepository, CityRepository cityRepository) {
        this.phoneRepository = phoneRepository;
        this.typeRepository = typeRepository;
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
    }

    public List<PhonesUsers> getPhone() {
        return phoneRepository.getPhoneUsers();
    }

    public String phoneById(Integer id) {
        return phoneRepository.phoneById(id);
    }

    public PhoneLines getByPhoneNumber(String number) throws PhoneNotExistsException, GoneLostException {
        String[] newNumber = number.split("-");
        List<PhoneLines> phoneLines = phoneRepository.getByPhoneNumber();
        PhoneLines phone = new PhoneLines();
        if (phoneLines != null) {
            for (PhoneLines phoneLine : phoneLines) {
                String[] arrSplit = phoneLine.getPhoneNumber().split("-");
                if (arrSplit[1].equals(newNumber[1])) {
                    phone = phoneLine;
                }
            }
        }
        if (phone == null) {
            return (PhoneLines) Optional.ofNullable(null)
                    .orElseThrow(() -> new PhoneNotExistsException("Phone Line do not exists"));
        } else if (phone.getStatus().toString().equals("DISABLED")) {
            return (PhoneLines) Optional.ofNullable(null).orElseThrow(() -> new GoneLostException("Phone Line has been deleted"));
        }
        return phone;
    }

    public List<PhoneLines> getPhoneLines() {
        List<PhoneLines> phoneLines = new ArrayList<PhoneLines>();
        phoneLines = phoneRepository.findAll();
        return phoneLines;
    }


    public PhoneLines add(PhoneDto phoneLineDto) throws ValidationException, UserException {
        PhoneLines saved = new PhoneLines();
        if (!phoneLineDto.getStatus().equals(LineStatus.ENABLED.toString())) {
            if (!phoneLineDto.getStatus().equals(LineStatus.DISABLED.toString())) {
                if (!phoneLineDto.getStatus().equals(LineStatus.SUSPENDED.toString())) {
                    return (PhoneLines) Optional.ofNullable(null).orElseThrow(() -> new ValidationException("Line Status is not valid"));
                } else {
                    saved.setStatus(LineStatus.SUSPENDED);
                }
            } else {
                saved.setStatus(LineStatus.DISABLED);
            }
        } else {
            saved.setStatus(LineStatus.ENABLED);
        }
        Type lineType = new Type();
        lineType = this.typeRepository.getById(phoneLineDto.getTypePhone().getId());
        if (lineType == null) {
            return (PhoneLines) Optional.ofNullable(null).orElseThrow(() -> new ValidationException("LineType is not valid"));
        }
        User user = new User();
        user = this.userRepository.getById(phoneLineDto.getUser().getId());
        if (user == null) {
            return (PhoneLines) Optional.ofNullable(null).orElseThrow(() -> new UserException("User do not Exists"));
        }
        String ferPhone = phoneLineDto.getPhoneNumber();
        ferPhone = ferPhone.substring(2, 9);
        City city = cityRepository.getCityPrefixById(user.getCity().getId());
        saved.setTypePhone(lineType);
        saved.setPhoneNumber(city.getPrefix() + "-" + ferPhone);
        saved.setUser(user);
        PhoneLines save = new PhoneLines();
        try {
            save = phoneRepository.save(saved);
        } catch (DataIntegrityViolationException e) {
            return (PhoneLines) Optional.ofNullable(null).orElseThrow(() -> new ValidationException("Phone number already exists"));
        } catch (Exception e) {
            return (PhoneLines) Optional.ofNullable(null).orElseThrow(() -> new ValidationException("Error. Check: Phone number"));
        }
        return save;
    }


    public ResponseEntity<PhoneLines> update(String phoneNumber, String status) throws PhoneNotExistsException, ValidationException {
        PhoneLines phoneLines = new PhoneLines();
        phoneLines = this.phoneRepository.getByGetPhoneNumber(phoneNumber);
        if (phoneLines == null) {
            return (ResponseEntity<PhoneLines>) Optional.ofNullable(null).orElseThrow(() -> new PhoneNotExistsException("Phone Line do not exists"));
        }
        if (!status.equals("DISABLED")) {
            if (!status.equals("ENABLED")) {
                if (!status.equals("SUSPENDED")) {
                    return (ResponseEntity<PhoneLines>) Optional.ofNullable(null).orElseThrow(() -> new ValidationException("Status is not valid"));
                } else {
                    phoneLines.setStatus(LineStatus.SUSPENDED);
                }
            } else {
                phoneLines.setStatus(LineStatus.ENABLED);
            }
        } else {
            phoneLines.setStatus(LineStatus.DISABLED);
        }
        return ResponseEntity.ok(this.phoneRepository.save(phoneLines));
    }


    public Object delete(Integer idPhone) throws PhoneNotExistsException, GoneLostException, ValidationException, UserException {
        PhoneLines phoneLine = new PhoneLines();
        phoneLine = this.phoneRepository.phoneByIdPhone(idPhone);
        if(phoneLine.getStatus() != LineStatus.DISABLED) {
            if (phoneLine != null) {
                User user = new User();
                user = this.userRepository.getById(phoneLine.getUser().getId());
                user.setActive(false);
                userRepository.save(user);
                phoneLine.setStatus(LineStatus.DISABLED);
                this.phoneRepository.save(phoneLine);
                return Optional.ofNullable(phoneLine).orElseThrow(() -> new PhoneNotExistsException("Phone not exists"));
            } else {
                return (ResponseEntity<PhoneLines>) Optional.ofNullable(null).orElseThrow(() -> new UserException("User does not exist"));
            }
        }
        return (ResponseEntity<PhoneLines>) Optional.ofNullable(null).orElseThrow(() -> new ValidationException("That line is already deleted\n" +
                "\n"));
    }
}


