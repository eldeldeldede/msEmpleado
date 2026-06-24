package cl.duoc.msempleado.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.msempleado.dto.UsuarioDTO;

@FeignClient(name = "msUsuario")
public interface UsuarioClient {

    @GetMapping("/api/v1/usuarios/dto/email/{email}")
    public UsuarioDTO obtenerUsuarioDTO(@PathVariable String email);
}

