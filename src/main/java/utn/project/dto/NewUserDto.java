package utn.project.dto;

import lombok.Data;
import utn.project.domain.City;

@Data
public class NewUserDto {

    private City city;

    private String name;

    private String surname;

    private String userName;

    private String dni;

    private String userType;

    private Boolean active;
}
