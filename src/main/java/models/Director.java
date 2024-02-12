package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Director {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idDirector;
	private String nombre;
	private String apellido;

	public Director(int idDirector, String nombre, String apellido) {
		this.idDirector = idDirector;
		this.nombre = nombre;
		this.apellido = apellido;
	}

	public int getIdDirector() {
		return idDirector;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

}
