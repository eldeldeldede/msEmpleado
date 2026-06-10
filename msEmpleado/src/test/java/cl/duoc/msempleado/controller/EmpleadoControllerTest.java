package cl.duoc.msempleado.controller;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import cl.duoc.msempleado.model.Cargo;
import cl.duoc.msempleado.model.Empleado;
import cl.duoc.msempleado.service.EmpleadoService;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmpleadoController.class)// levanta solo la capa web, no la bd
public class EmpleadoControllerTest {

    @Autowired
    private MockMvc mock; //mock que simula la peticiones http

    @MockitoBean
    private EmpleadoService service; // service falso

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
    void buscarPorId_retorna200() throws Exception{
        //ARRANGE: el service debe retornar el empleado
        when(service.buscarPorId(1)).thenReturn(empEjemplo);

        //ACT + ASSERT
        mock.perform(get("/api/v1/empleados/id/1"))
            .andExpect(status().isOk());
    }

    @Test
    void buscarPorId_retornar404() throws Exception{
        //ARRANGE : buscamos un doctor con id 99 y tira un error

        when(service.buscarPorId(99)).thenThrow(new RuntimeException("Empleado no encontrado"));

        //ACT + ASSERT
        mock.perform(get("/api/v1/empleados/id/99"))
            .andExpect(status().isNotFound()); // o sea un codigo HTTPS 404

    }

}
