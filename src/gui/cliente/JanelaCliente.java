package gui.cliente;

import controlador.cliente.ControladorCliente;
import gui.Janela;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaCliente extends Janela {
    
    private final ControladorCliente controlador;
    private PainelInicialCliente painelInicialCliente;
    
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
    
    private class AcaoBotaoJogar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String endereco = painelInicialCliente.getIP();
            int porta = painelInicialCliente.getPorta();
            String tipoJogador = painelInicialCliente.getTipoJogador();
            
            try {
                controlador.iniciarCliente(endereco, porta, tipoJogador);
            } catch (Exception ex) {
                mostrarMsgErro("ERRO: Não foi possível iniciar o cliente.");
            }
        }
    }
}
