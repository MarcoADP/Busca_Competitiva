package gui.cliente;

import java.awt.event.ActionListener;
import modelos.Empresa;

public class PainelJogadores extends javax.swing.JPanel {
    
    private final Empresa empresa;
    
    public PainelJogadores(Empresa empresa, ActionListener acaoComecar) {
        initComponents();
        this.empresa = empresa;
        configurarComponentes(acaoComecar);
    }
    
    private void configurarComponentes(ActionListener acaoComecar){
        painelTab.add(empresa.getNome(), new PainelEmpresa(empresa, (int) empresa.getCapital()));
        
        botaoComecar.addActionListener(acaoComecar);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        botaoComecar = new javax.swing.JButton();
        painelTab = new javax.swing.JTabbedPane();

        botaoComecar.setText("Começar");
        botaoComecar.setToolTipText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(300, Short.MAX_VALUE)
                .addComponent(botaoComecar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(painelTab)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(painelTab, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botaoComecar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoComecar;
    private javax.swing.JTabbedPane painelTab;
    // End of variables declaration//GEN-END:variables
}