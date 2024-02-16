package controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Year;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import dao.PeliculaDAO;
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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import models.Pelicula;
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
  private ComboBox<Integer> cbMin;

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
  private TextArea sinopsis;

  @FXML
  private Button btnGuardar;
  
  @FXML
  private Pane subirImagen;
  
  @FXML
  private ImageView poster;
  
  String posterURL = "/img/blanco.jpg";

  public PaginaAnadirController(int userGenre, String correo) {
    this.userGenre = userGenre;
    this.correo = correo;
  }
  
  @FXML
  void subirImagen(MouseEvent event) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Selecciona una imagen");
    ExtensionFilter imageFilter = new ExtensionFilter("Archivos de imagen", "*.png", "*.jpg", "*.jpeg", "*.gif");
    fileChooser.getExtensionFilters().add(imageFilter);
    File file = fileChooser.showOpenDialog(null);

    if (file != null) {
      try {
          URL fileUrl = file.toURI().toURL();
          Image image = new Image(fileUrl.toString());
          poster.setImage(image);
          posterURL = fileUrl.toString();
      } catch (MalformedURLException e) {
          e.printStackTrace();
      }
  }


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
    cbAno.setValue(Integer.toString(currentYear));
    
    ObservableList<Integer> minutos = FXCollections.observableArrayList();
    for (int i = 1; i <= 999; i++) {
      minutos.add(i);
    }
    cbMin.setItems(minutos);
    cbMin.setValue(1);

  }

  @FXML
  void clickGuardar(ActionEvent event) {
    UbicacionDAO.newUbi(cbUbicacion.getSelectionModel().getSelectedItem(), correo);
    PeliculaDAO.nuevaPelicula(
        new Pelicula(txtfTitulo.getText(), Year.parse(cbAno.getSelectionModel().getSelectedItem()),
            cbMin.getValue(), sldValoracion.getValue(), sinopsis.getText(), posterURL,
            chkbVisto.isSelected(), cbGenero.getSelectionModel().getSelectedItem(),
            cbUbicacion.getSelectionModel().getSelectedItem(), txtfActor.getText(),
            txtfDirector.getText()));
    cbUbicacion.getItems().clear();
    List<Ubicacion> ubicaciones = UbicacionDAO.getUbicacionesCorreo(correo);
    for (Ubicacion ubicacion : ubicaciones) {
      cbUbicacion.getItems().add(ubicacion.getNombre());
    }
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Pelicula guardada");
    alert.setHeaderText(null);
    alert.setContentText("Tu nueva pelicula ha sido registrada correctamente.");
    alert.showAndWait();
    txtfTitulo.clear();
    txtfDirector.clear();
    txtfActor.clear();
    sinopsis.clear();
    chkbVisto.setSelected(false);
    cbAno.setValue("2024");
    cbUbicacion.setValue("Ninguna");
    cbGenero.setValue("Acción");
    sldValoracion.setValue(0);
    sinopsis.clear();
    Image image = new Image("/img/blanco.jpg");
    poster.setImage(image);
    posterURL = "/img/blanco.jpg";
  }

  @FXML
  void cbAno(ActionEvent event) {

  }

  @FXML
  void cbGenero(ActionEvent event) {

  }
  
  @FXML
  void cbMin(ActionEvent event) {

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

