package simulador;

import ia.BuscaCompetitiva;
import ia.antsystem.AntSystem;
import ia.minimax.MiniMax;
import modelos.Empresa;

public class SimuladorCliente {
    
    private Empresa empresa;
    
    private int rodadasTotal;
    private int rodadaAtual;
    
    private int investimentoInicial;
    
    private BuscaCompetitiva buscaCompetitiva;
    
    private final String tipoJogador;
    
    private int demandaPorRodada;
    
    public SimuladorCliente(String tipoJogador){
        this.tipoJogador = tipoJogador;
        rodadaAtual = 1;
    }
    
    public void iniciarSimulador(int rodadasTotal, int investimento){
        this.rodadasTotal = rodadasTotal;
        this.investimentoInicial = investimento;
        
        switch (tipoJogador){
            case "JOGADOR":
                empresa = new Empresa("Empresa", investimento, false);
                break;
            case "IA1":
                empresa = new Empresa("IA-MiniMax", investimento, true);
                buscaCompetitiva = new MiniMax(empresa, rodadasTotal);
                buscaCompetitiva.executar();
                break;
            case "IA2":
                empresa = new Empresa("IA-AntSystem", investimento, true);
                buscaCompetitiva = new AntSystem(empresa, rodadasTotal);
                buscaCompetitiva.executar();
                break;
        }
    }
    
    public void proximaRodada(int demandaProximaRodada, int carrosVendidos){
        int acaoIA = -1;
        if (empresa.isBot()){
            acaoIA = buscaCompetitiva.proximaAcao();
            empresa.escolherAcoes(acaoIA);
        } else {
            empresa.fecharMes();
        }
        
        empresa.venderCarros(carrosVendidos);
        
        if (empresa.isBot() && buscaCompetitiva instanceof AntSystem){
            ((AntSystem)buscaCompetitiva).atualizaFeromonio(acaoIA, empresa.getCapital());
        }
        
        rodadaAtual++; 
        demandaPorRodada = demandaProximaRodada;
    }
    
    public void setDemandaPorRodada(int demandaPorRodada){
        this.demandaPorRodada = demandaPorRodada;
    }
    
    public boolean acabouJogo(){
        return rodadaAtual >= rodadasTotal;
    }
    
    public int getDemandaPorRodada(){
        return demandaPorRodada;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public int getRodadasTotal() {
        return rodadasTotal;
    }
    
    public void setRodadasTotal(int rodadas){
        rodadasTotal = rodadas;
    }

    public int getRodadaAtual() {
        return rodadaAtual;
    }

    public int getInvestimentoInicial() {
        return investimentoInicial;
    }
    
    public void setInvestimentoInicial(int investimento){
        investimentoInicial = investimento;
    }
    
}
