package gui;

import java.awt.event.ActionListener;
import java.util.Hashtable;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import servidor.simulador.Simulador;
import utilitarios.Util;

public class PainelInicial extends javax.swing.JPanel {
       
    public PainelInicial(ActionListener acaoBotaoIniciar) {
        initComponents();
        configurarComponentes(acaoBotaoIniciar);
    }
    
    private void configurarComponentes(ActionListener acaoBotaoIniciar){
        spinnerJogadores.setModel(new SpinnerNumberModel(1, 0, 5, 1));
        spinnerPorta.setModel(new SpinnerNumberModel(2525, 2000, 9999, 1));
        spinnerRodadas.setModel(new SpinnerNumberModel(5, 1, 15, 1));
        
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
    }

    public JSpinner getSpinnerIA() {
        return spinnerPorta;
    }

    public JSpinner getSpinnerPessoas() {
        return spinnerJogadores;
    }

    public JSpinner getSpinnerRodadas() {
        return spinnerRodadas;
    }

    public JSlider getSliderInvestimento() {
        return sliderInvestimento;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        botaoIniciar = new javax.swing.JButton();
        painelJogadores = new javax.swing.JPanel();
        numPessoas = new javax.swing.JLabel();
        spinnerJogadores = new javax.swing.JSpinner();
        numIA = new javax.swing.JLabel();
        spinnerPorta = new javax.swing.JSpinner();
        painelRodadas = new javax.swing.JPanel();
        numRodadas = new javax.swing.JLabel();
        spinnerRodadas = new javax.swing.JSpinner();
        labelInvestimento = new javax.swing.JLabel();
        sliderInvestimento = new javax.swing.JSlider();

        botaoIniciar.setText("Iniciar");

        painelJogadores.setBorder(javax.swing.BorderFactory.createTitledBorder("Jogadores"));

        numPessoas.setText("Número de Jogadores:");

        numIA.setText("Númerod da porta para conexão:");

        javax.swing.GroupLayout painelJogadoresLayout = new javax.swing.GroupLayout(painelJogadores);
        painelJogadores.setLayout(painelJogadoresLayout);
        painelJogadoresLayout.setHorizontalGroup(
            painelJogadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelJogadoresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelJogadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(numIA)
                    .addComponent(numPessoas))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelJogadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spinnerJogadores)
                    .addComponent(spinnerPorta))
                .addContainerGap())
        );
        painelJogadoresLayout.setVerticalGroup(
            painelJogadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelJogadoresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelJogadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numPessoas)
                    .addComponent(spinnerJogadores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelJogadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numIA)
                    .addComponent(spinnerPorta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(painelRodadas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(painelJogadores, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(botaoIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(painelJogadores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(painelRodadas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(botaoIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoIniciar;
    private javax.swing.JLabel labelInvestimento;
    private javax.swing.JLabel numIA;
    private javax.swing.JLabel numPessoas;
    private javax.swing.JLabel numRodadas;
    private javax.swing.JPanel painelJogadores;
    private javax.swing.JPanel painelRodadas;
    private javax.swing.JSlider sliderInvestimento;
    private javax.swing.JSpinner spinnerJogadores;
    private javax.swing.JSpinner spinnerPorta;
    private javax.swing.JSpinner spinnerRodadas;
    // End of variables declaration//GEN-END:variables
}
