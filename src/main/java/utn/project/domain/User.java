package utn.project.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private City id_city;

    @Column(name = "name",nullable = false)
    private String name;


    @Column(name = "surname",nullable = false)
    private String surname;

    @Column(name = "dni",nullable = false)
    private String dni;

    @Column(name = "user",nullable = false,unique = true)
    private String user;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "user_type",nullable = false)
    private String userType;


}