package utn.project.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import utn.project.domain.State;

import java.util.List;

@Repository
public interface StateRepository  extends JpaRepository<State,Integer> {
    @Query(value = "select * From states Where name = ?1", nativeQuery = true)
    List<State> findByName(String name);

    @Query(value = "select * FROM states WHERE id_country = ?1", nativeQuery = true)
    List<State> findByCountCountry(Integer id);


    @Query(value = "SELECT * FROM states s " +
            "INNER JOIN cities c on c.id_state = " +
            "s.id WHERE c.id > 50 ORDER by s.id ASC Limit 1;", nativeQuery = true)
    List<State> getMoreCity();
}
