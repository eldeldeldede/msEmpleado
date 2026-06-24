package cl.duoc.msempleado.service;

import java.util.List;
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

import cl.duoc.msempleado.client.UsuarioClient;
import cl.duoc.msempleado.dto.EmpleadoDTO;
import cl.duoc.msempleado.dto.UsuarioDTO;
import cl.duoc.msempleado.model.Cargo;
import cl.duoc.msempleado.model.Empleado;
import cl.duoc.msempleado.repository.EmpleadoRepository;

@ExtendWith(MockitoExtension.class)
public class EmpleadoServiceTest {

    @Mock
    private EmpleadoRepository repo; // ⚠️ debe llamarse igual que el campo en EmpleadoService

    @Mock
    private UsuarioClient clientUsuario; // ⚠️ necesario porque EmpleadoService lo tiene inyectado

    @InjectMocks
    private EmpleadoService empService;

    private Empleado empEjemplo;

    @BeforeEach
    void setUp() {
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

    // ─────────────────────────────────────────────
    // buscarPorId
    // ─────────────────────────────────────────────

    @Test
    void buscarPorID_encontrado() {
        // ARRANGE
        when(repo.findById(1)).thenReturn(Optional.of(empEjemplo));

        // ACT
        Empleado resultado = empService.buscarPorId(1);

        // ASSERT
        assertEquals(1, resultado.getId());
        assertEquals("Jeremish", resultado.getNombre());
    }

    @Test
    void buscarPorId_noEncontrado() {
        // ARRANGE
        when(repo.findById(99)).thenReturn(Optional.empty());

        // ACT + ASSERT
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            empService.buscarPorId(99);
        });

        assertEquals("empleado no encontrado", exception.getMessage());
    }

    // ─────────────────────────────────────────────
    // guardarEmpleado
    // ─────────────────────────────────────────────

    @Test
    void guardar() {
        // ARRANGE
        when(repo.save(empEjemplo)).thenReturn(empEjemplo);

        // ACT
        Empleado resultado = empService.guardarEmpleado(empEjemplo);

        // ASSERT
        assertEquals("Jeremish", resultado.getNombre());
    }

    // ─────────────────────────────────────────────
    // eliminarEmpleado
    // ─────────────────────────────────────────────

    @Test
    void eliminar_exitoso() {
        // ARRANGE
        when(repo.existsById(1)).thenReturn(true);

        // ACT + ASSERT: no debe lanzar excepción
        assertDoesNotThrow(() -> empService.eliminarEmpleado(1));

        // VERIFY: deleteById se llamó exactamente una vez
        verify(repo, times(1)).deleteById(1);
    }

    @Test
    void eliminar_noExiste() {
        // ARRANGE
        when(repo.existsById(99)).thenReturn(false);

        // ACT + ASSERT
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            empService.eliminarEmpleado(99);
        });

        assertEquals("empleado no encontrado", exception.getMessage());

        // VERIFY: deleteById nunca debe llamarse
        verify(repo, times(0)).deleteById(99);
    }

    // ─────────────────────────────────────────────
    // actualizarEmpleado
    // ─────────────────────────────────────────────

    @Test
    void actualizarEmpleado_exitoso() {
        // ARRANGE
        Empleado empActualizado = new Empleado();
        empActualizado.setRut("999999");
        empActualizado.setNombre("Jeremish Actualizado");
        empActualizado.setApellido("Weon Actualizado");
        empActualizado.setTelefono("11111111");
        empActualizado.setEmail("nuevo@duocuc.cl");
        empActualizado.setCargo(new Cargo(2, "Supervisor", 150000));
        empActualizado.setUsuarioId(2);
        empActualizado.setSucursalId(2);

        when(repo.findById(1)).thenReturn(Optional.of(empEjemplo));
        when(repo.save(empEjemplo)).thenReturn(empEjemplo);

        // ACT
        Empleado resultado = empService.actualizarEmpleado(1, empActualizado);

        // ASSERT
        assertEquals("Jeremish Actualizado", resultado.getNombre());
        assertEquals("999999", resultado.getRut());
        verify(repo, times(1)).save(empEjemplo);
    }

    @Test
    void actualizarEmpleado_noEncontrado() {
        // ARRANGE
        when(repo.findById(99)).thenReturn(Optional.empty());

        // ACT + ASSERT
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            empService.actualizarEmpleado(99, new Empleado());
        });

        assertEquals("empleado no encontrado", exception.getMessage());
    }

    // ─────────────────────────────────────────────
    // listar
    // ─────────────────────────────────────────────

    @Test
    void listar_retornaLista() {
        // ARRANGE
        when(repo.findAll()).thenReturn(List.of(empEjemplo));

        // ACT
        List<Empleado> resultado = empService.listar();

        // ASSERT
        assertEquals(1, resultado.size());
        assertEquals("Jeremish", resultado.get(0).getNombre());
    }

    // ─────────────────────────────────────────────
    // buscarPorRut
    // ─────────────────────────────────────────────

    @Test
    void buscarPorRut_encontrado() {
        // ARRANGE
        when(repo.findByRut("123456")).thenReturn(Optional.of(empEjemplo));

        // ACT
        Empleado resultado = empService.buscarPorRut("123456");

        // ASSERT
        assertEquals("123456", resultado.getRut());
        assertEquals("Jeremish", resultado.getNombre());
    }

    @Test
    void buscarPorRut_noEncontrado() {
        // ARRANGE
        when(repo.findByRut("000000")).thenReturn(Optional.empty());

        // ACT + ASSERT
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            empService.buscarPorRut("000000");
        });

        assertEquals("empleado no encontrado", exception.getMessage());
    }

    // ─────────────────────────────────────────────
    // buscarEmpleadoDTO
    // ─────────────────────────────────────────────

    @Test
    void buscarEmpleadoDTO_exitoso() {
        // ARRANGE
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1);
        usuarioDTO.setEmail("pe.cid@duocuc.cl");

        when(repo.findById(1)).thenReturn(Optional.of(empEjemplo));
        when(clientUsuario.obtenerUsuarioDTO("pe.cid@duocuc.cl")).thenReturn(usuarioDTO);

        // ACT
        EmpleadoDTO resultado = empService.buscarEmpleadoDTO(1);

        // ASSERT
        assertEquals(1, resultado.getIdEmpleado());
        assertEquals("Jeremish", resultado.getNombre());
        assertEquals("123456", resultado.getRut());
        assertEquals(1, resultado.getIdUsuario());
        assertEquals("pe.cid@duocuc.cl", resultado.getEmail());
    }

    @Test
    void buscarEmpleadoDTO_noEncontrado() {
        // ARRANGE
        when(repo.findById(99)).thenReturn(Optional.empty());

        // ACT + ASSERT
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            empService.buscarEmpleadoDTO(99);
        });

        assertEquals("empleado no encontrado", exception.getMessage());
    }
}