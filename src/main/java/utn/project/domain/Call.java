package utn.project.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "calls")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Call {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name="id_origin_phone")
    private Phone_lines phoneLines;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value="id_destiny_phone")
    @JoinColumn(name="id_destiny_phone")
    private Phone_lines destinyPhone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value="id_tariff")
    @JoinColumn(name="id_tariff")
    private Tariff tariff;

    @Column(name = "duration")
    private Float duration;

    @Column(name = "total_price")
    private Float totalPrice;

}
