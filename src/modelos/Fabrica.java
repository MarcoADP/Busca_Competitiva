package modelos;

public enum Fabrica {
    PEQUENA("Pequena", 400000, 1, 30000, 500),
    MEDIA("Média", 500000, 2, 60000, 800),
    GRANDE("Grande", 600000, 3, 90000, 1000);
    // Pequena (mais barata, produz 1 carro por vez, R$ 30.000 de custo por mês)
    // Media (preço médio, produz 2 carros por vez, R$ 60.000 de custo por mês)
    // Grande (mais cara, produz 3 carros por vez, R$ 90.000 de custo por mês)

    private final String nome;
    private final int preco;
    private final double fatorProducao;
    private final int gastoPorMes;
    private final int capacidade;

    Fabrica(String nome, int preco, double fatorProducao, int gasto, int capacidade) {
        this.nome = nome;
        this.preco = preco;
        this.fatorProducao = fatorProducao;
        this.gastoPorMes = gasto;
        this.capacidade = capacidade;
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
}