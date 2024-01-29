package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

	public static boolean loginUser(Connection conexion, String usuario, String password) {
		PreparedStatement sentencia;
		ResultSet resultSet = null;
		boolean loggedIn = false;
		try {
			sentencia = conexion.prepareStatement("SELECT * FROM usuario WHERE nombre = ? AND contraseña = ?");
			sentencia.setString(1, usuario);
			sentencia.setString(2, password);
			resultSet = sentencia.executeQuery();
			if (resultSet.next()) {
				loggedIn = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return loggedIn;
	}

}
