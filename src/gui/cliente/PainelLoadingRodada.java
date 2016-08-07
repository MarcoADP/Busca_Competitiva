package gui.cliente;

import java.awt.event.ActionListener;
import javax.swing.ImageIcon;

public class PainelLoadingRodada extends javax.swing.JPanel {
    
    private String msg1;
    private String msg2;
        
    public PainelLoadingRodada(ActionListener acaoBotaoContinuar, String msg1, String msg2) {
        this.msg1 = msg1;
        this.msg2 = msg2;
        initComponents();
        configurarComponentes(acaoBotaoContinuar);
        
        inserirGifLoading();
        textArea.setLineWrap(true);
        textArea.setEditable(false);
    }
    
    private void configurarComponentes(ActionListener acaoBotaoContinuar){
        botaoContinuar.setEnabled(false);
        botaoContinuar.addActionListener(acaoBotaoContinuar);
        labelMsg.setText(msg1);
    }
    
    public void mostrarInfo(String msg){
        textArea.setText(msg);
    }
    
    public void habilitarBotaoContinuar() {
        botaoContinuar.setEnabled(true);
        
        labelMsg.setText(msg2);
    }
    
    public final void inserirGifLoading() {
        ImageIcon icon = new ImageIcon("images/loading.gif");
        labelGIF.setIcon(icon);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        botaoContinuar = new javax.swing.JButton();
        labelGIF = new javax.swing.JLabel();
        labelMsg = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();

        botaoContinuar.setText("Continuar");

        labelGIF.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        labelMsg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelMsg.setText("Esperando outros jogadores se conectarem...");

        textArea.setColumns(20);
        textArea.setRows(5);
        scrollPane.setViewportView(textArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPane)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(botaoContinuar))
                    .addComponent(labelGIF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelMsg, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(labelGIF)
                .addGap(32, 32, 32)
                .addComponent(labelMsg)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(botaoContinuar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoContinuar;
    private javax.swing.JLabel labelGIF;
    private javax.swing.JLabel labelMsg;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTextArea textArea;
    // End of variables declaration//GEN-END:variables
}
