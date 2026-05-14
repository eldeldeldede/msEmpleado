package cl.duoc.msempleado.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.msempleado.model.Cargo;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Integer>{

}
