/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redflix91.vista;

/**
 *
 * @author luischdu
 */
public class seriecrear extends javax.swing.JFrame {

    /**
     * Creates new form seriecrear
     */
    public seriecrear() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_idserie = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_titulo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_temporadas = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_capitulos = new javax.swing.JTextField();
        btn_agregar = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Dialog", 3, 18)); // NOI18N
        jLabel1.setText("AGREGAR SERIE");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, -1, -1));

        txt_idserie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_idserieActionPerformed(evt);
            }
        });
        jPanel1.add(txt_idserie, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 270, -1));

        jLabel2.setText("ID SERIE:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, 20));

        jLabel3.setText("TITULO:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, 20));

        txt_titulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tituloActionPerformed(evt);
            }
        });
        jPanel1.add(txt_titulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, 250, -1));

        jLabel4.setText("TEMPORADAS:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, 20));

        txt_temporadas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_temporadasActionPerformed(evt);
            }
        });
        jPanel1.add(txt_temporadas, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 130, 240, -1));

        jLabel5.setText("CAPITULOS:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, 20));

        txt_capitulos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_capitulosActionPerformed(evt);
            }
        });
        jPanel1.add(txt_capitulos, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, 250, -1));

        btn_agregar.setText("Agregar serie");
        btn_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregarActionPerformed(evt);
            }
        });
        jPanel1.add(btn_agregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 390, 300));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_idserieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_idserieActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_idserieActionPerformed

    private void txt_tituloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tituloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tituloActionPerformed

    private void txt_temporadasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_temporadasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_temporadasActionPerformed

    private void txt_capitulosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_capitulosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_capitulosActionPerformed

    private void btn_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarActionPerformed
        // TODO add your handling code here:
        limpiarCamposParaCreacion();
    }//GEN-LAST:event_btn_agregarActionPerformed

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(seriecrear.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(seriecrear.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(seriecrear.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(seriecrear.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new seriecrear().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_agregar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txt_capitulos;
    private javax.swing.JTextField txt_idserie;
    private javax.swing.JTextField txt_temporadas;
    private javax.swing.JTextField txt_titulo;
    // End of variables declaration//GEN-END:variables

    private void limpiarCamposParaCreacion() {
        txt_idserie.setText("");
        txt_titulo.setText("");
        txt_temporadas.setText("");
        txt_capitulos.setText("");
    }
    
    
       private void crear() {
        Serie serie = new serie();
        serie.(tfNombre.getText());
        serie.setObrCosto(Long.parseLong(tfCosto.getText()));
        serie.setObrTipo(tfTipo.getText());
        serie.setExpId(Long.parseLong(tfExpId.getText()));

        Long id;
        StringBuffer respuesta = new StringBuffer();
        if(tfId.getText().equals("")){
            id = null;
            respuesta.append("Se cre?? la obra: ");
        }
        else{
            id = Long.parseLong(tfId.getText());
            respuesta.append("Se actualiz?? la obra: ");
        }
         
        obra.setObrId(id);
                
        try{
            obraRepositorio.save(obra);
            respuesta.append(obra.getObrId());
            tfId.setText(obra.getObrId().toString());
            lbEjecucion.setText(respuesta.toString());
        }
        catch(Exception e)
        {
            lbEjecucion.setText("Ocurrio un error al guardar");
        }
       
    }
}
