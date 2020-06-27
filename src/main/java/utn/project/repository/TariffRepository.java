package utn.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import utn.project.domain.Tariffs;


public interface TariffRepository extends JpaRepository<Tariffs, Integer> {
    @Query(value = "SELECT * FROM tariff  WHERE" +
            " tariff.id_destiny_city = ?1 AND tariff.id_origin_city = ?2", nativeQuery = true)
    Tariffs getTariffForOriginDestiny(Integer destiny, Integer origin);

    @Query(value = "SELECT * FROM tariffs t where t.id_destiny_city = ?1 and t.id_origin_city = ?2 ", nativeQuery = true)
    Tariffs getTariffByDestinyOriginFromTo(Integer idDestiny, Integer idOrigin);
}
