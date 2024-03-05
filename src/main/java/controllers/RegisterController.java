package controllers;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import application.Utils;
import dao.UbicacionDAO;
import dao.UsuarioDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
  private ComboBox<String> cbGenero;



  public void initialize() throws IOException {

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
      items.add(valor);
    }
    cbGenero.setItems(items);
    cbGenero.setValue("Seleccione una opción");
  }

  @FXML
  private void handleBtnRegistrarse() throws IOException {
    if (txtPassword.getText().isEmpty() || txtCorreo.getText().isEmpty()
        || txtUsuario.getText().isEmpty()) {
      Alert alert = new Alert(AlertType.WARNING);
      alert.setTitle("Error");
      alert.setHeaderText(null);
      alert.setContentText("Hay campos vacíos.");
      alert.showAndWait();
    } else if (cbGenero.getSelectionModel().getSelectedItem() == null
        || cbGenero.getSelectionModel().getSelectedItem().equals("Seleccione una opción")) {
      Alert alert = new Alert(AlertType.WARNING);
      alert.setTitle("Error");
      alert.setHeaderText(null);
      alert.setContentText("Elige un género válido.");
      alert.showAndWait();
    } else {
      if (txtPassword.getText().equals(txtPasswordRepeat.getText())) {
        ;
        UsuarioDAO.newUser(txtUsuario.getText(), txtCorreo.getText(), txtPassword.getText(),
            Utils.getIDByGenre(cbGenero.getSelectionModel().getSelectedItem()));
        UbicacionDAO.newUbi("Ninguna", txtCorreo.getText());
        try {
          handleBtnVolver();
        } catch (IOException e) {
          e.printStackTrace();
        }
        // hacer que no se registre si ya está ese usuario/correo
      } else {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Las contraseñas no coinciden.");
        alert.showAndWait();
      }
    }
  }

  @FXML
  private void handleBtnVolver() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
    try {
      Parent root = loader.load();
      Scene scene = new Scene(root);
      scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
      Stage stage = (Stage) btnVolver.getScene().getWindow();
      stage.setScene(scene);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  void cbGenero(ActionEvent event) {

  }

}
