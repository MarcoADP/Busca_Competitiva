package simulador;

public class Carro {
    public final static int MODELO_POPULAR = 1;
    public final static int MODELO_MEDIO = 2;
    public final static int MODELO_LUXO = 3;
    
    public final static int PRECO_POPULAR_NORMAL = 20000;
    public final static int PRECO_MEDIO_NORMAL = 35000;
    public final static int PRECO_LUXO_NORMAL = 50000;
    
    public final static int PRECO_POPULAR_BARATO = (int) (PRECO_POPULAR_NORMAL * 0.8);
    public final static int PRECO_MEDIO_BARATO = (int) (PRECO_MEDIO_NORMAL * 0.8);
    public final static int PRECO_LUXO_BARATO = (int) (PRECO_LUXO_NORMAL * 0.8);
    
    public final static int PRECO_POPULAR_CARO = (int) (PRECO_POPULAR_NORMAL * 1.5);
    public final static int PRECO_MEDIO_CARO = (int) (PRECO_MEDIO_NORMAL * 1.5);
    public final static int PRECO_LUXO_CARO = (int) (PRECO_LUXO_NORMAL * 1.5);
    
    public final static int CUSTO_POPULAR = 15000;
    public final static int CUSTO_MEDIO = 25000;
    public final static int CUSTO_LUXO = 35000;
    
    public final static int TEMPO_POPULAR = 7;
    public final static int TEMPO_MEDIO = 5;
    public final static int TEMPO_LUXO = 3;
    
    private int custo;
    private int tempo;
    private int preco_venda;
    private int tipo_preco;
    
    public Carro(int modelo, int tipo_preco){
        this.tipo_preco = tipo_preco;
        switch (modelo) {
            case 1:
                this.custo = CUSTO_POPULAR;
                this.tempo = TEMPO_POPULAR;
                if(this.tipo_preco == 1){
                    preco_venda = PRECO_POPULAR_BARATO;
                } else if (tipo_preco == 2){
                    preco_venda = PRECO_POPULAR_NORMAL;
                } else{
                    preco_venda = PRECO_POPULAR_CARO;
                }   break;
            case 2:
                this.custo = CUSTO_MEDIO;
                this.tempo = TEMPO_MEDIO;
                if(this.tipo_preco == 1){
                    preco_venda = PRECO_MEDIO_BARATO;
                } else if (tipo_preco == 2){
                    preco_venda = PRECO_MEDIO_NORMAL;
                } else{
                    preco_venda = PRECO_MEDIO_CARO;
                }   break;
            default:
                this.custo = CUSTO_LUXO;
                this.tempo = TEMPO_LUXO;
                if(tipo_preco == 1){
                    preco_venda = PRECO_LUXO_BARATO;
                } else if (tipo_preco == 2){
                    preco_venda = PRECO_LUXO_NORMAL;
                } else{
                    preco_venda = PRECO_LUXO_CARO;
                }   break;
        }
    }

    public int getTipo_preco() {
        return tipo_preco;
    }

    public void setTipo_preco(int tipo_preco) {
        this.tipo_preco = tipo_preco;
    }

    public int getCusto() {
        return custo;
    }

    public void setCusto(int custo) {
        this.custo = custo;
    }

    public int getTempo() {
        return tempo;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    public int getPreco_venda() {
        return preco_venda;
    }

    public void setPreco_venda(int preco_venda) {
        this.preco_venda = preco_venda;
    }
    
}
