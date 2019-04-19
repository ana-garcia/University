package messageexchange;

public class MainMenu extends javax.swing.JFrame {

    MessageExchange app;
    SocketInit sockInit;

    /**
     * Creates new form NewJFrame
     */
    public MainMenu(MessageExchange app) {
        initComponents();
        this.app = app;
        this.sockInit = app.sockInit;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pinField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        registerNick = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        recoverNick = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("Menu"); // NOI18N

        pinField.setToolTipText("Introduza Pin");
        pinField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pinFieldActionPerformed(evt);
            }
        });

        jButton1.setText("Entrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        registerNick.setToolTipText("Introduza Nick");
        registerNick.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerNickActionPerformed(evt);
            }
        });

        jButton2.setText("Registar Utilizador");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        textArea.setEditable(false);
        textArea.setColumns(20);
        textArea.setRows(5);
        jScrollPane1.setViewportView(textArea);

        recoverNick.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recoverNickActionPerformed(evt);
            }
        });

        jButton3.setText("Recuperar Password");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(recoverNick)
                            .addComponent(registerNick)
                            .addComponent(pinField))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton3)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jButton1)
                                .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jButton1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pinField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(registerNick, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(recoverNick, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addGap(22, 22, 22)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                .addContainerGap())
        );

        pinField.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String pinF = pinField.getText();
        if (pinF.isEmpty()) {
            textArea.setText("\nPor favor introduza o seu pin para entrar.");
        } else {
            int pin = Integer.parseInt(pinF);
            if (pin < 8010) {
                textArea.append("\nPin introduzido não é válido.");
            } else {
                String nick = sockInit.getNick(pin);
                if (nick.equals("")) {
                    textArea.append("\nPin introduzido não existe.");
                } else {
                    app.changeToMsgMenu(pin, nick);
                }
            }
        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void pinFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pinFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pinFieldActionPerformed

    private void registerNickActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerNickActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_registerNickActionPerformed

    private void recoverNickActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recoverNickActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_recoverNickActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String nick = registerNick.getText();
        if (nick.isEmpty()) {
            textArea.setText("\nPor favor introduza o nome de utilizador a registar primeiro.");
        } else {
            String res = "\n" + sockInit.register(nick);
            textArea.setText(res);
        }


    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String nick = recoverNick.getText();
        if (nick.isEmpty()) {
            textArea.setText("\nPor favor introduza o nome de utilizador a recuperar primeiro.");
        } else {
            String res = "\n" + sockInit.getPin(nick);
            textArea.setText(res);
        }

    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField pinField;
    private javax.swing.JTextField recoverNick;
    private javax.swing.JTextField registerNick;
    private javax.swing.JTextArea textArea;
    // End of variables declaration//GEN-END:variables
}
