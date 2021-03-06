package utn.project.dto;

import lombok.Data;
import utn.project.domain.City;

@Data
public class UpdateUserDto {

    private City city;
    private String name;
    private String surname;
    private String userType;
    private Boolean active;
}
