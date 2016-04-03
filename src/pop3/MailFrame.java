/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pop3;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;


/**
 *
 * @author mathieu
 */
public class MailFrame extends javax.swing.JFrame {
    
    int[] listMail;//list des mails, listMail[i] = 0 -> mail i non supprimé
                   //                listMail[i] = 1 -> mail i supprimé
    Client c;
    /**
     * Creates new form MailFrame
     */
    public MailFrame(int nbMail, String user, Client client) {
        c = client;
        initComponents();
        this.bienvenueLabel.setText(this.bienvenueLabel.getText() + user);
        listMail = new int[nbMail];
        this.listMailView.clearSelection();
        DefaultListModel listModel = new DefaultListModel();
        for(int i=1; i<= nbMail; i++){
            String mail = "Mail " + i;
            listModel.addElement(mail);
        }
        this.listMailView.setModel(listModel);
        this.supprimerButton.setEnabled(false);
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
        mailField = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        bienvenueLabel = new javax.swing.JLabel();
        listPane = new javax.swing.JScrollPane();
        listMailView = new javax.swing.JList<>();
        supprimerButton = new javax.swing.JButton();
        quitButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mailField.setColumns(20);
        mailField.setRows(5);
        jScrollPane1.setViewportView(mailField);

        jLabel1.setText("Lecture mail");

        bienvenueLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        bienvenueLabel.setText("Bienvenue, ");

        listMailView.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listMailView.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listMailViewMouseClicked(evt);
            }
        });
        listPane.setViewportView(listMailView);

        supprimerButton.setText("Supprimer Element");
        supprimerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supprimerButtonActionPerformed(evt);
            }
        });

        quitButton.setText("Quitter");
        quitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(listPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(quitButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(supprimerButton)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(175, 175, 175)
                .addComponent(bienvenueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(bienvenueLabel)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(listPane, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(supprimerButton)
                            .addComponent(quitButton))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void listMailViewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listMailViewMouseClicked
        if (evt.getClickCount() >=1){
            this.supprimerButton.setEnabled(true);
        }
        if (evt.getClickCount()==2){
            try {
                int index = this.listMailView.locationToIndex(evt.getPoint()) + 1;
                this.c.envoiMsg("RETR " + index);
                String mail = c.receiveMail();
                if(mail.contains("+OK")){
                    this.mailField.setText(mail);
                }
                else{
                    this.mailField.setText("Error in reading the mail.");
                }
            } catch (IOException ex) {
                Logger.getLogger(MailFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }//GEN-LAST:event_listMailViewMouseClicked

    private void quitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitButtonActionPerformed
        
    }//GEN-LAST:event_quitButtonActionPerformed

    private void supprimerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supprimerButtonActionPerformed
        
        
    }//GEN-LAST:event_supprimerButtonActionPerformed

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
            java.util.logging.Logger.getLogger(MailFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MailFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MailFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MailFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new MailFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bienvenueLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> listMailView;
    private javax.swing.JScrollPane listPane;
    private javax.swing.JTextArea mailField;
    private javax.swing.JButton quitButton;
    private javax.swing.JButton supprimerButton;
    // End of variables declaration//GEN-END:variables
}
