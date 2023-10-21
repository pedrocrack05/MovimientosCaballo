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
		 if (intento == 64) {
		        return 1;
		 }
		if (fila < 0 || fila >= 8 || columna < 0 || columna >= 8) {
			return -1;
		} else if (tablero[fila][columna] == 1) {
			return -1;
		} else {
			tablero[fila][columna] = 1;

			boolean movimientos[]= movimientos(fila, columna);
			int movimientosSolucion[]= new int[1];
			int j=0;
			for(int i=0; i<8;i++) {
				if(movimientos[i]== true) {
					movimientosSolucion[j]= i;
					movimientosSolucion= Arrays.copyOf(movimientosSolucion, movimientosSolucion.length+1);
					j++;
				}
			}
            System.out.println(intento+"= "+fila+", "+columna);
			
            int solucion= warnsdorff(fila, columna, movimientosSolucion);
            return resolver(fila+dy[solucion], columna+dx[solucion], intento+1);

		}
	}
	
	public static int warnsdorff(int fila, int columna, int[] movimientosSolucion)throws EImposible {
		int[] cantidadSiguientesCasillas = new int[movimientosSolucion.length + 1];
		for(int i=0; i<movimientosSolucion.length; i++) {
			cantidadSiguientesCasillas= Arrays.copyOf(cantidadSiguientesCasillas, cantidadSiguientesCasillas.length+1);
			boolean [] movimientosSiguienteCasilla= movimientos(fila+dy[movimientosSolucion[i]], columna+dx[movimientosSolucion[i]]);
			for(int j=0; j<8; j++) {
				if(movimientosSiguienteCasilla[j]==true) {
					cantidadSiguientesCasillas[i]+=1;
				}
			}
		}
				
		int mejorSolucion= cantidadSiguientesCasillas[0];
		for(int i=1; i<cantidadSiguientesCasillas.length; i++) {
			if(mejorSolucion>cantidadSiguientesCasillas[i]) {
				mejorSolucion= cantidadSiguientesCasillas[i];
			}
		}
		
		return mejorSolucion;
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

	public static void imprimirTablero(Caballo caballo) {
		for (int i = 0; i < 8; i++) {
			System.out.print("[");
			for (int j = 0; j < 8; j++) {
				System.out.print(tablero[i][j]+" , ");
			}
			System.out.print("]\n");
		}
	}

	public static void main(String[] args) {

		Caballo caballo= new Caballo(3, 6);
		try{
			System.out.println(caballo.resolver(3, 6, 1));
		}catch(EImposible e) {
			System.out.println(e.getMessage());
		}
		
		imprimirTablero(caballo);
	}
}
