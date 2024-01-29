package models;

import java.time.Year;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Pelicula {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPelicula;
    private String nombre;
    private String contraseña;
    private String email;
	private Year year;
	private int minutos;
	private double estrellas;
	private String sinopsis;
	
	public Pelicula(int idPelicula, String nombre, Year year, int minutos, double estrellas, String sinopsis) {
		this.idPelicula = idPelicula;
		this.nombre = nombre;
		this.year = year;
		this.minutos = minutos;
		this.estrellas = estrellas;
		this.sinopsis = sinopsis;
	}

	public int getIdPelicula() {
		return idPelicula;
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

	public Year getYear() {
		return year;
	}

	public void setYear(Year year) {
		this.year = year;
	}

	public int getMinutos() {
		return minutos;
	}

	public void setMinutos(int minutos) {
		this.minutos = minutos;
	}

	public double getEstrellas() {
		return estrellas;
	}

	public void setEstrellas(double estrellas) {
		this.estrellas = estrellas;
	}

	public String getSinopsis() {
		return sinopsis;
	}

	public void setSinopsis(String sinopsis) {
		this.sinopsis = sinopsis;
	}
	
}

