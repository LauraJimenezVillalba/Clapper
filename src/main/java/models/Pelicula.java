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
	private Year year;
	private int minutos;
	private double estrellas;
	private String sinopsis;
	private String poster;
	
	public Pelicula(int idPelicula, String nombre, Year year, int minutos, double estrellas, String sinopsis, String poster) {
		this.idPelicula = idPelicula;
		this.nombre = nombre;
		this.year = year;
		this.minutos = minutos;
		this.estrellas = estrellas;
		this.sinopsis = sinopsis;
		this.poster = poster;
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
	
	public String getPoster() {
		return poster;
	}
	
	public void setPoster(String poster) {
		this.poster = poster;
	}
	
}

