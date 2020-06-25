package utn.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import utn.project.domain.PhoneLines;
import utn.project.projections.PhonesUsers;

import java.util.List;


public interface PhoneRepository extends JpaRepository<PhoneLines, Integer>{
    @Query(value = "SELECT ph.id, u.name as nameUser, u.surname, ph.phone_number as Number " +
            "FROM phone_lines ph " +
            "INNER JOIN users u on u.id = " +
            "ph.id_user GROUP BY ph.id;", nativeQuery = true)
    List<PhonesUsers> getPhoneUsers();

    @Query(value = "select ph.phone_number From phone_lines ph Where id = ?1", nativeQuery = true)
    String phoneById(Integer id);
}
