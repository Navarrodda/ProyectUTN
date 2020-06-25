package utn.project.dto;

import lombok.Data;

@Data
public class UpdateUserDto {

    private Integer idCity;
    private String name;
    private String surname;
    private String userType;
}
