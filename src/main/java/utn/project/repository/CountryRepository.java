package utn.project.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import utn.project.domain.Country;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country,Integer> {
}
