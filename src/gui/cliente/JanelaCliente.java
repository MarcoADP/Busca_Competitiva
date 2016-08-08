package gui.cliente;

import controlador.cliente.ControladorCliente;
import gui.Janela;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class JanelaCliente extends Janela {
    
    private final ControladorCliente controlador;
    
    private PainelInicialCliente painelInicialCliente;
    private PainelLoadingJogadores painelLoadingJogadores;
    private PainelParametrosEmpresa painelParametrosEmpresa;
    private PainelRodada painelRodada;
    private PainelLoadingRodada painelLoadingRodada;
    private PainelChat painelChat;
    
    public JanelaCliente(ControladorCliente controlador) {
        this.controlador = controlador;
        configurarComponentes();
    }

    private void configurarComponentes() {
        this.setTitle("Simulador Empresa - Cliente");
        
        painelChat = new PainelChat(new AcaoBotaoEnviarChat());
        
        painelInicialCliente = new PainelInicialCliente(new AcaoBotaoJogar());
        mudarPainel(painelInicialCliente);
    }
    
    public void habilitarBotaoContinuar() {
        //tenta habilitar até parar de dar NullPointerException
        while (true) {
            try {
                painelLoadingJogadores.habilitarBotaoContinuar();
                break;
            } catch (NullPointerException e) {}
        }
    }
    
    public void habilitarBotaoContinuarRodada(){
        while (true) {
            try {
                painelLoadingRodada.habilitarBotaoContinuar();
                break;
            } catch (NullPointerException e) {}
        }
    }
    
    public void mostrarInfoRodada(String info) {
        while (true) {
            try {
                painelLoadingRodada.mostrarInfo(info);
                break;
            } catch (NullPointerException e) {}
        }
    }
    
    @Override
    public void novoJogo(){
        controlador.fecharCliente();
        super.novoJogo();
    }
    
    public void mostrarMsgVenceu(){
        JLabel label = new JLabel("Você Venceu!");
        label.setFont(new Font("Arial", Font.BOLD, 36));
        JOptionPane.showMessageDialog(this, label, "Vencedor", JOptionPane.PLAIN_MESSAGE);
    }
    
    public void mostrarMsgPerdeu(String empresaVencedor){
        JLabel labelPerdeu = new JLabel("<html>Você Perdeu :(<br></html>");
        labelPerdeu.setFont(new Font("Arial", Font.BOLD, 36));
        
        if (empresaVencedor == null) {
            JOptionPane.showMessageDialog(this, labelPerdeu, "Perdeu", JOptionPane.PLAIN_MESSAGE);
            return;
        }
        
        JLabel labelGanhou = new JLabel("Jogador "+empresaVencedor+" ganhou.");
        labelGanhou.setFont(new Font("Arial", Font.PLAIN, 18));
        
        JLabel[] labels = {labelPerdeu, labelGanhou};
        JOptionPane.showMessageDialog(this, labels, "Perdeu", JOptionPane.PLAIN_MESSAGE);
    }
    
    private void iniciarPainelLoadingJogadores() {
        painelLoadingJogadores = new PainelLoadingJogadores(
                new AcaoBotaoContinuarParametros(), "Esperando outros jogadores se conectarem...", "Todos os jogadores conectados!");
        mudarPainel(painelLoadingJogadores);
    }
    
    private void iniciarPainelParametrosEmpresa(){
        painelLoadingJogadores = null;
        painelParametrosEmpresa = new PainelParametrosEmpresa(controlador.getEmpresa(), controlador.getInvestimentoInicial(), new AcaoBotaoComecar());
        mudarPainel(painelParametrosEmpresa);
    }
    
    private void iniciarPainelLoadingParametrosJogadores(){
        painelLoadingJogadores = new PainelLoadingJogadores(
                new AcaoBotaoContinuarRodada(), "Esperando outros jogadores...", "Todos os jogadores prontos!");
        mudarPainel(painelLoadingJogadores);
    }
    
    private void iniciarPainelRodada(){
        painelLoadingJogadores = null;
        painelLoadingRodada = null;
        painelRodada = new PainelRodada(controlador.getSimulador(), new AcaoBotaoSimular(), painelChat);
        mudarPainel(painelRodada);
    }
    
    private void iniciarPainelLoadingRodada(){
        painelLoadingRodada = new PainelLoadingRodada(
                new AcaoBotaoContinuarRodada(), "Esperando outros jogadores...", "Todos os jogadores prontos!");
        mudarPainel(painelLoadingRodada);
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
    
    private class AcaoBotaoContinuarParametros implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            iniciarPainelParametrosEmpresa();
        }
    }
    
    private class AcaoBotaoContinuarRodada implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            iniciarPainelRodada();
        }
    }
    
    private class AcaoBotaoComecar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            controlador.enviarMensagemParametros(painelParametrosEmpresa.getNomeEmpresa());
            iniciarPainelLoadingParametrosJogadores();
        }
    }
    
    public class AcaoBotaoSimular implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            controlador.enviarMensagemRodada();
            iniciarPainelLoadingRodada();
        }
    }
    
    public class AcaoBotaoEnviarChat implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            controlador.enviarMensagemChat(painelChat.getTexto());
        }
    }
    
    public void appendMsgChat(String msg){
        painelChat.appendMsg(msg);
    }
    
}
