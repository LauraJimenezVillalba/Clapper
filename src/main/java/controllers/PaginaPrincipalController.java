package controllers;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

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
    	
        double saltoX = 203.0;
        double saltoY = 251.0;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
          .url("https://api.themoviedb.org/3/movie/popular?language=es-Es&page=1")
          .get()
          .addHeader("accept", "application/json")
          .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4ZWI4ZmQ2YjUwOGE5YmE3ZGY2OTdkNmQ5MWFhMGFjZiIsInN1YiI6IjY1NzgwYmY2MzVhNjFlMDEzYWMyMDZmMSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ujcPy-vulTWhsv3dvQlEBboqr5tTmOBDL4zZwliwFlI")
          .build();
        Response response = client.newCall(request).execute();
        JSONObject jsonResponse = new JSONObject(response.body().string());
        JSONArray popularArray = jsonResponse.getJSONArray("results");
        int imagenesPorFila = 4;
        int total = popularArray.length();

        for (int i = 0; i < total; i++) {
            double filaActual = Math.floor(i / imagenesPorFila);
            JSONObject movieObject = popularArray.getJSONObject(i);
            ImageView imageView = new ImageView();
            imageView.setFitHeight(248.0);
            imageView.setFitWidth(165.0);
            imageView.setLayoutX(21.0 + (i % imagenesPorFila) * saltoX);
            imageView.setLayoutY(66.0 + filaActual * saltoY);
            imageView.setPickOnBounds(true);
            imageView.setPreserveRatio(true);
            String imageUrl = "https://image.tmdb.org/t/p/w500/" + movieObject.getString("poster_path");
            Image image = new Image(imageUrl);
            imageView.setImage(image);
            peliculas.getChildren().add(imageView);
        }
    }


}

