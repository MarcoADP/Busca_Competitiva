package rede.servidor;

import gui.AreaLog;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import rede.protocolo.Protocolo;

public class Atendente implements Runnable {
    
    private Socket socket;
    
    private BufferedReader in;
    private PrintStream out;
    
    private boolean inicializado;
    private boolean executando;
    
    private Thread thread;
    
    private final Protocolo protocolo;
    
    public Atendente(Socket socket, Protocolo protocolo) throws IOException{
        this.protocolo = protocolo;
        this.socket = socket;
        
        this.inicializado = false;
        this.executando = false;
        
        open();
    }
    
    private void open() throws IOException{
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintStream(socket.getOutputStream());
            inicializado = true;
        } catch (Exception ex){
            close();
            throw ex;
        }
    }
    
    private void close(){
        if (in != null){
            try {
                in.close();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
        
        if (out != null){
            try {
                out.close();
            } catch (Exception ex){
                System.out.println(ex);
            }
        }
        
        try {
            socket.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
        
        in = null;
        out = null;
        socket = null;
        
        inicializado = false;
        executando = false;
        thread = null;
    }
    
    public void start(){
        if (!inicializado || executando){
            return;
        }
        
        executando = true;
        thread = new Thread(this);
        thread.start();
    }
    
    public void stop() throws InterruptedException{
        executando = false;
        
        if (thread != null){
            thread.join();
        }
    }
    
    public void send(String msg){
        //protocolo.processar(msg) -> send
        out.println(msg);
    }
    
    private void appendLog(String msg){
        AreaLog.appendLog(msg);
    }

    @Override
    public void run() {
        while (executando){
            String msg;
            try {
                socket.setSoTimeout(2500);
                msg = in.readLine();
                
                if (msg == null){
                    break;
                }
                
                appendLog("Mensagem recebida do cliente ["
                        + socket.getInetAddress().getHostName() + 
                        ":" + socket.getPort() + 
                        "]: "
                        + msg+"\n");
                
                if (msg.equals("FIM")) {
                    break;
                }
                out.println(msg);
            } catch (SocketTimeoutException ex){
                // ignorar
            } catch (IOException ex) {
                break;
                //System.out.println(ex);
            }
        }
        appendLog("Cliente ["
                + socket.getInetAddress().getHostAddress() + ":" + socket.getPort() + "] desconectado.\n");

        close();
    }
    
}
