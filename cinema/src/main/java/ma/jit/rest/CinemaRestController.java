package ma.jit.rest;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import ma.jit.dao.IFilmRepository;
import ma.jit.dao.ITicketRepository;
import ma.jit.entites.Film;
import ma.jit.entites.Ticket;

@RestController
@CrossOrigin("*")
public class CinemaRestController {
	@Autowired
	private IFilmRepository filmRepo;
	@Autowired
	private ITicketRepository ticketRepo;
	
	
	@GetMapping(path="/imageFilm/{id}",produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] image(@PathVariable(name="id") Long id) throws Exception {
		Film film = filmRepo.findById(id).get();
		String photoName=film.getPhoto();
		File file = new File(System.getProperty("user.home")+"/Desktop/images/"+photoName);
		Path path=Paths.get(file.toURI());
		return Files.readAllBytes(path);
		
	}
	@PostMapping("/payerTickets")
	@Transactional
	public List<Ticket> payer (@RequestBody TicketForm ticketForm) {
		List<Ticket> listTickets=new ArrayList<>();
		ticketForm.getTickets().forEach(idTicket->{
			//System.out.println(idTicket);
			Ticket ticket= ticketRepo.findById(idTicket).get();
			ticket.setNomClient(ticketForm.getNomClient());
			ticket.setReserve(true);
			ticket.setCodePayement(ticketForm.getCodePayement());
			ticketRepo.save(ticket);
			listTickets.add(ticket);
		});
		return listTickets;
		
	}

}

@Data
class TicketForm {
	private int codePayement;
	private String nomClient;
	private List<Long> tickets = new ArrayList<>();
}
