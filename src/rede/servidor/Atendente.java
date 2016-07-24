package rede.servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Arrays;

public class Atendente implements Runnable {
    
    private Socket socket;
    
    private BufferedReader in;
    private PrintStream out;
    
    private boolean inicializado;
    private boolean executando;
    
    private Thread thread;
    
    public Atendente(Socket socket) throws IOException{
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
        out.println(msg);
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
                System.out.println(Arrays.toString(msg.getBytes()));
                
                /*janela.append("Mensagem recebida do client ["
                        + socket.getInetAddress().getHostName() + 
                        ":" + socket.getPort() + 
                        "]: "
                        + msg+"\n");*/
                if (msg.equals("FIM")) {
                    break;
                }
                out.println(msg);
            } catch (SocketTimeoutException ex){
                //System.out.println(ex);
            } catch (IOException ex) {
                //System.out.println(ex);
            }
        }
        //janela.append("Encerrando conexão");
        close();
    }
    
}
