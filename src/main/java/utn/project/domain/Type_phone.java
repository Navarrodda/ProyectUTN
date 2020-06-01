package utn.project.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "type_phone")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Type_phone {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Integer id;

        @NotNull
        @Column(name = "type")
        private String type;

        @ManyToOne(fetch = FetchType.LAZY)
        @JsonBackReference
        @JoinColumn(name="id_phone_line")
        private Phone_lines phoneLines;
}