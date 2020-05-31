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
@Table(name = "phone_lines")
public class Phone_lines {

    @Column(name = "id")
    private Integer id;

    @Column(name = "id_user")
    private User id_user;

    @Column(name = "phone_number")
    private String phone_number;

}