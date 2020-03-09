package ma.jit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import ma.jit.entites.Categorie;
import ma.jit.entites.Cinema;
@RepositoryRestResource
@CrossOrigin("*")
public interface ICategorieRepository extends JpaRepository<Categorie, Long> {

}
