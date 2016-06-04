package simulador;

public class Carro {

    public final static int MODELO_POPULAR = 1;
    public final static int MODELO_MEDIO = 2;
    public final static int MODELO_LUXO = 3;
    
    public final static int TIPO_PRECO_NORMAL = 1;
    public final static int TIPO_PRECO_CARO = 2;
    
    public final static int PRECO_POPULAR = 20000;
    public final static int PRECO_MEDIO = 35000;
    public final static int PRECO_LUXO = 50000;
    
    // Custo da produção
    public final static int CUSTO_POPULAR = 15000;
    public final static int CUSTO_MEDIO = 25000;
    public final static int CUSTO_LUXO = 35000;
    
    // Tempo em dias
    public final static int TEMPO_POPULAR = 2;
    public final static int TEMPO_MEDIO = 3;
    public final static int TEMPO_LUXO = 5;
    
    private int custo;
    private int tempo;
    private int tipoPreco;
    private int precoVenda;
    private String nomeModelo;
    
    public Carro(int modelo, int tipo_preco){
        this.tipoPreco = tipo_preco;
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
            default: //MODELO_LUXO
                this.custo = CUSTO_LUXO;
                this.tempo = TEMPO_LUXO;
                this.nomeModelo = "Luxo";
                this.precoVenda = PRECO_LUXO; 
                break;
        }
    }
    
    public void alterarPreco(int opcao){
        switch(opcao){
            case 0:
                if(this.nomeModelo.equals("Popular")){
                    this.precoVenda = PRECO_POPULAR;
                } else if(this.nomeModelo.equals("Médio")){
                    this.precoVenda = PRECO_MEDIO;
                } else {
                    this.precoVenda = PRECO_LUXO;
                }
                break;
            case 1:
                if(this.nomeModelo.equals("Popular")){
                    this.precoVenda = (int) (PRECO_POPULAR * 1.5);
                } else if(this.nomeModelo.equals("Médio")){
                    this.precoVenda = (int) (PRECO_MEDIO * 1.5);
                } else {
                    this.precoVenda = (int) (PRECO_LUXO * 1.5);
                }
                break;
        }
    }
    
    //GET e SET
    public int getTipoPreco() {
        return tipoPreco;
    }

    public int getCusto() {
        return custo;
    }

    public int getTempo() {
        return tempo;
    }

    public int getPrecoVenda() {
        /*int preçoVenda = 0;
        switch(tipoPreco){
            case TIPO_PRECO_NORMAL:
                preçoVenda = this.precoVenda;
                break;
            case TIPO_PRECO_CARO:
                preçoVenda = (int) (this.precoVenda * 1.5);
                break;
        }*/
        return precoVenda;
    }
    
    public String getStringModelo(){
        return nomeModelo;
    }
}
