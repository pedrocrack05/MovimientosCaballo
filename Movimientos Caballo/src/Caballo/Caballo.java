package Caballo;

import java.util.Arrays;

public class Caballo {
	private final static int N = 8;
	private static int[][] tablero;
	private static int[] dx = { -2, -1, 1, 2, -2, -1, 1, 2 };
	private static int[] dy = { 1, 2, 2, 1, -1, -2, -2, -1 };
	public Paso[] pasos;
	// int contador = 0; //Solo con motivos de debug

	public Caballo(int fila, int columna) {
		tablero = new int[N][N];
		pasos = new Paso[N * N];
		try {
			resolver(fila, columna, 1);
		} catch (EImposible e) {
			System.out.println(e.getMessage());
		}
		imprimirTablero();
	}

	public void resolver(int fila, int columna, int salto) throws EImposible {
		// this.contador++;

		if (fila < 0 || fila >= N || columna < 0 || columna >= N) {
			throw new EImposible("El caballo no se encuentra en el tablero");
		} else if (tablero[fila][columna] != 0) {
			throw new EImposible("El caballo ya estuvo aqui");
		} else {
			tablero[fila][columna] = salto;// Guarda salto en tablero
			pasos[salto - 1] = new Paso(fila, columna);
			// guardarPaso(salto - 1, fila, columna);

			// Guardar los movimientos que puede y no puede hacer desde la pos actual
			boolean movimientos[] = movimientos(fila, columna);

			// Guardar solamente el índice de los movimientos posibles
			int[] movimientosPosibles = indicesPosibles(movimientos);

			// System.out.println(salto + "= " + fila + ", " + columna);

			int nextFila, nextColumna;
			if (salto < (N * N) - 1) {// Si estamos en saltos menores al penúltimo salto
				// Se verifica el camino más corto
				int mejorSolucion = warnsdorff(fila, columna, movimientosPosibles);
				// Se actualiza la posición en x e y
				nextFila = fila + dy[movimientosPosibles[mejorSolucion]];
				nextColumna = columna + dx[movimientosPosibles[mejorSolucion]];
				// Llamada recursiva al siguiente movimiento
				resolver(nextFila, nextColumna, salto + 1);
			} else {
				// Se guarda el penúltimo movimiento en la matriz de pasos
				// guardarPaso(salto - 1, fila, columna);
				pasos[salto - 1] = new Paso(fila, columna);
				// Se actualiza la posición del caballo
				nextFila = fila + dy[movimientosPosibles[0]];
				nextColumna = columna + dx[movimientosPosibles[0]];
				// Se guarda el último movimiento del caballo en la matriz de pasos
				// guardarPaso(salto, nextFila, nextColumna);
				pasos[salto] = new Paso(nextFila, nextColumna);
				// Se guarda la última posición del caballo en el tablero
				tablero[nextFila][nextColumna] = salto + 1;
				return;
			}
		}
	}

	private static boolean[] movimientos(int fila, int columna) throws EImposible {
		boolean[] posiblesMovimientos = new boolean[N];// Arreglo temporal para guardar posibilidad o no de moverse

		for (int i = 0; i < N; i++) {// Ciclo para verificar si puede o no moverse en cada dirección.

			// Variables temporales con los posibles movimientos a partir de la posición
			// actual.
			int tempFila = fila + dy[i];
			int tempColumna = columna + dx[i];

			// Condición para que pueda contrase un movimiento como válido:
			// ( 0 < tempFila < N) ^ (0 < tempColumna < N) ^ (casilla del tablero libre)
			posiblesMovimientos[i] = (tempFila >= 0 && tempFila < N && tempColumna >= 0 && tempColumna < N
					&& tablero[tempFila][tempColumna] == 0);// Guarda el resultado al evaluar la condición
		}

		// Verifica que haya por lo menos un movimiento válido entre los posibles
		// movimientos para que el programa continúe
		if (unMovimientoValido(posiblesMovimientos)) {
			return posiblesMovimientos;
		} else {
			throw new EImposible("No hay movimientos válidos desde la posición actual.");
		}
	}

	// Recorre todo el arreglo hsta que encuentre el primer movimiento válido
	private static boolean unMovimientoValido(boolean[] posiblesMovimientos) {
		for (boolean valido : posiblesMovimientos) {
			if (valido) {
				return true;
			}
		}
		return false;
	}

	// Guarda el índice de los pasos que se puedan hacer en ese momento.
	private int[] indicesPosibles(boolean[] movimientos) {
		int[] posibles = new int[movimientos.length];
		int k = 0;

		for (int i = 0; i < movimientos.length; i++) {
			if (movimientos[i]) {
				posibles[k] = i;
				k++;
			}
		}

		return Arrays.copyOf(posibles, k);
	}

	private static int warnsdorff(int fila, int columna, int[] movimientosPosibles) throws EImposible {
		int[] futuros = new int[movimientosPosibles.length]; // se crea un arreglo que dira la cantidad de soluciones futuras
		for (int i = 0; i < movimientosPosibles.length; i++) {
			boolean[] movimientosSiguiente = movimientos(fila + dy[movimientosPosibles[i]],
					columna + dx[movimientosPosibles[i]]);
			for (int j = 0; j < 8; j++) { // se itera 2 veces, una para obtener los movimientos siguientes de todos los pasos posibles
				if (movimientosSiguiente[j]) { // La otra es para verificar la cantidad de movimientos validos de los movimientos siguientes
					futuros[i] += 1;
				}
			}
		}

		int mejorMovimiento = -1;
		int mejorValor = Integer.MAX_VALUE; // Utilizar Integer.MAX_VALUE
		for (int i = 0; i < futuros.length; i++) {
			if (futuros[i] == mejorValor) {

			}
			if (futuros[i] < mejorValor) { // se obtiene el indice de la casilla solucion con menos movimientos posibles
				mejorValor = futuros[i];
				mejorMovimiento = i;
			}
		}

		return mejorMovimiento;
	}

	public void imprimirTablero() {
		for (int i = 0; i < 8; i++) {
			System.out.print("[");
			for (int j = 0; j < 8; j++) {
				System.out.print(tablero[i][j] + "\t");
			}
			System.out.print("]\n");
		}

		for (int i = 0; i < 64; i++) {
			System.out.print("[");
			System.out.print(pasos[i].getX() + "\t" + pasos[i].getY());
			System.out.print("]\n");
		}
	}

	public Paso[] getPasos() {
		return pasos;
	}

}
