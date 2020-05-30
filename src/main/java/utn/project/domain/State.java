package utn.project.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "states")
public class State {

    @Id
    private Integer id;

    private  String name;

    private Country id_country;
}
