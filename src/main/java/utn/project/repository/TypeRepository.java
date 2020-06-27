package utn.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import utn.project.domain.Type;

@Repository
public interface TypeRepository extends JpaRepository<Type, Integer> {

    @Query(value = "select * from type_phone t where t.id = ?1", nativeQuery = true)
    Type getById(Integer id);
}
