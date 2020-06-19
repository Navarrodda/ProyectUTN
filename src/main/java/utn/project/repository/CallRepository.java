package utn.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.project.domain.Call;

public interface CallRepository extends JpaRepository<Call,Integer> {

}
