package gui.servidor;

import controlador.servidor.ControladorServidor;
import gui.Janela;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class JanelaServidor extends Janela {
    
    private final ControladorServidor controlador;
    
    private PainelInicialServidor painelInicialServidor;
    private PainelLogServidor painelLog;
    
    public JanelaServidor(ControladorServidor controlador) {
        this.controlador = controlador;
        configurarComponentes();
    }

    private void configurarComponentes() {
        this.setTitle("Simulador Empresa - Servidor");
        this.setResizable(true);
        
        painelInicialServidor = new PainelInicialServidor(new AcaoBotaoIniciar());
        mudarPainel(painelInicialServidor);
    }
    
    @Override
    public void novoJogo() {
        controlador.fecharServidor();
        super.novoJogo();
    }
    
    private void iniciarPainelLog(){
        painelLog = new PainelLogServidor(controlador.getPorta(), controlador.getEndereco(), controlador.getTotalRodadas(), controlador.getTotalJogadores());
        mudarPainel(painelLog);
    }
    
    public void atualizarJogadoresConectados(int jogadores){
        painelLog.atualizarJogadoresConectados(jogadores);
    }
    
    public void atualizarRodadaAtual(int rodada){
        painelLog.atualizarRodadaAtual(rodada);
    }
    
    private class AcaoBotaoIniciar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int porta = painelInicialServidor.getPorta();
            int numJogadores = painelInicialServidor.getNumJogadores();
            int numRodadas = painelInicialServidor.getNumRodadas();
            int investimento = painelInicialServidor.getInvestimento();
            
            try {
                controlador.iniciarServidor(porta, numJogadores, numRodadas, investimento);
                
                iniciarPainelLog();
            } catch (IOException ex) {
                mostrarMsgErro("ERRO: Não foi possível iniciar o servidor.");
                System.out.println(ex);
            }
        }
    }
    
}
