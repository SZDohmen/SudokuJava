package sudoku;

import java.awt.event.KeyEvent;
import java.util.Random;

public final class GameInterface extends javax.swing.JFrame {
    
    int[][] grilla = new int[9][9];
    int[][] grillaResuelta = new int[9][9];
    
    public GameInterface() {        
        initComponents();
        this.setLocationRelativeTo(null); //para centrar la ventana en pantalla        
    }

    // ----- Método principal --------------------------------------------------
    public void nuevoJuego(int nivel) {
        
        int[] arreglo = new int[9];
        
        llenarArregloNumerosRandom(arreglo);
        llenarGrillaConArregloRandom(grillaResuelta, arreglo);
        cambiarOrdenFilasYColumnas(grillaResuelta, arreglo);
        
        for (int i = 0; i < 9; i++) {
            System.arraycopy(grillaResuelta[i], 0, grilla[i], 0, 9);
        }
        
        borrarNumerosAleatorios(nivel);
        cargarGrilla();
        btnResolver.setEnabled(true);
        
        System.out.println("--------SOLUCIÓN---------");        
        mostrarGrilla(grillaResuelta);
    }
    
    public int[] llenarArregloNumerosRandom(int[] array) {
        Random random = new Random();
        int posicion = 0;
        while (posicion < array.length) {
            int numeroRandom = random.nextInt(9);
            if (numeroRandom == 0) {
                numeroRandom = 9;
            }
            boolean repetido = false;
            while (!repetido) {
                for (int i = 0; i < posicion; i++) {
                    if (numeroRandom == array[i]) {
                        repetido = true;
                        break;
                    }
                }
                if (!repetido) {
                    array[posicion] = numeroRandom;
                    posicion++;
                }
            }
        }
        return array;
    }

    public int[][] llenarGrillaConArregloRandom(int[][] grid, int[] arregloUno) {
        int[] arregloDos = new int[9];
        int[] arregloTres = new int[9];

        for (int i = 0; i < 8; i++) {
            arregloDos[i] = arregloUno[i + 1];
        }
        arregloDos[8] = arregloUno[0];

        for (int i = 0; i < 7; i++) {
            arregloTres[i] = arregloUno[i + 2];
        }
        arregloTres[7] = arregloUno[0];
        arregloTres[8] = arregloUno[1];

        cambiarColumnasCadaTres(grid, arregloUno, 0);
        cambiarColumnasCadaTres(grid, arregloDos, 3);
        cambiarColumnasCadaTres(grid, arregloTres, 6);

        return grid;
    }

    public int[][] cambiarColumnasCadaTres(int[][] grid, int[] array, int fila) {
        //agrega arregloUno a la primera fila de la grilla
        System.arraycopy(array, 0, grid[fila], 0, 9);

        for (int i = 0; i < 3; i++) {
            //agrega intercalado arregloUno a 2da fila
            grid[fila + 1][i] = array[i + 3];
            grid[fila + 1][i + 3] = array[i + 6];
            grid[fila + 1][i + 6] = array[i];

            //agrega intercalado arregloUno a 3ra fila
            grid[fila + 2][i] = array[i + 6];
            grid[fila + 2][i + 3] = array[i];
            grid[fila + 2][i + 6] = array[i + 3];
        }
        return grid;
    }

    public int[][] cambiarOrdenFilasYColumnas(int[][] grid, int[] array) {

        System.arraycopy(grid[0], 0, array, 0, 9);
        System.arraycopy(grid[1], 0, grid[0], 0, 9);
        System.arraycopy(array, 0, grid[1], 0, 9);

        System.arraycopy(grid[5], 0, array, 0, 9);
        System.arraycopy(grid[4], 0, grid[5], 0, 9);
        System.arraycopy(array, 0, grid[4], 0, 9);

        System.arraycopy(grid[6], 0, array, 0, 9);
        System.arraycopy(grid[8], 0, grid[6], 0, 9);
        System.arraycopy(array, 0, grid[8], 0, 9);

        for (int i = 0; i < 9; i++) { array[i] = grid[i][0]; } //copiar primer columna de la grilla en el arreglo
        for (int i = 0; i < 9; i++) { grid[i][0] = grid[i][2]; } //copiar columna 2 a columna 0 de la grilla
        for (int i = 0; i < 9; i++) { grid[i][2] = array[i]; } //copiar arreglo en columna 2 de la grilla

        for (int i = 0; i < 9; i++) { array[i] = grid[i][0]; }
        for (int i = 0; i < 9; i++) { grid[i][0] = grid[i][1]; }
        for (int i = 0; i < 9; i++) { grid[i][1] = array[i]; }

        for (int i = 0; i < 9; i++) { array[i] = grid[i][3]; }
        for (int i = 0; i < 9; i++) { grid[i][3] = grid[i][5]; }
        for (int i = 0; i < 9; i++) { grid[i][5] = array[i]; }

        return grid;
    }

    public int[][] borrarNumerosAleatorios(int level) {             
        switch (level) {
            case 1: level = 40;
                break;
            case 2: level = 50;
                break;
            case 3: level = 60;
                break;
            default: level = 40;
        }

        Random random = new Random();
        for (int i = 0; i < level; i++) {
            int n = random.nextInt(9);
            int m = random.nextInt(9);
            if (grilla[n][m] != 0) {
                grilla[n][m] = 0;
            } else {
                i--;
            }
        }
        return grilla;
    }

    public void mostrarGrilla(int[][] grid) {        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(grid[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.println("-------------------------");
    }

    public void cargarGrilla() {        
        txtCelda11.setText(cargarCelda(this.grilla, txtCelda11, 0,0));
        txtCelda12.setText(cargarCelda(this.grilla, txtCelda12, 0,1));
        txtCelda13.setText(cargarCelda(this.grilla, txtCelda13, 0,2));
        txtCelda14.setText(cargarCelda(this.grilla, txtCelda14, 0,3));
        txtCelda15.setText(cargarCelda(this.grilla, txtCelda15, 0,4));
        txtCelda16.setText(cargarCelda(this.grilla, txtCelda16, 0,5));
        txtCelda17.setText(cargarCelda(this.grilla, txtCelda17, 0,6));
        txtCelda18.setText(cargarCelda(this.grilla, txtCelda18, 0,7));
        txtCelda19.setText(cargarCelda(this.grilla, txtCelda19, 0,8));
        //-----------------------------------------------------------
        txtCelda21.setText(cargarCelda(this.grilla, txtCelda21, 1,0));
        txtCelda22.setText(cargarCelda(this.grilla, txtCelda22, 1,1));
        txtCelda23.setText(cargarCelda(this.grilla, txtCelda23, 1,2));
        txtCelda24.setText(cargarCelda(this.grilla, txtCelda24, 1,3));
        txtCelda25.setText(cargarCelda(this.grilla, txtCelda25, 1,4));
        txtCelda26.setText(cargarCelda(this.grilla, txtCelda26, 1,5));
        txtCelda27.setText(cargarCelda(this.grilla, txtCelda27, 1,6));
        txtCelda28.setText(cargarCelda(this.grilla, txtCelda28, 1,7));
        txtCelda29.setText(cargarCelda(this.grilla, txtCelda29, 1,8));
        //-----------------------------------------------------------
        txtCelda31.setText(cargarCelda(this.grilla, txtCelda31, 2,0));
        txtCelda32.setText(cargarCelda(this.grilla, txtCelda32, 2,1));
        txtCelda33.setText(cargarCelda(this.grilla, txtCelda33, 2,2));
        txtCelda34.setText(cargarCelda(this.grilla, txtCelda34, 2,3));
        txtCelda35.setText(cargarCelda(this.grilla, txtCelda35, 2,4));
        txtCelda36.setText(cargarCelda(this.grilla, txtCelda36, 2,5));
        txtCelda37.setText(cargarCelda(this.grilla, txtCelda37, 2,6));
        txtCelda38.setText(cargarCelda(this.grilla, txtCelda38, 2,7));
        txtCelda39.setText(cargarCelda(this.grilla, txtCelda39, 2,8));
        //-----------------------------------------------------------
        txtCelda41.setText(cargarCelda(this.grilla, txtCelda41, 3,0));
        txtCelda42.setText(cargarCelda(this.grilla, txtCelda42, 3,1));
        txtCelda43.setText(cargarCelda(this.grilla, txtCelda43, 3,2));
        txtCelda44.setText(cargarCelda(this.grilla, txtCelda44, 3,3));
        txtCelda45.setText(cargarCelda(this.grilla, txtCelda45, 3,4));
        txtCelda46.setText(cargarCelda(this.grilla, txtCelda46, 3,5));
        txtCelda47.setText(cargarCelda(this.grilla, txtCelda47, 3,6));
        txtCelda48.setText(cargarCelda(this.grilla, txtCelda48, 3,7));
        txtCelda49.setText(cargarCelda(this.grilla, txtCelda49, 3,8));
        //-----------------------------------------------------------
        txtCelda51.setText(cargarCelda(this.grilla, txtCelda51, 4,0));
        txtCelda52.setText(cargarCelda(this.grilla, txtCelda52, 4,1));
        txtCelda53.setText(cargarCelda(this.grilla, txtCelda53, 4,2));
        txtCelda54.setText(cargarCelda(this.grilla, txtCelda54, 4,3));
        txtCelda55.setText(cargarCelda(this.grilla, txtCelda55, 4,4));
        txtCelda56.setText(cargarCelda(this.grilla, txtCelda56, 4,5));
        txtCelda57.setText(cargarCelda(this.grilla, txtCelda57, 4,6));
        txtCelda58.setText(cargarCelda(this.grilla, txtCelda58, 4,7));
        txtCelda59.setText(cargarCelda(this.grilla, txtCelda59, 4,8));
        //-----------------------------------------------------------
        txtCelda61.setText(cargarCelda(this.grilla, txtCelda61, 5,0));
        txtCelda62.setText(cargarCelda(this.grilla, txtCelda62, 5,1));
        txtCelda63.setText(cargarCelda(this.grilla, txtCelda63, 5,2));
        txtCelda64.setText(cargarCelda(this.grilla, txtCelda64, 5,3));
        txtCelda65.setText(cargarCelda(this.grilla, txtCelda65, 5,4));
        txtCelda66.setText(cargarCelda(this.grilla, txtCelda66, 5,5));
        txtCelda67.setText(cargarCelda(this.grilla, txtCelda67, 5,6));
        txtCelda68.setText(cargarCelda(this.grilla, txtCelda68, 5,7));
        txtCelda69.setText(cargarCelda(this.grilla, txtCelda69, 5,8));
        //-----------------------------------------------------------
        txtCelda71.setText(cargarCelda(this.grilla, txtCelda71, 6,0));
        txtCelda72.setText(cargarCelda(this.grilla, txtCelda72, 6,1));
        txtCelda73.setText(cargarCelda(this.grilla, txtCelda73, 6,2));
        txtCelda74.setText(cargarCelda(this.grilla, txtCelda74, 6,3));
        txtCelda75.setText(cargarCelda(this.grilla, txtCelda75, 6,4));
        txtCelda76.setText(cargarCelda(this.grilla, txtCelda76, 6,5));
        txtCelda77.setText(cargarCelda(this.grilla, txtCelda77, 6,6));
        txtCelda78.setText(cargarCelda(this.grilla, txtCelda78, 6,7));
        txtCelda79.setText(cargarCelda(this.grilla, txtCelda79, 6,8));
        //-----------------------------------------------------------
        txtCelda81.setText(cargarCelda(this.grilla, txtCelda81, 7,0));
        txtCelda82.setText(cargarCelda(this.grilla, txtCelda82, 7,1));
        txtCelda83.setText(cargarCelda(this.grilla, txtCelda83, 7,2));
        txtCelda84.setText(cargarCelda(this.grilla, txtCelda84, 7,3));
        txtCelda85.setText(cargarCelda(this.grilla, txtCelda85, 7,4));
        txtCelda86.setText(cargarCelda(this.grilla, txtCelda86, 7,5));
        txtCelda87.setText(cargarCelda(this.grilla, txtCelda87, 7,6));
        txtCelda88.setText(cargarCelda(this.grilla, txtCelda88, 7,7));
        txtCelda89.setText(cargarCelda(this.grilla, txtCelda89, 7,8));
        //-----------------------------------------------------------
        txtCelda91.setText(cargarCelda(this.grilla, txtCelda91, 8,0));
        txtCelda92.setText(cargarCelda(this.grilla, txtCelda92, 8,1));
        txtCelda93.setText(cargarCelda(this.grilla, txtCelda93, 8,2));
        txtCelda94.setText(cargarCelda(this.grilla, txtCelda94, 8,3));
        txtCelda95.setText(cargarCelda(this.grilla, txtCelda95, 8,4));
        txtCelda96.setText(cargarCelda(this.grilla, txtCelda96, 8,5));
        txtCelda97.setText(cargarCelda(this.grilla, txtCelda97, 8,6));
        txtCelda98.setText(cargarCelda(this.grilla, txtCelda98, 8,7));
        txtCelda99.setText(cargarCelda(this.grilla, txtCelda99, 8,8));        
    }
    
    public String cargarCelda(int[][]grid, javax.swing.JTextField celda, int i, int j){
        String variableCelda="";
        if (grilla[i][j] != 0) {
            celda.setEditable(false); //deshabilita la celda
            celda.setFont(new java.awt.Font("Tahoma", 1, 11)); //fuente en negrita
            celda.setForeground(new java.awt.Color(0, 51, 102)); //color azul
            variableCelda=Integer.toString(grilla[i][j]);
        } else {
            celda.setEditable(true);
            celda.setFont(new java.awt.Font("Tahoma", 0, 11));
            celda.setForeground(new java.awt.Color(0, 0, 0));
        }
        return variableCelda;
    }
    
    public void soloNumeros(KeyEvent evt){
        char car = evt.getKeyChar();
        if ((car < '0' || car > '9') && (car != (char) KeyEvent.VK_BACK_SPACE)) {
            evt.consume();
        }
    }
    
    public void comprobarGrilla(){
        txtCelda11.setText(comprobarCelda(this.grillaResuelta, txtCelda11, 0,0));
        txtCelda12.setText(comprobarCelda(this.grillaResuelta, txtCelda12, 0,1));
        txtCelda13.setText(comprobarCelda(this.grillaResuelta, txtCelda13, 0,2));
        txtCelda14.setText(comprobarCelda(this.grillaResuelta, txtCelda14, 0,3));
        txtCelda15.setText(comprobarCelda(this.grillaResuelta, txtCelda15, 0,4));
        txtCelda16.setText(comprobarCelda(this.grillaResuelta, txtCelda16, 0,5));
        txtCelda17.setText(comprobarCelda(this.grillaResuelta, txtCelda17, 0,6));
        txtCelda18.setText(comprobarCelda(this.grillaResuelta, txtCelda18, 0,7));
        txtCelda19.setText(comprobarCelda(this.grillaResuelta, txtCelda19, 0,8));
        //-----------------------------------------------------------
        txtCelda21.setText(comprobarCelda(this.grillaResuelta, txtCelda21, 1,0));
        txtCelda22.setText(comprobarCelda(this.grillaResuelta, txtCelda22, 1,1));
        txtCelda23.setText(comprobarCelda(this.grillaResuelta, txtCelda23, 1,2));
        txtCelda24.setText(comprobarCelda(this.grillaResuelta, txtCelda24, 1,3));
        txtCelda25.setText(comprobarCelda(this.grillaResuelta, txtCelda25, 1,4));
        txtCelda26.setText(comprobarCelda(this.grillaResuelta, txtCelda26, 1,5));
        txtCelda27.setText(comprobarCelda(this.grillaResuelta, txtCelda27, 1,6));
        txtCelda28.setText(comprobarCelda(this.grillaResuelta, txtCelda28, 1,7));
        txtCelda29.setText(comprobarCelda(this.grillaResuelta, txtCelda29, 1,8));
        //-----------------------------------------------------------
        txtCelda31.setText(comprobarCelda(this.grillaResuelta, txtCelda31, 2,0));
        txtCelda32.setText(comprobarCelda(this.grillaResuelta, txtCelda32, 2,1));
        txtCelda33.setText(comprobarCelda(this.grillaResuelta, txtCelda33, 2,2));
        txtCelda34.setText(comprobarCelda(this.grillaResuelta, txtCelda34, 2,3));
        txtCelda35.setText(comprobarCelda(this.grillaResuelta, txtCelda35, 2,4));
        txtCelda36.setText(comprobarCelda(this.grillaResuelta, txtCelda36, 2,5));
        txtCelda37.setText(comprobarCelda(this.grillaResuelta, txtCelda37, 2,6));
        txtCelda38.setText(comprobarCelda(this.grillaResuelta, txtCelda38, 2,7));
        txtCelda39.setText(comprobarCelda(this.grillaResuelta, txtCelda39, 2,8));
        //-----------------------------------------------------------
        txtCelda41.setText(comprobarCelda(this.grillaResuelta, txtCelda41, 3,0));
        txtCelda42.setText(comprobarCelda(this.grillaResuelta, txtCelda42, 3,1));
        txtCelda43.setText(comprobarCelda(this.grillaResuelta, txtCelda43, 3,2));
        txtCelda44.setText(comprobarCelda(this.grillaResuelta, txtCelda44, 3,3));
        txtCelda45.setText(comprobarCelda(this.grillaResuelta, txtCelda45, 3,4));
        txtCelda46.setText(comprobarCelda(this.grillaResuelta, txtCelda46, 3,5));
        txtCelda47.setText(comprobarCelda(this.grillaResuelta, txtCelda47, 3,6));
        txtCelda48.setText(comprobarCelda(this.grillaResuelta, txtCelda48, 3,7));
        txtCelda49.setText(comprobarCelda(this.grillaResuelta, txtCelda49, 3,8));
        //-----------------------------------------------------------
        txtCelda51.setText(comprobarCelda(this.grillaResuelta, txtCelda51, 4,0));
        txtCelda52.setText(comprobarCelda(this.grillaResuelta, txtCelda52, 4,1));
        txtCelda53.setText(comprobarCelda(this.grillaResuelta, txtCelda53, 4,2));
        txtCelda54.setText(comprobarCelda(this.grillaResuelta, txtCelda54, 4,3));
        txtCelda55.setText(comprobarCelda(this.grillaResuelta, txtCelda55, 4,4));
        txtCelda56.setText(comprobarCelda(this.grillaResuelta, txtCelda56, 4,5));
        txtCelda57.setText(comprobarCelda(this.grillaResuelta, txtCelda57, 4,6));
        txtCelda58.setText(comprobarCelda(this.grillaResuelta, txtCelda58, 4,7));
        txtCelda59.setText(comprobarCelda(this.grillaResuelta, txtCelda59, 4,8));
        //-----------------------------------------------------------
        txtCelda61.setText(comprobarCelda(this.grillaResuelta, txtCelda61, 5,0));
        txtCelda62.setText(comprobarCelda(this.grillaResuelta, txtCelda62, 5,1));
        txtCelda63.setText(comprobarCelda(this.grillaResuelta, txtCelda63, 5,2));
        txtCelda64.setText(comprobarCelda(this.grillaResuelta, txtCelda64, 5,3));
        txtCelda65.setText(comprobarCelda(this.grillaResuelta, txtCelda65, 5,4));
        txtCelda66.setText(comprobarCelda(this.grillaResuelta, txtCelda66, 5,5));
        txtCelda67.setText(comprobarCelda(this.grillaResuelta, txtCelda67, 5,6));
        txtCelda68.setText(comprobarCelda(this.grillaResuelta, txtCelda68, 5,7));
        txtCelda69.setText(comprobarCelda(this.grillaResuelta, txtCelda69, 5,8));
        //-----------------------------------------------------------
        txtCelda71.setText(comprobarCelda(this.grillaResuelta, txtCelda71, 6,0));
        txtCelda72.setText(comprobarCelda(this.grillaResuelta, txtCelda72, 6,1));
        txtCelda73.setText(comprobarCelda(this.grillaResuelta, txtCelda73, 6,2));
        txtCelda74.setText(comprobarCelda(this.grillaResuelta, txtCelda74, 6,3));
        txtCelda75.setText(comprobarCelda(this.grillaResuelta, txtCelda75, 6,4));
        txtCelda76.setText(comprobarCelda(this.grillaResuelta, txtCelda76, 6,5));
        txtCelda77.setText(comprobarCelda(this.grillaResuelta, txtCelda77, 6,6));
        txtCelda78.setText(comprobarCelda(this.grillaResuelta, txtCelda78, 6,7));
        txtCelda79.setText(comprobarCelda(this.grillaResuelta, txtCelda79, 6,8));
        //-----------------------------------------------------------
        txtCelda81.setText(comprobarCelda(this.grillaResuelta, txtCelda81, 7,0));
        txtCelda82.setText(comprobarCelda(this.grillaResuelta, txtCelda82, 7,1));
        txtCelda83.setText(comprobarCelda(this.grillaResuelta, txtCelda83, 7,2));
        txtCelda84.setText(comprobarCelda(this.grillaResuelta, txtCelda84, 7,3));
        txtCelda85.setText(comprobarCelda(this.grillaResuelta, txtCelda85, 7,4));
        txtCelda86.setText(comprobarCelda(this.grillaResuelta, txtCelda86, 7,5));
        txtCelda87.setText(comprobarCelda(this.grillaResuelta, txtCelda87, 7,6));
        txtCelda88.setText(comprobarCelda(this.grillaResuelta, txtCelda88, 7,7));
        txtCelda89.setText(comprobarCelda(this.grillaResuelta, txtCelda89, 7,8));
        //-----------------------------------------------------------
        txtCelda91.setText(comprobarCelda(this.grillaResuelta, txtCelda91, 8,0));
        txtCelda92.setText(comprobarCelda(this.grillaResuelta, txtCelda92, 8,1));
        txtCelda93.setText(comprobarCelda(this.grillaResuelta, txtCelda93, 8,2));
        txtCelda94.setText(comprobarCelda(this.grillaResuelta, txtCelda94, 8,3));
        txtCelda95.setText(comprobarCelda(this.grillaResuelta, txtCelda95, 8,4));
        txtCelda96.setText(comprobarCelda(this.grillaResuelta, txtCelda96, 8,5));
        txtCelda97.setText(comprobarCelda(this.grillaResuelta, txtCelda97, 8,6));
        txtCelda98.setText(comprobarCelda(this.grillaResuelta, txtCelda98, 8,7));
        txtCelda99.setText(comprobarCelda(this.grillaResuelta, txtCelda99, 8,8));
    }
        
    public String comprobarCelda(int[][]grillaResuleta, javax.swing.JTextField celda, int i, int j){
        String variableCelda="";
        // Debido a que el método recarga la grilla una vez clickeado el botón "Resolver", la validación
        // se hace sobre los números ingresados por el usuario en los text field de la interfaz gráfica 
        // y no los de la grilla generada al iniciar el juego
        if(!celda.getText().equals(Integer.toString(grilla[i][j]))){
            // Entrado al if se ha validado que no se está trabajando sobre los números deshabilitados de
            // la grilla
            
            // Si el número ingresado en la celda es correcto
            if(celda.getText().equals(Integer.toString(grillaResuelta[i][j]))){
                celda.setForeground(new java.awt.Color(102, 102, 0)); //color verde
                celda.setFont(new java.awt.Font("Tahoma", 1, 11)); //negrita
                variableCelda = Integer.toString(grillaResuelta[i][j]);
            
            // Si el número ingresado en la celda es incorrecto
            } else if (!celda.getText().equals(Integer.toString(grillaResuelta[i][j]))){
                celda.setText(Integer.toString(grillaResuelta[i][j]));
                celda.setForeground(new java.awt.Color(204, 0, 0)); //color rojo
                celda.setFont(new java.awt.Font("Tahoma", 1, 11));
                variableCelda = Integer.toString(grillaResuelta[i][j]);
            
            // Si la celda se encuentra vacía
            } else if(celda.getText().isEmpty()) {  //celda.getText()==""
                celda.setText(Integer.toString(grillaResuelta[i][j]));
                celda.setFont(new java.awt.Font("Tahoma", 1, 11));
                variableCelda = Integer.toString(grillaResuelta[i][j]);
            }
        } else {
            variableCelda = Integer.toString(grilla[i][j]);
        }
        celda.setEditable(false); // se deshabilitan todas las celdas para que el usuario no las modifique
        return variableCelda;
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnNuevoJuego = new javax.swing.JButton();
        txtCelda11 = new javax.swing.JTextField();
        txtCelda12 = new javax.swing.JTextField();
        txtCelda13 = new javax.swing.JTextField();
        txtCelda14 = new javax.swing.JTextField();
        txtCelda15 = new javax.swing.JTextField();
        txtCelda16 = new javax.swing.JTextField();
        txtCelda17 = new javax.swing.JTextField();
        txtCelda18 = new javax.swing.JTextField();
        txtCelda19 = new javax.swing.JTextField();
        txtCelda21 = new javax.swing.JTextField();
        txtCelda22 = new javax.swing.JTextField();
        txtCelda23 = new javax.swing.JTextField();
        txtCelda24 = new javax.swing.JTextField();
        txtCelda25 = new javax.swing.JTextField();
        txtCelda26 = new javax.swing.JTextField();
        txtCelda27 = new javax.swing.JTextField();
        txtCelda28 = new javax.swing.JTextField();
        txtCelda29 = new javax.swing.JTextField();
        txtCelda31 = new javax.swing.JTextField();
        txtCelda32 = new javax.swing.JTextField();
        txtCelda33 = new javax.swing.JTextField();
        txtCelda34 = new javax.swing.JTextField();
        txtCelda35 = new javax.swing.JTextField();
        txtCelda36 = new javax.swing.JTextField();
        txtCelda37 = new javax.swing.JTextField();
        txtCelda38 = new javax.swing.JTextField();
        txtCelda39 = new javax.swing.JTextField();
        txtCelda41 = new javax.swing.JTextField();
        txtCelda42 = new javax.swing.JTextField();
        txtCelda43 = new javax.swing.JTextField();
        txtCelda44 = new javax.swing.JTextField();
        txtCelda45 = new javax.swing.JTextField();
        txtCelda46 = new javax.swing.JTextField();
        txtCelda47 = new javax.swing.JTextField();
        txtCelda48 = new javax.swing.JTextField();
        txtCelda49 = new javax.swing.JTextField();
        txtCelda51 = new javax.swing.JTextField();
        txtCelda52 = new javax.swing.JTextField();
        txtCelda53 = new javax.swing.JTextField();
        txtCelda54 = new javax.swing.JTextField();
        txtCelda55 = new javax.swing.JTextField();
        txtCelda56 = new javax.swing.JTextField();
        txtCelda57 = new javax.swing.JTextField();
        txtCelda58 = new javax.swing.JTextField();
        txtCelda59 = new javax.swing.JTextField();
        txtCelda61 = new javax.swing.JTextField();
        txtCelda62 = new javax.swing.JTextField();
        txtCelda63 = new javax.swing.JTextField();
        txtCelda64 = new javax.swing.JTextField();
        txtCelda65 = new javax.swing.JTextField();
        txtCelda66 = new javax.swing.JTextField();
        txtCelda67 = new javax.swing.JTextField();
        txtCelda68 = new javax.swing.JTextField();
        txtCelda69 = new javax.swing.JTextField();
        txtCelda71 = new javax.swing.JTextField();
        txtCelda72 = new javax.swing.JTextField();
        txtCelda73 = new javax.swing.JTextField();
        txtCelda74 = new javax.swing.JTextField();
        txtCelda75 = new javax.swing.JTextField();
        txtCelda76 = new javax.swing.JTextField();
        txtCelda77 = new javax.swing.JTextField();
        txtCelda78 = new javax.swing.JTextField();
        txtCelda79 = new javax.swing.JTextField();
        txtCelda81 = new javax.swing.JTextField();
        txtCelda82 = new javax.swing.JTextField();
        txtCelda83 = new javax.swing.JTextField();
        txtCelda84 = new javax.swing.JTextField();
        txtCelda85 = new javax.swing.JTextField();
        txtCelda86 = new javax.swing.JTextField();
        txtCelda87 = new javax.swing.JTextField();
        txtCelda88 = new javax.swing.JTextField();
        txtCelda89 = new javax.swing.JTextField();
        txtCelda91 = new javax.swing.JTextField();
        txtCelda92 = new javax.swing.JTextField();
        txtCelda93 = new javax.swing.JTextField();
        txtCelda94 = new javax.swing.JTextField();
        txtCelda95 = new javax.swing.JTextField();
        txtCelda96 = new javax.swing.JTextField();
        txtCelda97 = new javax.swing.JTextField();
        txtCelda98 = new javax.swing.JTextField();
        txtCelda99 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnResolver = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SUDOKU");

        btnNuevoJuego.setText("Nuevo Juego");
        btnNuevoJuego.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNuevoJuegoMouseClicked(evt);
            }
        });
        btnNuevoJuego.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoJuegoActionPerformed(evt);
            }
        });

        txtCelda11.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda11.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda11.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda11ActionPerformed(evt);
            }
        });
        txtCelda11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda11KeyTyped(evt);
            }
        });

        txtCelda12.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda12.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda12.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda12KeyTyped(evt);
            }
        });

        txtCelda13.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda13.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda13.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda13KeyTyped(evt);
            }
        });

        txtCelda14.setBackground(new java.awt.Color(204, 204, 255));
        txtCelda14.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda14.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda14KeyTyped(evt);
            }
        });

        txtCelda15.setBackground(new java.awt.Color(204, 204, 255));
        txtCelda15.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda15.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda15KeyTyped(evt);
            }
        });

        txtCelda16.setBackground(new java.awt.Color(204, 204, 255));
        txtCelda16.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda16.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda16.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda16KeyTyped(evt);
            }
        });

        txtCelda17.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda17.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda17.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda17.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda17KeyTyped(evt);
            }
        });

        txtCelda18.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda18.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda18.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda18.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda18KeyTyped(evt);
            }
        });

        txtCelda19.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda19.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda19.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda19.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda19KeyTyped(evt);
            }
        });

        txtCelda21.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda21.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda21.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda21ActionPerformed(evt);
            }
        });
        txtCelda21.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda21KeyTyped(evt);
            }
        });

        txtCelda22.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda22.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda22.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda22ActionPerformed(evt);
            }
        });
        txtCelda22.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda22KeyTyped(evt);
            }
        });

        txtCelda23.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda23.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda23.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda23ActionPerformed(evt);
            }
        });
        txtCelda23.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda23KeyTyped(evt);
            }
        });

        txtCelda24.setBackground(new java.awt.Color(204, 204, 255));
        txtCelda24.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda24.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda24ActionPerformed(evt);
            }
        });
        txtCelda24.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda24KeyTyped(evt);
            }
        });

        txtCelda25.setBackground(new java.awt.Color(204, 204, 255));
        txtCelda25.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda25.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda25ActionPerformed(evt);
            }
        });
        txtCelda25.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda25KeyTyped(evt);
            }
        });

        txtCelda26.setBackground(new java.awt.Color(204, 204, 255));
        txtCelda26.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda26.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda26ActionPerformed(evt);
            }
        });
        txtCelda26.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda26KeyTyped(evt);
            }
        });

        txtCelda27.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda27.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda27.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda27ActionPerformed(evt);
            }
        });
        txtCelda27.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda27KeyTyped(evt);
            }
        });

        txtCelda28.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda28.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda28.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda28ActionPerformed(evt);
            }
        });
        txtCelda28.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda28KeyTyped(evt);
            }
        });

        txtCelda29.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda29.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda29.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda29ActionPerformed(evt);
            }
        });
        txtCelda29.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda29KeyTyped(evt);
            }
        });

        txtCelda31.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda31.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda31.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda31ActionPerformed(evt);
            }
        });
        txtCelda31.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda31KeyTyped(evt);
            }
        });

        txtCelda32.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda32.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda32.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda32ActionPerformed(evt);
            }
        });
        txtCelda32.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda32KeyTyped(evt);
            }
        });

        txtCelda33.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda33.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda33.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda33ActionPerformed(evt);
            }
        });
        txtCelda33.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda33KeyTyped(evt);
            }
        });

        txtCelda34.setBackground(new java.awt.Color(204, 204, 255));
        txtCelda34.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda34.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda34ActionPerformed(evt);
            }
        });
        txtCelda34.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda34KeyTyped(evt);
            }
        });

        txtCelda35.setBackground(new java.awt.Color(204, 204, 255));
        txtCelda35.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda35.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda35ActionPerformed(evt);
            }
        });
        txtCelda35.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda35KeyTyped(evt);
            }
        });

        txtCelda36.setBackground(new java.awt.Color(204, 204, 255));
        txtCelda36.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda36.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda36ActionPerformed(evt);
            }
        });
        txtCelda36.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda36KeyTyped(evt);
            }
        });

        txtCelda37.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda37.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda37.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda37ActionPerformed(evt);
            }
        });
        txtCelda37.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda37KeyTyped(evt);
            }
        });

        txtCelda38.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda38.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda38.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda38ActionPerformed(evt);
            }
        });
        txtCelda38.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda38KeyTyped(evt);
            }
        });

        txtCelda39.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda39.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda39.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda39ActionPerformed(evt);
            }
        });
        txtCelda39.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda39KeyTyped(evt);
            }
        });

        txtCelda41.setBackground(new java.awt.Color(204, 204, 255));
        txtCelda41.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda41.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda41ActionPerformed(evt);
            }
        });
        txtCelda41.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda41KeyTyped(evt);
            }
        });

        txtCelda42.setBackground(new java.awt.Color(204, 204, 255));
        txtCelda42.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda42.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda42ActionPerformed(evt);
            }
        });
        txtCelda42.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda42KeyTyped(evt);
            }
        });

        txtCelda43.setBackground(new java.awt.Color(204, 204, 255));
        txtCelda43.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda43.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda43ActionPerformed(evt);
            }
        });
        txtCelda43.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda43KeyTyped(evt);
            }
        });

        txtCelda44.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda44.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda44.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda44ActionPerformed(evt);
            }
        });
        txtCelda44.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda44KeyTyped(evt);
            }
        });

        txtCelda45.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda45.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda45.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda45ActionPerformed(evt);
            }
        });
        txtCelda45.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda45KeyTyped(evt);
            }
        });

        txtCelda46.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda46.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda46.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda46ActionPerformed(evt);
            }
        });
        txtCelda46.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda46KeyTyped(evt);
            }
        });

        txtCelda47.setBackground(new java.awt.Color(204, 204, 255));
        txtCelda47.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda47.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda47.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda47ActionPerformed(evt);
            }
        });
        txtCelda47.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda47KeyTyped(evt);
            }
        });

        txtCelda48.setBackground(new java.awt.Color(204, 204, 255));
        txtCelda48.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda48.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda48.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda48ActionPerformed(evt);
            }
        });
        txtCelda48.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda48KeyTyped(evt);
            }
        });

        txtCelda49.setBackground(new java.awt.Color(204, 204, 255));
        txtCelda49.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda49.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda49.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda49ActionPerformed(evt);
            }
        });
        txtCelda49.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda49KeyTyped(evt);
            }
        });

        txtCelda51.setBackground(new java.awt.Color(204, 204, 255));
        txtCelda51.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda51.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda51ActionPerformed(evt);
            }
        });
        txtCelda51.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda51KeyTyped(evt);
            }
        });

        txtCelda52.setBackground(new java.awt.Color(204, 204, 255));
        txtCelda52.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda52.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda52.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda52ActionPerformed(evt);
            }
        });
        txtCelda52.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda52KeyTyped(evt);
            }
        });

        txtCelda53.setBackground(new java.awt.Color(204, 204, 255));
        txtCelda53.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda53.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda53.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda53ActionPerformed(evt);
            }
        });
        txtCelda53.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda53KeyTyped(evt);
            }
        });

        txtCelda54.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda54.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda54.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda54.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda54ActionPerformed(evt);
            }
        });
        txtCelda54.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda54KeyTyped(evt);
            }
        });

        txtCelda55.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda55.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda55.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda55.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda55ActionPerformed(evt);
            }
        });
        txtCelda55.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda55KeyTyped(evt);
            }
        });

        txtCelda56.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda56.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda56.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda56.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda56ActionPerformed(evt);
            }
        });
        txtCelda56.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda56KeyTyped(evt);
            }
        });

        txtCelda57.setBackground(new java.awt.Color(204, 204, 255));
        txtCelda57.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda57.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda57.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda57ActionPerformed(evt);
            }
        });
        txtCelda57.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda57KeyTyped(evt);
            }
        });

        txtCelda58.setBackground(new java.awt.Color(204, 204, 255));
        txtCelda58.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda58.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda58.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda58ActionPerformed(evt);
            }
        });
        txtCelda58.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda58KeyTyped(evt);
            }
        });

        txtCelda59.setBackground(new java.awt.Color(204, 204, 255));
        txtCelda59.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda59.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda59.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda59ActionPerformed(evt);
            }
        });
        txtCelda59.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda59KeyTyped(evt);
            }
        });

        txtCelda61.setBackground(new java.awt.Color(204, 204, 255));
        txtCelda61.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda61.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda61.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda61ActionPerformed(evt);
            }
        });
        txtCelda61.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda61KeyTyped(evt);
            }
        });

        txtCelda62.setBackground(new java.awt.Color(204, 204, 255));
        txtCelda62.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda62.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda62.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda62ActionPerformed(evt);
            }
        });
        txtCelda62.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda62KeyTyped(evt);
            }
        });

        txtCelda63.setBackground(new java.awt.Color(204, 204, 255));
        txtCelda63.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda63.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda63.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda63ActionPerformed(evt);
            }
        });
        txtCelda63.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda63KeyTyped(evt);
            }
        });

        txtCelda64.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda64.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda64.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda64.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda64ActionPerformed(evt);
            }
        });
        txtCelda64.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda64KeyTyped(evt);
            }
        });

        txtCelda65.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda65.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda65.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda65.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda65ActionPerformed(evt);
            }
        });
        txtCelda65.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda65KeyTyped(evt);
            }
        });

        txtCelda66.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda66.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda66.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda66.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda66ActionPerformed(evt);
            }
        });
        txtCelda66.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda66KeyTyped(evt);
            }
        });

        txtCelda67.setBackground(new java.awt.Color(204, 204, 255));
        txtCelda67.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda67.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda67.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda67ActionPerformed(evt);
            }
        });
        txtCelda67.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda67KeyTyped(evt);
            }
        });

        txtCelda68.setBackground(new java.awt.Color(204, 204, 255));
        txtCelda68.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda68.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda68.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda68ActionPerformed(evt);
            }
        });
        txtCelda68.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda68KeyTyped(evt);
            }
        });

        txtCelda69.setBackground(new java.awt.Color(204, 204, 255));
        txtCelda69.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda69.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda69.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda69ActionPerformed(evt);
            }
        });
        txtCelda69.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda69KeyTyped(evt);
            }
        });

        txtCelda71.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda71.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda71.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda71.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda71ActionPerformed(evt);
            }
        });
        txtCelda71.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda71KeyTyped(evt);
            }
        });

        txtCelda72.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda72.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda72.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda72.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda72ActionPerformed(evt);
            }
        });
        txtCelda72.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda72KeyTyped(evt);
            }
        });

        txtCelda73.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda73.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda73.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda73.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda73ActionPerformed(evt);
            }
        });
        txtCelda73.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda73KeyTyped(evt);
            }
        });

        txtCelda74.setBackground(new java.awt.Color(204, 204, 255));
        txtCelda74.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda74.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda74.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda74ActionPerformed(evt);
            }
        });
        txtCelda74.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda74KeyTyped(evt);
            }
        });

        txtCelda75.setBackground(new java.awt.Color(204, 204, 255));
        txtCelda75.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda75.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda75.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda75ActionPerformed(evt);
            }
        });
        txtCelda75.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda75KeyTyped(evt);
            }
        });

        txtCelda76.setBackground(new java.awt.Color(204, 204, 255));
        txtCelda76.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda76.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda76.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda76ActionPerformed(evt);
            }
        });
        txtCelda76.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda76KeyTyped(evt);
            }
        });

        txtCelda77.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda77.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda77.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda77.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda77ActionPerformed(evt);
            }
        });
        txtCelda77.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda77KeyTyped(evt);
            }
        });

        txtCelda78.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda78.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda78.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda78.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda78ActionPerformed(evt);
            }
        });
        txtCelda78.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda78KeyTyped(evt);
            }
        });

        txtCelda79.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda79.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda79.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda79.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda79ActionPerformed(evt);
            }
        });
        txtCelda79.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda79KeyTyped(evt);
            }
        });

        txtCelda81.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda81.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda81.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda81.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda81ActionPerformed(evt);
            }
        });
        txtCelda81.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda81KeyTyped(evt);
            }
        });

        txtCelda82.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda82.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda82.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda82.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda82ActionPerformed(evt);
            }
        });
        txtCelda82.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda82KeyTyped(evt);
            }
        });

        txtCelda83.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda83.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda83.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda83.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda83ActionPerformed(evt);
            }
        });
        txtCelda83.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda83KeyTyped(evt);
            }
        });

        txtCelda84.setBackground(new java.awt.Color(204, 204, 255));
        txtCelda84.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda84.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda84.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda84ActionPerformed(evt);
            }
        });
        txtCelda84.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda84KeyTyped(evt);
            }
        });

        txtCelda85.setBackground(new java.awt.Color(204, 204, 255));
        txtCelda85.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda85.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda85.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda85ActionPerformed(evt);
            }
        });
        txtCelda85.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda85KeyTyped(evt);
            }
        });

        txtCelda86.setBackground(new java.awt.Color(204, 204, 255));
        txtCelda86.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda86.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda86.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda86ActionPerformed(evt);
            }
        });
        txtCelda86.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda86KeyTyped(evt);
            }
        });

        txtCelda87.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda87.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda87.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda87.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda87ActionPerformed(evt);
            }
        });
        txtCelda87.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda87KeyTyped(evt);
            }
        });

        txtCelda88.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda88.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda88.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda88.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda88ActionPerformed(evt);
            }
        });
        txtCelda88.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda88KeyTyped(evt);
            }
        });

        txtCelda89.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda89.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda89.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda89.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda89ActionPerformed(evt);
            }
        });
        txtCelda89.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda89KeyTyped(evt);
            }
        });

        txtCelda91.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda91.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda91.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda91.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda91ActionPerformed(evt);
            }
        });
        txtCelda91.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda91KeyTyped(evt);
            }
        });

        txtCelda92.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda92.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda92.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda92.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda92ActionPerformed(evt);
            }
        });
        txtCelda92.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda92KeyTyped(evt);
            }
        });

        txtCelda93.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda93.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda93.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda93.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda93ActionPerformed(evt);
            }
        });
        txtCelda93.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda93KeyTyped(evt);
            }
        });

        txtCelda94.setBackground(new java.awt.Color(204, 204, 255));
        txtCelda94.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda94.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda94.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda94ActionPerformed(evt);
            }
        });
        txtCelda94.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda94KeyTyped(evt);
            }
        });

        txtCelda95.setBackground(new java.awt.Color(204, 204, 255));
        txtCelda95.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda95.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda95.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda95ActionPerformed(evt);
            }
        });
        txtCelda95.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda95KeyTyped(evt);
            }
        });

        txtCelda96.setBackground(new java.awt.Color(204, 204, 255));
        txtCelda96.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda96.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda96.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda96ActionPerformed(evt);
            }
        });
        txtCelda96.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda96KeyTyped(evt);
            }
        });

        txtCelda97.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda97.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda97.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda97.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda97ActionPerformed(evt);
            }
        });
        txtCelda97.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda97KeyTyped(evt);
            }
        });

        txtCelda98.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda98.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda98.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda98.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda98ActionPerformed(evt);
            }
        });
        txtCelda98.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda98KeyTyped(evt);
            }
        });

        txtCelda99.setBackground(new java.awt.Color(255, 235, 200));
        txtCelda99.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelda99.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCelda99.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelda99ActionPerformed(evt);
            }
        });
        txtCelda99.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelda99KeyTyped(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SUDOKU");
        jLabel1.setToolTipText("");
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        btnResolver.setText("Resolver");
        btnResolver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnResolverMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(txtCelda11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(txtCelda13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(txtCelda21, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda22, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(txtCelda24, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda25, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda26, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda27, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda28, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda29, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(txtCelda31, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda32, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda33, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda34, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda35, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda36, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda37, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda38, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda39, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(txtCelda41, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda42, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda43, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda44, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda45, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda46, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda47, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda48, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda49, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(txtCelda51, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(txtCelda53, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda54, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda55, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda56, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda57, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda58, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda59, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(txtCelda61, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(txtCelda63, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda64, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda65, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda66, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda67, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda68, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(txtCelda71, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda72, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda73, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(txtCelda75, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda76, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda77, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda78, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda79, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(txtCelda81, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda82, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda83, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda84, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda85, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(txtCelda87, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda88, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda89, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(txtCelda91, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(txtCelda93, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda94, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda95, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda96, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda97, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda98, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtCelda99, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(btnNuevoJuego, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(btnResolver, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(txtCelda62, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(txtCelda23, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(203, 203, 203)
                        .addComponent(txtCelda86, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(txtCelda52, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(311, 311, 311)
                        .addComponent(txtCelda19, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(311, 311, 311)
                        .addComponent(txtCelda69, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(txtCelda12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addComponent(txtCelda74, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(275, 275, 275)
                        .addComponent(txtCelda18, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(txtCelda92, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCelda11, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda14, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda15, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda16, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda17, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCelda21, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda22, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda24, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda25, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda26, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda27, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda28, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda29, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCelda31, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda32, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda33, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda34, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda35, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda36, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda37, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda38, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda39, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCelda41, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda42, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda43, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda44, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda45, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda46, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda47, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda48, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda49, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCelda51, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda53, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda54, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda55, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda56, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda57, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda58, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda59, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCelda61, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda63, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda64, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda65, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda66, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda67, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda68, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCelda71, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda72, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda73, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda75, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda76, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda77, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda78, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda79, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCelda81, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda82, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda83, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda84, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda85, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda87, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda88, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda89, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCelda91, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda93, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda94, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda95, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda96, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda97, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda98, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelda99, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnNuevoJuego)
                            .addComponent(btnResolver)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(221, 221, 221)
                        .addComponent(txtCelda62, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(txtCelda23, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(283, 283, 283)
                        .addComponent(txtCelda86, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(190, 190, 190)
                        .addComponent(txtCelda52, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(txtCelda19, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(221, 221, 221)
                        .addComponent(txtCelda69, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(txtCelda12, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(252, 252, 252)
                        .addComponent(txtCelda74, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(txtCelda18, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(314, 314, 314)
                        .addComponent(txtCelda92, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoJuegoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoJuegoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNuevoJuegoActionPerformed

    private void txtCelda11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda11ActionPerformed

    }//GEN-LAST:event_txtCelda11ActionPerformed

    private void txtCelda21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda21ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda21ActionPerformed

    private void txtCelda22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda22ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda22ActionPerformed

    private void txtCelda23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda23ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda23ActionPerformed

    private void txtCelda24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda24ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda24ActionPerformed

    private void txtCelda25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda25ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda25ActionPerformed

    private void txtCelda26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda26ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda26ActionPerformed

    private void txtCelda27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda27ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda27ActionPerformed

    private void txtCelda28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda28ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda28ActionPerformed

    private void txtCelda29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda29ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda29ActionPerformed

    private void txtCelda31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda31ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda31ActionPerformed

    private void txtCelda32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda32ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda32ActionPerformed

    private void txtCelda33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda33ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda33ActionPerformed

    private void txtCelda34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda34ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda34ActionPerformed

    private void txtCelda35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda35ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda35ActionPerformed

    private void txtCelda36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda36ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda36ActionPerformed

    private void txtCelda37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda37ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda37ActionPerformed

    private void txtCelda38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda38ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda38ActionPerformed

    private void txtCelda39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda39ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda39ActionPerformed

    private void txtCelda41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda41ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda41ActionPerformed

    private void txtCelda42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda42ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda42ActionPerformed

    private void txtCelda43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda43ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda43ActionPerformed

    private void txtCelda44ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda44ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda44ActionPerformed

    private void txtCelda45ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda45ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda45ActionPerformed

    private void txtCelda46ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda46ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda46ActionPerformed

    private void txtCelda47ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda47ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda47ActionPerformed

    private void txtCelda48ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda48ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda48ActionPerformed

    private void txtCelda49ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda49ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda49ActionPerformed

    private void txtCelda51ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda51ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda51ActionPerformed

    private void txtCelda52ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda52ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda52ActionPerformed

    private void txtCelda53ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda53ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda53ActionPerformed

    private void txtCelda54ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda54ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda54ActionPerformed

    private void txtCelda55ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda55ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda55ActionPerformed

    private void txtCelda56ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda56ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda56ActionPerformed

    private void txtCelda57ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda57ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda57ActionPerformed

    private void txtCelda58ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda58ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda58ActionPerformed

    private void txtCelda59ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda59ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda59ActionPerformed

    private void txtCelda61ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda61ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda61ActionPerformed

    private void txtCelda62ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda62ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda62ActionPerformed

    private void txtCelda63ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda63ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda63ActionPerformed

    private void txtCelda64ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda64ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda64ActionPerformed

    private void txtCelda65ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda65ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda65ActionPerformed

    private void txtCelda66ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda66ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda66ActionPerformed

    private void txtCelda67ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda67ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda67ActionPerformed

    private void txtCelda68ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda68ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda68ActionPerformed

    private void txtCelda69ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda69ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda69ActionPerformed

    private void txtCelda71ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda71ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda71ActionPerformed

    private void txtCelda72ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda72ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda72ActionPerformed

    private void txtCelda73ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda73ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda73ActionPerformed

    private void txtCelda74ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda74ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda74ActionPerformed

    private void txtCelda75ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda75ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda75ActionPerformed

    private void txtCelda76ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda76ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda76ActionPerformed

    private void txtCelda77ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda77ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda77ActionPerformed

    private void txtCelda78ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda78ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda78ActionPerformed

    private void txtCelda79ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda79ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda79ActionPerformed

    private void txtCelda81ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda81ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda81ActionPerformed

    private void txtCelda82ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda82ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda82ActionPerformed

    private void txtCelda83ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda83ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda83ActionPerformed

    private void txtCelda84ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda84ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda84ActionPerformed

    private void txtCelda85ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda85ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda85ActionPerformed

    private void txtCelda86ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda86ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda86ActionPerformed

    private void txtCelda87ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda87ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda87ActionPerformed

    private void txtCelda88ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda88ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda88ActionPerformed

    private void txtCelda89ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda89ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda89ActionPerformed

    private void txtCelda91ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda91ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda91ActionPerformed

    private void txtCelda92ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda92ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda92ActionPerformed

    private void txtCelda93ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda93ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda93ActionPerformed

    private void txtCelda94ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda94ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda94ActionPerformed

    private void txtCelda95ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda95ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda95ActionPerformed

    private void txtCelda96ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda96ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda96ActionPerformed

    private void txtCelda97ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda97ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda97ActionPerformed

    private void txtCelda98ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda98ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda98ActionPerformed

    private void txtCelda99ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelda99ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelda99ActionPerformed

    private void btnNuevoJuegoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoJuegoMouseClicked
        // Se cierra la ventana de la grilla y se genera una nueva instancia para la selección del nivel
        // para luego aparecer la ventana de niveles
        this.setVisible(false);
        LevelInterface elegirNivel = new LevelInterface();
        elegirNivel.setVisible(true);
    }//GEN-LAST:event_btnNuevoJuegoMouseClicked

    private void txtCelda11KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda11KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda11KeyTyped

    private void txtCelda12KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda12KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda12KeyTyped

    private void txtCelda13KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda13KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda13KeyTyped

    private void txtCelda14KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda14KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda14KeyTyped

    private void txtCelda15KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda15KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda15KeyTyped

    private void txtCelda16KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda16KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda16KeyTyped

    private void txtCelda17KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda17KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda17KeyTyped

    private void txtCelda18KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda18KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda18KeyTyped

    private void txtCelda19KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda19KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda19KeyTyped

    private void txtCelda21KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda21KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda21KeyTyped

    private void txtCelda22KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda22KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda22KeyTyped

    private void txtCelda23KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda23KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda23KeyTyped

    private void txtCelda24KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda24KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda24KeyTyped

    private void txtCelda25KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda25KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda25KeyTyped

    private void txtCelda26KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda26KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda26KeyTyped

    private void txtCelda27KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda27KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda27KeyTyped

    private void txtCelda28KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda28KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda28KeyTyped

    private void txtCelda29KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda29KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda29KeyTyped

    private void txtCelda31KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda31KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda31KeyTyped

    private void txtCelda32KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda32KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda32KeyTyped

    private void txtCelda33KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda33KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda33KeyTyped

    private void txtCelda34KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda34KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda34KeyTyped

    private void txtCelda35KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda35KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda35KeyTyped

    private void txtCelda36KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda36KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda36KeyTyped

    private void txtCelda37KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda37KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda37KeyTyped

    private void txtCelda38KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda38KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda38KeyTyped

    private void txtCelda39KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda39KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda39KeyTyped

    private void txtCelda41KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda41KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda41KeyTyped

    private void txtCelda42KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda42KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda42KeyTyped

    private void txtCelda43KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda43KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda43KeyTyped

    private void txtCelda44KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda44KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda44KeyTyped

    private void txtCelda45KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda45KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda45KeyTyped

    private void txtCelda46KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda46KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda46KeyTyped

    private void txtCelda47KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda47KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda47KeyTyped

    private void txtCelda48KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda48KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda48KeyTyped

    private void txtCelda49KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda49KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda49KeyTyped

    private void txtCelda51KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda51KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda51KeyTyped

    private void txtCelda52KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda52KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda52KeyTyped

    private void txtCelda53KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda53KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda53KeyTyped

    private void txtCelda54KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda54KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda54KeyTyped

    private void txtCelda55KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda55KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda55KeyTyped

    private void txtCelda56KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda56KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda56KeyTyped

    private void txtCelda57KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda57KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda57KeyTyped

    private void txtCelda58KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda58KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda58KeyTyped

    private void txtCelda59KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda59KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda59KeyTyped

    private void txtCelda61KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda61KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda61KeyTyped

    private void txtCelda62KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda62KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda62KeyTyped

    private void txtCelda63KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda63KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda63KeyTyped

    private void txtCelda64KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda64KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda64KeyTyped

    private void txtCelda65KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda65KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda65KeyTyped

    private void txtCelda66KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda66KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda66KeyTyped

    private void txtCelda67KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda67KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda67KeyTyped

    private void txtCelda68KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda68KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda68KeyTyped

    private void txtCelda69KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda69KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda69KeyTyped

    private void txtCelda71KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda71KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda71KeyTyped

    private void txtCelda72KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda72KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda72KeyTyped

    private void txtCelda73KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda73KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda73KeyTyped

    private void txtCelda74KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda74KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda74KeyTyped

    private void txtCelda75KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda75KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda75KeyTyped

    private void txtCelda76KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda76KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda76KeyTyped

    private void txtCelda77KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda77KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda77KeyTyped

    private void txtCelda78KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda78KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda78KeyTyped

    private void txtCelda79KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda79KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda79KeyTyped

    private void txtCelda81KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda81KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda81KeyTyped

    private void txtCelda82KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda82KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda82KeyTyped

    private void txtCelda83KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda83KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda83KeyTyped

    private void txtCelda84KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda84KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda84KeyTyped

    private void txtCelda85KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda85KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda85KeyTyped

    private void txtCelda86KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda86KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda86KeyTyped

    private void txtCelda87KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda87KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda87KeyTyped

    private void txtCelda88KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda88KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda88KeyTyped

    private void txtCelda89KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda89KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda89KeyTyped

    private void txtCelda91KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda91KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda91KeyTyped

    private void txtCelda92KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda92KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda92KeyTyped

    private void txtCelda93KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda93KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda93KeyTyped

    private void txtCelda94KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda94KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda94KeyTyped

    private void txtCelda95KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda95KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda95KeyTyped

    private void txtCelda96KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda96KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda96KeyTyped

    private void txtCelda97KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda97KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda97KeyTyped

    private void txtCelda98KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda98KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda98KeyTyped

    private void txtCelda99KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelda99KeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_txtCelda99KeyTyped

    private void btnResolverMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnResolverMouseClicked
        comprobarGrilla();
        btnResolver.setEnabled(false);
    }//GEN-LAST:event_btnResolverMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GameInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameInterface().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNuevoJuego;
    private javax.swing.JButton btnResolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtCelda11;
    private javax.swing.JTextField txtCelda12;
    private javax.swing.JTextField txtCelda13;
    private javax.swing.JTextField txtCelda14;
    private javax.swing.JTextField txtCelda15;
    private javax.swing.JTextField txtCelda16;
    private javax.swing.JTextField txtCelda17;
    private javax.swing.JTextField txtCelda18;
    private javax.swing.JTextField txtCelda19;
    private javax.swing.JTextField txtCelda21;
    private javax.swing.JTextField txtCelda22;
    private javax.swing.JTextField txtCelda23;
    private javax.swing.JTextField txtCelda24;
    private javax.swing.JTextField txtCelda25;
    private javax.swing.JTextField txtCelda26;
    private javax.swing.JTextField txtCelda27;
    private javax.swing.JTextField txtCelda28;
    private javax.swing.JTextField txtCelda29;
    private javax.swing.JTextField txtCelda31;
    private javax.swing.JTextField txtCelda32;
    private javax.swing.JTextField txtCelda33;
    private javax.swing.JTextField txtCelda34;
    private javax.swing.JTextField txtCelda35;
    private javax.swing.JTextField txtCelda36;
    private javax.swing.JTextField txtCelda37;
    private javax.swing.JTextField txtCelda38;
    private javax.swing.JTextField txtCelda39;
    private javax.swing.JTextField txtCelda41;
    private javax.swing.JTextField txtCelda42;
    private javax.swing.JTextField txtCelda43;
    private javax.swing.JTextField txtCelda44;
    private javax.swing.JTextField txtCelda45;
    private javax.swing.JTextField txtCelda46;
    private javax.swing.JTextField txtCelda47;
    private javax.swing.JTextField txtCelda48;
    private javax.swing.JTextField txtCelda49;
    private javax.swing.JTextField txtCelda51;
    private javax.swing.JTextField txtCelda52;
    private javax.swing.JTextField txtCelda53;
    private javax.swing.JTextField txtCelda54;
    private javax.swing.JTextField txtCelda55;
    private javax.swing.JTextField txtCelda56;
    private javax.swing.JTextField txtCelda57;
    private javax.swing.JTextField txtCelda58;
    private javax.swing.JTextField txtCelda59;
    private javax.swing.JTextField txtCelda61;
    private javax.swing.JTextField txtCelda62;
    private javax.swing.JTextField txtCelda63;
    private javax.swing.JTextField txtCelda64;
    private javax.swing.JTextField txtCelda65;
    private javax.swing.JTextField txtCelda66;
    private javax.swing.JTextField txtCelda67;
    private javax.swing.JTextField txtCelda68;
    private javax.swing.JTextField txtCelda69;
    private javax.swing.JTextField txtCelda71;
    private javax.swing.JTextField txtCelda72;
    private javax.swing.JTextField txtCelda73;
    private javax.swing.JTextField txtCelda74;
    private javax.swing.JTextField txtCelda75;
    private javax.swing.JTextField txtCelda76;
    private javax.swing.JTextField txtCelda77;
    private javax.swing.JTextField txtCelda78;
    private javax.swing.JTextField txtCelda79;
    private javax.swing.JTextField txtCelda81;
    private javax.swing.JTextField txtCelda82;
    private javax.swing.JTextField txtCelda83;
    private javax.swing.JTextField txtCelda84;
    private javax.swing.JTextField txtCelda85;
    private javax.swing.JTextField txtCelda86;
    private javax.swing.JTextField txtCelda87;
    private javax.swing.JTextField txtCelda88;
    private javax.swing.JTextField txtCelda89;
    private javax.swing.JTextField txtCelda91;
    private javax.swing.JTextField txtCelda92;
    private javax.swing.JTextField txtCelda93;
    private javax.swing.JTextField txtCelda94;
    private javax.swing.JTextField txtCelda95;
    private javax.swing.JTextField txtCelda96;
    private javax.swing.JTextField txtCelda97;
    private javax.swing.JTextField txtCelda98;
    private javax.swing.JTextField txtCelda99;
    // End of variables declaration//GEN-END:variables
}
