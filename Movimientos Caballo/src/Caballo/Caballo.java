package Caballo;

import java.util.Arrays;

public class Caballo {
	private static int N=8;
	private static int[][] tablero;
	private static int[] dx = { -2, -1, 1, 2, -2, -1, 1, 2 };
	private static int[] dy = { 1, 2, 2, 1, -1, -2, -2, -1 };

	public Caballo(int fila, int columna) {
	    tablero = new int[N][N];
	    try {
	    resolver(fila, columna, 1);
	    }catch(EImposible e){
	    	System.out.println(e.getMessage());
	    }
	    imprimirTablero();
	}

	public void resolver(int fila, int columna, int intento) throws EImposible {
		if(intento > N*N) {
			imprimirTablero();
			return;
		}
		
		if (fila < 0 || fila >= N || columna < 0 || columna >= N) {
			throw new EImposible("El caballo no se encuentra en el tablero");
		} else if (tablero[fila][columna] != 0) {
			throw new EImposible("El caballo ya estuvo aqui");
		} else {
			tablero[fila][columna] = intento;

			boolean movimientos[]= movimientos(fila, columna);
			int k = 0;
			for (int i = 0; i<N; i++) {
				if (movimientos[i] == true) {
					k++;
				}
			}
			
			int movimientosSolucion[]= new int[k];
			int j=0;
			for(int i=0; i<N;i++) {
				if(movimientos[i]== true) {
					movimientosSolucion[j]= i;
					j++;
				}
			}
            System.out.println(intento+"= "+fila+", "+columna);
			
            int solucion= warnsdorff(fila, columna, movimientosSolucion);
            if(solucion==-1) {
            	tablero[fila][columna]=0;
            	return;
            }else {
            
            resolver(fila+dy[movimientosSolucion[solucion]], columna+dx[movimientosSolucion[solucion]], intento+1);
            tablero[fila][columna]=0;
           }

		}
	}
	
	public static int warnsdorff(int fila, int columna, int[] movimientosSolucion)throws EImposible {
		
		int[] futuros = new int[movimientosSolucion.length];
		for(int i=0; i<movimientosSolucion.length; i++) {
			boolean [] movimientosSiguienteCasilla= movimientos(fila+dy[movimientosSolucion[i]], columna+dx[movimientosSolucion[i]]);
				if(movimientosSiguienteCasilla[i]==true) {
					futuros[i]+=1;
				}
			}
				
		int mejorSolucion[]= futuros;
		int indice=0;
		Arrays.sort(mejorSolucion);
		for(int i=0; i<futuros.length; i++) {
			if(futuros[i]== mejorSolucion[0]) {
				indice= i;
				break;
			}
		}
		return indice;
		
	}	

	public static boolean[] movimientos(int fila, int columna) throws EImposible {
		boolean[] movimientosValidos = new boolean[N];

		//estos son los desplazamientos posibles

		for (int i = 0; i < N; i++) {
			int nuevaFila = fila + dy[i];
			int nuevaColumna = columna + dx[i];

			if (nuevaFila >= 0 && nuevaFila < N && nuevaColumna >= 0 && nuevaColumna < N && tablero[nuevaFila][nuevaColumna]==0) {
				movimientosValidos[i] = true;
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
	
	 private boolean esMovimientoValido(int x, int y) {
	        return (x >= 0 && x < N && y >= 0 && y < N && tablero[x][y] == 0);
	    }

	public void imprimirTablero() {
		for (int i = 0; i < 8; i++) {
			System.out.print("[");
			for (int j = 0; j < 8; j++) {
				System.out.print(tablero[i][j]+" ");
			}
			System.out.print("]\n");
		}
	}

	public static void main(String[] args) {

		Caballo caballo= new Caballo(0, 0);
	}
}
