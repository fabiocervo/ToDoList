package it.dstech.formazione.models;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Utente {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String username;
	private String password;
	private String mail;
	@Lob
	private String image;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "utente_ruolo", joinColumns = @JoinColumn(name = "utente_id"), inverseJoinColumns = @JoinColumn(name = "ruolo_id"))
	private Set<Ruolo> ruolo;
	private boolean active;
	@OneToMany(mappedBy = "utente", fetch = FetchType.EAGER)
	private List<Evento> eventi;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Set<Ruolo> getRuolo() {
		return ruolo;
	}

	public void setRuolo(Set<Ruolo> ruolo) {
		this.ruolo = ruolo;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<Evento> getEventi() {
		return eventi;
	}

	public void setEventi(List<Evento> eventi) {
		this.eventi = eventi;
	}
	
	

}
