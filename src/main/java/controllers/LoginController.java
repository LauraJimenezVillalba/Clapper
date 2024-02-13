package controllers;

import java.io.IOException;

import dao.UsuarioDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
		boolean success = UsuarioDAO.loginUser(txtUser.getText(), txtPassword.getText());
		if (success) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/PaginaPrincipal.fxml"));
			PaginaPrincipalController controller = new PaginaPrincipalController(UsuarioDAO.userGenre(txtUser.getText()), UsuarioDAO.userCorreo(txtUser.getText()));
			loader.setController(controller);
			try {
				Parent root = loader.load();
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
				Stage stage = (Stage) btnIniciarSesion.getScene().getWindow();
				stage.setScene(scene);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Datos incorrectos.");
			alert.showAndWait();
		}
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
