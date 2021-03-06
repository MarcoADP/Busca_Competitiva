package gui.cliente;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.DefaultComboBoxModel;
import modelos.Carro;
import modelos.Empresa;
import modelos.Fabrica;
import utilitarios.Util;

public class PainelEmpresa extends javax.swing.JPanel {
    
    private final Empresa empresa;
    private final int investimento;
    
    public PainelEmpresa(Empresa empresa, int investimento) {
        initComponents();
        this.empresa = empresa;
        this.investimento = investimento;
        configurarComponentes();
    }
    
    private void configurarComponentes(){
        String[] tiposFabrica = {"Pequena", "Média", "Grande"};
        comboFabrica.setModel(new DefaultComboBoxModel(tiposFabrica));
        comboFabrica.addItemListener(new ListenerFabrica());
        
        String[] tiposCarros = {"Popular", "Médio", "Luxo"};
        comboCarro.setModel(new DefaultComboBoxModel(tiposCarros));
        comboCarro.addItemListener(new ListenerCarro());
        
        campoNomeEmpresa.setText(empresa.getNome());
        
        mudarLabelVerbaInicial(investimento);
        mudarLabelGasto();
        mudarLabelTempo();
        mudarLabelVerbaRestante();
        atualizarPainelInfoFabrica();
        atualizarPainelInfoCarro();
                
        if (empresa.isBot()){
            campoNomeEmpresa.setEnabled(false);
            comboFabrica.setEnabled(false);
            comboCarro.setEnabled(false);
            comboFabrica.setSelectedItem(empresa.getFabrica().getNome());
            comboCarro.setSelectedItem(empresa.getCarro().getStringModelo());
        }
    }
    
    public String getNomeEmpresa(){
        return campoNomeEmpresa.getText();
    }
    
    private void mudarLabelGasto(){
        double gasto = empresa.calcularGastoFixo();
        String str = Util.formatarDinheiro(gasto);
        labelGasto.setText("Gastos fixos: "+str);
    }
    
    private void mudarLabelTempo(){
        String str = String.format("%.2f dias", empresa.carrosPorDia());
        labelTempo.setText("Tempo de fabricação de um carro: "+str);
    }
    
    private void mudarLabelVerbaInicial(int valor){
        String str = Util.formatarDinheiro(valor);
        labelVerbaInicial.setText("Verba inicial: "+str);
    }
    
    private void mudarLabelVerbaRestante(){
        double valor = empresa.getCapital();
        String str = Util.formatarDinheiro(valor);
        labelVerbaRestante.setText("Verba restante: "+str);
    }
    
    private void atualizarPainelInfoFabrica(){
        labelFabricaPreco.setText("Preço: "+Util.formatarDinheiro(empresa.getFabrica().getPreco()));
        labelFabricaGasto.setText("Gasto por mês: "+Util.formatarDinheiro(empresa.getFabrica().getGastoPorMes()));
        labelFabricaCapacidade.setText("Capacidade: "+empresa.getFabrica().getCapacidade()+" carros");
        labelFabricaProducao.setText("Produz  "+empresa.getFabrica().getFatorProducao()+" carro(s) por vez");
    }
    
    private void atualizarPainelInfoCarro(){
        labelCarroPreco.setText("Preço de venda: "+Util.formatarDinheiro(empresa.getCarro().getPrecoVenda()));
        labelCarroCusto.setText("Custo de produção: "+Util.formatarDinheiro(empresa.getCarro().getCusto()));
    }
    
    class ListenerFabrica implements ItemListener{
        @Override
        public void itemStateChanged(ItemEvent event) {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                String str = (String) event.getItem();
                if (str.equals("Pequena")){
                    empresa.setFabrica(Fabrica.PEQUENA);
                } else if (str.equals("Média")){
                    empresa.setFabrica(Fabrica.MEDIA);
                } else if (str.equals("Grande")){
                    empresa.setFabrica(Fabrica.GRANDE);
                }
                mudarLabelGasto();
                mudarLabelTempo();
                mudarLabelVerbaRestante();
                atualizarPainelInfoFabrica();
            }
        }       
    }
    
    class ListenerCarro implements ItemListener{
        @Override
        public void itemStateChanged(ItemEvent event) {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                String str = (String) event.getItem();
                if (str.equals("Popular")){
                    empresa.setCarro(Carro.MODELO_POPULAR);
                } else if (str.equals("Médio")){
                    empresa.setCarro(Carro.MODELO_MEDIO);
                } else if (str.equals("Luxo")){
                    empresa.setCarro(Carro.MODELO_LUXO);
                }
                mudarLabelGasto();
                mudarLabelTempo();
                mudarLabelVerbaRestante();
                atualizarPainelInfoCarro();
            }
        }       
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        labelNomeEmpresa = new javax.swing.JLabel();
        campoNomeEmpresa = new javax.swing.JTextField();
        labelFabrica = new javax.swing.JLabel();
        comboFabrica = new javax.swing.JComboBox<>();
        labelCarro = new javax.swing.JLabel();
        comboCarro = new javax.swing.JComboBox<>();
        painelInfo = new javax.swing.JPanel();
        labelVerbaInicial = new javax.swing.JLabel();
        labelTempo = new javax.swing.JLabel();
        labelGasto = new javax.swing.JLabel();
        labelVerbaRestante = new javax.swing.JLabel();
        painelInfoFabrica = new javax.swing.JPanel();
        labelFabricaPreco = new javax.swing.JLabel();
        labelFabricaProducao = new javax.swing.JLabel();
        labelFabricaGasto = new javax.swing.JLabel();
        labelFabricaCapacidade = new javax.swing.JLabel();
        painelInfoCarro = new javax.swing.JPanel();
        labelCarroPreco = new javax.swing.JLabel();
        labelCarroCusto = new javax.swing.JLabel();

        labelNomeEmpresa.setText("Nome da Empresa:");

        campoNomeEmpresa.setText("Empresa 1");

        labelFabrica.setText("Tipo de Fábrica:");

        comboFabrica.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        labelCarro.setText("Modelo de Carro:");

        comboCarro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        painelInfo.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações"));

        labelVerbaInicial.setText("Verba inicial:");

        labelTempo.setText("Tempo de fabricação do carro:");

        labelGasto.setText("Gastos fixos:");

        labelVerbaRestante.setText("Verba restante:");

        javax.swing.GroupLayout painelInfoLayout = new javax.swing.GroupLayout(painelInfo);
        painelInfo.setLayout(painelInfoLayout);
        painelInfoLayout.setHorizontalGroup(
            painelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelVerbaInicial)
                    .addComponent(labelTempo)
                    .addComponent(labelGasto)
                    .addComponent(labelVerbaRestante))
                .addContainerGap(120, Short.MAX_VALUE))
        );
        painelInfoLayout.setVerticalGroup(
            painelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelVerbaInicial)
                .addGap(18, 18, 18)
                .addComponent(labelTempo)
                .addGap(18, 18, 18)
                .addComponent(labelGasto)
                .addGap(18, 18, 18)
                .addComponent(labelVerbaRestante)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        painelInfoFabrica.setBorder(javax.swing.BorderFactory.createTitledBorder("Info Fábrica"));

        labelFabricaPreco.setText("Preço:");

        labelFabricaProducao.setText("Produz 1 carro por vez");

        labelFabricaGasto.setText("Gasto por mês:");

        labelFabricaCapacidade.setText("Capacidade: ");

        javax.swing.GroupLayout painelInfoFabricaLayout = new javax.swing.GroupLayout(painelInfoFabrica);
        painelInfoFabrica.setLayout(painelInfoFabricaLayout);
        painelInfoFabricaLayout.setHorizontalGroup(
            painelInfoFabricaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelInfoFabricaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelInfoFabricaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelFabricaPreco)
                    .addComponent(labelFabricaGasto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(painelInfoFabricaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelFabricaProducao)
                    .addComponent(labelFabricaCapacidade))
                .addContainerGap())
        );
        painelInfoFabricaLayout.setVerticalGroup(
            painelInfoFabricaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelInfoFabricaLayout.createSequentialGroup()
                .addGroup(painelInfoFabricaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelFabricaPreco)
                    .addComponent(labelFabricaProducao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelInfoFabricaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelFabricaCapacidade)
                    .addComponent(labelFabricaGasto)))
        );

        painelInfoCarro.setBorder(javax.swing.BorderFactory.createTitledBorder("Info Carro"));

        labelCarroPreco.setText("Preço de venda:");

        labelCarroCusto.setText("Custo de produção:");

        javax.swing.GroupLayout painelInfoCarroLayout = new javax.swing.GroupLayout(painelInfoCarro);
        painelInfoCarro.setLayout(painelInfoCarroLayout);
        painelInfoCarroLayout.setHorizontalGroup(
            painelInfoCarroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelInfoCarroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelInfoCarroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelCarroPreco)
                    .addComponent(labelCarroCusto))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        painelInfoCarroLayout.setVerticalGroup(
            painelInfoCarroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelInfoCarroLayout.createSequentialGroup()
                .addComponent(labelCarroPreco)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelCarroCusto)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(painelInfoFabrica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(painelInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelNomeEmpresa)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(campoNomeEmpresa)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addComponent(painelInfoCarro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelFabrica)
                                .addGap(18, 18, 18)
                                .addComponent(comboFabrica, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelCarro)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(comboCarro, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 100, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelNomeEmpresa)
                            .addComponent(campoNomeEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelFabrica)
                    .addComponent(comboFabrica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(painelInfoFabrica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCarro)
                    .addComponent(comboCarro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(painelInfoCarro, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(painelInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField campoNomeEmpresa;
    private javax.swing.JComboBox<String> comboCarro;
    private javax.swing.JComboBox<String> comboFabrica;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel labelCarro;
    private javax.swing.JLabel labelCarroCusto;
    private javax.swing.JLabel labelCarroPreco;
    private javax.swing.JLabel labelFabrica;
    private javax.swing.JLabel labelFabricaCapacidade;
    private javax.swing.JLabel labelFabricaGasto;
    private javax.swing.JLabel labelFabricaPreco;
    private javax.swing.JLabel labelFabricaProducao;
    private javax.swing.JLabel labelGasto;
    private javax.swing.JLabel labelNomeEmpresa;
    private javax.swing.JLabel labelTempo;
    private javax.swing.JLabel labelVerbaInicial;
    private javax.swing.JLabel labelVerbaRestante;
    private javax.swing.JPanel painelInfo;
    private javax.swing.JPanel painelInfoCarro;
    private javax.swing.JPanel painelInfoFabrica;
    // End of variables declaration//GEN-END:variables
}
