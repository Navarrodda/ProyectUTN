package utn.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import utn.project.domain.Bill;
import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Integer> {
    @Query( value= "select b.* from bills b " +
            "inner join phone_lines ph on ph.id = b.id_number_call " +
            "where ph.id_user = ?1 order by b.date_of_issue asc", nativeQuery = true)
    List<Bill> getBillsByIdUser(Integer idUser);

    @Query(value = "select b.* from bills b " +
            "inner join phone_lines ph on ph.id = b.id_number_call " +
            "where ph.id_user = ?3 and " +
            "b.date_of_issue BETWEEN concat(?1, ' 00:00:00') and DATE_ADD(concat( ?2, ' 00:00:00'), interval 1 DAY) " +
            "order by b.id asc", nativeQuery = true)
    List<Bill> findBillsBtwDatesByIdUser(String firstDate, String secondDate, Integer isUser);

    @Query(value = "select b.* from bills b " +
            "where b.date_of_issue BETWEEN concat(?1, ' 00:00:00') and DATE_ADD(concat( ?2, ' 00:00:00'), interval 1 DAY) " +
            "order by b.id asc", nativeQuery = true)
    List<Bill> findBillsBtwDates(String firstDate, String secondDate);

}
