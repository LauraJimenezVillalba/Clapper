package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Actor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idActor;
	private String nombre;
	private String apellido;

	public Actor(int idActor, String nombre, String apellido) {
		this.idActor = idActor;
		this.nombre = nombre;
		this.apellido = apellido;
	}

	public int getIdActor() {
		return idActor;
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
