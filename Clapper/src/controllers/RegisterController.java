package controllers;

import java.io.IOException;
import java.sql.Connection;
import conexion.Conexion;
import dao.Dao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class RegisterController {

    @FXML
    private Button btnRegistrarse;

    @FXML
    private Button btnVolver;

    @FXML
    private ImageView fondo;

    @FXML
    private TextField txtCorreo;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtPasswordRepeat;

    @FXML
    private TextField txtUsuario;
    
    @FXML
    private void handleBtnRegistrarse() {
      if (txtPassword.getText().equals(txtPasswordRepeat.getText())) {
        Connection conexion = Conexion.conectar();
        Dao.newUser(conexion, txtUsuario.getText(), txtCorreo.getText(), txtPassword.getText());
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Usuario registrado");
        alert.setHeaderText(null);
        alert.setContentText("Tu nuevo usuario ha sido registrado correctamente.");
        alert.showAndWait();
      } else {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Las contraseñas no coinciden.");
        alert.showAndWait();
      }
    }
    
    @FXML
    private void handleBtnVolver() throws IOException {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
      try {
          Parent root = loader.load();
          Scene scene = new Scene(root);
          Stage stage = (Stage) btnVolver.getScene().getWindow();
          stage.setScene(scene);
      } catch (IOException e) {
          e.printStackTrace();
      }
    }

}
