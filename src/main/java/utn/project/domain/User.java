package utn.project.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import utn.project.domain.enums.UserType;

import javax.persistence.*;

@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name="id_city")
    private City city;

    @Column(name = "name",nullable = false)
    private String name;


    @Column(name = "surname",nullable = false)
    private String surname;

    @Column(name = "dni",nullable = false)
    private String dni;

    @Column(name = "username",nullable = false,unique = true)
    private String userName;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "user_type", columnDefinition = "varchar(20) default 'CUSTOMER'")
    @Enumerated(value = EnumType.STRING)
    private UserType userType;

    @Column(name = "active", columnDefinition = "bool default true")
    private Boolean active;

}