package Caballo;

public class Caballo {
	
	private int fila, columna;
	private int posicion[][];
	private int x[]= {columna+2, columna+1, columna-2, columna-1};
	private int y[]= {fila+2, fila+1, fila-2, fila-1};
	
	public Caballo(int[][] posicion) {
		this.x= new int[8];
		this.y= new int [8];
		this.posicion= posicion;
		posicion= new int[8][8];
		
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				posicion[i][j]=0;
			}
		}
		
		/* 1) No se debe desbordar en movimientos hacia arriba o hacia abajo
		 * 2) Debe verificar que no haya un 1 en la posicion deseada para moverse
		 * 3) PENE
		 * 4) Chupan
		 * 5) szs
		 * 6) rico
		 */
		
	}
	
	private boolean resolver(int fila, int columna) throws EImposible{
		if(fila<0 || fila>8 || columna<0 || columna>0) {
			throw new EImposible("La ficha no se encuentra en el tablero");
		}else if(posicion[fila][columna]==1){
				throw new EImposible("Ya hizo este movimiento");
			}else {
				posicion[fila][columna]=1;
				
			
		}
		
	}
	
	public boolean[] movimientos(int fila, int columna) throws EImposible{
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				if(y[j]<0 || y[i]>8) {
					throw new EImposible("Movimiento no valido");
				}
			}
		}
	}

}
