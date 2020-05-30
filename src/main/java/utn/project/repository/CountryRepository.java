package utn.project.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import utn.project.domain.Country;

import java.util.List;


public interface CountryRepository extends JpaRepository<Country,Integer> {
    @Query(value = "select * From countries Where name = ?1", nativeQuery = true)
    List<Country> findByName(String name);

}
