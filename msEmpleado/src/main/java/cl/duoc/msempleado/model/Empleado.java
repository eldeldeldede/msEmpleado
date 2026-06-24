package cl.duoc.msempleado.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "empleado")
@Schema(description = "Representa un empleado en el sistema del Rent a Car.")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID unico del empleado, es autoincrementable dentro de la base de datos, por lo que al momento de crearse puede llevar un null el input",
            examples = {"1"})
    private Integer id;

    @Column(nullable = false, unique = true)
    @Schema(description = "Rut del empleado, es unico dentro de la base de datos, por lo que al momento de crearse puede llevar un null el input",
            examples = {"12345678-9"})
    private String rut;

    @Column(nullable = false)
    @Schema(description = "Nombre del empleado. No puede ser nulo",
            examples = {"Juan"}
    )
    private String nombre;

    @Column(nullable = false)
    @Schema(description = "Apellido del empleado. No puede ser nulo",
            examples = {"Perez"}
    )
    private String apellido;

    @Column(nullable = false)
    @Schema(description = "Telefono del empleado. No puede ser nulo",
            examples = {"912345678"}
    )
    private String telefono;

    @Column(nullable = false, unique = true)
    @Schema(description = "Email del empleado. No puede ser nulo y es único dentro de la base de datos",
            examples = {"juan.perez@email.cl"}
    )
    private String email;

    @Column(name = "usuario_id", nullable = true)
    @Schema(description = "ID del usuario asociado al empleado. Puede ser nulo si el empleado no tiene un usuario asociado",
            examples = {"1"}
    )
    private Integer usuarioId;

    @Column(name = "sucursal_id", nullable = true)
    @Schema(description = "ID de la sucursal a la que pertenece el empleado. Puede ser nulo si el empleado no está asignado a una sucursal",
            examples = {"1"}
    )
    private Integer sucursalId;

    @ManyToOne//Datos relacionados se cargan inmediatamente
    @JoinColumn(name = "cargo_id", nullable = false)
    @Schema(description = "Cargo del empleado. No puede ser nulo y representa la relación con la entidad Cargo")
    @JsonBackReference
    private Cargo cargo;

    


}
