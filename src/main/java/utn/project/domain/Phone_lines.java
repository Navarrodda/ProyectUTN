package utn.project.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "phone_lines")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Phone_lines {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name="id_user")
    private User id_user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_type_line")
    private Type type;

    @Column(name = "phone_number",nullable = false,unique = true)
    private String phoneNumber;



}