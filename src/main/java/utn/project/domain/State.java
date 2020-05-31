package utn.project.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "states")
public class State {

    @Column(name = "id")
    private Integer id;

    @Column(name = "id_country")
    private Country id_country;

    @Column(name = "name")
    private  String name;


}
