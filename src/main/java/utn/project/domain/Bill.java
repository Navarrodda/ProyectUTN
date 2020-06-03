package utn.project.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "bill")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value="id_number_call")
    @JoinColumn(name="id_number_call")
    private  Phone_lines numberCall;

    @Column(name = "cant_call")
    private Integer cantCall;
    
    @Column(name = "cost_price")
    private Float priceCost;

    @Column(name = "total_cost")
    private Float totalCost;

    @Column(name = "cost_min")
    private Float costMin;

    @Column(name = "date_of_issue")
    private Date date;

    @Column(name = "payment_due_data")
    private Date paymentData;
}
