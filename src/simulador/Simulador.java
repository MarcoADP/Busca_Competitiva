package simulador;

import modelos.Empresa;
import gui.TelaPrincipal;
import java.util.ArrayList;
import java.util.Random;
import modelos.Fabrica;

public class Simulador {
    
    public final static int INVESTIMENTO_INICIAL_BAIXO = 1000000;    // R$ 1.000.000
    public final static int INVESTIMENTO_INICIAL_MEDIO = 3000000;    // R$ 3.000.000
    public final static int INVESTIMENTO_INICIAL_ALTO = 5000000;     // R$ 5.000.000
    
    public final static int DEMANDA_POR_RODADA = 1000;
    
    private TelaPrincipal tela;
    
    private ArrayList<Empresa> listaJogador;    // mudar para lista jogadores
    private ArrayList<Empresa> listaIA;
    private int numRodadas;
    private int rodada;
    private int investimento;
    
    public Simulador(){
        novoJogo();
    }
    
    public void novoJogo(){
        tela = new TelaPrincipal(this);
        investimento = 0;
        numRodadas = 0;
        rodada = 1;
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
            lista.add(new Empresa("Empresa "+(i+1), investimento, false, Fabrica.PEQUENA));
        }
        return lista;
    }
    
    private ArrayList<Empresa> criarListaIA(int numIA){
        //completar atributos usando random
        ArrayList<Empresa> lista = new ArrayList<>(numIA);
        for (int i = 0; i < numIA; i++) {
            lista.add(new Empresa("IA "+(i+1), investimento, true, Fabrica.PEQUENA));
        }
        return lista;
    }
    
    public void atenderDemanda(){  
        Integer totalProb = 0;
        //normalizar as probabilidades
        for(Empresa empresa : listaJogador){
            totalProb += empresa.getProbabilidadeVenda();
        }
        
        //calcular a prob de cada
        ArrayList<Integer> listaProb = new ArrayList<>();
        for(Empresa empresa : listaJogador){
            /*totalProb - 100
              empresa.Prob - x
               x - 100*Empresa.Prob/totalProb
            */
            Integer prob = (int) (100 * empresa.getProbabilidadeVenda() / totalProb);
            listaProb.add(prob);    
        }
        
        //vender cada carro
        int i, j, limite, maximo, numeroSorteado, indEmpresa = -1;
        Empresa empresaVendedora;
        Random random = new Random();
        for(i = 0; i < DEMANDA_POR_RODADA; i++){
            maximo = Integer.MIN_VALUE;
            int numeroEmpresa = listaJogador.size();
            for(j = 0; j < numeroEmpresa; j++){
                limite = listaProb.get(i);
                numeroSorteado = random.nextInt(limite);
                if(numeroSorteado > maximo){
                    indEmpresa = j;
                    maximo = numeroSorteado;
                }
            }
            empresaVendedora = listaJogador.get(indEmpresa);
            if(!empresaVendedora.atualizaEstoque()){    //se a empresa esta com estoque zerado
                i = i - 1;  //volta o carro para a demanda
                //o numero sorteado para empresa com estoque vazio sera entre [0 e 1[
                listaProb.set(indEmpresa, 1);
            }
        }
   }

    public ArrayList<Empresa> getListaJogador() {
        return listaJogador;
    }

    public ArrayList<Empresa> getListaIA() {
        return listaIA;
    }

    public int getRodada() {
        return rodada;
    }
    
}
