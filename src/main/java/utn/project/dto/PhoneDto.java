package utn.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utn.project.domain.Type;
import utn.project.domain.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneDto {
    User user;
    String phoneNumber;
    Type typePhone;
    String status;
}