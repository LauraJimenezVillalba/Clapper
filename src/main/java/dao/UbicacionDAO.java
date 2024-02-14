package dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import models.Ubicacion;

public class UbicacionDAO {

  public static void newUbi(String nombre, String correo) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;

    try {
        transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Ubicacion WHERE nombre = :nombre AND correo = :correo");
        query.setParameter("nombre", nombre);
        query.setParameter("correo", correo);
        List<Ubicacion> ubicacionesExistente = query.list();
        if (ubicacionesExistente.isEmpty()) {
            Ubicacion newUbi = new Ubicacion();
            newUbi.setNombre(nombre);
            newUbi.setCorreo(correo);
            session.save(newUbi);
            transaction.commit();
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
  
  public static List<Ubicacion> getUbicacionesCorreo(String correo) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    List<Ubicacion> ubicaciones = null;

    try {
      Query query = session.createQuery("FROM Ubicacion WHERE correo = :correo");
      query.setParameter("correo", correo);
      ubicaciones = query.list();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      session.close();
    }

    return ubicaciones;
  }

}
