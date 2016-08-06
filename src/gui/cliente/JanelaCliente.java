package gui.cliente;

import controlador.cliente.ControladorCliente;
import gui.Janela;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import simulador.Simulador;

public class JanelaCliente extends Janela {
    
    private final ControladorCliente controlador;
    
    private PainelInicialCliente painelInicialCliente;
    private PainelLoadingJogadores painelLoadingJogadores;
    private PainelParametrosEmpresa painelJogadores;
    private PainelRodada painelRodada;
    
    public JanelaCliente(ControladorCliente controlador) {
        this.controlador = controlador;
        configurarComponentes();
    }

    private void configurarComponentes() {
        this.setTitle("Simulador Empresa - Cliente");
        
        painelInicialCliente = new PainelInicialCliente(new AcaoBotaoJogar());
        mudarPainel(painelInicialCliente);
    }
    
    public void habilitarBotaoContinuar() {
        painelLoadingJogadores.habilitarBotaoContinuar();
    }
    
    @Override
    public void novoJogo(){
        controlador.fecharCliente();
        super.novoJogo();
    }
    
    private void iniciarPainelLoadingJogadores() {
        painelLoadingJogadores = new PainelLoadingJogadores(new AcaoBotaoContinuar());
        mudarPainel(painelLoadingJogadores);
    }
    
    private void iniciarPainelParametrosEmpresa(){
        painelJogadores = new PainelParametrosEmpresa(controlador.getEmpresa(), controlador.getInvestimentoInicial(), new AcaoBotaoComecar());
        mudarPainel(painelJogadores);
    }
    
    private void iniciarPainelRodada(){
        painelRodada = new PainelRodada(new Simulador(), new AcaoBotaoSimular());
        mudarPainel(painelRodada);
    }
        
    private class AcaoBotaoJogar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String endereco = painelInicialCliente.getIP();
            int porta = painelInicialCliente.getPorta();
            String tipoJogador = painelInicialCliente.getTipoJogador();
            
            try {                
                controlador.iniciarCliente(endereco, porta, tipoJogador);
                
                iniciarPainelLoadingJogadores();
            } catch (Exception ex) {
                mostrarMsgErro("ERRO: Não foi possível conectar ao servidor.");
            }
        }
    }
    
    private class AcaoBotaoContinuar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            iniciarPainelParametrosEmpresa();
        }
    }
    
    private class AcaoBotaoComecar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // enviar dados para servidor
            // esperar até todos enviarem
        }
    }
    
    public class AcaoBotaoSimular implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // enviar dados para servidor
            // esperar até todos enviarem
        }
    }
    
}
