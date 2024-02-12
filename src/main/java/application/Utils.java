package application;

import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class Utils {

	public static String minutosHoras(int id) throws IOException {

		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url("https://api.themoviedb.org/3/movie/" + id + "?language=es-ES")
				.get().addHeader("accept", "application/json")
				.addHeader("Authorization",
						"Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4ZWI4ZmQ2YjUwOGE5YmE3ZGY2OTdkNmQ5MWFhMGFjZiIsInN1YiI6IjY1NzgwYmY2MzVhNjFlMDEzYWMyMDZmMSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ujcPy-vulTWhsv3dvQlEBboqr5tTmOBDL4zZwliwFlI")
				.build();
		Response response = client.newCall(request).execute();
		JSONObject jsonResponse = new JSONObject(response.body().string());

		int minutos = jsonResponse.getInt("runtime");
		int horas = minutos / 60;
		int minutosRestantes = minutos % 60;
		if (horas == 0) {
			return minutosRestantes + "min";
		} else if (minutosRestantes == 0) {
			return horas + "h";
		} else {
			return horas + "h " + minutosRestantes + "min";
		}
	}
	
	public static int getIDByGenre(String genre) throws IOException {

		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url("https://api.themoviedb.org/3/genre/movie/list?language=es").get()
				.addHeader("accept", "application/json")
				.addHeader("Authorization",
						"Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4ZWI4ZmQ2YjUwOGE5YmE3ZGY2OTdkNmQ5MWFhMGFjZiIsInN1YiI6IjY1NzgwYmY2MzVhNjFlMDEzYWMyMDZmMSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ujcPy-vulTWhsv3dvQlEBboqr5tTmOBDL4zZwliwFlI")
				.build();
		Response response = client.newCall(request).execute();
		JSONObject jsonResponse = new JSONObject(response.body().string());
		JSONArray arrayGeneros = jsonResponse.getJSONArray("genres");
		for (int i = 0; i < arrayGeneros.length(); i++) {
			JSONObject genreObject = arrayGeneros.getJSONObject(i);
			String genreName = genreObject.getString("name");
			if (genreName.equals(genre)) {
				return genreObject.getInt("id");
			}
		}
		return 0;

	}

	public static String getGenreByID(int id) throws IOException {

		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url("https://api.themoviedb.org/3/genre/movie/list?language=es").get()
				.addHeader("accept", "application/json")
				.addHeader("Authorization",
						"Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4ZWI4ZmQ2YjUwOGE5YmE3ZGY2OTdkNmQ5MWFhMGFjZiIsInN1YiI6IjY1NzgwYmY2MzVhNjFlMDEzYWMyMDZmMSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ujcPy-vulTWhsv3dvQlEBboqr5tTmOBDL4zZwliwFlI")
				.build();
		Response response = client.newCall(request).execute();
		JSONObject jsonResponse = new JSONObject(response.body().string());
		JSONArray arrayGeneros = jsonResponse.getJSONArray("genres");
		for (int i = 0; i < arrayGeneros.length(); i++) {
			JSONObject genreObject = arrayGeneros.getJSONObject(i);
			int genreId = genreObject.getInt("id");
			if (genreId == id) {
				return genreObject.getString("name");
			}
		}
		return null;

	}

}
