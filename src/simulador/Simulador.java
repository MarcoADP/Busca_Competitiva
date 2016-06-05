package simulador;

import modelos.Empresa;
import gui.TelaPrincipal;
import java.util.ArrayList;
import java.util.Random;

public class Simulador {
    
    public final static int INVESTIMENTO_INICIAL_BAIXO = 1000000;    // R$ 1.000.000
    public final static int INVESTIMENTO_INICIAL_MEDIO = 3000000;    // R$ 3.000.000
    public final static int INVESTIMENTO_INICIAL_ALTO = 5000000;     // R$ 5.000.000
    
    public final static int DEMANDA_POR_RODADA = 1000;
    
    private TelaPrincipal tela;
    
    private ArrayList<Empresa> listaJogador;    // mudar para lista jogadores
    private ArrayList<Empresa> listaIA;
    private int numRodadas;
    private int investimento;
    
    public Simulador(){
        novoJogo();
    }
    
    public void novoJogo(){
        tela = new TelaPrincipal(this);
        investimento = 0;
        numRodadas = 0;
        listaIA = null;
        listaJogador = null;
    }
    
    public void iniciarJogo(int numJogadores, int numIA, int numRodadas, int investimento){
        this.numRodadas = numRodadas;
        this.investimento = investimento;
        listaJogador = criarListaJogadores(numJogadores);
        listaIA = criarListaIA(numIA);
    }
    
    private ArrayList<Empresa> criarListaJogadores(int numJogadores){
        ArrayList<Empresa> lista = new ArrayList<>(numJogadores);
        for (int i = 0; i < numJogadores; i++) {
            lista.add(new Empresa("Empresa "+(i+1), investimento, false));
        }
        return lista;
    }
    
    private ArrayList<Empresa> criarListaIA(int numIA){
        //completar atributos usando random
        ArrayList<Empresa> lista = new ArrayList<>(numIA);
        for (int i = 0; i < numIA; i++) {
            lista.add(new Empresa("IA "+(i+1), investimento, true));
        }
        return lista;
    }
    
    public void atenderDemanda(){
       ArrayList<Integer> listaProb = new ArrayList<>();
       int totalProb = 0;
       Empresa empresaCompradora = null;
       for(Empresa empresa : listaJogador){
           totalProb += empresa.getProbabilidadeVenda();
           listaProb.add(totalProb);
       }
       Random random = new Random();
       int i, j;
       int totalEmpresas = this.listaJogador.size();
       for(i = 0; i < DEMANDA_POR_RODADA; i++){
           int num = random.nextInt(totalProb);
           for(j = 0; j < totalEmpresas; j++){
               if(listaProb.get(j) < num){
                   empresaCompradora = listaJogador.get(j);
                   break;
               }
           }
           empresaCompradora.venderCarro();
       }   
   }

    public ArrayList<Empresa> getListaJogador() {
        return listaJogador;
    }

    public ArrayList<Empresa> getListaIA() {
        return listaIA;
    }

}
