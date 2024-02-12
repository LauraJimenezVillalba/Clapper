package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Genero {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idGenero;
	private String nombre;

	public Genero(int idGenero, String nombre) {
		this.idGenero = idGenero;
		this.nombre = nombre;
	}

	public int getIdGenero() {
		return idGenero;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
