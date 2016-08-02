package gui.servidor;

import java.awt.event.ActionListener;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Hashtable;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import simulador.Simulador;
import utilitarios.Util;

public class PainelInicialServidor extends javax.swing.JPanel {
       
    public PainelInicialServidor(ActionListener acaoBotaoIniciar) {
        initComponents();
        configurarComponentes(acaoBotaoIniciar);
    }
    
    private void configurarComponentes(ActionListener acaoBotaoIniciar){
        spinnerJogadores.setModel(new SpinnerNumberModel(2, 2, 10, 1));
        spinnerPorta.setModel(new SpinnerNumberModel(2525, 2000, 65535, 1));
        spinnerRodadas.setModel(new SpinnerNumberModel(5, 1, 15, 1));
        
        spinnerPorta.setEditor(new JSpinner.NumberEditor(spinnerPorta, "#"));
        
        botaoIniciar.addActionListener(acaoBotaoIniciar);
        
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        String strBaixo = Util.formatarDinheiro(Simulador.INVESTIMENTO_INICIAL_BAIXO);
        String strMedio = Util.formatarDinheiro(Simulador.INVESTIMENTO_INICIAL_MEDIO);
        String strAlto = Util.formatarDinheiro(Simulador.INVESTIMENTO_INICIAL_ALTO);
        labelTable.put(Simulador.INVESTIMENTO_INICIAL_BAIXO, new JLabel("<html><center>Baixo</center></br>"+strBaixo+"</html>"));
        labelTable.put(Simulador.INVESTIMENTO_INICIAL_MEDIO, new JLabel("<html><center>Médio</center></br>"+strMedio+"</html>"));
        labelTable.put(Simulador.INVESTIMENTO_INICIAL_ALTO, new JLabel("<html><center>Alto</center></br>"+strAlto+"</html>"));
        sliderInvestimento.setLabelTable(labelTable);
        sliderInvestimento.setMaximum(Simulador.INVESTIMENTO_INICIAL_ALTO);
        sliderInvestimento.setMinimum(Simulador.INVESTIMENTO_INICIAL_BAIXO);
        sliderInvestimento.setMajorTickSpacing(Simulador.INVESTIMENTO_INICIAL_MEDIO - Simulador.INVESTIMENTO_INICIAL_BAIXO);
        sliderInvestimento.setSnapToTicks(true);
        sliderInvestimento.setPaintLabels(true);
        sliderInvestimento.setPaintTicks(true);
        
        try {
            labelIP.setText("IP deste Servidor: "+Inet4Address.getLocalHost().getHostAddress());
        } catch (UnknownHostException ex) {
            System.out.println(ex);
        }
    }
    
    public int getPorta(){
        return (int) spinnerPorta.getValue();
    }
    
    public int getNumJogadores(){
        return (int) spinnerJogadores.getValue();
    }
    
    public int getNumRodadas(){
        return (int) spinnerRodadas.getValue();
    }
    
    public int getInvestimento(){
        return (int) sliderInvestimento.getValue();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        botaoIniciar = new javax.swing.JButton();
        painelJogadores = new javax.swing.JPanel();
        numPessoas = new javax.swing.JLabel();
        spinnerJogadores = new javax.swing.JSpinner();
        painelRodadas = new javax.swing.JPanel();
        numRodadas = new javax.swing.JLabel();
        spinnerRodadas = new javax.swing.JSpinner();
        labelInvestimento = new javax.swing.JLabel();
        sliderInvestimento = new javax.swing.JSlider();
        painelConfigServidor = new javax.swing.JPanel();
        numIA = new javax.swing.JLabel();
        spinnerPorta = new javax.swing.JSpinner();
        labelIP = new javax.swing.JLabel();

        botaoIniciar.setText("Iniciar");

        painelJogadores.setBorder(javax.swing.BorderFactory.createTitledBorder("Jogadores"));

        numPessoas.setText("Número de Jogadores:");

        javax.swing.GroupLayout painelJogadoresLayout = new javax.swing.GroupLayout(painelJogadores);
        painelJogadores.setLayout(painelJogadoresLayout);
        painelJogadoresLayout.setHorizontalGroup(
            painelJogadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelJogadoresLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(numPessoas)
                .addGap(18, 18, 18)
                .addComponent(spinnerJogadores)
                .addContainerGap())
        );
        painelJogadoresLayout.setVerticalGroup(
            painelJogadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelJogadoresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelJogadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numPessoas)
                    .addComponent(spinnerJogadores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        painelRodadas.setBorder(javax.swing.BorderFactory.createTitledBorder("Parâmetros"));

        numRodadas.setText("Número de Rodadas (em meses):");

        labelInvestimento.setText("Investimento Inicial:");

        javax.swing.GroupLayout painelRodadasLayout = new javax.swing.GroupLayout(painelRodadas);
        painelRodadas.setLayout(painelRodadasLayout);
        painelRodadasLayout.setHorizontalGroup(
            painelRodadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelRodadasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelRodadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelRodadasLayout.createSequentialGroup()
                        .addComponent(numRodadas)
                        .addGap(18, 18, 18)
                        .addComponent(spinnerRodadas, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE))
                    .addGroup(painelRodadasLayout.createSequentialGroup()
                        .addComponent(labelInvestimento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(sliderInvestimento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        painelRodadasLayout.setVerticalGroup(
            painelRodadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelRodadasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelRodadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numRodadas)
                    .addComponent(spinnerRodadas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(painelRodadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(sliderInvestimento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelInvestimento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        painelConfigServidor.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuração do Servidor"));

        numIA.setText("Número da porta para conexão:");

        labelIP.setText("IP do Servidor: ");

        javax.swing.GroupLayout painelConfigServidorLayout = new javax.swing.GroupLayout(painelConfigServidor);
        painelConfigServidor.setLayout(painelConfigServidorLayout);
        painelConfigServidorLayout.setHorizontalGroup(
            painelConfigServidorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelConfigServidorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelConfigServidorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelConfigServidorLayout.createSequentialGroup()
                        .addComponent(numIA)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerPorta))
                    .addGroup(painelConfigServidorLayout.createSequentialGroup()
                        .addComponent(labelIP)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        painelConfigServidorLayout.setVerticalGroup(
            painelConfigServidorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelConfigServidorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelConfigServidorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numIA)
                    .addComponent(spinnerPorta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelIP)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(painelRodadas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(painelJogadores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(botaoIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(painelConfigServidor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(painelConfigServidor, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(painelJogadores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(painelRodadas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botaoIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoIniciar;
    private javax.swing.JLabel labelIP;
    private javax.swing.JLabel labelInvestimento;
    private javax.swing.JLabel numIA;
    private javax.swing.JLabel numPessoas;
    private javax.swing.JLabel numRodadas;
    private javax.swing.JPanel painelConfigServidor;
    private javax.swing.JPanel painelJogadores;
    private javax.swing.JPanel painelRodadas;
    private javax.swing.JSlider sliderInvestimento;
    private javax.swing.JSpinner spinnerJogadores;
    private javax.swing.JSpinner spinnerPorta;
    private javax.swing.JSpinner spinnerRodadas;
    // End of variables declaration//GEN-END:variables
}
