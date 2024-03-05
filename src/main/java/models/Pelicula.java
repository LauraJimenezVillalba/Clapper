package models;

import java.time.Year;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Pelicula {

  @Id
  private int idPelicula;
  private String nombre;
  private Year year;
  private int minutos;
  private double estrellas;
  private String sinopsis;
  private String poster;
  private boolean visto;
  private String genero;
  private String ubicacion;
  private String actores;
  private String directores;

  public Pelicula() {}

  public Pelicula(String nombre, Year year, int minutos, double estrellas, String sinopsis,
      String poster, boolean visto, String genero, String ubicacion, String actores,
      String directores) {
    this.nombre = nombre;
    this.year = year;
    this.minutos = minutos;
    this.estrellas = estrellas;
    this.sinopsis = sinopsis;
    this.poster = poster;
    this.visto = visto;
    this.genero = genero;
    this.ubicacion = ubicacion;
    this.actores = actores;
    this.directores = directores;
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

  public boolean isVisto() {
    return visto;
  }

  public void setVisto(boolean visto) {
    this.visto = visto;
  }

  public String getGenero() {
    return genero;
  }

  public void setGenero(String genero) {
    this.genero = genero;
  }

  public String getUbicacion() {
    return ubicacion;
  }

  public void setUbicacion(String ubicacion) {
    this.ubicacion = ubicacion;
  }

  public String getActores() {
    return actores;
  }

  public void setActores(String actores) {
    this.actores = actores;
  }

  public String getDirectores() {
    return directores;
  }

  public void setDirectores(String directores) {
    this.directores = directores;
  }



}

