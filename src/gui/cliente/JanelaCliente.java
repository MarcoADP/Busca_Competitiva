package gui.cliente;

import controlador.cliente.ControladorCliente;
import gui.Janela;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaCliente extends Janela {
    
    private final ControladorCliente controlador;
    
    private PainelInicialCliente painelInicialCliente;
    private PainelLoadingJogadores painelLoadingJogadores;
    
    public JanelaCliente(ControladorCliente controlador) {
        this.controlador = controlador;
        configurarComponentes();
    }

    private void configurarComponentes() {
        this.setTitle("Simulador Empresa - Cliente");
        
        painelInicialCliente = new PainelInicialCliente(new AcaoBotaoJogar());
        mudarPainel(painelInicialCliente);
    }
    
    @Override
    public void novoJogo(){
        controlador.fecharCliente();
    }
    
    private void iniciarPainelLoadingJogadores(){
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
            
        }
    }
}
