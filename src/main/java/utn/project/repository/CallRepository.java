package utn.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import utn.project.domain.Call;
import utn.project.projections.CallDate;
import utn.project.projections.CallMore;
import utn.project.projections.CallUser;

import java.util.List;

public interface CallRepository extends JpaRepository<Call,Integer> {
    @Query(value = "select concat(c.date) as date, c.* from calls c " +
            "where c.date BETWEEN concat(?1, ' 00:00:00') and DATE_ADD(concat( ?2, ' 00:00:00'), interval 1 DAY) " +
            "order by c.id_tariff asc", nativeQuery = true)
    List<Call> getCallsBetweenDates(String firstDate, String secondDate);

    @Query(value = "select concat(c.date) as date, c.* " +
            "from calls c " +
            "inner join phone_lines pl on c.id_origin_phone = pl.id " +
            "where pl.id_user = ?1 and " +
            "c.date BETWEEN concat(?2, ' 00:00:00') and DATE_ADD(concat( ?3, ' 00:00:00'), interval 1 DAY) " +
            "order by c.date asc", nativeQuery = true)
    List<Call> getCallsBetweenDatesByUser(Integer idUser, String firstDate, String secondDate);

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
    List<CallDate> getCallsBtwDatesByUser(Integer id,String firstDate, String secondDate);

    @Query(value = "SELECT c.name as NameCity, s.name as NameState, phon.phone_number as Phone FROM " +
            "users u INNER JOIN phone_lines phon on phon.id_user = u.id " +
            "INNER JOIN calls cal on cal.id_origin_phone = phon.id " +
            "INNER JOIN phone_lines phond on phond.id = cal.id_destiny_phone " +
            "INNER JOIN users user2 on user2.id = phond.id_user " +
            "INNER JOIN cities c on c.id = user2.id_city " +
            "INNER JOIN states s on s.id = c.id_state WHERE u.id = ?1 LIMIT 1", nativeQuery = true)
    List<CallMore> getCallsMoreCity(Integer id);
}
