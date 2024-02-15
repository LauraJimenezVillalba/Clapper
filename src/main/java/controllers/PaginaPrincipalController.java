package controllers;

import java.io.IOException;
import java.text.DecimalFormat;
import org.json.JSONArray;
import org.json.JSONObject;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import application.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class PaginaPrincipalController {

  @FXML
  private ImageView imgViewAnadePelicula;

  @FXML
  private ImageView imgViewBuscaPelicula;

  @FXML
  private ImageView imgViewPerfil;

  @FXML
  private AnchorPane peliculas;

  private int userGenre;
  private String correo;

  public PaginaPrincipalController(int userGenre, String correo) {
    this.userGenre = userGenre;
    this.correo = correo;
  }

  @FXML
  void imgViewAnadePelicula(MouseEvent event) {

  }

  @FXML
  void imgViewBuscaPelicula(MouseEvent event) {

  }

  @FXML
  void imgViewPerfil(MouseEvent event) {

  }

  public void initialize() throws IOException {

    int total = imprimirPeliculas("popular", 0);
    total = imprimirPeliculas("genre", total);
    total = imprimirPeliculas("upcoming", total);
    total = imprimirPeliculas("now_playing", total);

    // NavBar

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

    // Fin de NavBar

  }

  void imprimirLabel(String texto, double abajo) {
    Label label = new Label(texto);
    label.setLayoutX(21.0);
    label.setLayoutY(14.0 + abajo);
    label.setTextFill(Color.web("#fffbfb"));
    Font font = new Font("Arial", 29.0);
    label.setFont(font);
    peliculas.getChildren().add(label);
  }

  int imprimirPeliculas(String type, int totalAnterior) throws IOException {

    double saltoX = 203.0;
    double saltoY = 251.0;
    OkHttpClient client = new OkHttpClient();
    String url;
    if (type.equals("genre")) {
      url =
          "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=es-ES&page=1&sort_by=popularity.desc&with_genres="
              + userGenre;
    } else {
      url = "https://api.themoviedb.org/3/movie/" + type + "?language=es-Es&page=1";
    }
    Request request = new Request.Builder().url(url).get().addHeader("accept", "application/json")
        .addHeader("Authorization",
            "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4ZWI4ZmQ2YjUwOGE5YmE3ZGY2OTdkNmQ5MWFhMGFjZiIsInN1YiI6IjY1NzgwYmY2MzVhNjFlMDEzYWMyMDZmMSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ujcPy-vulTWhsv3dvQlEBboqr5tTmOBDL4zZwliwFlI")
        .build();
    Response response = client.newCall(request).execute();
    JSONObject jsonResponse = new JSONObject(response.body().string());
    JSONArray arrayPeliculas = jsonResponse.getJSONArray("results");
    int imagenesPorFila = 4;
    int total = totalAnterior + arrayPeliculas.length();

    if (type.equals("popular")) {
      imprimirLabel("Destacados", 0);
    } else if (type.equals("now_playing")) {
      imprimirLabel("Ahora en cines",
          (66.0 * 3) + Math.floor(totalAnterior / imagenesPorFila) * saltoY);
    } else if (type.equals("upcoming")) {
      imprimirLabel("Próximos lanzamientos",
          (66.0 * 2) + Math.floor(totalAnterior / imagenesPorFila) * saltoY);
    } else if (type.equals("genre")) {
      imprimirLabel("Basado en tus gustos",
          66.0 + Math.floor(totalAnterior / imagenesPorFila) * saltoY);
    }

    for (int i = 0; i < arrayPeliculas.length(); i++) {

      double filaActual = Math.floor((totalAnterior + i) / imagenesPorFila);
      JSONObject movieObject = arrayPeliculas.getJSONObject(i);

      ImageView imageView = new ImageView();
      imageView.setFitHeight(248.0);
      imageView.setFitWidth(165.0);
      imageView.setPreserveRatio(false);
      imageView.setPickOnBounds(true);
      String imageUrl = "https://image.tmdb.org/t/p/w500/" + movieObject.getString("poster_path");
      Image image = new Image(imageUrl);
      imageView.setImage(image);

      String etiquetaTitulo;
      String releaseDate = movieObject.optString("release_date", "");
      if (releaseDate.length() >= 4) {
        etiquetaTitulo = movieObject.getString("title") + " (" + releaseDate.substring(0, 4) + ")";
      } else {
        etiquetaTitulo = movieObject.getString("title");
      }
      Label labelPrincipal = new Label(etiquetaTitulo);
      Font font = Font.font("Arial", FontWeight.BOLD, 20.0);
      labelPrincipal.setFont(font);
      labelPrincipal.setTextFill(Color.web("#16242B"));
      labelPrincipal.setMaxWidth(165.0);
      labelPrincipal.setWrapText(true);
      labelPrincipal.setVisible(false);
      labelPrincipal.setAlignment(Pos.CENTER);
      labelPrincipal.setTextAlignment(TextAlignment.CENTER);

      JSONArray genresIDArray = movieObject.getJSONArray("genre_ids");
      String etiquetaGenero = "";
      for (int j = 0; j < genresIDArray.length(); j++) {
        int genreID = genresIDArray.getInt(j);
        String genre = Utils.getGenreByID(genreID);
        etiquetaGenero += genre + ", ";
      }
      if (etiquetaGenero.length() > 0) {
        etiquetaGenero = etiquetaGenero.substring(0, etiquetaGenero.length() - 2);
      }
      String etiquetaGeneroEnviar = etiquetaGenero;
      etiquetaGenero += "\n" + Utils.minutosHoras(movieObject.getInt("id"));

      Label labelGenero = new Label(etiquetaGenero);
      Font fontSmall = Font.font("Arial", FontWeight.BOLD, 16.0);
      labelGenero.setFont(fontSmall);
      labelGenero.setTextFill(Color.web("#16242B"));
      labelGenero.setMaxWidth(165.0);
      labelGenero.setWrapText(true);
      labelGenero.setVisible(false);
      labelGenero.setAlignment(Pos.CENTER);
      labelGenero.setTextAlignment(TextAlignment.CENTER);

      DecimalFormat formato = new DecimalFormat("#.#");
      String etiquetaEstrellas = formato.format(movieObject.getBigDecimal("vote_average")) + "★";
      Font fontBig = Font.font("Arial", FontWeight.BOLD, 26.0);
      Label labelEstrellas = new Label(etiquetaEstrellas);
      labelEstrellas.setFont(fontBig);
      labelEstrellas.setTextFill(Color.web("#16242B"));
      labelEstrellas.setMaxWidth(165.0);
      labelEstrellas.setWrapText(true);
      labelEstrellas.setVisible(false);
      labelEstrellas.setAlignment(Pos.CENTER);
      labelEstrellas.setTextAlignment(TextAlignment.CENTER);

      StackPane stackPane = new StackPane(imageView, labelPrincipal, labelGenero, labelEstrellas);
      StackPane.setAlignment(labelGenero, Pos.BOTTOM_CENTER);
      StackPane.setAlignment(labelPrincipal, Pos.TOP_CENTER);
      StackPane.setMargin(labelGenero, new Insets(0, 0, 20, 0));
      StackPane.setMargin(labelPrincipal, new Insets(10, 0, 0, 0));
      stackPane.setLayoutX(21.0 + (i % imagenesPorFila) * saltoX);
      if (type.equals("now_playing")) {
        stackPane.setLayoutY((66.0 * 4) + filaActual * saltoY);
      } else if (type.equals("upcoming")) {
        stackPane.setLayoutY((66.0 * 3) + filaActual * saltoY);
      } else if (type.equals("genre")) {
        stackPane.setLayoutY((66.0 * 2) + filaActual * saltoY);
      } else {
        stackPane.setLayoutY(66.0 + filaActual * saltoY);
      }

      stackPane.setOnMouseClicked(event -> {
        FXMLLoader loader =
            new FXMLLoader(getClass().getResource("/views/PaginaInfoPelicula.fxml"));
        PaginaInfoPeliculaController controller = new PaginaInfoPeliculaController(false,
            movieObject.getInt("id"), etiquetaGeneroEnviar, etiquetaTitulo, imageUrl,
            labelEstrellas.getText(), movieObject.getString("overview"), userGenre, correo);
        loader.setController(controller);
        try {
          Parent root = loader.load();
          Scene scene = new Scene(root);
          scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
          Stage stage = (Stage) stackPane.getScene().getWindow();
          stage.setScene(scene);
        } catch (IOException e) {
          e.printStackTrace();
        }
      });

      stackPane.setOnMouseEntered(event -> {
        Color colorToOverlay = Color.web("#BCBCBC");
        ColorInput colorInput = new ColorInput(0, 0, imageView.getBoundsInLocal().getWidth(),
            imageView.getBoundsInLocal().getHeight(), colorToOverlay);
        Blend blend = new Blend(BlendMode.SRC_OVER);
        blend.setTopInput(colorInput);
        imageView.setEffect(blend);
        labelPrincipal.setVisible(true);
        labelGenero.setVisible(true);
        labelEstrellas.setVisible(true);
      });

      stackPane.setOnMouseExited(event -> {
        imageView.setEffect(null);
        labelPrincipal.setVisible(false);
        labelGenero.setVisible(false);
        labelEstrellas.setVisible(false);
      });

      peliculas.getChildren().add(stackPane);
    }

    return total;

  }

}
