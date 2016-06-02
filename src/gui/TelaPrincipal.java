package gui;

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
        sair.addActionListener(new AcaoMenuSair());
    }
    
    private void iniciarPainelInicial(){
        painelInicial = new PainelInicial();
        add(painelInicial);
    }
    
    private static void setSystemLookAndFeel(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
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
}
