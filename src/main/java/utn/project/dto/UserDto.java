package utn.project.dto;

import aj.org.objectweb.asm.ConstantDynamic;
import lombok.Getter;
import lombok.Setter;
import utn.project.domain.City;

@Getter
@Setter
public class UserDto {

    private Integer id;
    private City City;
    private String name;
    private String surname;
    private String dni;
    private String username;
    private String password;
    private String userType;
    private Boolean active;
}