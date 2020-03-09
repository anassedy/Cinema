package ma.jit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import ma.jit.entites.Cinema;
import ma.jit.entites.Salle;
@RepositoryRestResource
@CrossOrigin("*")
public interface ISalleRepository extends JpaRepository<Salle, Long> {

}
