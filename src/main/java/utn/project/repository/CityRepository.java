package utn.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import utn.project.domain.City;
import utn.project.projections.CityMoreState;

import java.util.List;
import java.util.Optional;


@Repository
public interface CityRepository extends JpaRepository<City,Integer> {
    @Query(value = "select * From cities Where name = ?1", nativeQuery = true)
    List<City> findByName(String name);

    @Query(value = "select * From cities c Where id = ?1", nativeQuery = true)
    City getCityForId(Integer id);

    @Query(value = "Select c.name, c.prefix, s.name as State, co.name as Country From cities c inner join states " +
            "s on c.id_state = s.id INNER JOIN countries co on co.id = s.id_country" +
            " order by c.id", nativeQuery = true)
    List<CityMoreState>getCityMoreStateProjections();

    @Query(value = "select * From cities c Where id = ?1", nativeQuery = true)
    City getCityPrefixById(Integer id);

    @Query(value = "select * From cities c Where prefix = ?1", nativeQuery = true)
    City getCityPrefixByPrefix(String prefix);
}
