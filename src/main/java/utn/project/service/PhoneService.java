package utn.project.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.project.domain.PhoneLines;
import utn.project.exceptions.GoneLostException;
import utn.project.exceptions.PhoneNotExistsException;
import utn.project.projections.PhonesUsers;
import utn.project.repository.PhoneRepository;
import java.util.List;
import java.util.Optional;

@Service
public class PhoneService {

    private final PhoneRepository phoneRepository;

    @Autowired
    public PhoneService(final PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    public void add(final PhoneLines phone) {
        phoneRepository.save(phone);
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
        if(phoneLines != null) {
            for (PhoneLines phoneLine : phoneLines) {
                String[] arrSplit = phoneLine.getPhoneNumber().split("-");
                if(arrSplit[1].equals(newNumber[1]))
                {
                    phone = phoneLine;
                }
            }
        }
        if(phone==null) {
            return (PhoneLines) Optional.ofNullable(null)
                        .orElseThrow(() -> new PhoneNotExistsException("Phone Line do not exists"));
            }  else if (phone.getStatus().toString().equals("DISABLED")) {
                return (PhoneLines) Optional.ofNullable(null).orElseThrow(() -> new GoneLostException("Phone Line has been deleted"));
        }
        return phone;
    }


}


