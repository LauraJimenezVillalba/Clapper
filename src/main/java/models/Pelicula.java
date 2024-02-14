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
  private boolean visto;
  private String genero;
  private String ubicacion;
  //actores, directores
  
  public Pelicula() {}
  
  public Pelicula(int idPelicula, String nombre, Year year, int minutos, double estrellas,
      String sinopsis, String poster, boolean visto, String genero, String ubicacion) {
    this.idPelicula = idPelicula;
    this.nombre = nombre;
    this.year = year;
    this.minutos = minutos;
    this.estrellas = estrellas;
    this.sinopsis = sinopsis;
    this.poster = poster;
    this.visto = visto;
    this.genero = genero;
    this.ubicacion = ubicacion;
  }

  public int getIdPelicula() {
    return idPelicula;
  }

  public void setIdPelicula(int idPelicula) {
    this.idPelicula = idPelicula;
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

}

