package modelos;

import utilitarios.Util;

public enum Fabrica {
    PEQUENA("Pequena", 300000, 1, 30000, 50, 10),
    MEDIA("Media", 500000, 2, 50000, 80, 10),
    GRANDE("Grande", 700000, 3, 70000, 100, 10);
    // Pequena (mais barata, produz 1 carro por vez, R$ 30.000 de custo por mês)
    // Media (preço médio, produz 2 carros por vez, R$ 60.000 de custo por mês)
    // Grande (mais cara, produz 3 carros por vez, R$ 90.000 de custo por mês)

    private final String nome;
    private final int preco;
    private final double fatorProducao;
    private final int gastoPorMes;
    private final int capacidade;
    private final int numeroFuncionarioInicial;

    Fabrica(String nome, int preco, double fatorProducao, int gasto, int capacidade, int numeroFunc) {
        this.nome = nome;
        this.preco = preco;
        this.fatorProducao = fatorProducao;
        this.gastoPorMes = gasto;
        this.capacidade = capacidade;
        this.numeroFuncionarioInicial = numeroFunc;
    }
    
    public static Fabrica aleatorio(){
        Fabrica[] fabricas = Fabrica.values();
        int random = Util.getRandomInt(fabricas.length);
        return fabricas[random];
    }

    public int getPreco() {
        return preco;
    }

    public double getFatorProducao() {
        return fatorProducao;
    }

    public String getNome() {
        return nome;
    }

    public int getGastoPorMes() {
        return gastoPorMes;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public int getNumeroFuncionarioInicial() {
        return numeroFuncionarioInicial;
    }   
}
