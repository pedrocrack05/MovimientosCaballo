package Caballo;

import java.util.Arrays;

public class Caballo {
	private final static int N = 8;
	private static int[][] tablero;
	private static int[] dx = { -2, -1, 1, 2, -2, -1, 1, 2 };
	private static int[] dy = { 1, 2, 2, 1, -1, -2, -2, -1 };
	private int[][] pasos = new int[N * N][2];
	// int contador = 0;

	public Caballo(int fila, int columna) {
		tablero = new int[N][N];
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
			guardarPaso(salto - 1, fila, columna);

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
				guardarPaso(salto - 1, fila, columna);
				// Se actualiza la posición del caballo
				nextFila = fila + dy[movimientosPosibles[0]];
				nextColumna = columna + dx[movimientosPosibles[0]];
				// Se guarda el último movimiento del caballo en la matriz de pasos
				guardarPaso(salto, nextFila, nextColumna);
				// Se guarda la última posición del caballo en el tablero
				tablero[nextFila][nextColumna] = salto + 1;
				return;
			}
		}
	}

	public void guardarPaso(int salto, int fila, int columna) {
		pasos[salto][0] = fila;
		pasos[salto][1] = columna;
	}

	public int[] indicesPosibles(boolean[] movimientos) {
		// Guarda en un arreglo solamente los pasos que sean posibles
		int[] posibles = new int[0];
		int k = 0;
		for (int i = 0; i < N; i++) {
			if (movimientos[i]) {
				if (posibles == null) {
					posibles = Arrays.copyOf(posibles, 1);
					posibles[k] = i;
					k++;
				} else {
					posibles = Arrays.copyOf(posibles, posibles.length + 1);
					posibles[posibles.length - 1] = i;
					k++;
				}
			}

		}

		return posibles;
	}

	public static int warnsdorff(int fila, int columna, int[] movimientosPosibles) throws EImposible {
		int[] futuros = new int[movimientosPosibles.length];

		for (int i = 0; i < movimientosPosibles.length; i++) {
			boolean[] movimientosSiguiente = movimientos(fila + dy[movimientosPosibles[i]],
					columna + dx[movimientosPosibles[i]]);
			for (int j = 0; j < 8; j++) {
				if (movimientosSiguiente[j]) {
					futuros[i] += 1;
				}
			}
		}

		int mejorMovimiento = -1;
		int mejorValor = Integer.MAX_VALUE; // Utilizar Integer.MAX_VALUE

		for (int i = 0; i < futuros.length; i++) {
			if (futuros[i] < mejorValor) {
				mejorValor = futuros[i];
				mejorMovimiento = i;
			}
		}

		return mejorMovimiento;
	}

	public static boolean[] movimientos(int fila, int columna) throws EImposible {
		boolean[] movimientosValidos = new boolean[N];

		// estos son los desplazamientos posibles

		for (int i = 0; i < N; i++) {
			int nuevaFila = fila + dy[i];
			int nuevaColumna = columna + dx[i];
			if (nuevaFila >= 0 && nuevaFila < N && nuevaColumna >= 0 && nuevaColumna < N
					&& tablero[nuevaFila][nuevaColumna] == 0) {
				movimientosValidos[i] = true;

			} else {
				movimientosValidos[i] = false;
			}
		}

		if (!HayMovimientosValidos(movimientosValidos)) {
			throw new EImposible("No hay movimientos válidos desde la posición actual.");
		} else {

			return movimientosValidos;
		}
	}

	private static boolean HayMovimientosValidos(boolean[] movimientosValidos) {
		for (boolean valido : movimientosValidos) {
			if (valido) {
				return true;
			}
		}

		return false;
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
			for (int j = 0; j < 2; j++) {
				System.out.print(pasos[i][j] + "\t");
			}
			System.out.print("]\n");
		}
	}

	public int[][] getPasos() {
		return pasos;
	}

	public static void main(String[] args) {

		Caballo caballo = new Caballo(0, 0);
		// System.out.println(caballo.contador);
	}
}
