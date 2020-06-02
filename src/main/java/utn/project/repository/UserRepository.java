package utn.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import utn.project.domain.User;
import utn.project.projections.UserFilter;
import utn.project.projections.UserPhoneTypeLin;


import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    @Query(value = "Select u.name, u.surname, u.user, u.dni, c.name as City, u.user_type as Type_User From users u inner join cities " +
            "c on c.id = u.id_city order by u.id ", nativeQuery = true)
    List<UserFilter>getUserFilter();

    @Query(value = "Select u.user, p.phone_number as Phone, c.name as City " +
            "From users u inner join cities c on c.id = u.id_city " +
            "INNER JOIN  phone_lines p on p.id_user = u.id  " +
            "order by u.id ", nativeQuery = true)
    List<UserPhoneTypeLin>getUserFilterPone();

}
