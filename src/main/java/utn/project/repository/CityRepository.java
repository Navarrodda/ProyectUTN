package utn.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import utn.project.domain.City;
import utn.project.domain.State;
import utn.project.projections.CityMoreStateProjections;

import java.util.List;


@Repository
public interface CityRepository extends JpaRepository<City,Integer> {
    @Query(value = "select * From cities Where name = ?1", nativeQuery = true)
    List<State> findByName(String name);


    @Query(value = "Select c.name, c.prefix, s.name as State From cities c inner join states " +
            "s on c.id_state = s.id order by c.id ", nativeQuery = true)
    List<CityMoreStateProjections>getCityMoreStateProjections();
}
