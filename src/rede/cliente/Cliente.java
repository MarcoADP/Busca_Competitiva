package rede.cliente;

import controlador.cliente.ControladorCliente;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Arrays;

public class Cliente implements Runnable{
    
    private Socket socket;
    
    private BufferedReader in;
    private PrintStream out;
    
    private boolean inicializado;
    private boolean executando;
    
    private Thread thread;
    
    private final ControladorCliente controlador;
    private String mensagem;
    
    public Cliente(ControladorCliente controlador, String endereco, int porta) throws Exception {
        this.controlador = controlador;
        inicializado = false;
        executando = false;
        
        open(endereco, porta);
    }

    private void open(String endereco, int porta) throws Exception {
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(endereco, porta), 2000);
            
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();
            
            in = new BufferedReader(new InputStreamReader(input));
            out = new PrintStream(output);
            
            inicializado = true;
        } catch (Exception e) {
            System.out.println(e);
            close();
            throw e;
        }
    }

    private void close() {
        if (in != null) {
            try {
                in.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        
        if (out != null) {
            try {
                out.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        
        if (socket != null) {
            try {
                socket.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        
        in = null;
        out = null;
        socket = null;
        
        inicializado = false;
        executando = false;
        
        thread = null;
    }

    public void start(){
        if(!inicializado || executando) {
            return;
        }
        
        executando = true;
        
        thread = new Thread(this);
        thread.start();
    }
    
    public void stop() throws Exception{
        executando = false;
        
        if(thread != null) {
            thread.join();
        }
    }
    
    public void send(String mensagem){
        out.println(mensagem);
    }

    @Override
    public void run() {
        while(executando){
            try {
                socket.setSoTimeout(2500);
                
                mensagem = in.readLine();
                
                if(mensagem==null) {
                    break;
                }
                
                System.out.println("Mensagem enviada do servidor: " + mensagem);
                
                synchronized(controlador){
                    controlador.receberMensagemServidor(mensagem);
                }
            } catch(SocketTimeoutException e){
                // ignorar
            } catch(SocketException e){
                new Thread(() -> controlador.servidorDesconectado()).start();
                break;
            }catch(Exception e){
                System.out.println(e);
                break;
            }
        }
        
        close();
    }

    public boolean isExecutando() {
        return executando;
    }

    public String getMensagem() {
        return mensagem;
    }
    
}