package cl.duoc.msempleado.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.msempleado.dto.EmpleadoDTO;
import cl.duoc.msempleado.model.Empleado;
import cl.duoc.msempleado.repository.EmpleadoRepository;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository repo;

    public List<Empleado> listar(){
        return repo.findAll();
    }

    public Empleado buscarPorId(Integer id){
        return repo.findById(id).orElseThrow(() -> new RuntimeException("empleado no encontrado"));
    }

    public Empleado buscarPorRut(String rut){
        return repo.findByRut(rut).orElseThrow(() -> new RuntimeException("empleado no encontrado"));
    }
        
    public Empleado guardarEmpleado(Empleado empleado){
        return repo.save(empleado);
    }

    public void eliminarEmpleado(Integer id){
        if(repo.existsById(id)){
            repo.deleteById(id);
        }else{
            throw new RuntimeException("empleado no encontrado");
        }
    }

    public Empleado actualizarEmpleado(Integer id, Empleado empleadoActualizar){
        Empleado empleado = repo.findById(id).orElseThrow(() -> new RuntimeException("empleado no encontrado"));
        empleado.setRut(empleadoActualizar.getRut());
        empleado.setNombre(empleadoActualizar.getNombre());
        empleado.setApellido(empleadoActualizar.getApellido());
        empleado.setTelefono(empleadoActualizar.getTelefono());
        empleado.setGmail(empleadoActualizar.getGmail());
        empleado.setDireccion(empleadoActualizar.getDireccion());
        empleado.setCargo(empleadoActualizar.getCargo());

        return repo.save(empleado);

    }

    public EmpleadoDTO buscarEmpleadoDTOPorId(Integer id){
        Empleado empleado = buscarPorId(id);
        return new EmpleadoDTO(empleado.getId(), empleado.getNombre(), empleado.getRut());
    }
}
