package simulador;

import modelos.Empresa;
import gui.TelaPrincipal;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import utilitarios.Util;

public class Simulador {
    
    public final static int INVESTIMENTO_INICIAL_BAIXO = 1000000;    // R$ 1.000.000
    public final static int INVESTIMENTO_INICIAL_MEDIO = 3000000;    // R$ 3.000.000
    public final static int INVESTIMENTO_INICIAL_ALTO = 5000000;     // R$ 5.000.000
    
    public final static double FATOR_DEMANDA_MIN_RANDOM = 0.5;
    public final static double FATOR_DEMANDA_MAX_RANDOM = 0.9;
    
    public final static int LIMITE_PROFUNDIDADE = 5;
    
    private int somaProducaoPorMes;
    
    private TelaPrincipal tela;
    
    private ArrayList<Empresa> listaJogador;
    private ArrayList<Empresa> listaIA;
    private ArrayList<TreeElement> arvoreIA;
    private int numRodadas;
    private int rodada;
    private int investimento;
    private boolean acabou;
    private int demandaPorRodada;
    private String strInfoRodada;
    private int carrosVendidos;
    
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
        arvoreIA = null;
        acabou = false;
        strInfoRodada = "";
        demandaPorRodada = 0;
        carrosVendidos = 0;
        somaProducaoPorMes = 0;
    }
    
    public void iniciarJogo(int numJogadores, int numIA, int numRodadas, int investimento){
        this.numRodadas = numRodadas;
        this.investimento = investimento;
        listaJogador = criarListaJogadores(numJogadores);
        listaIA = criarListaIA(numIA);
        arvoreIA = criarArvore(listaIA);
        minimax();
    }
    
    private void minimax(){
        int limite;
        if (numRodadas - rodada + 1 > LIMITE_PROFUNDIDADE){
            limite = LIMITE_PROFUNDIDADE;
        } else {
            limite = numRodadas - rodada + 1;
        }
        
        for (TreeElement raiz : arvoreIA) {
            raiz.gerarFilhos(limite);
            raiz.calcularMelhorFolha();
            
        }
        TreeElement m = arvoreIA.get(0);
        for (int i = 0; i < 6; i++) {
            System.out.println(m.getId());
            m = m.getMelhorFilho();
        }
        System.out.println("\n");
    }
    
    public void calcularSomaProducao(){
        int soma = 0;
        for (Empresa empresa : listaJogador) {
            soma += empresa.carrosPorMes();
        }
        for (Empresa empresa : listaIA) {
            soma += empresa.carrosPorMes();
        }
        somaProducaoPorMes = soma;
        calcularDemanda();
    }
    
    private void calcularDemanda(){
        demandaPorRodada = somaProducaoPorMes;
        // Demanda tem fator aleatório, para evetualmente beneficiar quem tem mais estoque;
        double rand = Util.getRandomDouble(FATOR_DEMANDA_MIN_RANDOM, FATOR_DEMANDA_MAX_RANDOM);
        demandaPorRodada = (int)(demandaPorRodada * rand);
    }
    
    public void proximaRodada(){
        fecharMesEmpresas(listaJogador);
        tomarDecisaoIA();
        
        atenderDemanda();
        atualizarInfoRodada();
        
        verificaCapitalNegativo(listaJogador);
        verificaCapitalNegativo(listaIA);
        
        rodada++;
        if (rodada > numRodadas && !acabou) {
            mostraVencedor();
        }
        
        calcularDemanda();
    }
    
        
    private void tomarDecisaoIA(){
        if ((rodada - 1) % LIMITE_PROFUNDIDADE == 0 && rodada != 1){
            arvoreIA = criarArvore(listaIA);
            minimax();
        }
        for (int i = 0; i < listaIA.size(); i++) {
            Empresa empresa = listaIA.get(i);
            int proximo = arvoreIA.get(i).getMelhorFilho().getId();
            empresa.escolherAcoes(proximo);
        }
    }
    
    private void atualizarInfoRodada(){
        strInfoRodada = "";
        for (Empresa empresa : listaJogador) {
            appendInfoRodada(empresa.getNome()+" vendeu "+empresa.getCarrosVendidosNoMes()+" carros");
            appendInfoRodada(" e lucrou "+Util.formatarDinheiro(empresa.getLucrouNoMes()));
            appendInfoRodada("\n---------------------------------\n");
        }
        for (Empresa empresa : listaIA) {
            appendInfoRodada(empresa.getNome()+" vendeu "+empresa.getCarrosVendidosNoMes()+" carros");
            appendInfoRodada(" e lucrou "+Util.formatarDinheiro(empresa.getLucrouNoMes()));
            appendInfoRodada("\n---------------------------------\n");
        }
        appendInfoRodada("Total de carros vendidos: "+carrosVendidos);
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
                if (removido.isBot()){
                    arvoreIA.remove(i);
                }
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
    
    private ArrayList<TreeElement> criarArvore(ArrayList<Empresa> listaIA){
        ArrayList<TreeElement> lista = new ArrayList<>(listaIA.size());
        for (Empresa empresa : listaIA) {
            lista.add(new TreeElement(empresa));
        }
        return lista;
    }
    
    private ArrayList<Empresa> criarListaJogadores(int numJogadores){
        ArrayList<Empresa> lista = new ArrayList<>(numJogadores);
        for (int i = 0; i < numJogadores; i++) {
            lista.add(new Empresa("Empresa "+(i+1), investimento, false));
        }
        return lista;
    }
    
    private ArrayList<Empresa> criarListaIA(int numIA){
        ArrayList<Empresa> lista = new ArrayList<>(numIA);
        for (int i = 0; i < numIA; i++) {
            lista.add(new Empresa("IA "+(i+1), investimento, true));
        }
        return lista;
    }
    
    public void atenderDemanda(){  
        carrosVendidos = 0;
        
        Integer totalProb = 0;
        //normalizar as probabilidades
        for(Empresa empresa : listaJogador){
            totalProb += empresa.getProbabilidadeVenda();
        }
        for(Empresa empresa : listaIA){
            totalProb += empresa.getProbabilidadeVenda();
        }
        
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
        for(i = 0; i < demandaPorRodada; i++){
            maximo = Integer.MIN_VALUE;
            int numeroEmpresa = listaJogador.size()+listaIA.size();
            for(j = 0; j < numeroEmpresa; j++){
                limite = listaProb.get(j);
                numeroSorteado = Util.getRandomInt(limite);
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
            
            if(empresaVendedora.temEstoque()){    //vender carro
                empresaVendedora.venderCarro();
                carrosVendidos++;
            } else {    //se a empresa esta com estoque zerado
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
            if(empresa.temEstoque()){
                return false;
            }
        }
        for(Empresa empresa : listaIA){
            if(empresa.temEstoque()){
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

    public int getDemandaPorRodada() {
        return demandaPorRodada;
    }

    public boolean acabou() {
        return acabou;
    }

    public int getInvestimento() {
        return investimento;
    }

    public int getNumRodadas() {
        return numRodadas;
    }
    
}