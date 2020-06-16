package utn.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import utn.project.domain.Tariff;


public interface TariffRepository extends JpaRepository<Tariff, Integer> {
    @Query(value = "SELECT t.price_for_minute FROM tariff t " +
            "WHERE t.id_destiny_city = id_destiny AND t.id_origin_city = id_origin ", nativeQuery = true)
    Tariff getTariffForOriginDestiny(Integer id_destiny,Integer id_origin);
}
