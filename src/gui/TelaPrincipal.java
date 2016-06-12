package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.*;
import modelos.Empresa;
import simulador.*;

public class TelaPrincipal extends JFrame{
    
    private PainelInicial painelInicial;
    private PainelJogadores painelJogadores;
    private PainelRodada painelRodada;
    private PainelLoading painelLoading;
    
    private JMenuBar menuBar;
    private JMenu arquivo;
    private JMenu ajuda;
    
    private final Simulador simulador;

    public TelaPrincipal(Simulador simulador) {
        this.simulador = simulador;
        setSystemLookAndFeel();
        iniciarComponentes();
    }
    
    private void iniciarComponentes(){
        setTitle("Simulador Empresa");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        iniciarMenu();
        iniciarPainelInicial();
        
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        pack();
    }
    
    private void iniciarMenu(){
        menuBar = new JMenuBar();
        
        arquivo = new JMenu("Arquivo");
        ajuda = new JMenu("Ajuda");
        JMenuItem novoJogo = new JMenuItem("Novo Jogo");
        JMenuItem sair = new JMenuItem("Sair");
        JMenuItem sobre = new JMenuItem("Sobre");
        
        arquivo.add(novoJogo);
        arquivo.addSeparator();
        arquivo.add(sair);
        ajuda.add(sobre);
        
        menuBar.add(arquivo);
        menuBar.add(ajuda);
        setJMenuBar(menuBar);
        
        novoJogo.addActionListener(new AcaoNovoJogo());
        sobre.addActionListener(new TelaSobre(this));
        sair.addActionListener(new AcaoMenuSair());
    }
    
    private void iniciarPainelInicial(){
        painelInicial = new PainelInicial(new AcaoBotaoIniciar());
        add(painelInicial);
    }
    
    private void iniciarJogo(int numPessoas, int numIA, int numRodadas, int investimento){
        getContentPane().removeAll();
        
        simulador.iniciarJogo(numPessoas, numIA, numRodadas, investimento);
        
        painelJogadores = new PainelJogadores(simulador, new AcaoBotaoComecar());
        add(painelJogadores);
        pack();
        setLocationRelativeTo(null);
    }
    
    public void mostrarVencedor(Empresa empresa){
        painelRodada.venceu(new AcaoNovoJogo(), empresa);
        JLabel label;
        if (empresa == null){
            label = new JLabel("Todos perderam!");
        } else {
            label = new JLabel(empresa.getNome()+" Venceu!");
        }
        
        label.setFont(new Font("Arial", Font.BOLD, 36));
        JOptionPane.showMessageDialog(null, label, "Vencedor", JOptionPane.PLAIN_MESSAGE);
    }
      
    public void setPainelRodada(){
        painelRodada.atualizar();
        getContentPane().removeAll();
        add(painelRodada);
        pack();
        setLocationRelativeTo(null);
    }

    public PainelRodada getPainelRodada() {
        return painelRodada;
    }
    
    private class AcaoBotaoIniciar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int numPessoas = (int)painelInicial.getSpinnerPessoas().getValue();
            int numIA = (int)painelInicial.getSpinnerIA().getValue();
            int numRodadas = (int)painelInicial.getSpinnerRodadas().getValue();
            int investimento = painelInicial.getSliderInvestimento().getValue();
            
            if (numPessoas == 0 && numIA == 0){
                JOptionPane.showMessageDialog(null, "Erro: Número de pessoas e de IA não podem ser 0.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (numPessoas == 0 && numIA < 2){
                JOptionPane.showMessageDialog(null, "Erro: É preciso pelo menos 2 jogadores.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (numPessoas < 2 && numIA == 0){
                JOptionPane.showMessageDialog(null, "Erro: É preciso pelo menos 2 jogadores.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            iniciarJogo(numPessoas, numIA, numRodadas, investimento);
        }
    }
    
    private class AcaoBotaoComecar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            simulador.calcularSomaProducao();
            painelRodada = new PainelRodada(simulador, new AcaoBotaoSimular());
            setPainelRodada();
        }
    }
    
    private class AcaoBotaoSimular implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            getContentPane().removeAll();
            painelLoading = new PainelLoading(TelaPrincipal.this, simulador);
            add(painelLoading);
            pack();
            setLocationRelativeTo(null);
            painelLoading.executar();
            
            simulador.proximaRodada();
        }
    }
    
    private class AcaoNovoJogo implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            simulador.novoJogo();
        }
    }
    
    private class AcaoMenuSair implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            System.exit(0);
        }
    }
    
    private static void setSystemLookAndFeel(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
