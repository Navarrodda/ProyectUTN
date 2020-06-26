package utn.project.repository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import utn.project.domain.User;
import utn.project.domain.enums.LineStatus;
import utn.project.projections.UserFilter;
import utn.project.projections.UserPhoneTypeLin;
import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    User save(User user) throws DataIntegrityViolationException;

    @Query(value = "SELECT u.* from users u where u.id = ?1", nativeQuery = true)
    User getById(Integer id);

    @Query(value = "SELECT u.password from users u where u.id_user = ?1", nativeQuery = true)
    String findIdWithPassword(Integer idUser);

    @Query(value = "SELECT u.* from users u where u.username = ?1 and u.password = ?2", nativeQuery = true)
    User getByUsername(String username, String password);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE users u set u.active = false where u.id = ?1", nativeQuery = true)
    Integer modify(Integer id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE users u set u.active = false where u.id = ?1", nativeQuery = true)
    void delete(Integer id);

    @Query(value = "SELECT u.id, u.name, u.surname, u.user, u.dni, c.name as City, u.user_type as Type_User " +
            "From users u inner join cities " +
            "c on c.id = u.id_city order by u.id ", nativeQuery = true)
    List<UserFilter>getUserFilter();

    @Query(value = "SELECT u.user, p.phone_number as Phone, c.name as City, typ.type as type " +
            "From users u inner join cities c on c.id = u.id_city " +
            "inner join  phone_lines p on p.id_user = u.id inner join type_phone typ on typ.id = p.type_phone " +
            "order by u.id ", nativeQuery = true)
    List<UserPhoneTypeLin>getUserFilterPone();

    @Query(value = "SELECT * from users Where id = ?1", nativeQuery = true)
    User getUserCity(Integer id);

    @Query(value = "SELECT u.* from users u " +
            "INNER JOIN phone_lines ph on ph.id_user = u.id " +
            "WHERE ph.status = \"ENABLED\" and u.active = 1", nativeQuery = true)
    List<User> getUsersLineActive();

    @Query(value = "SELECT u.* from users u " +
            "INNER JOIN phone_lines ph on ph.id_user = u.id " +
            "WHERE ph.status = \"DISABLED\" and u.active = 1", nativeQuery = true)
    List<User> getUsersLineDisabled();

    @Query(value = "SELECT u.* from users u " +
            "INNER JOIN phone_lines ph on ph.id_user = u.id " +
            "WHERE ph.status = \"SUSPENDED\" and u.active = 1", nativeQuery = true)
    List<User> getUserSuspended();

}
