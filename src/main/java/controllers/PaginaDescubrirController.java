package controllers;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.time.Year;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import application.Utils;
import dao.PeliculaDAO;
import dao.UbicacionDAO;
import dao.UsuarioDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
import models.Ubicacion;
import models.Pelicula;

public class PaginaDescubrirController {

  int userGenre;
  String correo;

  @FXML
  private ImageView buscar;

  @FXML
  private ComboBox<String> cbEstado;

  @FXML
  private ComboBox<String> cbGeneros;

  @FXML
  private ComboBox<String> cbUbicacion;

  @FXML
  private ComboBox<String> cbYear;

  @FXML
  private ImageView imgViewAnadePelicula;

  @FXML
  private ImageView imgViewPaginaPrincipal;

  @FXML
  private ImageView imgViewPerfil;

  @FXML
  private AnchorPane peliculas;

  @FXML
  private TextField txtfBuscarActor;

  @FXML
  private TextField txtfBuscarTitulo;

  public PaginaDescubrirController(int userGenre, String correo) {
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

    // rellenar comboboxes

    cbEstado.getItems().addAll(new String("Visto"), new String("No Visto"));
    cbEstado.setValue("No Visto");

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
    cbGeneros.setItems(items);

    ObservableList<String> years = FXCollections.observableArrayList();
    int currentYear = java.time.LocalDate.now().getYear();
    for (int year = currentYear; year >= 1900; year--) {
      years.add(String.valueOf(year));
    }
    cbYear.setItems(years);

    List<Ubicacion> ubicaciones = UbicacionDAO.getUbicacionesCorreo(correo);
    for (Ubicacion ubicacion : ubicaciones) {
      cbUbicacion.getItems().add(ubicacion.getNombre());
    }
    cbUbicacion.setValue("Ninguna");

    // Fin de NavBar

    buscar.setOnMouseClicked(event -> {

      String url = "https://api.themoviedb.org/3/search/movie?query=" + txtfBuscarTitulo.getText()
          + "&include_adult=false&language=es-ES&page=1";
      if (txtfBuscarTitulo.getText().isEmpty()) {
        url =
            "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=es-ES&page=1&sort_by=popularity.desc";
      }
      String selectedGenre = cbGeneros.getSelectionModel().getSelectedItem();
      if (selectedGenre != null && !selectedGenre.isEmpty()) {
        try {
          url += "&with_genres=" + Utils.getIDByGenre(selectedGenre);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      String selectedYear = cbYear.getSelectionModel().getSelectedItem();
      if (selectedYear != null && !selectedYear.isEmpty()) {
        url += "&primary_release_year=" + Integer.parseInt(selectedYear);
      }

      if (!txtfBuscarActor.getText().isEmpty()) {
        Request requestPeople = new Request.Builder()
            .url("https://api.themoviedb.org/3/search/person?query=" + txtfBuscarActor.getText()
                + "&include_adult=false&language=es-ES&page=1")
            .get().addHeader("accept", "application/json")
            .addHeader("Authorization",
                "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4ZWI4ZmQ2YjUwOGE5YmE3ZGY2OTdkNmQ5MWFhMGFjZiIsInN1YiI6IjY1NzgwYmY2MzVhNjFlMDEzYWMyMDZmMSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ujcPy-vulTWhsv3dvQlEBboqr5tTmOBDL4zZwliwFlI")
            .build();

        Response responsePeople;
        try {
          responsePeople = client.newCall(requestPeople).execute();
          JSONObject jsonResponsePeople = new JSONObject(responsePeople.body().string());
          JSONArray arrayPeople = jsonResponsePeople.getJSONArray("results");
          JSONObject personObject = arrayPeople.getJSONObject(0);
          url += "&with_people=" + personObject.getInt("id");
        } catch (IOException e) {
          e.printStackTrace();
        }

      }

      Request request = new Request.Builder().url(url).get().addHeader("accept", "application/json")
          .addHeader("Authorization",
              "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4ZWI4ZmQ2YjUwOGE5YmE3ZGY2OTdkNmQ5MWFhMGFjZiIsInN1YiI6IjY1NzgwYmY2MzVhNjFlMDEzYWMyMDZmMSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ujcPy-vulTWhsv3dvQlEBboqr5tTmOBDL4zZwliwFlI")
          .build();
      Response response;
      try {
        int contador = buscarPeliculas();
        if (cbEstado.getSelectionModel().getSelectedItem().equals("No visto") && cbUbicacion.getSelectionModel().getSelectedItem().equals("Ninguna")) {
          response = client.newCall(request).execute();
          imprimir(contador, response, client, request);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }

    });
  }

  void imprimir(int contador, Response response, OkHttpClient client, Request request) throws IOException {

    double saltoX = 175.0;
    double saltoY = 251.0;
    JSONObject jsonResponse = new JSONObject(response.body().string());
    JSONArray arrayPeliculas = jsonResponse.getJSONArray("results");
    int imagenesPorFila = 3;

    for (int i = 0; i < arrayPeliculas.length(); i++) {

      double filaActual = (i / imagenesPorFila) + contador;
      JSONObject movieObject = arrayPeliculas.getJSONObject(i);

      ImageView imageView = new ImageView();
      imageView.setFitHeight(248.0);
      imageView.setFitWidth(165.0);
      imageView.setPreserveRatio(false);
      imageView.setPickOnBounds(true);
      String urlEnviar = "";
      if (!movieObject.isNull("poster_path")) {
        String imageUrl = "https://image.tmdb.org/t/p/w500/" + movieObject.getString("poster_path");
        urlEnviar = imageUrl;
        Image image = new Image(imageUrl);
        imageView.setImage(image);
      } else {
        InputStream imageUrl = getClass().getResourceAsStream("/img/blanco.jpg");
        Image image = new Image(imageUrl);
        urlEnviar = imageUrl.toString();
        imageView.setImage(image);
      }
      String urlEnviarFinal = urlEnviar;

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
      stackPane.setLayoutY(16.0 + filaActual * saltoY);

      stackPane.setOnMouseClicked(event -> {
        FXMLLoader loader =
            new FXMLLoader(getClass().getResource("/views/PaginaInfoPelicula.fxml"));
        PaginaInfoPeliculaController controller = new PaginaInfoPeliculaController(false,
            movieObject.getInt("id"), etiquetaGeneroEnviar, etiquetaTitulo, urlEnviarFinal,
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
  }
  
  int buscarPeliculas() {

    String titulo = txtfBuscarTitulo.getText();
    String actor = txtfBuscarActor.getText();
    String genero = cbGeneros.getSelectionModel().getSelectedItem();
    String ubicacion = cbUbicacion.getSelectionModel().getSelectedItem();
    String year = cbYear.getSelectionModel().getSelectedItem();
    Year yearObject = (year != null && !year.isEmpty()) ? Year.parse(year) : null;
    boolean visto = "Visto".equals(cbEstado.getValue());
    ObservableList<Pelicula> peliculasEncontradas = FXCollections.observableArrayList(
            PeliculaDAO.obtenerPeliculasConFiltros(titulo, null, actor, yearObject, genero, ubicacion, visto));
    double saltoX = 175.0;
    double saltoY = 251.0;
    int imagenesPorFila = 3;
    int contador = 0;

    for (Pelicula pelicula : peliculasEncontradas) {

      double filaActual = contador / imagenesPorFila;

      ImageView imageView = new ImageView();
      imageView.setFitHeight(248.0);
      imageView.setFitWidth(165.0);
      imageView.setPreserveRatio(false);
      imageView.setPickOnBounds(true);
      String urlEnviar = "";
        InputStream imageUrl = getClass().getResourceAsStream(pelicula.getPoster());
        Image image = new Image(imageUrl);
        urlEnviar = imageUrl.toString();
        imageView.setImage(image);
      String urlEnviarFinal = urlEnviar;

      String releaseDate = pelicula.getYear().toString();
      Label labelPrincipal = new Label(pelicula.getNombre() + " (" + releaseDate + ")");
      Font font = Font.font("Arial", FontWeight.BOLD, 20.0);
      labelPrincipal.setFont(font);
      labelPrincipal.setTextFill(Color.web("#16242B"));
      labelPrincipal.setMaxWidth(165.0);
      labelPrincipal.setWrapText(true);
      labelPrincipal.setVisible(false);
      labelPrincipal.setAlignment(Pos.CENTER);
      labelPrincipal.setTextAlignment(TextAlignment.CENTER);

      String etiquetaGenero = pelicula.getGenero();
      String etiquetaGeneroEnviar = etiquetaGenero;
      int horas = pelicula.getMinutos() / 60;
      int minutosRestantes = pelicula.getMinutos() % 60;
      if (horas == 0) {
        etiquetaGenero += "\n" + minutosRestantes + "min";
      } else if (minutosRestantes == 0) {
        etiquetaGenero += "\n" + horas + "h";
      } else {
        etiquetaGenero += "\n" + horas + "h " + minutosRestantes + "min";
      }

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
      String etiquetaEstrellas = formato.format(pelicula.getEstrellas()) + "★";
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
      stackPane.setLayoutX(21.0 + (contador % imagenesPorFila) * saltoX);
      stackPane.setLayoutY(16.0 + filaActual * saltoY);

      stackPane.setOnMouseClicked(event -> {
        FXMLLoader loader =
            new FXMLLoader(getClass().getResource("/views/PaginaInfoPelicula.fxml"));
        PaginaInfoPeliculaController controller = new PaginaInfoPeliculaController(true,
            pelicula.getIdPelicula(), etiquetaGeneroEnviar, pelicula.getNombre(), urlEnviarFinal,
            labelEstrellas.getText(), pelicula.getSinopsis(), userGenre, correo);
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
      contador++;
    }
    return contador % imagenesPorFila;
  }

  @FXML
  void cbAnoDesde(ActionEvent event) {

  }

  @FXML
  void cbEstado(ActionEvent event) {

  }

  @FXML
  void cbGeneros(ActionEvent event) {

  }

  @FXML
  void cbUbicacion(ActionEvent event) {

  }

  @FXML
  void imgViewAnadePelicula(MouseEvent event) {

  }

  @FXML
  void imgViewPaginaPrincipal(MouseEvent event) {

  }

  @FXML
  void imgViewPerfil(MouseEvent event) {

  }

  @FXML
  void txtfBuscarActor(ActionEvent event) {

  }

  @FXML
  void txtfBuscarTitulo(ActionEvent event) {

  }

}
