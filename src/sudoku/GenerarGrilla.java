
package sudoku;

import java.util.Random;
import javax.swing.JOptionPane;

// Clase de prueba para la creación de la grilla:
// Crea la grilla de números para el juego Sudoku dependiendo al nivel de dificultad ingresado por el usuario

public class GenerarGrilla {
    //public static void main(String[] args){
    public void generarGrilla(){
        int[][] grilla = new int[9][9];
        int[] arreglo = new int[9];
        int nivel=0;
        llenarArregloNumerosRandom(arreglo);
        llenarGrillaConArregloRandom(grilla, arreglo);
        cambiarOrdenColumnas(grilla);
        cambiarOrdenFilas(grilla);
        borrarNumerosAleatorios(grilla,nivel,arreglo);
        mostrarGrilla(grilla, nivel);    
    }
           
    public int[] llenarArregloNumerosRandom(int[] arreglo){
        Random random = new Random();
        int posicion = 0;
        while(posicion < arreglo.length) {
            int numeroRandom = random.nextInt(9);
            if(numeroRandom==0){
                numeroRandom=9;
            }
            boolean repetido = false;           
            while(!repetido) {
                for(int i=0; i<posicion; i++) {
                    if(numeroRandom == arreglo[i]) {
                        repetido = true;
                        break;
                    }
                }
                if(!repetido) {
                    arreglo[posicion] = numeroRandom;
                    posicion++;
                }
            }
        }
        return arreglo;
    }
        
    public int[][] llenarGrillaConArregloRandom(int[][]grilla, int[]arregloUno){
        int[] arregloDos = new int[9];
        int[] arregloTres = new int[9];
        
        for(int i=0;i<8;i++){            
            arregloDos[i]=arregloUno[i+1];
        }
        arregloDos[8]=arregloUno[0];
        
        for(int i=0;i<7;i++){            
            arregloTres[i]=arregloUno[i+2];
        }
        arregloTres[7]=arregloUno[0];
        arregloTres[8]=arregloUno[1];
        
        cambiarColumnasCadaTres(grilla, arregloUno, 0);
        cambiarColumnasCadaTres(grilla, arregloDos, 3);
        cambiarColumnasCadaTres(grilla, arregloTres, 6);
        
        return grilla;
    }
    
    public int[][] cambiarColumnasCadaTres(int[][]grilla, int[]arreglo, int fila){
        //agrega arregloUno a la primera fila de la grilla
        System.arraycopy(arreglo, 0, grilla[fila], 0, 9);
        
        for(int i=0; i<3; i++){
            //agrega intercalado arregloUno a 2da fila
            grilla[fila+1][i]=arreglo[i+3];
            grilla[fila+1][i+3]=arreglo[i+6];
            grilla[fila+1][i+6]=arreglo[i];
            
            //agrega intercalado arregloUno a 3ra fila
            grilla[fila+2][i]=arreglo[i+6];
            grilla[fila+2][i+3]=arreglo[i];
            grilla[fila+2][i+6]=arreglo[i+3];
        }
        return grilla;
    }
    
    public int[][] cambiarOrdenFilas(int[][]grilla){
        int[]arreglo=new int[9];
        
        //System.arraycopy(grilla[0], 0, arreglo, 0, 9); //copiar primer fila de la grilla en el arreglo
        //System.arraycopy(grilla[2], 0, grilla[0], 0, 9); //copiar fila 2 a fila 0 de la grilla
        //System.arraycopy(arreglo, 0, grilla[2], 0, 9); //copiar arreglo en fila 2 de la grilla
        
        System.arraycopy(grilla[0], 0, arreglo, 0, 9);
        System.arraycopy(grilla[1], 0, grilla[0], 0, 9);
        System.arraycopy(arreglo, 0, grilla[1], 0, 9);
        
        //System.arraycopy(grilla[3], 0, arreglo, 0, 9);
        //System.arraycopy(grilla[5], 0, grilla[3], 0, 9);
        //System.arraycopy(arreglo, 0, grilla[5], 0, 9);
        
        System.arraycopy(grilla[5], 0, arreglo, 0, 9);
        System.arraycopy(grilla[4], 0, grilla[5], 0, 9);
        System.arraycopy(arreglo, 0, grilla[4], 0, 9);
        
        System.arraycopy(grilla[6], 0, arreglo, 0, 9);
        System.arraycopy(grilla[8], 0, grilla[6], 0, 9);
        System.arraycopy(arreglo, 0, grilla[8], 0, 9);
        
        return grilla;
    }
    
    public int[][] cambiarOrdenColumnas(int[][]grilla){
        int[]arreglo=new int[9];
        
        for(int i=0; i<9; i++){ arreglo[i]=grilla[i][0]; } //copiar primer columna de la grilla en el arreglo
        for(int i=0; i<9; i++){ grilla[i][0]=grilla[i][2]; } //copiar columna 2 a columna 0 de la grilla
        for(int i=0; i<9; i++){ grilla[i][2]=arreglo[i]; } //copiar arreglo en columna 2 de la grilla
        
        for(int i=0; i<9; i++){ arreglo[i]=grilla[i][0]; } 
        for(int i=0; i<9; i++){ grilla[i][0]=grilla[i][1]; }
        for(int i=0; i<9; i++){ grilla[i][1]=arreglo[i]; }
        
        for(int i=0; i<9; i++){ arreglo[i]=grilla[i][3]; }
        for(int i=0; i<9; i++){ grilla[i][3]=grilla[i][5]; }
        for(int i=0; i<9; i++){ grilla[i][5]=arreglo[i]; }
        
        //for(int i=0; i<9; i++){ arreglo[i]=grilla[i][6]; }
        //for(int i=0; i<9; i++){ grilla[i][6]=grilla[i][8]; }
        //for(int i=0; i<9; i++){ grilla[i][8]=arreglo[i]; }
        
        return grilla;
    }
    
    public int[][] borrarNumerosAleatorios(int[][] grilla, int nivel, int[]arreglo){
        int response = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de la dificultad de nivel"
                +"\n1. Fácil"+"\n2. Intermedio"+"\n3. Difícil"));
        
        switch (response){
            case 1: nivel = 40; break;
            case 2: nivel = 50; break;
            case 3: nivel = 60; break;
            default: nivel = 30;
        }
        
        Random random = new Random();
        for(int i=0; i<nivel; i++){
            int n = random.nextInt(9);
            int m = random.nextInt(9);
            if(grilla[n][m] != 0){
                grilla[n][m]=0;
            } else {
                i--;
            }
        }
        return grilla;
    }
    
    public void mostrarGrilla(int[][]grilla, int nivel){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(grilla[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }  
    
}
