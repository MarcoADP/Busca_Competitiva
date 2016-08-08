package gui.cliente;

import java.awt.event.ActionListener;

public class PainelChat extends javax.swing.JPanel {

    public PainelChat(ActionListener acaoBotaoEnviar) {
        initComponents();
        coonfigurarComponentes(acaoBotaoEnviar);
    }
    
    private void coonfigurarComponentes(ActionListener acaoBotaoEnviar){
        areaChat.setLineWrap(true);
        areaChat.setEditable(false);
        
        botaoEnviar.addActionListener(acaoBotaoEnviar);
    }
    
    public String getTexto(){
        String msg = areaMensagem.getText();
        areaMensagem.setText("");
        return msg;
    }
    
    public void appendMsg(String msg){
        areaChat.append(msg+"\n");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        botaoEnviar = new javax.swing.JButton();
        scrollPane = new javax.swing.JScrollPane();
        areaChat = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        areaMensagem = new javax.swing.JTextArea();

        botaoEnviar.setText("Enviar");

        areaChat.setColumns(20);
        areaChat.setRows(5);
        scrollPane.setViewportView(areaChat);

        areaMensagem.setColumns(20);
        areaMensagem.setRows(5);
        jScrollPane1.setViewportView(areaMensagem);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPane)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(botaoEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(botaoEnviar, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaChat;
    private javax.swing.JTextArea areaMensagem;
    private javax.swing.JButton botaoEnviar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane scrollPane;
    // End of variables declaration//GEN-END:variables
}