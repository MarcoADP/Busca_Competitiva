package simulador;

import controlador.servidor.ControladorServidor;
import modelos.Empresa;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import modelos.Fabrica;
import utilitarios.Util;

public class SimuladorServidor {
    
    public final static int INVESTIMENTO_INICIAL_BAIXO = 1000000;    // R$ 1.000.000
    public final static int INVESTIMENTO_INICIAL_MEDIO = 3000000;    // R$ 3.000.000
    public final static int INVESTIMENTO_INICIAL_ALTO = 5000000;     // R$ 5.000.000
    
    public final static double FATOR_DEMANDA_MIN_RANDOM = 0.7;
    public final static double FATOR_DEMANDA_MAX_RANDOM = 1.1;
    
    private ArrayList<Empresa> listaJogador;
    private Map<String, Empresa> jogadores;
    
    private int rodadasTotal;
    private int rodadaAtual;
    
    private int investimento;
    private int numJogadores;
    
    private boolean acabou;
    private int demandaPorRodada;
    private String strInfoRodada;
    private int carrosVendidos;
    
    private final ControladorServidor controlador;
    
    public SimuladorServidor(ControladorServidor controlador){
        this.controlador = controlador;
        novoJogo();
    }
    
    public final void novoJogo(){
        investimento = 0;
        rodadasTotal = 0;
        rodadaAtual = 1;
        listaJogador = null;
        acabou = false;
        strInfoRodada = "";
        demandaPorRodada = 0;
        carrosVendidos = 0;
    }
    
    public void iniciarJogo(int numJogadores, int numRodadas, int investimento){
        this.rodadasTotal = numRodadas;
        this.investimento = investimento;
        this.numJogadores = numJogadores;
        listaJogador = new ArrayList<>(numJogadores);
        jogadores = new HashMap<>();
    }
    
    public void addEmpresa(String id, String nomeEmpresa, String fabrica, String modeloCarro){
        Empresa empresa = new Empresa(nomeEmpresa, investimento, false);
        
        empresa.setFabrica(Fabrica.valueOf(fabrica));
        int modelo = Integer.parseInt(modeloCarro);
        empresa.setCarro(modelo);
        
        listaJogador.add(empresa);
        jogadores.put(id, empresa);
    }
    
    private int calcularSomaProducao(){
        int soma = 0;
        for (Empresa empresa : listaJogador) {
            soma += empresa.carrosPorMes();
        }
        return soma;
    }
    
    public void calcularDemanda(){
        demandaPorRodada = calcularSomaProducao();
        // Demanda tem fator aleatório, para evetualmente beneficiar quem tem mais estoque;
        double rand = Util.getRandomDouble(FATOR_DEMANDA_MIN_RANDOM, FATOR_DEMANDA_MAX_RANDOM);
        demandaPorRodada = (int)(demandaPorRodada * rand);
    }
    
    public void proximaRodada(){
        fecharMesEmpresas(listaJogador);
        
        atenderDemanda();
        atualizarInfoRodada();
        
        verificaCapitalNegativo(listaJogador);
        
        rodadaAtual++;
        if (rodadaAtual > rodadasTotal) {
            acabou = true;
        }
        
        calcularDemanda();
    }
    
    private void atualizarInfoRodada(){
        strInfoRodada = "";
        appendInfoRodada("\n======================================\n");
        appendInfoRodada("RODADA: "+rodadaAtual);
        appendInfoRodada("\n======================================\n");
        for (Empresa empresa : listaJogador) {
            appendInfoRodada(empresa.getNome()+" vendeu "+empresa.getCarrosVendidosNoMes()+" carros");
            appendInfoRodada(" e lucrou "+Util.formatarDinheiro(empresa.getLucrouNoMes()));
            appendInfoRodada("\n---------------------------------\n");
        }
        appendInfoRodada("Total de carros vendidos: "+carrosVendidos);
        appendInfoRodada("\n======================================\n\n");
    }
    
    private void appendInfoRodada(String str){
        strInfoRodada += str;
    }
    
    public String getInfoRodada(){
        return strInfoRodada;
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
                controlador.jogadorPerdeu(getIdEmpresa(empresa), empresa.getNome());
            }
        }
    }
    
    public Empresa verificaVencedor(){
        double maior = -1;
        Empresa vencedora = null;
        for (Empresa empresa : listaJogador) {
            if (empresa.getCapital() > maior){
                maior = empresa.getCapital();
                vencedora = empresa;
            }
        }
        
        return vencedora;
    }
    
    public void atenderDemanda(){  
        carrosVendidos = 0;
        
        Integer totalProb = 0;
        //normalizar as probabilidades
        for(Empresa empresa : listaJogador){
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
        
        //vender cada carro
        int i, j, limite, maximo, numeroSorteado, indEmpresa = -1;
        Empresa empresaVendedora;
        for(i = 0; i < demandaPorRodada; i++){
            maximo = Integer.MIN_VALUE;
            int numeroEmpresa = listaJogador.size();
            for(j = 0; j < numeroEmpresa; j++){
                limite = listaProb.get(j);
                numeroSorteado = Util.getRandomInt(limite);
                if(numeroSorteado > maximo){
                    indEmpresa = j;
                    maximo = numeroSorteado;
                }
            }
            
            empresaVendedora = listaJogador.get(indEmpresa);
            
            
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
        
        return true;
    }
    
    public void removerJogador(String id){
        Empresa empresa = jogadores.get(id);
        listaJogador.remove(empresa);
    }
    
    public String getIdEmpresa(Empresa empresa) {
        for (Map.Entry<String, Empresa> entry : jogadores.entrySet()) {
            String idEmpresa = entry.getKey();
            Empresa e = entry.getValue();
            if (e == empresa){
                return idEmpresa;
            }
        }
        return null;
    }
    
    public Empresa getEmpresa(String id){
        return jogadores.get(id);
    }
    
    public void realizarAcaoEmpresa(String id, int funcionariosContratar, int tipoPrecoCarro, int tipoMarketing) {
        Empresa empresa = jogadores.get(id);
        empresa.setTipoMarketing(tipoMarketing);
        empresa.setFuncionariosAContratar(funcionariosContratar);
        empresa.getCarro().setTipoPreco(tipoPrecoCarro);
        empresa.getCarro().alterarPreco(tipoPrecoCarro);
    }
    
    public ArrayList<Empresa> getListaJogador() {
        return listaJogador;
    }

    public int getRodadaAtual() {
        return rodadaAtual;
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

    public int getRodadasTotal() {
        return rodadasTotal;
    }
    
    public int getNumJogadores() {
        return numJogadores;
    }
    
}