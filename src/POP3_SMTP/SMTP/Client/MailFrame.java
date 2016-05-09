/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POP3_SMTP.SMTP.Client;

import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author mathieu
 */
public class MailFrame extends javax.swing.JFrame {

    Client c;

    /**
     * Creates new form MailFrame
     */
    public MailFrame() {
        //c = new Client(InetAddress.getLocalHost(), 110);
        /* Timer timer = new Timer(); String point = "."; TimerTask task = new
         * TimerTask() { public void run() { switch (point) { case ".":
         * point=".."; break; case "..": point="..."; break; case "...":
         * point="."; break; default: point="."; break; } } timer.schedule(
         * task, 0L ,1000L); */
        initComponents();
        patienterLabel.setVisible(false);
        envoiButton.setEnabled(false);
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
        bienvenueLabel = new javax.swing.JLabel();
        quitButton = new javax.swing.JButton();
        expediteurField = new javax.swing.JTextField();
        destinataireField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        objetField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        envoiButton = new javax.swing.JButton();
        patienterLabel = new javax.swing.JLabel();
        testButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        ipField = new javax.swing.JTextField();
        portField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mailField.setColumns(20);
        mailField.setRows(5);
        jScrollPane1.setViewportView(mailField);

        bienvenueLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        bienvenueLabel.setText("Bienvenue");

        quitButton.setText("Quitter");
        quitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Expediteur:");

        jLabel2.setText("Destinataires:");

        jLabel3.setText("Objet:");

        jLabel4.setText("Corps du mail:");

        envoiButton.setText("Envoyer");
        envoiButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                envoiButtonActionPerformed(evt);
            }
        });

        patienterLabel.setText("Veuillez patienter...");

        testButton.setText("Tester");
        testButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testButtonActionPerformed(evt);
            }
        });

        jLabel5.setText("IP Address:");

        jLabel6.setText("port:");

        ipField.setText("127.0.0.1");

        portField.setText("25000");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(quitButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(envoiButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(patienterLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(46, 46, 46)
                                .addComponent(jLabel3))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(destinataireField, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(objetField)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(expediteurField)
                                .addGap(26, 26, 26)
                                .addComponent(testButton))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ipField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(174, 174, 174)
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(portField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(8, 8, 8)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 357, Short.MAX_VALUE)
                .addComponent(bienvenueLabel)
                .addGap(313, 313, 313))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(bienvenueLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ipField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(104, 104, 104))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(portField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(24, 24, 24)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(expediteurField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(testButton)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(destinataireField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(objetField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(patienterLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(envoiButton)
                    .addComponent(quitButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void quitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitButtonActionPerformed
        try {
            c.envoiMsg("QUIT");
            String a = c.receiveResponse();
            JOptionPane.showMessageDialog(null, "A bientot!\n" + a);
            this.dispose();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erreur : " + ex);
            Logger.getLogger(MailFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_quitButtonActionPerformed

    private void envoiButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_envoiButtonActionPerformed
        patienterLabel.setVisible(true);
        try {
            if ("".equals(destinataireField.getText())) {
                JOptionPane.showMessageDialog(null, "Veuillez remplir le champs de destinataire.");
                patienterLabel.setVisible(false);
                return;
            }
            //Début de l'envoie du mail. -------------------------------------------
            //Envoi du MAIL FROM
            c.envoiMsg("MAIL FROM:<" + expediteurField.getText() + ">"); //Envoi MAIL FROM
            String s = c.receiveResponse();
            if (!s.startsWith("250")) {
                JOptionPane.showMessageDialog(null, "Expediteur error:\n" + s);
                c.envoiMsg("RSET");
                c.receiveResponse();
                patienterLabel.setVisible(false);
                return;
            }

            //Envoi des RCPT TO
            //Si aucun rcpt to ne recoit 250 OK, la connexion est terminée
            String[] destinataires = destinataireField.getText().split("; ");
            boolean destValide = false;
            for (String dest : destinataires) {
                c.envoiMsg("RCPT TO:<" + dest + ">");
                s = c.receiveResponse();
                destValide = destValide || (s.startsWith("250")); //au moins 1 destinataire valide.
            }
            if (!destValide) {
                JOptionPane.showMessageDialog(null, "No valid expeditor\nConnexion aborted");
                c.envoiMsg("RSET");//reset
                c.receiveResponse();
                c.envoiMsg("QUIT");//Deconnexion du client
                c.receiveResponse();
                patienterLabel.setVisible(false);
                envoiButton.setEnabled(false);
                return;
            }

            //Recepteurs valides, envoi du DATA
//            String[] data = mailField.getText().split("\n");
            c.envoiMsg("DATA");
            s = c.receiveResponse();
            if (!s.startsWith("354")) {
                JOptionPane.showMessageDialog(null, "Data not valid\nConnexion aborted");
                c.envoiMsg("QUIT");//Deconnexion du client
                c.receiveResponse();
                patienterLabel.setVisible(false);
                envoiButton.setEnabled(false);
                return;
            }
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String str = "";
            str += "Date : " + dateFormat.format(date) + "\n";
            str += "From : " + expediteurField.getText() + "\n";
            str += "To : " + destinataireField.getText() + "\n";
            str += "Objet : " + objetField.getText() + "\n";
            str += mailField.getText() + "\n";
            c.envoiMsg(str + ".");
//            c.envoiMsg("Date : " + dateFormat.format(date));
//            c.envoiMsg("From : " + expediteurField.getText());
//            c.envoiMsg("To : " + destinataireField.getText());
//            c.envoiMsg("Objet : " + objetField.getText());
//            for (String line : data) {
//                c.envoiMsg(line);
//            }
//            c.envoiMsg(".\r\n");
            s = c.receiveResponse();
            if (!s.startsWith("250")) {
                JOptionPane.showMessageDialog(null, "Error during transfering of data.\nConnexion aborted");
                c.envoiMsg("QUIT");//Deconnexion du client
                c.receiveResponse();
                patienterLabel.setVisible(false);
                envoiButton.setEnabled(false);
                return;
            }
            c.envoiMsg("QUIT");
            c.receiveResponse();
            patienterLabel.setVisible(false);
            envoiButton.setEnabled(false);
            JOptionPane.showMessageDialog(null, "Mail sent.\nConnexion closed.");
        } catch (IOException ex) {
            Logger.getLogger(MailFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_envoiButtonActionPerformed

    private void testButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testButtonActionPerformed
        try {
            if("".equals(ipField.getText()) || "".equals(portField.getText())){
                JOptionPane.showMessageDialog(null, "Veuillez remplir les champs de l'IP et du port.");
                patienterLabel.setVisible(false);
                return;
            }
            c = new Client(InetAddress.getByName(ipField.getText()), parseInt(portField.getText()));
            patienterLabel.setVisible(true);
            //c = new Client(InetAddress.getByName("localhost"), 25000);
            //Init client -------------------------------------------------------------
            if ("".equals(expediteurField.getText())) {
                JOptionPane.showMessageDialog(null, "Veuillez remplir le champs d'expéditeur.");
                patienterLabel.setVisible(false);
                return;
            }
            String s = c.receiveResponse();//attendu 220 OK
            if (!s.startsWith("220")) {
                JOptionPane.showMessageDialog(null, "Connexion to server failed\n" + s);
                patienterLabel.setVisible(false);
                return;
            }
            String domaine = expediteurField.getText().split("@")[1];//Récupération du nom de domaine
            c.envoiMsg("EHLO " + domaine);//Envoi du EHLO
            s = c.receiveResponse();
            if (!s.startsWith("250")) {
                JOptionPane.showMessageDialog(null, "Connexion to server failed\n" + s);
                patienterLabel.setVisible(false);
                return;
            }
        } catch (IOException ex) {
            Logger.getLogger(MailFrame.class.getName()).log(Level.SEVERE, null, ex);
            envoiButton.setEnabled(false);
        }
        envoiButton.setEnabled(true);
        patienterLabel.setVisible(false);
    }//GEN-LAST:event_testButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
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
                new MailFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bienvenueLabel;
    private javax.swing.JTextField destinataireField;
    private javax.swing.JButton envoiButton;
    private javax.swing.JTextField expediteurField;
    private javax.swing.JTextField ipField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea mailField;
    private javax.swing.JTextField objetField;
    private javax.swing.JLabel patienterLabel;
    private javax.swing.JTextField portField;
    private javax.swing.JButton quitButton;
    private javax.swing.JButton testButton;
    // End of variables declaration//GEN-END:variables
}
