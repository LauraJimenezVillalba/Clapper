package dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import models.Usuario;

public class UsuarioDAO {

  public static void newUser(String usuario, String correo, String password) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;

    try {
      transaction = session.beginTransaction();
      Usuario newUser = new Usuario();
      newUser.setNombre(usuario);
      newUser.setContraseña(password);
      newUser.setEmail(correo);
      session.save(newUser);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    } finally {
      session.close();
    }
  }

  public static boolean loginUser(String usuario, String password) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    boolean loggedIn = false;

    try {
      transaction = session.beginTransaction();
      Query query =
          session.createQuery("FROM Usuario WHERE nombre = :usuario AND contraseña = :password");
      query.setParameter("usuario", usuario);
      query.setParameter("password", password);
      Usuario user = (Usuario) query.uniqueResult();
      loggedIn = (user != null);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    } finally {
      session.close();
    }

    return loggedIn;
  }
}

