package application;

import java.awt.Label;

import Caballo.Caballo;
import Caballo.Paso;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Chess extends Application {

	@Override
	public void start(Stage escena) {
		GridPane tablero = new GridPane();// Crea un panel en forma de cuadrícula
		tablero.setPrefSize(400, 425);
		crearCasillas(tablero); // Creación de casillas
		crearEscena(escena, tablero); // Creación de la escena

		Button iniciarButton = new Button("Iniciar");
//	    iniciarButton.setOnAction(event -> iniciarRecorridoCaballo(tablero));
		tablero.add(iniciarButton, 0, 8); // Agregar el botón "Iniciar" al tablero

		// Etiqueta para las filas
		Label filasLabel = new Label("Filas:");
//	    HBox filasBox = new HBox(filasLabel);
//	    tablero.add(filasBox, 1, 8);

		// Crear ComboBox para las filas
		ObservableList<Integer> filasOptions = FXCollections.observableArrayList(0, 1, 2, 3, 4, 5, 6, 7);
		ComboBox<Integer> filasComboBox = new ComboBox<>(filasOptions);
		filasComboBox.setValue(7); // Valor predeterminado
		tablero.add(filasComboBox, 2, 8);

		// Etiqueta para las columnas
		Label columnasLabel = new Label("Columnas:");
//	    HBox columnasBox = new HBox(columnasLabel);
//	    tablero.add(columnasBox, 3, 8);

		// Crear ComboBox para las columnas
		ObservableList<Integer> columnasOptions = FXCollections.observableArrayList(0, 1, 2, 3, 4, 5, 6, 7);
		ComboBox<Integer> columnasComboBox = new ComboBox<>(columnasOptions);
		columnasComboBox.setValue(7); // Valor predeterminado
		tablero.add(columnasComboBox, 4, 8);
	}

	public void crearCasillas(GridPane tablero) {
		int count = 0;
		double size = 50; // Tamaño
		for (int i = 0; i < 8; i++) {
			count++;
			for (int j = 0; j < 8; j++) {
				Rectangle casilla = new Rectangle(size, size, size, size);
				if (count % 2 == 0)
					casilla.setFill(Color.WHITE);
				tablero.add(casilla, j, i);
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

	public void recorridoCaballo(GridPane tablero, ImageView caballo, Paso[] pasos) {
		for (int i = 0; i < 64; i++) {
			agregarCaballo(tablero, caballo, pasos[i].getX(), pasos[i].getY());
		}
	}

	public static void main(String[] args) {
		launch();
		Caballo caballo = new Caballo(7, 7);
		caballo.getPasos();
	}
}
