package utn.project.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity(name = "cities")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name="id_state")
    private  State id_state;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "prefix",nullable = false)
    private  String prefix;
}
