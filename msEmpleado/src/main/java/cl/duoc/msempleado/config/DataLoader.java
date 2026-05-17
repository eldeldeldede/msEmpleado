package cl.duoc.msempleado.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.msempleado.model.Cargo;
import cl.duoc.msempleado.model.Empleado;
import cl.duoc.msempleado.repository.CargoRepository;
import cl.duoc.msempleado.repository.EmpleadoRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(EmpleadoRepository empleadoRepo,
                                   CargoRepository cargoRepo 
    ){
        return args -> {
            if(cargoRepo.count()>0 && empleadoRepo.count()>0){
                System.out.println("Datos ya cargados");
            }else{
                Cargo cargo1 = new Cargo(null, "Gerente", 2000000);
                Cargo cargo2 = new Cargo(null, "Ejecutivo de ventas", 1000000);
                Cargo cargo3 = new Cargo(null, "Asistente administrativo", 800000);
                cargoRepo.save(cargo1);
                cargoRepo.save(cargo2);
                cargoRepo.save(cargo3);

                Empleado empleado1 = new Empleado(null, "12345678-9", "Juan", "Pérez", "912345678", "juan.perez@empresa.com", 2, 1,cargo1);
                Empleado empleado2 = new Empleado(null, "98765432-1", "María", "González", "987654321", "maria.gonzalez@empresa.com", 3, 2, cargo2);
                Empleado empleado3 = new Empleado(null, "11111111-1", "Carlos", "Sánchez", "912345679", "carlos.sanchez@empresa.com", 4, 3, cargo3);
                empleadoRepo.save(empleado1);
                empleadoRepo.save(empleado2);
                empleadoRepo.save(empleado3);
                System.out.println("Datos cargados");
            }
        };
    }
}
