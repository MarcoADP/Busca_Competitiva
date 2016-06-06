package gui;

import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import modelos.Empresa;
import simulador.Simulador;

public class PainelRodada extends javax.swing.JPanel {

    private final Simulador simulador;
    
    public PainelRodada(Simulador simulador, ActionListener acaoSimular) {
        initComponents();
        this.simulador = simulador;
        configurarComponentes(acaoSimular);
    }
    
    private void configurarComponentes(ActionListener acaoSimular){
        JPanel painelCentro = new JPanel(new WrapLayout());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setViewportView(painelCentro);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        for (Empresa empresa : simulador.getListaJogador()) {
            painelCentro.add(new PainelEmpresaRodada(empresa));
        }
        
        for (Empresa empresa : simulador.getListaIA()) {
            painelCentro.add(new PainelEmpresaRodada(empresa));
        }
        
        botaoSimular.addActionListener(acaoSimular);
        setRodada(simulador.getRodada());
    }
    
    public void setRodada(int rodada){
        labelRodada.setText("Rodada "+rodada);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane = new javax.swing.JScrollPane();
        botaoSimular = new javax.swing.JButton();
        labelRodada = new javax.swing.JLabel();

        scrollPane.setBorder(null);

        botaoSimular.setText("Simular Rodada");
        botaoSimular.setActionCommand("SimularRodada");
        botaoSimular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSimularActionPerformed(evt);
            }
        });

        labelRodada.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelRodada.setText("Rodada");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelRodada)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botaoSimular)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(botaoSimular, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(labelRodada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void botaoSimularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSimularActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botaoSimularActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoSimular;
    private javax.swing.JLabel labelRodada;
    private javax.swing.JScrollPane scrollPane;
    // End of variables declaration//GEN-END:variables
}
