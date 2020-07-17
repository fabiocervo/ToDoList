package it.dstech.formazione.controller;


import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.TimeZone;

import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.dstech.formazione.models.Evento;
import it.dstech.formazione.models.Sched;
import it.dstech.formazione.models.Utente;
import it.dstech.formazione.service.EventoServiceDAO;
import it.dstech.formazione.service.MailServiceDAO;
import it.dstech.formazione.service.UtenteServiceDAO;

@Controller
public class MyController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MyController.class);
	@Autowired
	private UtenteServiceDAO utenteServ;
	@Autowired
	private EventoServiceDAO eventoServ;
	@Autowired
	private MailServiceDAO mailServ;
	@Autowired
	private TaskScheduler scheduler;
	
	@GetMapping(value = { "/", "/login" })
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}

	@GetMapping(value = "/registrazione")
	public ModelAndView registration() {
		ModelAndView modelAndView = new ModelAndView();
		Utente user = new Utente();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("registrazione");
		return modelAndView;
	}

	@PostMapping(value = "/registrazione")
	public ModelAndView createNewUser(Utente user, BindingResult bindingResult, @RequestParam("file") MultipartFile multiPartFile) {
		ModelAndView modelAndView = new ModelAndView();
		Utente userExists = utenteServ.findByUsername(user.getUsername());
		if (userExists != null) {
			bindingResult.rejectValue("username", "error.user",
					"There is already a user registered with the user name provided");

		}

		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registrazione");
		} else {
			MultipartFile image =  multiPartFile;
		      InputStream f;
			try {
				f = image.getInputStream();
			
		      byte[] imageBytes = new byte[(int) image.getSize()];
		      f.read(imageBytes, 0, imageBytes.length);
		      f.close();
		      user.setImage(Base64.getEncoder().encodeToString(imageBytes));
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			utenteServ.add(user);
			modelAndView.addObject("messaggio", "User has been registered successfully");
			modelAndView.setViewName("login");
		}
		return modelAndView;
	}
	
	@GetMapping(value = "/utente/home")
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utente utente = utenteServ.findByUsername(auth.getName());
		mav.setViewName("utente/home");
		mav.addObject("listaEventi", utente.getEventi());
		mav.addObject("utente", utente);
		return mav;
	}
	
	
    @PostMapping(value = "/salvaEvento")
    public ModelAndView salvaEvento(Evento evento, RedirectAttributes redirectAttributes) {
    	ModelAndView mav = new ModelAndView();
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	Utente user = utenteServ.findByUsername(auth.getName());
    	evento.setUtente(user);
        Evento eventoSalvato = eventoServ.add(evento);
    	utenteServ.addEvento(user, evento);
    	if (eventoSalvato != null) {
			LocalDateTime data = eventoSalvato.getData();
			int minuti = data.getMinute();
			int ore = data.getHour();
			int giorno = data.getDayOfMonth();
			int mese = data.getMonthValue();
			String expression = "";
			if ((minuti - 5)<0) {
				expression += "0 " + (minuti+5) + " " + (ore-1) + " " + giorno + " " + mese + "?";
				if ((ore-1)<0) {
					expression += "0 " + (minuti+5) + " " + (ore+23) + " " + giorno + " " + mese + "?";
				} else {
					expression += "0 " + (minuti-5) + " " + (ore) + " " + giorno + " " + mese + "?";
				}
			} 
			LOGGER.info("chist è o' cron " + expression);
			CronTrigger trigger = new CronTrigger(expression, TimeZone.getTimeZone(TimeZone.getDefault().getID()));
			Sched sched = new Sched(eventoSalvato, mailServ);
			scheduler.schedule(sched, trigger);
			redirectAttributes.addAttribute("messaggio", "Evento aggiunto con successo");

		}
    	    
			mav.setViewName("redirect:/utente/home");
			return mav;
    }
}
