package simulador;

import ia.BuscaCompetitiva;
import ia.minimax.MiniMax;
import modelos.Empresa;

public class SimuladorCliente {
    
    private Empresa empresa;
    
    private int rodadasTotal;
    private int rodadaAtual;
    
    private int investimentoInicial;
    
    private BuscaCompetitiva buscaCompetitiva;
    
    private final String tipoJogador;
    
    public SimuladorCliente(String tipoJogador){
        this.tipoJogador = tipoJogador;
        rodadaAtual = 0;
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
                break;
            case "IA2":
                empresa = new Empresa("IA-AntSystem", investimento, true);
                //buscaCompetitiva = ant system
                break;
        }
    }
    
    public void proximaRodada(int demanda){
        rodadaAtual++;
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
