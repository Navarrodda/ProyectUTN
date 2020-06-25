package utn.project.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import utn.project.domain.enums.LineStatus;
import javax.persistence.*;

@Entity(name = "phone_lines")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PhoneLines {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name="id_user")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value="type_phone")
    @JoinColumn(name="type_phone")
    private Type typePhone;

    @Column(name = "phone_number",nullable = false,unique = true)
    private String phoneNumber;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status" ,columnDefinition = "varchar(50) default 'ENABLED'")
    private LineStatus status = LineStatus.ENABLED;
}