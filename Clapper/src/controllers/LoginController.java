package controllers;

import java.sql.Connection;

import conexion.Conexion;
import dao.Dao;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class LoginController {

	@FXML
	private Button btnIniciarSesion;
	
	@FXML
	private Button btnRegistrarse;
	
	@FXML
	private ImageView fondo;
	
	@FXML
	private TextField txtUser;
	
	@FXML
	private TextField txtPassword;
	
	private void handleBtnIniciarSesion() {
		Connection conexion = Conexion.conectar();
		Dao.loginUser(conexion, txtUser.getText(), txtPassword.getText());
	}
	
}
