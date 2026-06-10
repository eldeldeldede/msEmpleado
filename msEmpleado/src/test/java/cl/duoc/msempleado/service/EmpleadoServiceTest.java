package cl.duoc.msempleado.service;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.msempleado.model.Cargo;
import cl.duoc.msempleado.model.Empleado;
import cl.duoc.msempleado.repository.EmpleadoRepository;

@ExtendWith(MockitoExtension.class)
public class EmpleadoServiceTest {
    @Mock // no es el repo real, solo va a ser una simulacion de repo
    private EmpleadoRepository empRepository;

    // el servicio real con el repo simulado inyectado
    @InjectMocks 
    private EmpleadoService empService;


    private Empleado empEjemplo;

    @BeforeEach
    void setUp(){

        empEjemplo = new Empleado();
        empEjemplo.setId(1);
        empEjemplo.setRut("123456");
        empEjemplo.setNombre("Jeremish");
        empEjemplo.setApellido("Weon");
        empEjemplo.setTelefono("93343449");
        empEjemplo.setEmail("pe.cid@duocuc.cl");
        empEjemplo.setUsuarioId(1);
        empEjemplo.setSucursalId(1);
        empEjemplo.setCargo(new Cargo(1, "Gerente", 200000));


    }

    @Test
    public void buscarPorID_encontrado(){

        //ARANGE : preparamos la prueba, le decimos que hacer

        Optional<Empleado> optionalEmpleado = Optional.of(empEjemplo);
        when(empRepository.findById(1)).thenReturn(optionalEmpleado);

        //ACT : llamamos al metodo real

        Empleado resultado = empService.buscarPorId(1);

        //ASSERT : verificamos si el doctor que retorno es el correcto

        assertEquals(1,resultado.getId());
        assertEquals("Jeremish", resultado.getNombre());

    }


    @Test
    void buscarPorId_noEncontrado(){
        //ARANGE : preparamos la prueba pero apra que retorne un empleado vacio
        Optional<Empleado> empleadoVacio = Optional.empty();
        when(empRepository.findById(99)).thenReturn(empleadoVacio);

        //ACT + ASSERT : verificamos si lanza la exception correcta
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            empService.buscarPorId(99);
        });

        assertEquals("empleado no encontrado", exception.getMessage());
    }


    @Test
    void guardar(){
        //ARANGE : configuramos que el repository retorne el doctor guardado
        when(empRepository.save(empEjemplo)).thenReturn(empEjemplo);

        //ACT :
        Empleado resultado = empService.guardarEmpleado(empEjemplo);

        //ASSERT
        assertEquals("Jeremish", resultado.getNombre());
    }


    @Test
    void Eliminar_exitoso(){
        //ARANGE : el doctor existe
        when(empRepository.existsById(1)).thenReturn(true);

        //ASSERT : no debe lanzar error/exception 
        assertDoesNotThrow(() -> empService.eliminarEmpleado(1));

        //verificamos que el deleteByID fue exitoso solo una vez
        verify(empRepository, times(1)).deleteById(1);
    }


    @Test
    void eliminar_noExiste(){

    // ARRANGE: el empleado no existe
    when(empRepository.existsById(99)).thenReturn(false);

    // ACT + ASSERT: verificar que se lance la excepción
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        empService.eliminarEmpleado(99);
    });

    assertEquals("empleado no encontrado", exception.getMessage());

    // Verificar que nunca se intentó eliminar
    verify(empRepository, times(0)).deleteById(99);
}

}
