package utn.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.project.domain.User;
import utn.project.exceptions.UserAlreadyExistsException;
import utn.project.projections.UserFilter;
import utn.project.projections.UserPhoneTypeLin;
import utn.project.repository.UserRepository;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void add(final User user) throws UserAlreadyExistsException {
        userRepository.save(user);
    }

    public List<UserFilter> getUser(){
        return userRepository.getUserFilter();
    }

    public List<UserPhoneTypeLin> getUserPhone(){
        return userRepository.getUserFilterPone();
    }

}
