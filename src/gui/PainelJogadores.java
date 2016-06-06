package gui;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import modelos.Empresa;

public class PainelJogadores extends javax.swing.JPanel {

    private final ArrayList<Empresa> listaJogadores;
    private final ArrayList<Empresa> listaIA;
    
    public PainelJogadores(ArrayList<Empresa> listaJogadores, ArrayList<Empresa> listaIA, ActionListener acaoComecar) {
        initComponents();
        this.listaJogadores = listaJogadores;
        this.listaIA = listaIA;
        configurarComponentes(acaoComecar);
    }
    
    private void configurarComponentes(ActionListener acaoComecar){
        JPanel painelCentro = new JPanel(new WrapLayout());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setViewportView(painelCentro);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        for (Empresa empresa : listaJogadores) {
            painelCentro.add(new PainelEmpresa(empresa));
        }
        
        for (Empresa empresa : listaIA) {
            painelCentro.add(new PainelEmpresa(empresa));
        }
        
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

        scrollPane = new javax.swing.JScrollPane();
        botaoComecar = new javax.swing.JButton();

        scrollPane.setBorder(null);

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
            .addComponent(scrollPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botaoComecar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoComecar;
    private javax.swing.JScrollPane scrollPane;
    // End of variables declaration//GEN-END:variables
}
