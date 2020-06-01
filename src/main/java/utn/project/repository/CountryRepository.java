package utn.project.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utn.project.domain.Country;


@Repository
public interface CountryRepository extends JpaRepository<Country,Integer> {
}
