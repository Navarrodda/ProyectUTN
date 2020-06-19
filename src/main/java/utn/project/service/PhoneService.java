package utn.project.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.project.domain.Phone_lines;
import utn.project.projections.PhonesUsers;
import utn.project.repository.PhoneRepository;

import java.util.List;

@Service
public class PhoneService {

    private final PhoneRepository phoneRepository;

    @Autowired
    public PhoneService(final PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    public void add(final Phone_lines phone){
        phoneRepository.save(phone);
    }

    public List<PhonesUsers> getPhone(){
        return phoneRepository.getPhoneUsers();
    }

    public  String phoneById(Integer id){return phoneRepository.phoneById(id);}

}
