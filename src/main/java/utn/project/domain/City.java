package utn.project.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "cities")
public class City {

    private Integer id;

    private String name;

    private  String pre_fil_ex;

    private  State id_state;

}
