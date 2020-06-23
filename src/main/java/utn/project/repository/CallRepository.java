package utn.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import utn.project.domain.Call;
import utn.project.projections.CallDate;
import utn.project.projections.CallUser;

import java.util.List;

public interface CallRepository extends JpaRepository<Call,Integer> {
    @Query(value = "SELECT cal.date as Date, phon2.phone_number as callD, cal.duration as Duration, cal.total_price as TotalPrice  " +
            "FROM users u INNER JOIN phone_lines phon on phon.id_user = u.id " +
            "INNER JOIN calls cal on cal.id_origin_phone = phon.id " +
            "INNER JOIN phone_lines phon2 on phon2.id = cal.id_destiny_phone " +
            "WHERE u.id = ?1 ORDER BY cal.date;", nativeQuery = true)
    List<CallUser> getCallUser(Integer id);

    @Query(value = "SELECT concat(cal.date) as Date, phon2.phone_number as callD, " +
            "cal.duration as Duration, cal.total_price as TotalPrice " +
            "FROM users u INNER JOIN phone_lines phon on phon.id_user = u.id " +
            "INNER JOIN calls cal on cal.id_origin_phone = phon.id " +
            "INNER JOIN phone_lines phon2 on phon2.id = cal.id_destiny_phone " +
            "WHERE u.id = ?1 AND cal.date BETWEEN DATE_ADD(concat(?2, ' 23:59:59'), " +
            "INTERVAL -1 DAY) and concat( ?3, ' 23:59:59') ORDER BY cal.date asc;", nativeQuery = true)
    List<CallDate> getCallsBtwDatesByUser(Integer id, String startDate, String finalDate);
}
