package modelos;

import utilitarios.Util;

public class Carro {

    public final static int MODELO_POPULAR = 1;
    public final static int MODELO_MEDIO = 2;
    public final static int MODELO_LUXO = 3;
    
    public final static int TIPO_PRECO_NORMAL = 0;
    public final static int TIPO_PRECO_CARO = 1;
    
    public final static double FATOR_PRECO_NORMAL = 1.0;
    public final static double FATOR_PRECO_CARO = 1.3;
    
    public final static int PRECO_POPULAR = 20000;
    public final static int PRECO_MEDIO = 35000;
    public final static int PRECO_LUXO = 50000;
    
    // Custo da produção
    public final static int CUSTO_POPULAR = 10000;
    public final static int CUSTO_MEDIO = 20000;
    public final static int CUSTO_LUXO = 30000;
    
    // Tempo em dias
    public final static int TEMPO_POPULAR = 2;
    public final static int TEMPO_MEDIO = 3;
    public final static int TEMPO_LUXO = 4;
    
    private int modelo;
    private int custo;
    private int tempo;
    private int tipoPreco;
    private int precoVenda;
    private String nomeModelo;
    
    public Carro(int modelo, int tipo_preco){
        this.tipoPreco = tipo_preco;
        this.modelo = modelo;
        switch (modelo) {
            case MODELO_POPULAR:
                this.custo = CUSTO_POPULAR;
                this.tempo = TEMPO_POPULAR;
                this.nomeModelo = "Popular";
                this.precoVenda = PRECO_POPULAR;
                break;
            case MODELO_MEDIO:
                this.custo = CUSTO_MEDIO;
                this.tempo = TEMPO_MEDIO;
                this.nomeModelo = "Médio";
                this.precoVenda = PRECO_MEDIO; 
                break;
            case MODELO_LUXO:
                this.custo = CUSTO_LUXO;
                this.tempo = TEMPO_LUXO;
                this.nomeModelo = "Luxo";
                this.precoVenda = PRECO_LUXO; 
                break;
        }
    }
    
    public void alterarPreco(int tipo){
        this.tipoPreco = tipo;
        double fator = 0;
        int preco = 0;
        switch(tipoPreco){
            case TIPO_PRECO_NORMAL:
                fator = FATOR_PRECO_NORMAL;
                break;
            case TIPO_PRECO_CARO:
                fator = FATOR_PRECO_CARO;
                break;
        }
        
        switch (modelo) {
            case MODELO_POPULAR:
                preco = PRECO_POPULAR;
                break;
            case MODELO_MEDIO:
                preco = PRECO_MEDIO;
                break;
            case MODELO_LUXO:
                preco = PRECO_LUXO;
        }
        this.precoVenda = (int) (preco * fator);
    }
    
    public static Carro aleatorio(){
        int random = Util.getRandomInt(MODELO_LUXO) + 1;
        return new Carro(random, TIPO_PRECO_NORMAL);
    }
    
    //GET e SET

    public int getCusto() {
        return custo;
    }

    public int getTempo() {
        return tempo;
    }

    public int getPrecoVenda() {
        return precoVenda;
    }
    
    public String getStringModelo(){
        return nomeModelo;
    }
    
    public int getModelo(){
        return modelo;
    }
    
    public int getTipoPreco() {
        return tipoPreco;
    }
    
    public void setTipoPreco(int tipoPreco) {
        this.tipoPreco = tipoPreco;
    }
    
}
