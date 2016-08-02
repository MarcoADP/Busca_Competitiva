package gui.cliente;

import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class PainelInicialCliente extends javax.swing.JPanel {

    private ButtonGroup grupoBotao;
    
    public PainelInicialCliente(ActionListener acaoBotaoJogar) {
        initComponents();
        configurarComponentes(acaoBotaoJogar);
    }  
    
    private void configurarComponentes(ActionListener acaoBotaoJogar){
        spinnerPorta.setModel(new SpinnerNumberModel(2525, 2000, 65535, 1));
        spinnerPorta.setEditor(new JSpinner.NumberEditor(spinnerPorta, "#"));
        
        grupoBotao = new ButtonGroup();
        grupoBotao.add(radioIA1);
        grupoBotao.add(radioIA2);
        grupoBotao.add(radioJogador);
        grupoBotao.setSelected(radioJogador.getModel(), true);
        radioIA1.setActionCommand("IA1");
        radioIA2.setActionCommand("IA2");
        radioJogador.setActionCommand("JOGADOR");
        
        btnJogar.addActionListener(acaoBotaoJogar);
    }
    
    public String getIP() {
        return campoIP.getText();
    }
    
    public int getPorta() {
        return (int) spinnerPorta.getValue();
    }
    
    public String getTipoJogador(){
        return grupoBotao.getSelection().getActionCommand();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnJogar = new javax.swing.JButton();
        painelConfigCliente = new javax.swing.JPanel();
        labelIP = new javax.swing.JLabel();
        campoIP = new javax.swing.JTextField();
        labelPorta = new javax.swing.JLabel();
        spinnerPorta = new javax.swing.JSpinner();
        painelEscolhaJogador = new javax.swing.JPanel();
        radioJogador = new javax.swing.JRadioButton();
        radioIA1 = new javax.swing.JRadioButton();
        radioIA2 = new javax.swing.JRadioButton();

        btnJogar.setText("Jogar");

        painelConfigCliente.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuração da Conexão"));

        labelIP.setText("IP do Servidor:");

        campoIP.setText("localhost");

        labelPorta.setText("Porta para conexão:");

        javax.swing.GroupLayout painelConfigClienteLayout = new javax.swing.GroupLayout(painelConfigCliente);
        painelConfigCliente.setLayout(painelConfigClienteLayout);
        painelConfigClienteLayout.setHorizontalGroup(
            painelConfigClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelConfigClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelConfigClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelPorta)
                    .addComponent(labelIP))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelConfigClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(campoIP)
                    .addComponent(spinnerPorta, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE))
                .addContainerGap())
        );
        painelConfigClienteLayout.setVerticalGroup(
            painelConfigClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelConfigClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelConfigClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelIP)
                    .addComponent(campoIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(painelConfigClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPorta)
                    .addComponent(spinnerPorta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        painelEscolhaJogador.setBorder(javax.swing.BorderFactory.createTitledBorder("Tipo de Jogador"));

        radioJogador.setText("Jogador");

        radioIA1.setText("Algoritmo IA 1 - MiniMax");
        radioIA1.setToolTipText("");

        radioIA2.setText("Algoritmo IA 2 - AntSystem");

        javax.swing.GroupLayout painelEscolhaJogadorLayout = new javax.swing.GroupLayout(painelEscolhaJogador);
        painelEscolhaJogador.setLayout(painelEscolhaJogadorLayout);
        painelEscolhaJogadorLayout.setHorizontalGroup(
            painelEscolhaJogadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelEscolhaJogadorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelEscolhaJogadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radioJogador)
                    .addComponent(radioIA1)
                    .addComponent(radioIA2))
                .addContainerGap(102, Short.MAX_VALUE))
        );
        painelEscolhaJogadorLayout.setVerticalGroup(
            painelEscolhaJogadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelEscolhaJogadorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(radioJogador)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(radioIA1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(radioIA2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(painelConfigCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(painelEscolhaJogador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnJogar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(painelConfigCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(painelEscolhaJogador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnJogar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnJogar;
    private javax.swing.JTextField campoIP;
    private javax.swing.JLabel labelIP;
    private javax.swing.JLabel labelPorta;
    private javax.swing.JPanel painelConfigCliente;
    private javax.swing.JPanel painelEscolhaJogador;
    private javax.swing.JRadioButton radioIA1;
    private javax.swing.JRadioButton radioIA2;
    private javax.swing.JRadioButton radioJogador;
    private javax.swing.JSpinner spinnerPorta;
    // End of variables declaration//GEN-END:variables
}
