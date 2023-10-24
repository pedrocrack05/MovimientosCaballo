package application;

import Caballo.Caballo;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Chess extends Application {

	@Override
	public void start(Stage escena) {
		GridPane tablero = new GridPane();// Crea un panel en forma de cuadrícula
		crearCasillas(tablero); // Creación de casillas
		crearEscena(escena, tablero); // Creación de la escena
		// agregarCaballo(tablero, new
		// ImageView(getClass().getResource("caballo.png").toExternalForm()), 0, 0);
	}

	public void crearCasillas(GridPane tablero) {
		int count = 0;
		double s = 50; // Tamaño
		for (int i = 0; i < 8; i++) {
			count++;
			for (int j = 0; j < 8; j++) {
				Rectangle r = new Rectangle(s, s, s, s);
				if (count % 2 == 0)
					r.setFill(Color.WHITE);
				tablero.add(r, j, i);
				count++;
			}
		}
	}

	public void crearEscena(Stage escena, GridPane tablero) {
		Scene scene = new Scene(tablero);
		escena.setTitle("Tablero");
		escena.setScene(scene);
		escena.show();
	}

	public void agregarCaballo(GridPane tablero, ImageView caballo, int x, int y) {
		tablero.add(caballo, x, y);
	}

	public void recorridoCaballo(GridPane tablero, ImageView caballo, int[][] movimientos) {
		for (int i = 0; i < 64; i++) {
			agregarCaballo(tablero, caballo, movimientos[i][0], movimientos[i][1]);
		}
	}

	public static void main(String[] args) {
		Caballo caballo = new Caballo(7, 7);
		// System.out.println(caballo.contador);
		launch();
	}
}
