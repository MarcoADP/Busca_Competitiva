package gui.cliente;

import controlador.cliente.ControladorCliente;
import gui.Janela;
import gui.PainelJogadores;
import gui.PainelRodada;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import simulador.Simulador;

public class JanelaCliente extends Janela {
    
    private final ControladorCliente controlador;
    
    private PainelInicialCliente painelInicialCliente;
    private PainelLoadingJogadores painelLoadingJogadores;
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
    
    public void habilitarBotaoContinuar() throws MalformedURLException{
        painelLoadingJogadores.habilitarBotaoContinuar();
    }
    
    @Override
    public void novoJogo(){
        controlador.fecharCliente();
    }
    
    private void iniciarPainelLoadingJogadores() throws MalformedURLException{
        painelLoadingJogadores = new PainelLoadingJogadores(new AcaoBotaoContinuar());
        mudarPainel(painelLoadingJogadores);
    }
        
    private class AcaoBotaoJogar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String endereco = painelInicialCliente.getIP();
            int porta = painelInicialCliente.getPorta();
            String tipoJogador = painelInicialCliente.getTipoJogador();
            
            try {                
                iniciarPainelLoadingJogadores();
                controlador.iniciarCliente(endereco, porta, tipoJogador);
            } catch (Exception ex) {
                mostrarMsgErro("ERRO: Não foi possível conectar ao servidor.");
            }
        }
    }
    
    public class AcaoBotaoSimular implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            
        }
    }
    
    private class AcaoBotaoContinuar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            painelRodada = new PainelRodada(new Simulador(), new AcaoBotaoSimular());
            mudarPainel(painelRodada);
        }
    }
    
}
