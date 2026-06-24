package cl.duoc.msempleado.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoDTO {

    private Integer idEmpleado;
    private String nombre;
    private String rut;
    private Integer idUsuario;
    private String email;

}
