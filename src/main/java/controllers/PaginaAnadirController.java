package controllers;

import java.io.IOException;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import dao.UbicacionDAO;
import dao.UsuarioDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Ubicacion;

public class PaginaAnadirController {

  int userGenre;
  String correo;
  
    @FXML
    private ComboBox<String> cbAno;

    @FXML
    private ComboBox<String> cbGenero;

    @FXML
    private ComboBox<String> cbUbicacion;

    @FXML
    private CheckBox chkbVisto;

    @FXML
    private ImageView imgViewAgregaImagen;

    @FXML
    private ImageView imgViewBuscaPelicula;

    @FXML
    private ImageView imgViewPaginaPrincipal;

    @FXML
    private ImageView imgViewPerfil;

    @FXML
    private Slider sldValoracion;

    @FXML
    private TextField txtfActor;

    @FXML
    private TextField txtfDirector;

    @FXML
    private TextField txtfTitulo;
    
    @FXML
    private Button btnGuardar;
    
    public PaginaAnadirController(int userGenre, String correo) {
      this.userGenre = userGenre;
      this.correo = correo;
    }

    public void initialize() throws IOException {
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
      
      imgViewPerfil.setOnMouseClicked(event -> {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/PaginaPerfil.fxml"));
        PaginaPerfilController controller = new PaginaPerfilController(userGenre, correo);
        loader.setController(controller);
        try {
          Parent root = loader.load();
          Scene scene = new Scene(root);
          scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
          Stage stage = (Stage) imgViewPerfil.getScene().getWindow();
          stage.setScene(scene);
        } catch (IOException e) {
          e.printStackTrace();
        }
      });
      
      List<Ubicacion> ubicaciones = UbicacionDAO.getUbicacionesCorreo(correo);
      for (Ubicacion ubicacion : ubicaciones) {
        cbUbicacion.getItems().add(ubicacion.getNombre());
      }
      cbUbicacion.setValue("Ninguna");
      
      
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
      cbGenero.setValue("Acción");

      ObservableList<String> years = FXCollections.observableArrayList();
      int currentYear = java.time.LocalDate.now().getYear();
      for (int year = currentYear; year >= 1900; year--) {
        years.add(String.valueOf(year));
      }
      cbAno.setItems(years);
      cbAno.setValue( Integer.toString(currentYear));
      
    }
    
    @FXML
    void clickGuardar(ActionEvent event) {
      UbicacionDAO.newUbi(cbUbicacion.getSelectionModel().getSelectedItem(), correo);
      cbUbicacion.getItems().clear();
      List<Ubicacion> ubicaciones = UbicacionDAO.getUbicacionesCorreo(correo);
      for (Ubicacion ubicacion : ubicaciones) {
        cbUbicacion.getItems().add(ubicacion.getNombre());
      }
      cbUbicacion.setValue("Ninguna");
    }
    
    @FXML
    void cbAno(ActionEvent event) {

    }

    @FXML
    void cbGenero(ActionEvent event) {

    }

    @FXML
    void cbUbicacion(ActionEvent event) {

    }

    @FXML
    void chkbVisto(ActionEvent event) {

    }

    @FXML
    void imgViewAgregaImagen(MouseEvent event) {

    }

    @FXML
    void imgViewBuscaPelicula(MouseEvent event) {

    }

    @FXML
    void imgViewPaginaPrincipal(MouseEvent event) {

    }

    @FXML
    void imgViewPerfil(MouseEvent event) {

    }

    @FXML
    void txtfActor(ActionEvent event) {

    }

    @FXML
    void txtfDirector(ActionEvent event) {

    }

    @FXML
    void txtfTitulo(ActionEvent event) {

    }

}

