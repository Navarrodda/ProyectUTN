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
    
    @NotNull
    @Column(name = "name")
    private String name;


    @NotNull
    @Column(name = "surname")
    private String surname;

    @NotNull
    @Column(name = "dni")
    private String dni;

    @NotNull
    @Column(name = "user")
    private String user;

    @NotNull
    @Column(name = "password")
    private String password;

    @NotNull
    @Column(name = "user_type")
    private String userType;


}