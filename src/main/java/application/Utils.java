package application;

public class Utils {

	String minutosHoras(int minutos) {
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

}
