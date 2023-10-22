package Caballo;

public class Caballo {
	private static int N = 8;
	private static int[][] tablero;
	private static int[] dx = { -2, -1, 1, 2, -2, -1, 1, 2 };
	private static int[] dy = { 1, 2, 2, 1, -1, -2, -2, -1 };
	private int[][] pasos = new int[64][2];

	public Caballo(int fila, int columna) {
		tablero = new int[N][N];
		try {
			resolver(fila, columna, 1);
		} catch (EImposible e) {
			System.out.println(e.getMessage());
		}
		imprimirTablero();
		// System.out.println(intento);
	}

	public void resolver(int fila, int columna, int intento) throws EImposible {
		// this.intento++;
		if (fila < 0 || fila >= N || columna < 0 || columna >= N) {
			throw new EImposible("El caballo no se encuentra en el tablero");
		} else if (tablero[fila][columna] != 0) {
			throw new EImposible("El caballo ya estuvo aqui");
		} else {
			tablero[fila][columna] = intento;
			boolean movimientos[] = movimientos(fila, columna);
			int k = 0;
			for (int i = 0; i < N; i++) {
				if (movimientos[i]) {
					k++;
				}
			}

			int movimientosSolucion[] = new int[k];// Se realiza un arreglo con los movimientos que se pueden hacer
			int j = 0;
			for (int i = 0; i < N; i++) {
				if (movimientos[i]) {
					movimientosSolucion[j] = i;
					j++;
				}
			}
			System.out.println(intento + "= " + fila + ", " + columna);
			pasos[intento - 1][0] = fila;
			pasos[intento - 1][1] = columna;

			if (intento < 63) {
				int solucion = warnsdorff(fila, columna, movimientosSolucion);
				resolver(fila + dy[movimientosSolucion[solucion]], columna + dx[movimientosSolucion[solucion]],
						intento + 1);
			} else {
				System.out.println((intento + 1) + "= " + (fila + dy[movimientosSolucion[0]]) + ", "
						+ (columna + dx[movimientosSolucion[0]]));
				pasos[intento - 1][0] = fila;
				pasos[intento - 1][1] = columna;
				tablero[fila + dy[movimientosSolucion[0]]][columna + dx[movimientosSolucion[0]]] = intento + 1;
				return;
			}

			// tablero[fila][columna] = 0;

		}
	}

	public static int warnsdorff(int fila, int columna, int[] movimientosSolucion) throws EImposible {
		int[] futuros = new int[movimientosSolucion.length];
		for (int i = 0; i < movimientosSolucion.length; i++) {
			boolean[] movimientosSiguienteCasilla = movimientos(fila + dy[movimientosSolucion[i]],
					columna + dx[movimientosSolucion[i]]);
			for (int j = 0; j < 8; j++) {
				if (movimientosSiguienteCasilla[j]) {
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
	}

	public int[][] getPasos() {
		return pasos;
	}

	public static void main(String[] args) {

		new Caballo(3, 6);
	}
}
