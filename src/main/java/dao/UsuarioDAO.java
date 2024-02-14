package dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import models.Usuario;

public class UsuarioDAO {

  public static void newUser(String usuario, String correo, String password, int genero) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;

    try {
        transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Usuario WHERE nombre = :usuario OR email = :correo");
        query.setParameter("usuario", usuario);
        query.setParameter("correo", correo);

        List<Usuario> usuariosExistente = query.list();

        if (usuariosExistente.isEmpty()) {
            Usuario newUser = new Usuario();
            newUser.setNombre(usuario);
            newUser.setContraseña(password);
            newUser.setEmail(correo);
            newUser.setGenero(genero);
            session.save(newUser);
            transaction.commit();
        } else {
          Alert alert = new Alert(AlertType.WARNING);
          alert.setTitle("Error");
          alert.setHeaderText(null);
          alert.setContentText("El correo o el usuario ya está ocupado. Escoge otro diferente.");
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



  public static void updateUser(String correo, String nuevoNombre, String nuevaContraseña,
      int nuevoGenero) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;

    try {
      transaction = session.beginTransaction();
      Usuario existingUser = (Usuario) session.createQuery("FROM Usuario WHERE email = :correo")
          .setParameter("correo", correo).uniqueResult();

      if (existingUser != null) {
        existingUser.setNombre(nuevoNombre);
        existingUser.setContraseña(nuevaContraseña);
        existingUser.setGenero(nuevoGenero);
        session.update(existingUser);
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

  public static int userGenre(String usuario) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    int id = 0;

    try {
      transaction = session.beginTransaction();
      Query query = session.createQuery("FROM Usuario WHERE nombre = :usuario");
      query.setParameter("usuario", usuario);
      Usuario user = (Usuario) query.uniqueResult();
      id = user.getGenero();
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    } finally {
      session.close();
    }
    return id;
  }

  public static String userCorreo(String usuario) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    String correo = "";

    try {
      transaction = session.beginTransaction();
      Query query = session.createQuery("FROM Usuario WHERE nombre = :usuario");
      query.setParameter("usuario", usuario);
      Usuario user = (Usuario) query.uniqueResult();
      correo = user.getEmail();
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    } finally {
      session.close();
    }
    return correo;
  }

  public static String correoToUser(String email) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    String username = "";

    try {
      transaction = session.beginTransaction();
      Query query = session.createQuery("FROM Usuario WHERE email = :email");
      query.setParameter("email", email);
      Usuario user = (Usuario) query.uniqueResult();
      username = user.getNombre();
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    } finally {
      session.close();
    }
    return username;
  }

}
