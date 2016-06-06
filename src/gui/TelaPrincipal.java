package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.*;
import simulador.Simulador;

public class TelaPrincipal extends JFrame{
    
    private PainelInicial painelInicial;
    private PainelJogadores painelJogadores;
    private PainelRodada painelRodada;
    
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
        
        novoJogo.addActionListener(new AcaoMenuNovoJogo());
        sobre.addActionListener(new TelaSobre(this));
        sair.addActionListener(new AcaoMenuSair());
    }
    
    private void iniciarPainelInicial(){
        painelInicial = new PainelInicial(new AcaoBotaoIniciar());
        add(painelInicial);
    }
    
    private void iniciarJogo(int numPessoas, int numIA, int numRodadas, int investimento){
        setMinimumSize(new Dimension(500, 450));
        setPreferredSize(new Dimension(850, 500));
        setResizable(true);
        getContentPane().removeAll();
        
        simulador.iniciarJogo(numPessoas, numIA, numRodadas, investimento);
        
        painelJogadores = new PainelJogadores(simulador.getListaJogador(), simulador.getListaIA(), new AcaoBotaoComecar());
        add(painelJogadores);
        pack();
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
            getContentPane().removeAll();
            painelRodada = new PainelRodada(simulador, new AcaoBotaoSimular());
            add(painelRodada);
            pack();
        }
    }
    
    private class AcaoBotaoSimular implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //simulador.proximaRodada();
        }
    }
    
    private class AcaoMenuNovoJogo implements ActionListener{
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
