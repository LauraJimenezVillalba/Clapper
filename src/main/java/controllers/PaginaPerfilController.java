package controllers;

import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import application.Utils;
import dao.UsuarioDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

  public void initialize() throws IOException {

    lbCorreo.setText(correo);
    txtfUsuario.setText(UsuarioDAO.correoToUser(correo));
    // NavBar
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

    imgViewAnadePelicula.setOnMouseClicked(event -> {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/PaginaAnadir.fxml"));
      PaginaAnadirController controller = new PaginaAnadirController(userGenre, correo);
      loader.setController(controller);
      try {
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
        Stage stage = (Stage) imgViewAnadePelicula.getScene().getWindow();
        stage.setScene(scene);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    String idGenero = "";

    OkHttpClient client = new OkHttpClient();
    Request requestGenre = new Request.Builder()
        .url("https://api.themoviedb.org/3/genre/movie/list?language=es").get()
        .addHeader("accept", "application/json")
        .addHeader("Authorization",
            "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4ZWI4ZmQ2YjUwOGE5YmE3ZGY2OTdkNmQ5MWFhMGFjZiIsInN1YiI6IjY1NzgwYmY2MzVhNjFlMDEzYWMyMDZmMSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ujcPy-vulTWhsv3dvQlEBboqr5tTmOBDL4zZwliwFlI")
        .build();
    Response responseGenre = client.newCall(requestGenre).execute();
    JSONObject jsonResponse = new JSONObject(responseGenre.body().string());
    JSONArray arrayGenres = jsonResponse.getJSONArray("genres");
    ObservableList<String> items = FXCollections.observableArrayList();
    for (int i = 0; i < arrayGenres.length(); i++) {
      JSONObject jsonObject = arrayGenres.getJSONObject(i);
      String valor = jsonObject.getString("name");
      if (jsonObject.getInt("id") == userGenre) {
        idGenero = valor;
      }
      items.add(valor);
    }
    cbGenero.setItems(items);
    cbGenero.setValue(idGenero);
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
  void btnModificar(ActionEvent event) throws IOException {
    if (btnModificar.getText().equals("Modificar")) {
      cbGenero.setDisable(false);
      txtfUsuario.setDisable(false);
      pswfContrasena.setDisable(false);
      pswfConfirmaContrasena.setDisable(false);
      btnModificar.setText("Guardar");
    } else {
      if (pswfContrasena.getText().isEmpty() || txtfUsuario.getText().isEmpty()) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Hay campos vacíos.");
        alert.showAndWait();
      } else {
        if (pswfContrasena.getText().equals(pswfConfirmaContrasena.getText())) {
          ;
          userGenre = Utils.getIDByGenre(cbGenero.getSelectionModel().getSelectedItem());
          UsuarioDAO.updateUser(correo, txtfUsuario.getText(), pswfContrasena.getText(), userGenre);
          Alert alert = new Alert(AlertType.INFORMATION);
          alert.setTitle("Usuario actualizado");
          alert.setHeaderText(null);
          alert.setContentText("Tus datos han sido actualizados.");
          alert.showAndWait();
        } else {
          Alert alert = new Alert(AlertType.WARNING);
          alert.setTitle("Error");
          alert.setHeaderText(null);
          alert.setContentText("Las contraseñas no coinciden.");
          alert.showAndWait();
        }
      }
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

