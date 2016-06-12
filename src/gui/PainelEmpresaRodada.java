package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import modelos.Carro;
import modelos.Empresa;
import utilitarios.Util;

public class PainelEmpresaRodada extends javax.swing.JPanel {
    
    private final Empresa empresa;
    
    private ButtonGroup grupoMarketing;
    private ButtonGroup grupoPrecoCarro;
    private ButtonGroup grupoFuncionario;
    
    private int numFuncionarios;
    
    public PainelEmpresaRodada(Empresa empresa) {
        initComponents();
        this.empresa = empresa;
        configurarComponentes();
    }
    
    private void configurarComponentes(){
        grupoMarketing = new ButtonGroup();
        grupoPrecoCarro = new ButtonGroup();
        grupoFuncionario = new ButtonGroup();
        
        grupoMarketing.add(radioMarketingAlto);
        grupoMarketing.add(radioMarketingNormal);
        
        grupoPrecoCarro.add(radioPrecoCaro);
        grupoPrecoCarro.add(radioPrecoNormal);
        
        grupoFuncionario.add(radioFuncionarioContratar);
        grupoFuncionario.add(radioFuncionarioManter);
        grupoFuncionario.add(radioFuncionarioDemitir);
        
        configurarMarketing();
        configurarPrecoCarro();
        configurarFuncionario();
        atualizarInfo();
        
        if (empresa.isIsBot()){
            radioFuncionarioContratar.setEnabled(false);
            radioFuncionarioDemitir.setEnabled(false);
            radioFuncionarioManter.setEnabled(false);
            radioPrecoCaro.setEnabled(false);
            radioPrecoNormal.setEnabled(false);
            radioMarketingAlto.setEnabled(false);
            radioMarketingNormal.setEnabled(false);
        }
        
    }
    
    private void configurarMarketing(){
        radioMarketingAlto.setText("Alto - "+(Empresa.INVESTIMENTO_MARKETING_ALTO * 100)+"% do capital");
        radioMarketingNormal.setText("Normal - "+(Empresa.INVESTIMENTO_MARKETING_NORMAL * 100)+"% do capital");
        
        radioMarketingAlto.setActionCommand("Alto");
        radioMarketingNormal.setActionCommand("Normal");
        
        switch(empresa.getTipoMarketing()){
            case Empresa.MARKETING_ALTO:
                grupoMarketing.setSelected(radioMarketingAlto.getModel(), true);
                break;
            case Empresa.MARKETING_NORMAL:
                grupoMarketing.setSelected(radioMarketingNormal.getModel(), true);
                break;
        }
        
        radioMarketingAlto.addActionListener(new MarketingListener());
        radioMarketingNormal.addActionListener(new MarketingListener());
    }
    
    private void configurarPrecoCarro(){
        radioPrecoCaro.setText("Caro - Valor do carro + " + ((Carro.FATOR_PRECO_CARO - 1) * 100) + "%");
        radioPrecoNormal.setText("Normal - Valor do carro normal");

        radioPrecoCaro.setActionCommand("Caro");
        radioPrecoNormal.setActionCommand("Normal");
        
        switch(empresa.getCarro().getTipoPreco()){
            case Carro.TIPO_PRECO_CARO:
                grupoPrecoCarro.setSelected(radioPrecoCaro.getModel(), true);
                break;
            case Carro.TIPO_PRECO_NORMAL:
                grupoPrecoCarro.setSelected(radioPrecoNormal.getModel(), true);
                break;
        }
        radioPrecoCaro.addActionListener(new PrecoCarroListener());
        radioPrecoNormal.addActionListener(new PrecoCarroListener());
    }
    
    private void configurarFuncionario(){
        radioFuncionarioContratar.setText("Contratar "+Empresa.FATOR_FUNCIONARIO_CONTRATAR);
        radioFuncionarioDemitir.setText("Demitir "+Empresa.FATOR_FUNCIONARIO_DEMITIR);
        radioFuncionarioManter.setText("Manter");
        
        radioFuncionarioManter.setActionCommand("Manter");
        radioFuncionarioContratar.setActionCommand("Contratar");
        radioFuncionarioDemitir.setActionCommand("Demitir");
        
        grupoFuncionario.setSelected(radioFuncionarioManter.getModel(), true);
        
        radioFuncionarioContratar.addActionListener(new FuncionarioListener());
        radioFuncionarioDemitir.addActionListener(new FuncionarioListener());
        radioFuncionarioManter.addActionListener(new FuncionarioListener());
        
        numFuncionarios = 0;
        
        if (empresa.getNumeroFuncionarios() + Empresa.FATOR_FUNCIONARIO_CONTRATAR > Empresa.LIMITE_FUNCIONARIO){
            radioFuncionarioContratar.setEnabled(false);
        }
        if (empresa.getNumeroFuncionarios() - Empresa.FATOR_FUNCIONARIO_DEMITIR < 0){
            radioFuncionarioDemitir.setEnabled(false);
        }
    }
    
    private void atualizarInfo(){
        labelCapital.setText("Capital: "+Util.formatarDinheiro(empresa.getCapital()));
        labelFabrica.setText("Tipo de fábrica: "+empresa.getFabrica().getNome());
        labelGastoFabrica.setText("Gasto mensal da fábrica: "+Util.formatarDinheiro(empresa.getFabrica().getGastoPorMes()));
        labelGastoProducao.setText("Gasto mensal de produção: "+Util.formatarDinheiro(empresa.calcularGastoProducaoCarros()));
        labelGastoFuncionario.setText("Gasto total com funcionários: "+Util.formatarDinheiro(empresa.calcularGastoFuncionarios()));
        labelGastoMarketing.setText("Gasto com marketing: "+Util.formatarDinheiro(empresa.calcularInvestimentoMarketing()));
        labelEstoque.setText("Estoque: "+empresa.getEstoqueCarro()+" carros");
        labelEstoqueCapacidade.setText("Capacidade do estoque: "+empresa.getFabrica().getCapacidade()+" carros");
        labelCarroModelo.setText("Modelo do carro: "+empresa.getCarro().getStringModelo());
        labelCarroPreco.setText("Preço de venda do carro: "+Util.formatarDinheiro(empresa.getCarro().getPrecoVenda()));
        labelCarroGasto.setText("Custo de produção do carro: "+Util.formatarDinheiro(empresa.getCarro().getCusto()));
        labelCarroProduzidos.setText("Total a ser produzido: "+empresa.calcularCarrosProduzidos());
        labelFuncionario.setText("Funcionários: "+(empresa.getNumeroFuncionarios()+numFuncionarios));
        labelFuncionarioMax.setText("Máximo de funcionários: "+Empresa.LIMITE_FUNCIONARIO);
        labelFuncionarioSalario.setText("Salário de um funcionário: "+Util.formatarDinheiro(Empresa.SALARIO_FUNCIONARIO));
    }
    
    private class MarketingListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch(grupoMarketing.getSelection().getActionCommand()){
                case "Normal":
                    empresa.setTipoMarketing(Empresa.MARKETING_NORMAL);
                    break;
                case "Alto":
                    empresa.setTipoMarketing(Empresa.MARKETING_ALTO);
                    break;
            }
            atualizarInfo();
        }
    }
    
    private class PrecoCarroListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch(grupoPrecoCarro.getSelection().getActionCommand()){
                case "Normal":
                    empresa.getCarro().alterarPreco(Carro.TIPO_PRECO_NORMAL);
                    break;
                case "Caro":
                    empresa.getCarro().alterarPreco(Carro.TIPO_PRECO_CARO);
                    break;
            }
            atualizarInfo();
        }
    }
    
    private class FuncionarioListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch(grupoFuncionario.getSelection().getActionCommand()){
                case "Contratar":
                    numFuncionarios = Empresa.FATOR_FUNCIONARIO_CONTRATAR;
                    break;
                case "Demitir":
                    numFuncionarios = -Empresa.FATOR_FUNCIONARIO_DEMITIR;
                    break;
                case "Manter":
                    numFuncionarios = 0;
            }
            empresa.setFuncionariosAContratar(numFuncionarios);
            atualizarInfo();
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

        painelMarketing = new javax.swing.JPanel();
        labelInvestimento = new javax.swing.JLabel();
        radioMarketingNormal = new javax.swing.JRadioButton();
        radioMarketingAlto = new javax.swing.JRadioButton();
        painelInfo = new javax.swing.JPanel();
        labelCapital = new javax.swing.JLabel();
        labelFabrica = new javax.swing.JLabel();
        painelPrecoCarro = new javax.swing.JPanel();
        radioPrecoNormal = new javax.swing.JRadioButton();
        labelPrecoCarro = new javax.swing.JLabel();
        radioPrecoCaro = new javax.swing.JRadioButton();
        painelFuncionarios = new javax.swing.JPanel();
        labelContratarFuncionario = new javax.swing.JLabel();
        radioFuncionarioDemitir = new javax.swing.JRadioButton();
        radioFuncionarioManter = new javax.swing.JRadioButton();
        radioFuncionarioContratar = new javax.swing.JRadioButton();
        painelInfoCarro = new javax.swing.JPanel();
        labelEstoque = new javax.swing.JLabel();
        labelEstoqueCapacidade = new javax.swing.JLabel();
        labelCarroModelo = new javax.swing.JLabel();
        labelCarroPreco = new javax.swing.JLabel();
        labelCarroGasto = new javax.swing.JLabel();
        labelCarroProduzidos = new javax.swing.JLabel();
        painelInfoFuncionario = new javax.swing.JPanel();
        labelFuncionario = new javax.swing.JLabel();
        labelFuncionarioSalario = new javax.swing.JLabel();
        labelFuncionarioMax = new javax.swing.JLabel();
        painelInfoGastos = new javax.swing.JPanel();
        labelGastoFabrica = new javax.swing.JLabel();
        labelGastoProducao = new javax.swing.JLabel();
        labelGastoFuncionario = new javax.swing.JLabel();
        labelGastoMarketing = new javax.swing.JLabel();

        painelMarketing.setBorder(javax.swing.BorderFactory.createTitledBorder("Controlar investimento em marketing"));

        labelInvestimento.setText("Investimento em marketing:");

        radioMarketingNormal.setText("Normal");

        radioMarketingAlto.setText("Alto");
        radioMarketingAlto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioMarketingAltoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painelMarketingLayout = new javax.swing.GroupLayout(painelMarketing);
        painelMarketing.setLayout(painelMarketingLayout);
        painelMarketingLayout.setHorizontalGroup(
            painelMarketingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelMarketingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelInvestimento)
                .addGap(18, 18, 18)
                .addGroup(painelMarketingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radioMarketingNormal)
                    .addComponent(radioMarketingAlto))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        painelMarketingLayout.setVerticalGroup(
            painelMarketingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelMarketingLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(painelMarketingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelInvestimento)
                    .addComponent(radioMarketingNormal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(radioMarketingAlto)
                .addGap(160, 160, 160))
        );

        painelInfo.setBorder(javax.swing.BorderFactory.createTitledBorder("Geral"));

        labelCapital.setText("Capital:");

        labelFabrica.setText("Tipo de fábrica:");

        javax.swing.GroupLayout painelInfoLayout = new javax.swing.GroupLayout(painelInfo);
        painelInfo.setLayout(painelInfoLayout);
        painelInfoLayout.setHorizontalGroup(
            painelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelCapital)
                    .addComponent(labelFabrica))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        painelInfoLayout.setVerticalGroup(
            painelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelCapital)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelFabrica)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        painelPrecoCarro.setBorder(javax.swing.BorderFactory.createTitledBorder("Controlar preço do carro"));

        radioPrecoNormal.setText("Normal - Valor do carro normal");
        radioPrecoNormal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioPrecoNormalActionPerformed(evt);
            }
        });

        labelPrecoCarro.setText("Preço do carro:");

        radioPrecoCaro.setText("Caro");
        radioPrecoCaro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioPrecoCaroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painelPrecoCarroLayout = new javax.swing.GroupLayout(painelPrecoCarro);
        painelPrecoCarro.setLayout(painelPrecoCarroLayout);
        painelPrecoCarroLayout.setHorizontalGroup(
            painelPrecoCarroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPrecoCarroLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelPrecoCarro)
                .addGap(18, 18, 18)
                .addGroup(painelPrecoCarroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radioPrecoCaro)
                    .addComponent(radioPrecoNormal))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        painelPrecoCarroLayout.setVerticalGroup(
            painelPrecoCarroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPrecoCarroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelPrecoCarroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPrecoCarro)
                    .addComponent(radioPrecoNormal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(radioPrecoCaro)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        painelFuncionarios.setBorder(javax.swing.BorderFactory.createTitledBorder("Controlar funcionários"));

        labelContratarFuncionario.setText("Funcionários:");

        radioFuncionarioDemitir.setText("Demitir 10");

        radioFuncionarioManter.setText("Manter");

        radioFuncionarioContratar.setText("Contratar 10");

        javax.swing.GroupLayout painelFuncionariosLayout = new javax.swing.GroupLayout(painelFuncionarios);
        painelFuncionarios.setLayout(painelFuncionariosLayout);
        painelFuncionariosLayout.setHorizontalGroup(
            painelFuncionariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelFuncionariosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelContratarFuncionario)
                .addGap(18, 18, 18)
                .addGroup(painelFuncionariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radioFuncionarioContratar)
                    .addComponent(radioFuncionarioManter)
                    .addComponent(radioFuncionarioDemitir))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        painelFuncionariosLayout.setVerticalGroup(
            painelFuncionariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelFuncionariosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelFuncionariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelContratarFuncionario)
                    .addComponent(radioFuncionarioDemitir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(radioFuncionarioManter)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(radioFuncionarioContratar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        painelInfoCarro.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações do Carro"));

        labelEstoque.setText("Estoque:");

        labelEstoqueCapacidade.setText("Capacidade do estoque:");

        labelCarroModelo.setText("Modelo do carro:");

        labelCarroPreco.setText("Preço de venda do carro:");

        labelCarroGasto.setText("Custo de produção do carro:");

        labelCarroProduzidos.setText("Total a ser produzido:");

        javax.swing.GroupLayout painelInfoCarroLayout = new javax.swing.GroupLayout(painelInfoCarro);
        painelInfoCarro.setLayout(painelInfoCarroLayout);
        painelInfoCarroLayout.setHorizontalGroup(
            painelInfoCarroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelInfoCarroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelInfoCarroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelEstoque)
                    .addComponent(labelEstoqueCapacidade)
                    .addComponent(labelCarroModelo)
                    .addComponent(labelCarroPreco)
                    .addComponent(labelCarroGasto)
                    .addComponent(labelCarroProduzidos))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        painelInfoCarroLayout.setVerticalGroup(
            painelInfoCarroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelInfoCarroLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelEstoque)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelEstoqueCapacidade)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelCarroModelo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelCarroPreco)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelCarroGasto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelCarroProduzidos)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        painelInfoFuncionario.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações dos Funcionários"));

        labelFuncionario.setText("Funcionários: ");

        labelFuncionarioSalario.setText("Salário de um funcionário:");

        labelFuncionarioMax.setText("Máximo de funcionários:");

        javax.swing.GroupLayout painelInfoFuncionarioLayout = new javax.swing.GroupLayout(painelInfoFuncionario);
        painelInfoFuncionario.setLayout(painelInfoFuncionarioLayout);
        painelInfoFuncionarioLayout.setHorizontalGroup(
            painelInfoFuncionarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelInfoFuncionarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelInfoFuncionarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelFuncionario)
                    .addComponent(labelFuncionarioMax)
                    .addComponent(labelFuncionarioSalario))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        painelInfoFuncionarioLayout.setVerticalGroup(
            painelInfoFuncionarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelInfoFuncionarioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelFuncionario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelFuncionarioSalario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelFuncionarioMax)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        painelInfoGastos.setBorder(javax.swing.BorderFactory.createTitledBorder("Gastos"));

        labelGastoFabrica.setText("Gasto mensal da fábrica:");

        labelGastoProducao.setText("Gasto mensal de produção:");

        labelGastoFuncionario.setText("Gasto mensal com funcionários:");

        labelGastoMarketing.setText("Gasto com o marketing:");

        javax.swing.GroupLayout painelInfoGastosLayout = new javax.swing.GroupLayout(painelInfoGastos);
        painelInfoGastos.setLayout(painelInfoGastosLayout);
        painelInfoGastosLayout.setHorizontalGroup(
            painelInfoGastosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelInfoGastosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelInfoGastosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelGastoFabrica)
                    .addComponent(labelGastoProducao)
                    .addComponent(labelGastoFuncionario)
                    .addComponent(labelGastoMarketing))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        painelInfoGastosLayout.setVerticalGroup(
            painelInfoGastosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelInfoGastosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelGastoFabrica)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelGastoProducao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelGastoFuncionario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelGastoMarketing)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(painelMarketing, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(painelInfoFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(painelInfoGastos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(painelInfoCarro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(painelInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(painelPrecoCarro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(painelFuncionarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(painelMarketing, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(painelFuncionarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(painelPrecoCarro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(painelInfoCarro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(painelInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(painelInfoGastos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(painelInfoFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void radioMarketingAltoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioMarketingAltoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radioMarketingAltoActionPerformed

    private void radioPrecoCaroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioPrecoCaroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radioPrecoCaroActionPerformed

    private void radioPrecoNormalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioPrecoNormalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radioPrecoNormalActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel labelCapital;
    private javax.swing.JLabel labelCarroGasto;
    private javax.swing.JLabel labelCarroModelo;
    private javax.swing.JLabel labelCarroPreco;
    private javax.swing.JLabel labelCarroProduzidos;
    private javax.swing.JLabel labelContratarFuncionario;
    private javax.swing.JLabel labelEstoque;
    private javax.swing.JLabel labelEstoqueCapacidade;
    private javax.swing.JLabel labelFabrica;
    private javax.swing.JLabel labelFuncionario;
    private javax.swing.JLabel labelFuncionarioMax;
    private javax.swing.JLabel labelFuncionarioSalario;
    private javax.swing.JLabel labelGastoFabrica;
    private javax.swing.JLabel labelGastoFuncionario;
    private javax.swing.JLabel labelGastoMarketing;
    private javax.swing.JLabel labelGastoProducao;
    private javax.swing.JLabel labelInvestimento;
    private javax.swing.JLabel labelPrecoCarro;
    private javax.swing.JPanel painelFuncionarios;
    private javax.swing.JPanel painelInfo;
    private javax.swing.JPanel painelInfoCarro;
    private javax.swing.JPanel painelInfoFuncionario;
    private javax.swing.JPanel painelInfoGastos;
    private javax.swing.JPanel painelMarketing;
    private javax.swing.JPanel painelPrecoCarro;
    private javax.swing.JRadioButton radioFuncionarioContratar;
    private javax.swing.JRadioButton radioFuncionarioDemitir;
    private javax.swing.JRadioButton radioFuncionarioManter;
    private javax.swing.JRadioButton radioMarketingAlto;
    private javax.swing.JRadioButton radioMarketingNormal;
    private javax.swing.JRadioButton radioPrecoCaro;
    private javax.swing.JRadioButton radioPrecoNormal;
    // End of variables declaration//GEN-END:variables
}
