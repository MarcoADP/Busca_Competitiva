package gui;

import gui.cliente.PainelJogadores;
import gui.cliente.PainelRodada;
import controlador.cliente.ControladorCliente;
import controlador.servidor.ControladorServidor;
import gui.cliente.PainelInicialCliente;
import gui.servidor.PainelInicialServidor;
import simulador.Simulador;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.*;
import modelos.Empresa;

public class Janela extends JFrame {
    
    private PainelInicialServidor painelInicialServidor;
    private PainelJogadores painelJogadores;
    private PainelRodada painelRodada;
    private PainelLoading painelLoading;
    private PainelInicialCliente painelCliente;
    private PainelEscolhaInicial painelEscolhaInicial;
    
    private JMenuBar menuBar;
    private JMenu arquivo;
    private JMenu ajuda;
    
    public Simulador simulador;

    public Janela(Simulador simulador) {
        this.simulador = simulador;
        setSystemLookAndFeel();
        iniciarComponentes();
    }
    
    public Janela() {
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
        painelEscolhaInicial = new PainelEscolhaInicial(new AcaoIniciarServidor(), new AcaoIniciarCliente());
        add(painelEscolhaInicial);
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
    
    public void mostrarMsgErro(String msg){
        JOptionPane.showMessageDialog(null, msg, "Erro", JOptionPane.ERROR_MESSAGE);
    }
    
    public void mudarPainel(JPanel painel){
        this.getContentPane().removeAll();
        this.add(painel);
        this.pack();
    }

    public PainelRodada getPainelRodada() {
        return painelRodada;
    }
    
    public void novoJogo(){
        Janela j = new Janela();
        dispose();
    }
    
    private class AcaoIniciarServidor implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            ControladorServidor controladorServidor = new ControladorServidor();
            dispose();
        }
    }
    
    private class AcaoIniciarCliente implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            ControladorCliente controladorCliente = new ControladorCliente();
            dispose();
        }
    }
    
    private class AcaoNovoJogo implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            novoJogo();
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
