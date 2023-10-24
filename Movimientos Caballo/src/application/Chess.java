
package application;

import java.awt.Label;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import Caballo.Caballo;
import Caballo.Paso;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class Chess extends Application {
	private int currentIndex = 0;
	private Paso[] pasos;
	private ImageView caballoImageView;
	private Button[][] botones = new Button[8][8];
	private GridPane tablero;
	int posx, poxy;

	@Override
	public void start(Stage escena) {

		escena.setResizable(false);// Evita que se pueda redimensionar la ventana
		tablero = new GridPane(); // Crea un panel en forma de cuadrícula
		tablero.setPrefSize(400, 425);

		crearCasillas(tablero); // Creación de casillas (Colorea el tablero)
		crearEscena(escena, tablero); // Creación de la escena (Inicializa y muestra todo)

		// icaballo.setVisible(false);

		Button iniciarButton = new Button("Iniciar");
		// iniciarButton.setOnAction(event -> iniciarRecorridoCaballo(tablero));
		tablero.add(iniciarButton, 0, 8); // Agregar el botón "Iniciar" al tablero

		Label filasLabel = new Label("Filas:");
//	    tablero.add(filasLabel, 1, 8);

		Label columnasLabel = new Label("Columnas:");
//	    tablero.add(columnasLabel, 3, 8);

		// Crear ComboBox para las filas
		ObservableList<Integer> filasOptions = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8);
		ComboBox<Integer> filasComboBox = new ComboBox<>(filasOptions);
		filasComboBox.setValue(1); // Valor predeterminado
		tablero.add(filasComboBox, 2, 8);

		// Crear ComboBox para las columnas
		ObservableList<Integer> columnasOptions = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8);
		ComboBox<Integer> columnasComboBox = new ComboBox<>(columnasOptions);
		columnasComboBox.setValue(1); // Valor predeterminado
		tablero.add(columnasComboBox, 4, 8);
		
		/*
		 * filasComboBox.valueProperty().addListener(new ChangeListener<Integer>() {
		 * public void changed(ObservableValue<? extends Integer> observable, Integer
		 * oldValue, Integer newValue) { agregarCaballo(tablero, icaballo, 0,
		 * filasComboBox.getValue() - 1); } }); ;
		 * 
		 * columnasComboBox.valueProperty().addListener(new ChangeListener<Integer>() {
		 * 
		 * @Override public void changed(ObservableValue<? extends Integer> observable,
		 * Integer oldValue, Integer newValue) { agregarCaballo(tablero, icaballo,
		 * columnasComboBox.getValue() - 1, 0); } });
		 */

		iniciarButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int xTemp = columnasComboBox.getValue() - 1;
				int yTemp = filasComboBox.getValue() - 1;
				Caballo caballo = new Caballo(xTemp, yTemp);
				pasos = caballo.getPasos();
				recorridoCaballo(tablero, caballito(), pasos, yTemp, yTemp);

			}
		});
	}

	public ImageView caballito() {
		ImageView iCaballo = new ImageView(new Image("\\application\\kballo.png"));
		iCaballo.setFitHeight(50);
		iCaballo.setFitWidth(50);
		iCaballo.setPreserveRatio(true);
		caballoImageView= iCaballo;
		return iCaballo;
	}

	public void crearCasillas(GridPane tablero) {
		int count = 0;
		double size = 50; // Tamaño
		for (int i = 0; i < 8; i++) {
			count++;
			for (int j = 0; j < 8; j++) {
				Button casilla = new Button();
				casilla.setMinSize(size, size);
				casilla.setMaxSize(size, size);
				if (count % 2 == 0) {
					casilla.setStyle("-fx-background-color: white;");
				} else {
					casilla.setStyle("-fx-background-color: black;");
				}
				tablero.add(casilla, j, i);
				botones[i][j] = casilla;
				count++;
			}
		}
	}

	public void crearEscena(Stage lienzo, GridPane tablero) {
		Scene scene = new Scene(tablero);
		lienzo.setTitle("Tablero");
		lienzo.setScene(scene);
		lienzo.show();
	}

	private void moverCaballo() {
		if (currentIndex < pasos.length) {
			Paso paso = pasos[currentIndex];
			int x = paso.getX();
			int y = paso.getY();

			tablero.getChildren().remove(caballoImageView);
			tablero.add(caballoImageView, x, y);

			currentIndex++;
		}
	}

	public void recorridoCaballo(GridPane tablero, ImageView caballo, Paso[] pasos, int fila, int columna) {
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(800), new EventHandler<ActionEvent>() {
			int b;

			@Override
			public void handle(ActionEvent event) {
				Button button = botones[pasos[++b].getX()][pasos[b].getY()];
				if (button != null) {
					String colorStyle = String.format("-fx-background-color: orangered;");
					button.setStyle(colorStyle);
					button.setGraphic(caballoImageView);
				}
			}
		}));
	      
		// assuming same size of inner arrays here
		timeline.setCycleCount(64);
		timeline.play();
	}

	public static void main(String[] args) {
		launch();
	}
}
