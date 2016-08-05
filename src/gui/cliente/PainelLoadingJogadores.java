package gui.cliente;

import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class PainelLoadingJogadores extends javax.swing.JPanel {

    public PainelLoadingJogadores(ActionListener acaoBotaoContinuar) throws MalformedURLException {
        initComponents();
        configurarComponentes(acaoBotaoContinuar);
        
        URL url = new URL("https://aconquistablog.files.wordpress.com/2013/06/paciencia-carregando.gif");
        
        inserirGIF(url);
    }
    
    private void configurarComponentes(ActionListener acaoBotaoContinuar){
        botaoContinuar.setEnabled(false);
        botaoContinuar.addActionListener(acaoBotaoContinuar);
    }
    
    public void habilitarBotaoContinuar() throws MalformedURLException{
        botaoContinuar.setEnabled(true);
        URL url = new URL("http://www.funwithmyspace.com/picture_library/game14.gif");
        inserirGIF(url);
    }    
    
    public final void inserirGIF(URL url) throws MalformedURLException{
        ImageIcon icon = new ImageIcon(url);
        labelGIF.setIcon(icon);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        botaoContinuar = new javax.swing.JButton();
        labelGIF = new javax.swing.JLabel();
        labelMsg = new javax.swing.JLabel();

        botaoContinuar.setText("Continuar");

        labelGIF.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        labelMsg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelMsg.setText("Esperando outros jogadores se conectarem...");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(botaoContinuar))
                    .addComponent(labelGIF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelMsg, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(labelGIF)
                .addGap(32, 32, 32)
                .addComponent(labelMsg)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(botaoContinuar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoContinuar;
    private javax.swing.JLabel labelGIF;
    private javax.swing.JLabel labelMsg;
    // End of variables declaration//GEN-END:variables
}
