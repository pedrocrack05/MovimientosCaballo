package Caballo;

public class Caballo {
    private int fila, columna;
    private int[][] posicion;

    public Caballo(int[][] posicion) {
        this.posicion = posicion;
        this.fila = 0;
        this.columna = 0;
        this.posicion = new int[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.posicion[i][j] = 0;
            }
        }
    }

    public boolean resolver(int fila, int columna) throws EImposible {
        if (fila < 0 || fila >= 8 || columna < 0 || columna >= 8) {
            throw new EImposible("La ficha no se encuentra en el tablero");
        } else if (posicion[fila][columna] == 1) {
            throw new EImposible("Ya hizo este movimiento");
        } else {
            posicion[fila][columna] = 1;
            return true;
        }
    }

    public boolean[] movimientos() throws EImposible {
        boolean[] movimientosValidos = new boolean[8];
        
        //estos son los desplazamientos posibles

        int[] dx = { -2, -1, 1, 2, -2, -1, 1, 2 };
        int[] dy = { 1, 2, 2, 1, -1, -2, -2, -1 };

        for (int i = 0; i < 8; i++) {
            int nuevaFila = fila + dx[i];
            int nuevaColumna = columna + dy[i];

            if (nuevaFila >= 0 && nuevaFila < 8 && nuevaColumna >= 0 && nuevaColumna < 8) {
                if (posicion[nuevaFila][nuevaColumna] == 0) {
                    movimientosValidos[i] = true;
                } else {
                    movimientosValidos[i] = false;
                }
            } else {
                movimientosValidos[i] = false;
            }
        }

        if (noHayMovimientosValidos(movimientosValidos)) {
            throw new EImposible("No hay movimientos válidos desde la posición actual.");
        }

        return movimientosValidos;
    }

    private boolean noHayMovimientosValidos(boolean[] movimientosValidos) {
        for (boolean valido : movimientosValidos) {
            if (valido) {
                return false;
            }
        }
        return true;
    }
}
