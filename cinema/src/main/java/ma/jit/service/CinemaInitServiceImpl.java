package ma.jit.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.jit.dao.ICategorieRepository;

import ma.jit.dao.ICinemaRepository;
import ma.jit.dao.IFilmRepository;
import ma.jit.dao.IPlaceRepository;
import ma.jit.dao.IProjectionRepository;
import ma.jit.dao.ISalleRepository;
import ma.jit.dao.ISeanceRepository;
import ma.jit.dao.ITicketRepository;
import ma.jit.dao.IVilleRepository;
import ma.jit.entites.Categorie;
import ma.jit.entites.Cinema;
import ma.jit.entites.Film;
import ma.jit.entites.Place;
import ma.jit.entites.Projection;
import ma.jit.entites.Salle;
import ma.jit.entites.Seance;
import ma.jit.entites.Ticket;
import ma.jit.entites.Ville;

@Service
@Transactional
public class CinemaInitServiceImpl implements ICinemaInitService {
	
	@Autowired
	private IVilleRepository villeRepo;
	@Autowired
	private ICinemaRepository cinemaRepo;
	@Autowired
	private ISalleRepository salleRepo;
	@Autowired
	private IPlaceRepository placeRepo;
	@Autowired
	private ISeanceRepository seanceRepo;
	@Autowired
	private IFilmRepository filmRepo;
	@Autowired
	private IProjectionRepository projectionRepo;
	@Autowired
	private ICategorieRepository categoriesRepo;
	@Autowired
	private ITicketRepository ticketRepo;
	

	@Override
	public void initVilles() {

		Stream.of("Casablanca","Marrakech","Rabat","Tanger").forEach(v->{
			Ville ville = new Ville();
			ville.setName(v);
			villeRepo.save(ville);
		});
	}

	@Override
	public void initCinemas() {
		villeRepo.findAll().forEach(v->{
			Stream.of("Megarama","IMax","FOUNOUN","CHAHRAZAD","DAOULIZ")
			.forEach(nameCinema->{
				Cinema cinema = new Cinema();
				cinema.setName(nameCinema);
				cinema.setVille(v);
				cinema.setNombreSalle(3+(int)(Math.random()*7));
				cinemaRepo.save(cinema);
			});
		});
	}

	@Override
	public void initSalles() {
		cinemaRepo.findAll().forEach(cinema ->{
			for(int i=0;i<cinema.getNombreSalle();i++) {
				Salle salle = new Salle();
				salle.setName("Salle "+(i+1));
				salle.setCinema(cinema);
				salle.setNombrePlace(15+(int)(Math.random()*20));
				salleRepo.save(salle);
			}
		});
		
	}

	@Override
	public void initPlace() {
		salleRepo.findAll().forEach(salle ->{
			for(int i=0;i<salle.getNombrePlace();i++) {
				Place place= new Place();
				place.setNumero(i+1);
				place.setSalle(salle);
				placeRepo.save(place);
			}
		});
	}

	@Override
	public void initSeances() {
		DateFormat dateFormat=new SimpleDateFormat("HH:mm");
		Stream.of("12:00","15:00","17:00","19:00","21:00").forEach(s->{
			Seance seance = new Seance();
			try {
				seance.setHeureDebut(dateFormat.parse(s));
				seanceRepo.save(seance);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	@Override
	public void initCategories() {
		Stream.of("Histoire","Action","Drama","Fiction").forEach(cat->{
			Categorie categorie= new Categorie();
			categorie.setName(cat);
			categoriesRepo.save(categorie);
		});
	}

	@Override
	public void initFilms() {
		double[] durees = new double [] {1,1.5,2,2.5,3};
		List<Categorie> categories = categoriesRepo.findAll();
		Stream.of("12Homme En Colere","Forrest Gump","Green Book","Green Mile","Le Parrain","Le Seigneur Des Anneaux")
		.forEach(f->{
			Film film = new Film();
			film.setTitre(f);
			film.setDuree(durees[new Random().nextInt(durees.length)]);
			film.setPhoto(f.replaceAll(" ", "")+".jpg");
			film.setCategorie(categories.get(new Random().nextInt(categories.size())));
			filmRepo.save(film);
		});
	}

	@Override
	public void initProjections() {
		double[] prices = new double [] {30,50,60,70,90,100};
		List<Film> films = filmRepo.findAll();
		villeRepo.findAll().forEach(ville->{
			ville.getCinema().forEach(cinema->{
				cinema.getSalles().forEach(salles->{
					int index = new Random().nextInt(films.size());
					Film film= films.get(index);
						seanceRepo.findAll().forEach(seance->{
							Projection projection = new Projection();
							projection.setDateProjection(new Date());
							projection.setFilm(film);
							projection.setPrix(prices[new Random().nextInt(prices.length)]);
							projection.setSalle(salles);
							projection.setSeance(seance);
							projectionRepo.save(projection);
						
					});
				});
			});
		});
	}

	@Override
	public void initTickets() {
		projectionRepo.findAll().forEach(p->{
			p.getSalle().getPlaces().forEach(place->{
				Ticket ticket = new Ticket();
				ticket.setPlace(place);
				ticket.setPrix(p.getPrix());
				ticket.setProjection(p);
				ticket.setReserve(false);
				ticketRepo.save(ticket);
			});
		});
	}
	

}
