package dao;

import java.time.Year;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import models.Pelicula;
import models.Usuario;

public class PeliculaDAO {

  public static List<Pelicula> obtenerPeliculasConFiltros(String titulo, String director,
      String actor, Year year, String genero, String ubicacion, boolean visto) {
    Session session = HibernateUtil.getSessionFactory().openSession();

    try {
      Criteria criteria = session.createCriteria(Pelicula.class);
      if (titulo != null && !titulo.isEmpty()) {
        criteria.add(Restrictions.ilike("nombre", "%" + titulo + "%"));
      }
      if (director != null && !director.isEmpty()) {
        criteria.add(Restrictions.ilike("directores", "%" + director + "%"));
      }
      if (actor != null && !actor.isEmpty()) {
        criteria.add(Restrictions.ilike("actores", "%" + actor + "%"));
      }
      if (year != null) {
        criteria.add(Restrictions.eq("year", year));
      }
      if (genero != null && !genero.isEmpty()) {
        criteria.add(Restrictions.ilike("genero", "%" + genero + "%"));
      }
      if (ubicacion != null && !ubicacion.isEmpty()) {
        criteria.add(Restrictions.ilike("ubicacion", "%" + ubicacion + "%"));
      }
      criteria.add(Restrictions.eq("visto", visto));

      return criteria.list();
    } finally {
      session.close();
    }
  }

  public static void nuevaPelicula(Pelicula pelicula) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;

    try {
      transaction = session.beginTransaction();
      Query query = session.createQuery("FROM Pelicula WHERE nombre = :nombre");
      query.setParameter("nombre", pelicula.getNombre());

      List<Pelicula> peliculasExistente = query.list();

      if (peliculasExistente.isEmpty()) {
        session.save(pelicula);
        transaction.commit();
      } else {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert
            .setContentText("Ya existe una película con ese nombre. Por favor, elige otro nombre.");
        alert.showAndWait();
      }
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    } finally {
      session.close();
    }
  }
  
  public static Pelicula peliculaByTitulo(String nombre) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    Pelicula pelicula = null;

    try {
      transaction = session.beginTransaction();
      Query query = session.createQuery("FROM Pelicula WHERE nombre = :nombre");
      query.setParameter("nombre", nombre);
      pelicula = (Pelicula) query.uniqueResult();
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    } finally {
      session.close();
    }
    return pelicula;
  }
}
