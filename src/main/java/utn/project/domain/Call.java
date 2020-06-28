package utn.project.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

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
    private PhoneLines phoneLines;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value="id_destiny_phone")
    @JoinColumn(name="id_destiny_phone")
    private PhoneLines destinyPhone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value="id_tariff")
    @JoinColumn(name="id_tariff")
    private Tariff tariff;

    @NotNull
    @Column(name = "duration")
    private Float duration;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)/**datetime**/
    @JoinColumn(name="date",nullable = false)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Date date;

    @NotNull
    @Column(name = "total_price")
    private Float totalPrice;

}
