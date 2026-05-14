package cl.duoc.msempleado.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.msempleado.dto.EmpleadoDTO;
import cl.duoc.msempleado.model.Empleado;
import cl.duoc.msempleado.service.EmpleadoService;

@RestController
@RequestMapping("api/v1/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService service;

    @PostMapping
    public ResponseEntity<Empleado> guardarEmpleado(@RequestBody Empleado empleado){
        Empleado nuevoEmpleado = service.guardarEmpleado(empleado);
        return ResponseEntity.ok(nuevoEmpleado);
    }

    @GetMapping
    public ResponseEntity<List<Empleado>> listarEmpleado(){
        List<Empleado> empleado = service.listar();
        if(empleado != null){
            return ResponseEntity.ok(empleado);
        }else{
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Empleado> buscarPorId(@PathVariable Integer id){
        try {
            Empleado empleado = service.buscarPorId(id);
            return ResponseEntity.ok(empleado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dto/{id}")
    public ResponseEntity<EmpleadoDTO> buscarDTO(@PathVariable Integer id){
        try {
            EmpleadoDTO empleadoDTO = service.buscarEmpleadoDTOPorId(id);
            return ResponseEntity.ok(empleadoDTO);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/rut/{rut}")
    public ResponseEntity<Empleado> buscarPorRut(@PathVariable String rut){
        try{
            Empleado empleado = service.buscarPorRut(rut);
            return ResponseEntity.ok(empleado);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{rut}")
    public ResponseEntity<?> eliminarEmpleado(@PathVariable Integer id){
        try{
            service.eliminarEmpleado(id);
            return ResponseEntity.noContent().build();
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable Integer id, @RequestBody Empleado empleadoActualizar){
        try {
            Empleado empleado = service.actualizarEmpleado(id, empleadoActualizar);    
            return ResponseEntity.ok(empleado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
