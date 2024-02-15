package controllers;

import java.io.IOException;
import java.io.InputStream;
import org.json.JSONArray;
import org.json.JSONObject;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import dao.PeliculaDAO;
import dao.UsuarioDAO;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Pelicula;

public class PaginaInfoPeliculaController {

  int id;
  String generos;
  String tituloCompuesto;
  String imageUrl;
  String estrellas;
  String sinopsis;
  int userGenre;
  String correo;
  boolean local;

  public PaginaInfoPeliculaController(boolean local, int id, String generos, String tituloCompuesto,
      String imageUrl, String estrellas, String sinopsis, int userGenre, String correo) {
    this.id = id;
    this.generos = generos;
    this.tituloCompuesto = tituloCompuesto;
    this.imageUrl = imageUrl;
    this.estrellas = estrellas;
    this.sinopsis = sinopsis;
    this.userGenre = userGenre;
    this.correo = correo;
    this.local = local;
  }

  @FXML
  private ImageView imgViewAnadePelicula;

  @FXML
  private ImageView imgViewAnadir;

  @FXML
  private ImageView imgViewBuscaPelicula;

  @FXML
  private ImageView imgViewCartelPelicula;

  @FXML
  private ImageView imgViewPaginaPrincipal;

  @FXML
  private ImageView imgViewPerfil;

  @FXML
  private TextArea textoAEscribir;

  @FXML
  private Label labelTitulo;

  @FXML
  private Label labelEstrellas;

  public void initialize() throws IOException {

    String textoPrincipal = "Reparto principal: ";
    labelEstrellas.setText(estrellas);

    if (!local) {
      Image image = new Image(imageUrl);
      imgViewCartelPelicula.setImage(image);
      OkHttpClient client = new OkHttpClient();
      Request request = new Request.Builder()
          .url("https://api.themoviedb.org/3/movie/" + id + "/credits?language=es-ES").get()
          .addHeader("accept", "application/json")
          .addHeader("Authorization",
              "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4ZWI4ZmQ2YjUwOGE5YmE3ZGY2OTdkNmQ5MWFhMGFjZiIsInN1YiI6IjY1NzgwYmY2MzVhNjFlMDEzYWMyMDZmMSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ujcPy-vulTWhsv3dvQlEBboqr5tTmOBDL4zZwliwFlI")
          .build();
      Response response = client.newCall(request).execute();
      JSONObject jsonResponse = new JSONObject(response.body().string());
      JSONArray arrayActores = jsonResponse.getJSONArray("cast");
      StringBuilder actoresString = new StringBuilder("");
      int numActores = Math.min(arrayActores.length(), 7);
      for (int i = 0; i < numActores; i++) {
        JSONObject actor = arrayActores.getJSONObject(i);
        String nombreActor = actor.getString("name");
        actoresString.append(nombreActor).append(", ");
      }
      if (actoresString.length() >= 2) {
        textoPrincipal += actoresString.substring(0, actoresString.length() - 2);
      } else {
        textoPrincipal += actoresString;
      }

      JSONArray arrayDirectores = jsonResponse.getJSONArray("crew");
      StringBuilder directoresString = new StringBuilder("");
      for (int i = 0; i < arrayDirectores.length(); i++) {
        JSONObject director = arrayDirectores.getJSONObject(i);
        if (director.getString("job").equals("Director")) {
          String nombreDirector = director.getString("name");
          directoresString.append(nombreDirector).append(", ");
        }
      }
      if (directoresString.length() >= 2) {
        textoPrincipal +=
            "\nDirector(es): " + directoresString.substring(0, directoresString.length() - 2);
      } else {
        textoPrincipal += "\nDirector(es): " + directoresString;
      }

      textoPrincipal += "\nGénero: " + generos;
      String regex = "\\s*(.*?)\\s*\\((\\d{4})\\)\\s*";
      java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
      java.util.regex.Matcher matcher = pattern.matcher(tituloCompuesto);
      if (matcher.matches()) {
        labelTitulo.setText(matcher.group(1).trim());
        textoPrincipal += "\nAño: " + matcher.group(2);
      }

      textoPrincipal += "\n" + sinopsis;
      PseudoClass centered = PseudoClass.getPseudoClass("centered");
      textoAEscribir.pseudoClassStateChanged(centered, true);

      textoAEscribir.setText(textoPrincipal);
      textoAEscribir.setBackground(null);
      
    } else {
      
      Pelicula pelicula = PeliculaDAO.peliculaByTitulo(tituloCompuesto);
      
      InputStream imageUrl = getClass().getResourceAsStream(pelicula.getPoster());
      Image image = new Image(imageUrl);
      imgViewCartelPelicula.setImage(image);
      
      textoPrincipal += pelicula.getActores();
      textoPrincipal += "\nDirector(es): " + pelicula.getDirectores();
      textoPrincipal += "\nGénero: " + pelicula.getGenero();
      String regex = "\\s*(.*?)\\s*\\((\\d{4})\\)\\s*";
      java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
      java.util.regex.Matcher matcher = pattern.matcher(tituloCompuesto);
      if (matcher.matches()) {
        labelTitulo.setText(matcher.group(1).trim());
        textoPrincipal += "\nAño: " + matcher.group(2);
      }

      textoPrincipal += "\n" + pelicula.getSinopsis();
      PseudoClass centered = PseudoClass.getPseudoClass("centered");
      textoAEscribir.pseudoClassStateChanged(centered, true);

      textoAEscribir.setText(textoPrincipal);
      textoAEscribir.setBackground(null);
    }
    

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
    // Fin de NavBar

  }

  @FXML
  void imgViewAnadePelicula(MouseEvent event) {

  }

  @FXML
  void imgViewAnadir(MouseEvent event) {

  }

  @FXML
  void imgViewBuscaPelicula(MouseEvent event) {

  }

  @FXML
  void imgViewCartelPelicula(MouseEvent event) {

  }

  @FXML
  void imgViewPaginaPrincipal(MouseEvent event) {

  }

  @FXML
  void imgViewPerfil(MouseEvent event) {

  }

  @FXML
  void txtfAno(ActionEvent event) {

  }

  @FXML
  void txtfDirector(ActionEvent event) {

  }

  @FXML
  void txtfGenero(ActionEvent event) {

  }

  @FXML
  void txtfPuntuacion(ActionEvent event) {

  }

  @FXML
  void txtfReparto(ActionEvent event) {

  }

  @FXML
  void txtfTitulo(ActionEvent event) {

  }

}
