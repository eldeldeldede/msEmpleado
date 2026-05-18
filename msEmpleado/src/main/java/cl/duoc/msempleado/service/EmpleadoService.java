package cl.duoc.msempleado.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.msempleado.client.UsuarioClient;
import cl.duoc.msempleado.dto.EmpleadoDTO;
import cl.duoc.msempleado.dto.UsuarioDTO;
import cl.duoc.msempleado.model.Empleado;
import cl.duoc.msempleado.repository.EmpleadoRepository;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository repo;

    @Autowired
    private UsuarioClient clientUsuario;;

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
        empleado.setEmail(empleadoActualizar.getEmail());
        empleado.setCargo(empleadoActualizar.getCargo());
        empleado.setUsuarioId(empleadoActualizar.getUsuarioId());
        empleado.setSucursalId(empleadoActualizar.getSucursalId());

        return repo.save(empleado);

    }

    public EmpleadoDTO buscarEmpleadoDTOPorId(Integer id){
        Empleado empleado = buscarPorId(id);

        UsuarioDTO usuario = clientUsuario.obtenerUsuarioDTO(empleado.getUsuarioId());
        return new EmpleadoDTO(empleado.getId(), empleado.getNombre(), empleado.getRut(), usuario);
    }
}
