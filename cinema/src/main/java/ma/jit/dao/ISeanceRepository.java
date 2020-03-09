package ma.jit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import ma.jit.entites.Cinema;
import ma.jit.entites.Seance;
@RepositoryRestResource
@CrossOrigin("*")
public interface ISeanceRepository extends JpaRepository<Seance, Long> {

}
