package controllers;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import org.json.JSONArray;
import org.json.JSONObject;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import application.Utils;
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

public class PaginaDescubrirController {

    @FXML
    private ComboBox<?> cbAnoDesde;

    @FXML
    private ComboBox<?> cbAnoHasta;

    @FXML
    private ComboBox<?> cbEstado;

    @FXML
    private ComboBox<?> cbGeneros;

    @FXML
    private ComboBox<?> cbUbicacion;

    @FXML
    private ImageView imgViewAnadePelicula;

    @FXML
    private ImageView imgViewPaginaPrincipal;

    @FXML
    private ImageView imgViewPerfil;
    
    @FXML
    private ImageView buscar;

    @FXML
    private TextField txtfBuscar;
    
    @FXML
    private AnchorPane peliculas;
    
    public void initialize() throws IOException {
    	// NavBar
        imgViewPaginaPrincipal.setOnMouseClicked(event -> {
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/PaginaPrincipal.fxml"));
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
        
        // Fin de NavBar
        
        buscar.setOnMouseClicked(event -> {
        	
        	OkHttpClient client = new OkHttpClient();
        	Request request = new Request.Builder()
        	  .url("https://api.themoviedb.org/3/search/movie?query=" + txtfBuscar.getText() + "&include_adult=false&language=es-ES&page=1")
        	  .get()
        	  .addHeader("accept", "application/json")
        	  .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4ZWI4ZmQ2YjUwOGE5YmE3ZGY2OTdkNmQ5MWFhMGFjZiIsInN1YiI6IjY1NzgwYmY2MzVhNjFlMDEzYWMyMDZmMSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ujcPy-vulTWhsv3dvQlEBboqr5tTmOBDL4zZwliwFlI")
        	  .build();
        	Response response;
			try {
				response = client.newCall(request).execute();
				imprimir(response, client, request);
			} catch (IOException e) {
				e.printStackTrace();
			}
        	
        });
    }
    
    void imprimir(Response response, OkHttpClient client, Request request) throws IOException {
    	
    	double saltoX = 203.0;
        double saltoY = 248.0;
        JSONObject jsonResponse = new JSONObject(response.body().string());
        JSONArray arrayPeliculas = jsonResponse.getJSONArray("results");
        int imagenesPorFila = 4;

        for (int i = 0; i < arrayPeliculas.length(); i++) {

          double filaActual = i / imagenesPorFila;
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
          String etiquetaEstrellas = formato.format(movieObject.getBigDecimal("vote_average")) + "★" ;
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
          stackPane.setLayoutX(16.0 + (i % imagenesPorFila) * saltoX);
          stackPane.setLayoutY(10.0 + filaActual * saltoY);
          
          System.out.println(labelPrincipal.getText());
          
          stackPane.setOnMouseClicked(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/PaginaInfoPelicula.fxml"));
            PaginaInfoPeliculaController controller = new PaginaInfoPeliculaController(movieObject.getInt("id"), etiquetaGeneroEnviar, etiquetaTitulo, urlEnviarFinal, labelEstrellas.getText(), movieObject.getString("overview"));
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

    @FXML
    void cbAnoDesde(ActionEvent event) {

    }

    @FXML
    void cbAnoHasta(ActionEvent event) {

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
    void txtfBuscar(ActionEvent event) {

    }

}
