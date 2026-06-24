package cl.duoc.msempleado.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cargo")
@Schema(description = "Representa un cargo en el sistema del Rent a Car.")
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID unico del cargo, es autoincrementable dentro de la base de datos, por lo que al momento de crearse puede llevar un null el input",
            examples = {"1"})
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Nombre del cargo. No puede ser nulo",
            examples = {"Gerente"}
    )
    private String nombreCargo;

    @Column(nullable = false)
    @Schema(description = "Sueldo asociado al cargo. No puede ser nulo",
            examples = {"2000000"}
    )
    private Integer sueldo;


}
