package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Dao {

  public static void newUser(Connection conexion, String usuario, String correo, String password) {
    PreparedStatement sentencia;
    try {
      sentencia = conexion.prepareStatement("INSERT INTO usuario (nombre, contraseña, email) VALUES (?, ?, ?)");
      sentencia.setString(1, usuario);
      sentencia.setString(2, password);
      sentencia.setString(3, correo);
      sentencia.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
}
