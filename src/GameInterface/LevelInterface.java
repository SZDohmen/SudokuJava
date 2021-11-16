
package GameInterface;

public class LevelInterface extends javax.swing.JFrame {
    
    public LevelInterface() {
        initComponents();
        this.setLocationRelativeTo(null); //para centrar la ventana en pantalla
    }
    
    public void elegirNivel(int nivel){
        // El método recibe el nivel según el botón clickeado ("Fácil", "Medio", "Difícil")
        // La ventana desaparece y crea una nueva instancia de juego
        // Se le settea el nivel escogido al método que se encarga de iniciar la grilla para el jugador
        // Luego vuelve visible la ventana donde se cargará la grilla
        this.setVisible(false);        
        GameInterface juego = new GameInterface();
        juego.nuevoJuego(nivel);
        juego.setVisible(true);
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Titulo = new javax.swing.JLabel();
        btnFacil = new javax.swing.JButton();
        btnMedio = new javax.swing.JButton();
        btnDificil = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SUDOKU");

        Titulo.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        Titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Titulo.setText("SUDOKU");
        Titulo.setToolTipText("");

        btnFacil.setText("Fácil");
        btnFacil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnFacilMouseClicked(evt);
            }
        });
        btnFacil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFacilActionPerformed(evt);
            }
        });

        btnMedio.setText("Medio");
        btnMedio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMedioMouseClicked(evt);
            }
        });

        btnDificil.setText("Difícil");
        btnDificil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDificilMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnDificil, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnMedio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                            .addComponent(btnFacil, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(Titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(Titulo)
                .addGap(18, 18, 18)
                .addComponent(btnFacil)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMedio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDificil)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFacilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFacilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnFacilActionPerformed

    private void btnFacilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFacilMouseClicked
        elegirNivel(1);
    }//GEN-LAST:event_btnFacilMouseClicked

    private void btnMedioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMedioMouseClicked
        elegirNivel(2);
    }//GEN-LAST:event_btnMedioMouseClicked

    private void btnDificilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDificilMouseClicked
        elegirNivel(3);
    }//GEN-LAST:event_btnDificilMouseClicked

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
            java.util.logging.Logger.getLogger(LevelInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LevelInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LevelInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LevelInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LevelInterface().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Titulo;
    private javax.swing.JButton btnDificil;
    private javax.swing.JButton btnFacil;
    private javax.swing.JButton btnMedio;
    // End of variables declaration//GEN-END:variables
}
