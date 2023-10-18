package Caballo;

import java.util.Arrays;

public class Caballo {
	private int fila, columna;
	private static int[][] tablero;
	private static int[] dx = { -2, -1, 1, 2, -2, -1, 1, 2 };
	private static int[] dy = { 1, 2, 2, 1, -1, -2, -2, -1 };

	public Caballo(int fila, int columna) {
		this.fila = fila;
		this.columna = columna;
		tablero = new int[8][8];

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				tablero[i][j] = 0;
			}
		}
	}

	public int resolver(int fila, int columna, int intento) throws EImposible {

		if (fila < 0 || fila >= 8 || columna < 0 || columna >= 8) {
			throw new EImposible("El caballo no se encuentra en el tablero");
		} else if (tablero[fila][columna] == 1) {
			throw new EImposible("Ya hizo este movimiento");
		} else {
			tablero[fila][columna] = 1;

			boolean movimientos[]= movimientos(fila, columna);
			int movimientosSolucion[]= new int[8];
			for(int i=0; i<8;i++) {
				if(movimientos[i]== true) {
					movimientosSolucion[i]= i;
				}else {
					movimientosSolucion[i]= 9;
				}
			}

			int k=0;
			while(movimientosSolucion[k]==9 && k<8) {
				k++;
			}
			
			System.out.println(intento);
			
			return resolver(fila+dy[k], columna+dx[k], intento+1);
		}

	}

	public static boolean[] movimientos(int fila, int columna) throws EImposible {
		boolean[] movimientosValidos = new boolean[8];

		//estos son los desplazamientos posibles

		for (int i = 0; i < 8; i++) {
			int nuevaFila = fila + dy[i];
			int nuevaColumna = columna + dx[i];

			if (nuevaFila >= 0 && nuevaFila < 8 && nuevaColumna >= 0 && nuevaColumna < 8) {
				if (tablero[nuevaFila][nuevaColumna] == 0) {
					movimientosValidos[i] = true;
				} else {
					movimientosValidos[i] = false;
				}
			} else {
				movimientosValidos[i] = false;
			}
		}

		if (noHayMovimientosValidos(movimientosValidos)==false) {
			throw new EImposible("No hay movimientos válidos desde la posición actual.");
		}else {

			return movimientosValidos;
		}
	}

	private static boolean noHayMovimientosValidos(boolean[] movimientosValidos) {
		for (boolean valido : movimientosValidos) {
			if (valido) {
				return true;
			}
		}

		return false;
	}

	public static void main(String[] args) {

		Caballo caballo= new Caballo(3, 6);
		try{
			System.out.println(caballo.resolver(3, 6, 1));
		}catch(EImposible e) {
			System.out.println(e.getMessage());
		}

	}
}
