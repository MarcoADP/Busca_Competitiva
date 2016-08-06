package rede.servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import rede.protocolo.Protocolo;
import rede.protocolo.ProtocoloServidor;

public class Atendente implements Runnable {
    
    private Socket socket;
    
    private BufferedReader in;
    private PrintStream out;
    
    private boolean inicializado;
    private boolean executando;
    
    private Thread thread;
    
    private final ProtocoloServidor protocolo;
    
    private String mensagem;
    
    String id;
    
    public Atendente(Socket socket, ProtocoloServidor protocolo) throws IOException{
        this.protocolo = protocolo;
        this.socket = socket;
        
        this.inicializado = false;
        this.executando = false;
        
        open();
        
        id = socket.getInetAddress().getHostName() + ":" + socket.getPort();
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
    
    private void enviarParaProtocolo(String msg){
        new Thread(() -> protocolo.receber(msg)).start();
    }

    @Override
    public void run() {
        enviarParaProtocolo(id); // Enviando id para o protocolo
        while (executando){
            try {
                socket.setSoTimeout(2500);
                
                mensagem = Protocolo.lerMensagem(in);
                
                if (mensagem == null){
                    break;
                }
                
                enviarParaProtocolo(mensagem);
                
            } catch (SocketTimeoutException ex){
                // ignorar
            } catch (IOException ex) {
                System.out.println(ex);
                break;
            } catch (Exception ex){
                System.out.println(ex);
                break;
            }
        }
        new Thread(() -> protocolo.disconectarCliente(id)).start();

        close();
    }

    public String getMensagem() {
        return mensagem;
    }
    
    public String getID() {
        return id;
    }
}
