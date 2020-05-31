package utn.project.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cities")
public class City {

    @Column(name = "id")
    private Integer id;

    @Column(name = "id_state")
    private  State id_state;

    @Column(name = "name")
    private String name;

    @Column(name = "prefilex")
    private  String pre_fil_ex;
}
