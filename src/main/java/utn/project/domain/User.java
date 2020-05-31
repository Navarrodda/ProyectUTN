package utn.project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "users")
public class User {

    @Column(name = "id")
    private Integer id;

    @Column(name = "id_city")
    private City id_city;

    @Column(name = "name")
    private String name;

    @Column(name = "lastname")
    private String surname;

    @Column(name = "dni")
    private String dni;

    @Column(name = "user")
    private String user;

    @Column(name = "password")
    private String password;

    @Column(name = "usertipe")
    private String user_ti_pe;


}