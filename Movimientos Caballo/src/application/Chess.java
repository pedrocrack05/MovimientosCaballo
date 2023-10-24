package application;

import Caballo.Caballo;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Chess extends Application {
	
	private Caballo caballo;

	@Override
	public void start(Stage escena) {
	    GridPane tablero = new GridPane();// Crea un panel en forma de cuadrícula
	    crearCasillas(tablero); // Creación de casillas
	    crearEscena(escena, tablero); // Creación de la escena

	    Button iniciarButton = new Button("Iniciar");
//	    iniciarButton.setOnAction(event -> iniciarRecorridoCaballo(tablero));
	    tablero.add(iniciarButton, 0, 8); // Agregar el botón "Iniciar" al tablero

	    // Crear ComboBox para las filas
	    ObservableList<Integer> filasOptions = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8);
	    ComboBox<Integer> filasComboBox = new ComboBox<>(filasOptions);
	    filasComboBox.setValue(7); // Valor predeterminado
	    tablero.add(filasComboBox, 1, 8);

	    // Crear ComboBox para las columnas
	    ObservableList<Integer> columnasOptions = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8);
	    ComboBox<Integer> columnasComboBox = new ComboBox<>(columnasOptions);
	    columnasComboBox.setValue(7); // Valor predeterminado
	    tablero.add(columnasComboBox, 2, 8);
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
