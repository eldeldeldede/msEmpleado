package cl.duoc.msempleado.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.duoc.msempleado.dto.EmpleadoDTO;
import cl.duoc.msempleado.model.Cargo;
import cl.duoc.msempleado.model.Empleado;
import cl.duoc.msempleado.service.EmpleadoService;

@WebMvcTest(EmpleadoController.class)
public class EmpleadoControllerTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private ObjectMapper objectMapper; // serializa objetos Java a JSON

    @MockitoBean
    private EmpleadoService service;

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
    // GET /api/v1/empleados  →  listarEmpleado()
    // ─────────────────────────────────────────────

    @Test
    void listar_retorna200ConLista() throws Exception {
        // ARRANGE
        when(service.listar()).thenReturn(List.of(empEjemplo));

        // ACT + ASSERT
        mock.perform(get("/api/v1/empleados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Jeremish"));
    }

    @Test
    void listar_retorna204CuandoFalla() throws Exception {
        // ARRANGE: service lanza excepción → controller devuelve 204 noContent
        when(service.listar()).thenThrow(new RuntimeException("error inesperado"));

        // ACT + ASSERT
        mock.perform(get("/api/v1/empleados"))
                .andExpect(status().isNoContent());
    }

    // ─────────────────────────────────────────────
    // POST /api/v1/empleados  →  guardarEmpleado()
    // ─────────────────────────────────────────────

    @Test
    void guardarEmpleado_retorna200() throws Exception {
        // ARRANGE
        when(service.guardarEmpleado(any(Empleado.class))).thenReturn(empEjemplo);

        // ACT + ASSERT
        mock.perform(post("/api/v1/empleados")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(empEjemplo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Jeremish"))
                .andExpect(jsonPath("$.rut").value("123456"));
    }

    @Test
    void guardarEmpleado_retorna204CuandoFalla() throws Exception {
        // ARRANGE: service lanza excepción → controller devuelve 204 noContent
        when(service.guardarEmpleado(any(Empleado.class)))
                .thenThrow(new RuntimeException("error al guardar"));

        // ACT + ASSERT
        mock.perform(post("/api/v1/empleados")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(empEjemplo)))
                .andExpect(status().isNoContent());
    }

    // ─────────────────────────────────────────────
    // GET /api/v1/empleados/id/{id}  →  buscarPorId()
    // ─────────────────────────────────────────────

    @Test
    void buscarPorId_retorna200() throws Exception {
        // ARRANGE
        when(service.buscarPorId(1)).thenReturn(empEjemplo);

        // ACT + ASSERT
        mock.perform(get("/api/v1/empleados/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Jeremish"));
    }

    @Test
    void buscarPorId_retorna404() throws Exception {
        // ARRANGE
        when(service.buscarPorId(99)).thenThrow(new RuntimeException("empleado no encontrado"));

        // ACT + ASSERT
        mock.perform(get("/api/v1/empleados/id/99"))
                .andExpect(status().isNotFound());
    }

    // ─────────────────────────────────────────────
    // GET /api/v1/empleados/rut/{rut}  →  buscarPorRut()
    // ─────────────────────────────────────────────

    @Test
    void buscarPorRut_retorna200() throws Exception {
        // ARRANGE
        when(service.buscarPorRut("123456")).thenReturn(empEjemplo);

        // ACT + ASSERT
        mock.perform(get("/api/v1/empleados/rut/123456"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rut").value("123456"));
    }

    @Test
    void buscarPorRut_retorna404() throws Exception {
        // ARRANGE
        when(service.buscarPorRut("000000")).thenThrow(new RuntimeException("empleado no encontrado"));

        // ACT + ASSERT
        mock.perform(get("/api/v1/empleados/rut/000000"))
                .andExpect(status().isNotFound());
    }

    // ─────────────────────────────────────────────
    // GET /api/v1/empleados/dto/{id}  →  buscarDTO()
    // ─────────────────────────────────────────────

    @Test
    void buscarDTO_retorna200() throws Exception {
        // ARRANGE — ajusta los argumentos del constructor según tu EmpleadoDTO real
        EmpleadoDTO dto = new EmpleadoDTO(1, "Jeremish", "123456", 1, "pe.cid@duocuc.cl");
        when(service.buscarEmpleadoDTO(1)).thenReturn(dto);

        // ACT + ASSERT
        mock.perform(get("/api/v1/empleados/dto/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEmpleado").value(1))
                .andExpect(jsonPath("$.nombre").value("Jeremish"));
    }

    @Test
    void buscarDTO_retorna404() throws Exception {
        // ARRANGE
        when(service.buscarEmpleadoDTO(99)).thenThrow(new RuntimeException("empleado no encontrado"));

        // ACT + ASSERT
        mock.perform(get("/api/v1/empleados/dto/99"))
                .andExpect(status().isNotFound());
    }

    // ─────────────────────────────────────────────
    // PUT /api/v1/empleados/{id}  →  actualizarEmpleado()
    // ─────────────────────────────────────────────

    @Test
    void actualizarEmpleado_retorna200() throws Exception {
        // ARRANGE
        when(service.actualizarEmpleado(eq(1), any(Empleado.class))).thenReturn(empEjemplo);

        // ACT + ASSERT
        mock.perform(put("/api/v1/empleados/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(empEjemplo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Jeremish"));
    }

    @Test
    void actualizarEmpleado_retorna404() throws Exception {
        // ARRANGE
        when(service.actualizarEmpleado(eq(99), any(Empleado.class)))
                .thenThrow(new RuntimeException("empleado no encontrado"));

        // ACT + ASSERT
        mock.perform(put("/api/v1/empleados/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(empEjemplo)))
                .andExpect(status().isNotFound());
    }

    // ─────────────────────────────────────────────
    // DELETE /api/v1/empleados/{id}  →  eliminarEmpleado()
    // ─────────────────────────────────────────────

    @Test
    void eliminarEmpleado_retorna204() throws Exception {
        // ARRANGE: eliminarEmpleado() es void → doNothing simula éxito
        doNothing().when(service).eliminarEmpleado(1);

        // ACT + ASSERT: 204 No Content es el retorno exitoso del controller
        mock.perform(delete("/api/v1/empleados/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void eliminarEmpleado_retorna404() throws Exception {
        // ARRANGE
        doThrow(new RuntimeException("empleado no encontrado")).when(service).eliminarEmpleado(99);

        // ACT + ASSERT
        mock.perform(delete("/api/v1/empleados/99"))
                .andExpect(status().isNotFound());
    }
}