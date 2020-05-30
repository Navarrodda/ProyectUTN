package utn.project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "users")
public class User {

    private Integer id;
    private String name;
    private String surname;
    private String dni;
    private String user;
    private String password;
    private String user_ti_pe;

    private City id_city;
}