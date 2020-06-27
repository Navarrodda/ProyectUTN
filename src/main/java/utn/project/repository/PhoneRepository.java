package utn.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import utn.project.domain.PhoneLines;
import utn.project.projections.PhonesUsers;
import javax.transaction.Transactional;
import java.util.List;


public interface PhoneRepository extends JpaRepository<PhoneLines, Integer>{
    @Query(value = "select * from phone_lines;", nativeQuery = true)
    List<PhoneLines> getByPhoneNumber();

    @Query(value = "select p.* from phone_lines p where p.phone_number = ?1", nativeQuery = true)
    PhoneLines getByGetPhoneNumber(String number);

    @Query(value = "SELECT ph.id, u.name as nameUser, u.surname, ph.phone_number as Number " +
            "FROM phone_lines ph " +
            "INNER JOIN users u on u.id = " +
            "ph.id_user GROUP BY ph.id;", nativeQuery = true)
    List<PhonesUsers> getPhoneUsers();

    @Query(value = "select ph.phone_number From phone_lines ph Where id = ?1", nativeQuery = true)
    String phoneById(Integer id);

    @Query(value = "select * From phone_lines ph Where id = ?1", nativeQuery = true)
    PhoneLines phoneByIdPhone(Integer id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE phone_lines ph SET ph.status = \"DISABLED\" WHERE ph.id_user = ?1;", nativeQuery = true)
    void deleteUser(Integer id);

    @Query(value = "SELECT * FROM phone_lines ph WHERE ph.id_user = ?1", nativeQuery = true)
    PhoneLines phoneByIdUser(Integer id);
}
