package controllers;

import java.io.IOException;

import dao.UsuarioDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PaginaPerfilController {

	int userGenre;
	String correo;
	
    @FXML
    private Button btnCerrarSesion;

    @FXML
    private Button btnModificar;

    @FXML
    private ComboBox<String> cbGenero;

    @FXML
    private ImageView imgViewAnadePelicula;

    @FXML
    private ImageView imgViewBuscaPelicula;

    @FXML
    private ImageView imgViewPaginaPrincipal;

    @FXML
    private Label lbCorreo;

    @FXML
    private PasswordField pswfConfirmaContrasena;

    @FXML
    private PasswordField pswfContrasena;

    @FXML
    private TextField txtfUsuario;
    
    public PaginaPerfilController(int userGenre, String correo) {
		this.userGenre = userGenre;
		this.correo = correo;
	}

    public void initialize() {
    	
    	lbCorreo.setText(correo);
    	txtfUsuario.setText(UsuarioDAO.correoToUser(correo));
    	//NavBar
    	imgViewPaginaPrincipal.setOnMouseClicked(event -> {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/PaginaPrincipal.fxml"));
			PaginaPrincipalController controller = new PaginaPrincipalController(userGenre, correo);
			loader.setController(controller);
			try {
				Parent root = loader.load();
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
				Stage stage = (Stage) imgViewPaginaPrincipal.getScene().getWindow();
				stage.setScene(scene);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		imgViewBuscaPelicula.setOnMouseClicked(event -> {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/PaginaDescubrir.fxml"));
			PaginaDescubrirController controller = new PaginaDescubrirController(userGenre, correo);
			loader.setController(controller);
			try {
				Parent root = loader.load();
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
				Stage stage = (Stage) imgViewBuscaPelicula.getScene().getWindow();
				stage.setScene(scene);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
    }
    
    @FXML
    void btnCerrarSesion(ActionEvent event) {
    	Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmar cierre de sesión");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("¿Estás seguro de que deseas cerrar la sesión?");
        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);
        if (result == ButtonType.OK) {
        	Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("Se ha cerrado sesión");
    		alert.setHeaderText(null);
    		alert.setContentText("Vas a regresar a la pantalla inicial inicial.");
    		alert.showAndWait();
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
    		try {
    			Parent root = loader.load();
    			Scene scene = new Scene(root);
    			scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
    			Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();
    			stage.setScene(scene);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        }
    	
    }

    @FXML
    void btnModificar(ActionEvent event) {
    	if (btnModificar.getText().equals("Modificar")) {
    		cbGenero.setDisable(false);
        	txtfUsuario.setDisable(false);
        	pswfContrasena.setDisable(false);
        	pswfConfirmaContrasena.setDisable(false);
        	btnModificar.setText("Guardar");
    	} else {
    		cbGenero.setDisable(true);
        	txtfUsuario.setDisable(true);
        	pswfContrasena.setDisable(true);
        	pswfConfirmaContrasena.setDisable(true);
    		btnModificar.setText("Modificar");
    	}
    }

    @FXML
    void cbGenero(ActionEvent event) {

    }

    @FXML
    void imgViewAnadePelicula(MouseEvent event) {

    }

    @FXML
    void imgViewBuscaPelicula(MouseEvent event) {

    }

    @FXML
    void imgViewPaginaPrincipal(MouseEvent event) {

    }

    @FXML
    void pswfConfirmaContrasena(ActionEvent event) {

    }

    @FXML
    void pswfContrasena(ActionEvent event) {

    }

    @FXML
    void txtfUsuario(ActionEvent event) {

    }

}

