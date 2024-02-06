package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
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

public class PaginaPrincipalController {

    @FXML
    private ImageView imgViewAnadePelicula;

    @FXML
    private ImageView imgViewBuscaPelicula;

    @FXML
    private ImageView imgViewPerfil;
    
    @FXML
    private AnchorPane peliculas;

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
    	total = imprimirPeliculas("upcoming", total);
    	total = imprimirPeliculas("now_playing", total);
    	
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
        Request request = new Request.Builder()
          .url("https://api.themoviedb.org/3/movie/" + type + "?language=es-Es&page=1")
          .get()
          .addHeader("accept", "application/json")
          .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4ZWI4ZmQ2YjUwOGE5YmE3ZGY2OTdkNmQ5MWFhMGFjZiIsInN1YiI6IjY1NzgwYmY2MzVhNjFlMDEzYWMyMDZmMSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ujcPy-vulTWhsv3dvQlEBboqr5tTmOBDL4zZwliwFlI")
          .build();
        Response response = client.newCall(request).execute();
        JSONObject jsonResponse = new JSONObject(response.body().string());
        JSONArray arrayPeliculas = jsonResponse.getJSONArray("results");
        int imagenesPorFila = 4;
        int total = totalAnterior + arrayPeliculas.length();
        
        if (type.equals("popular")) {
    		imprimirLabel("Destacados", 0);
    	} else if (type.equals("now_playing")) {
    		imprimirLabel("Ahora en cines",  (66.0 * 2) + Math.floor(totalAnterior / imagenesPorFila) * saltoY);
    	} else if (type.equals("upcoming")) {
    		imprimirLabel("Próximos lanzamientos",  66.0 + Math.floor(totalAnterior / imagenesPorFila) * saltoY);
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
            
            String etiquetaTitulo = movieObject.getString("title") + "( " + movieObject.getString("release_date").substring(0, 4) + " )";
            Label labelPrincipal = new Label(etiquetaTitulo);
            Font font = Font.font("Arial", FontWeight.BOLD, 20.0);
            labelPrincipal.setFont(font);
            labelPrincipal.setTextFill(Color.web("#16242B"));
            labelPrincipal.setMaxWidth(165.0);
            labelPrincipal.setWrapText(true);
            labelPrincipal.setVisible(false);
            labelPrincipal.setAlignment(Pos.CENTER);
            labelPrincipal.setTextAlignment(TextAlignment.CENTER);
            
            // get id de generos, meterlos en un array, tomar nombre e imprimir
            String etiquetaGenero = "";
            Label labelGenero = new Label(etiquetaGenero);
            Font fontSmall = Font.font("Arial", FontWeight.BOLD, 16.0);
            labelGenero.setFont(fontSmall);
            labelGenero.setTextFill(Color.web("#16242B"));
            labelGenero.setMaxWidth(165.0);
            labelGenero.setWrapText(true);
            labelGenero.setVisible(false);
            labelGenero.setAlignment(Pos.CENTER);
            labelGenero.setTextAlignment(TextAlignment.CENTER);
            
            StackPane stackPane = new StackPane(imageView, labelPrincipal, labelGenero);
            stackPane.setLayoutX(21.0 + (i % imagenesPorFila) * saltoX);
            if (type.equals("now_playing")) {
            	stackPane.setLayoutY((66.0 * 3) + filaActual * saltoY);
            } else if (type.equals("upcoming")) {
            	stackPane.setLayoutY((66.0 * 2) + filaActual * saltoY);
            } else {
            	stackPane.setLayoutY(66.0 + filaActual * saltoY);
            }

            stackPane.setOnMouseEntered(event -> {
                Color colorToOverlay = Color.web("#BCBCBC");
                ColorInput colorInput = new ColorInput(0, 0, imageView.getBoundsInLocal().getWidth(), imageView.getBoundsInLocal().getHeight(), colorToOverlay);
                Blend blend = new Blend(BlendMode.SRC_OVER);
                blend.setTopInput(colorInput);
                imageView.setEffect(blend);
                labelGenero.setVisible(true);
            });

            stackPane.setOnMouseExited(event -> {
                imageView.setEffect(null);
                labelGenero.setVisible(false);
            });
            
            peliculas.getChildren().add(stackPane);
        }
        
        return total;
        
    }

}

