package utn.project.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "type_phone")
public class Type_phone {

        private Integer id;

        private String type;

}