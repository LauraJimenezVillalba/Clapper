package controllers;

import java.io.IOException;
import java.sql.Connection;

import conexion.Conexion;
import dao.Dao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

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
	
	@FXML
	private void handleBtnIniciarSesion() {
		Connection conexion = Conexion.conectar();
		Dao.loginUser(conexion, txtUser.getText(), txtPassword.getText());
	}
	
	@FXML
	private void handleBtnRegistrarse() throws IOException {
	  FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Register.fxml"));
      try {
          Parent root = loader.load();
          Scene scene = new Scene(root);
          Stage stage = (Stage) btnRegistrarse.getScene().getWindow();
          stage.setScene(scene);
      } catch (IOException e) {
          e.printStackTrace();
      }
    }
	
}
