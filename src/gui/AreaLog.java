package gui;

import javax.swing.JTextArea;

public class AreaLog {
    
    private final static JTextArea LOG = new JTextArea();
    
    static {
        LOG.setEditable(false);
        LOG.setLineWrap(true);
    }
    
    private AreaLog() { }
    
    public static void appendLog(String msg) {
        LOG.append(msg);
    }
    
    public static void setLog(String msg){
        LOG.setText(msg);
    }
    
    public static JTextArea getTextArea() {
        return LOG;
    }
}
