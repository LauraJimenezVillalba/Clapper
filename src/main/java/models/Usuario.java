package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int genero;
	private String nombre;
	private String contraseña;
	private String email;

	public Usuario() {
	}

	public Usuario(String nombre, String contraseña, String email, int genero) {
		this.nombre = nombre;
		this.contraseña = contraseña;
		this.email = email;
		this.genero = genero;
	}

	public int getId() {
		return id;
	}
	
	public int getGenero() {
		return genero;
	}
	
	public void setGenero(int genero) {
		this.genero = genero;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
