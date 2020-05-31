package utn.project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "countries")
public class Country {

    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;
}
