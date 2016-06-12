package simulador;

import modelos.Empresa;
import gui.TelaPrincipal;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;
import modelos.Fabrica;

public class Simulador {
    
    public final static int INVESTIMENTO_INICIAL_BAIXO = 1000000;    // R$ 1.000.000
    public final static int INVESTIMENTO_INICIAL_MEDIO = 3000000;    // R$ 3.000.000
    public final static int INVESTIMENTO_INICIAL_ALTO = 5000000;     // R$ 5.000.000
    
    public final static int DEMANDA_POR_RODADA = 1000;
    
    private TelaPrincipal tela;
    
    private ArrayList<Empresa> listaJogador;    // mudar para lista jogadores
    private ArrayList<Empresa> listaIA;
    private ArrayList<TreeElement> arvore;
    private int numRodadas;
    private int rodada;
    private int investimento;
    private boolean acabou;
    
    private String strInfoRodada;
    
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
        arvore = null;
        acabou = false;
        strInfoRodada = "";
    }
    
    public void iniciarJogo(int numJogadores, int numIA, int numRodadas, int investimento){
        this.numRodadas = numRodadas;
        this.investimento = investimento;
        listaJogador = criarListaJogadores(numJogadores);
        listaIA = criarListaIA(numIA);
        arvore = criarArvore(numIA, listaIA);
        
        //TreeElement arvore = new TreeElement(0, null, listaJogador.get(0));
        //arvore.empresa.mostraEmpresa();
        //arvore.gerarFilhos(5);
        //arvore.melhorFolha(0);
        //System.out.println("sdfs => " + arvore.melhorFilho.id);
        //System.out.println("aa => " + arvore.melhorFilho.melhorFilho.empresa.getCapital());
        //arvore.mostraArvore(0);
        /*int tam = arvore.filhos.size();
        for(int i = 0; i < tam; i++){
            arvore.filhos.get(i).empresa.mostraEmpresa();
        }*/
        //arvore.filhos.get(0).empresa.mostraEmpresa();
        
    }
    
    public void proximaRodada(){
        rodada++;
        
        fecharMesEmpresas(listaJogador);
        fecharMesEmpresas(listaIA);
        
        atenderDemanda();
        
        verificaCapitalNegativo(listaJogador);
        verificaCapitalNegativo(listaIA);
        
        if (rodada >= numRodadas && !acabou) {
            mostraVencedor();
        }
        
        tela.getPainelRodada().atualizar();
    }
    
    private void fecharMesEmpresas(ArrayList<Empresa> lista){
        for (Empresa empresa : lista) {
           empresa.fecharMes();
        }
    }
    
    private void verificaCapitalNegativo(ArrayList<Empresa> lista){
        for (int i = lista.size()-1; i >= 0; i--) {
            Empresa empresa = lista.get(i);
            
            if (empresa.getCapital() < 0){
                Empresa removido = lista.remove(i);
                mostraCapitalNegativo(removido);
            }
            
            if (acabou){
                return;
            }
        }
    }
    
    private void appendInfoRodada(String str){
        strInfoRodada += str;
    }
    
    public String getInfoRodada(){
        return strInfoRodada;
    }
    
    private void mostraCapitalNegativo(Empresa empresa){
        JOptionPane.showMessageDialog(null, empresa.getNome() + " perdeu, pois o capital ficou negativo!");
        int num = listaJogador.size() + listaIA.size();
        if (num == 2) {
            mostraVencedor();
        }
    }
    
    private void mostraVencedor() {
        Empresa vencedor = verificaVencedor();
        tela.mostrarVencedor(vencedor);
    }
    
    private Empresa verificaVencedor(){
        double maior = -1;
        Empresa vencedora = null;
        for (Empresa empresa : listaJogador) {
            if (empresa.getCapital() > maior){
                maior = empresa.getCapital();
                vencedora = empresa;
            }
        }
        
        for (Empresa empresa : listaIA) {
            if (empresa.getCapital() > maior){
                maior = empresa.getCapital();
                vencedora = empresa;
            }
        }
        
        if (vencedora != null){
            acabou = true;
        }
        
        return vencedora;
    }
    
    private ArrayList<TreeElement> criarArvore(int numIa, ArrayList<Empresa> listaIA){
        ArrayList<TreeElement> lista = new ArrayList<>(numIa);
        for(int i = 0; i < numIa; i++){
            lista.add(new TreeElement(0, null, listaIA.get(i)));
        }
        return lista;
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
        for(Empresa empresa : listaIA){
            totalProb += empresa.getProbabilidadeVenda();
        }
        System.out.println(totalProb);
        
        
        //calcular a prob de cada
        //0 a listaJogador.size-1 -> jogadores reais
        ArrayList<Integer> listaProb = new ArrayList<>();
        for(Empresa empresa : listaJogador){
            /*totalProb - 100
              empresa.Prob - x
               x - 100*Empresa.Prob/totalProb
            */
            Integer prob = (int) (100 * empresa.getProbabilidadeVenda() / totalProb);
            listaProb.add(prob);    
        }
        
        //listaJogador.size a listaIA.size-1 -> IA
        for(Empresa empresa : listaIA){
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
            if(indEmpresa < listaJogador.size()){
                empresaVendedora = listaJogador.get(indEmpresa);
            } else {
                int ind = indEmpresa - listaJogador.size();
                empresaVendedora = listaIA.get(ind);
            }
            if(!empresaVendedora.atualizaEstoque()){    //se a empresa esta com estoque zerado
                i = i - 1;  //volta o carro para a demanda
                //o numero sorteado para empresa com estoque vazio sera entre [0 e 1[
                listaProb.set(indEmpresa, 1);
            }
            if(this.confereEstoque()){
                return;
            }
        }
   }
    
    public boolean confereEstoque(){
        for(Empresa empresa : listaJogador){
            if(empresa.getEstoqueCarro() > 0){
                return false;
            }
        }
        for(Empresa empresa : listaIA){
            if(empresa.getEstoqueCarro() > 0){
                return false;
            }
        }
        
        return true;
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