package utn.project.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "tariff")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Tariff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name="id_origin_city")
    private City originCity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value="id_destiny_city")
    @JoinColumn(name="id_destiny_city")
    private City destinyCity;

    @Column(name = "price_for_minute")
    private Float minutePrice;


}
