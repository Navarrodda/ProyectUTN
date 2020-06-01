package utn.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.project.domain.User;

public interface UserRepository extends JpaRepository<User,Integer> {


}
