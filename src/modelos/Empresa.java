package modelos;

public class Empresa {
    
    public final static int INVESTIMENTO_MARKETING_NORMAL = 1;
    public final static int INVESTIMENTO_MARKETING_ALTO = 2;
    
    public final static int MARKETING_NORMAL = 1;
    public final static int MARKETING_ALTO = 2;
    
    //CONFIGURAÇÃO INICIAL
    private String nome;
    private double capital;             //Começa com um valor de investimento inicial e vai modificando com o decorrer das jogadas
    
    private Carro carro;                //Produto
    private Fabrica fabrica;
    
    private int estoqueCarro;
    
    private int numeroFuncionarios;
    private double salarioFuncionario;
    private double gastoFuncionarios;
    private int limiteFuncionarios;
    
    private double gastosTotais;
    private int probabilidadeVenda;
    
    private final boolean isBot;          // true se for IA, false se for jogador
            
    //PARÂMETROS VARIÁVEIS
    private double investimentoMarketing;

    public Empresa(String nome, int investimento, boolean isBot){
        this.nome = nome;
        this.capital = investimento;
        this.carro = new Carro(Carro.MODELO_POPULAR, Carro.TIPO_PRECO_NORMAL);
        this.fabrica = Fabrica.PEQUENA;
        this.isBot = isBot;
        this.estoqueCarro = 0;
        // atributos padrões
    }
    
    /*public Empresa(double investimentoInicial, int tamanhoFabrica, int numeroFuncionarios, double salarioFuncionario, int modelo, int tipo_preco, int limiteFuncionarios, int tipo_investimento) {
        this.carro = new Carro(modelo, tipo_preco);
        this.capital = investimentoInicial;
        this.tamanhoFabrica = tamanhoFabrica;
        this.numeroFuncionarios = numeroFuncionarios;
        this.limiteFuncionarios = limiteFuncionarios;
        this.salarioFuncionario = salarioFuncionario;
        this.gastoFuncionarios = this.salarioFuncionario * this.numeroFuncionarios;
        
        switch (this.tamanhoFabrica) {
            case FABRICA_PEQUENA:
                this.quantidadeCarro = 500;
                this.custoFabricacao = (int) (this.carro.getCusto() * 0.9);
                this.tempoFabricacao = (int) (this.carro.getTempo() * 1.5);
                break;
            case FABRICA_MEDIA:
                this.quantidadeCarro = 800;
                this.custoFabricacao = this.carro.getCusto();
                this.tempoFabricacao = this.carro.getTempo();
                break;
            default:
                this.quantidadeCarro = 1000;
                this.custoFabricacao = (int) (this.carro.getCusto() * 1.2);
                this.tempoFabricacao = (int) (this.carro.getTempo() * 0.8);
                break;
        }
        this.gastosTotais = calcularGasto();
        this.probabilidadeVenda = calcularProbabilidade(tipo_preco, tipo_investimento);
    }*/

    public boolean atualizaEstoque(){
        if(this.estoqueCarro > 0){
            this.estoqueCarro = this.estoqueCarro - 1;
            this.capital = this.capital + this.carro.getPrecoVenda();
            return true;
        }
        return false;
    }
    
    public double carrosPorDia(){
        return carro.getTempo() / fabrica.getFatorProducao();
    }
    
    public int carrosPorMes(){
        return (int) (30 / carrosPorDia());
    }
    
    public double gastoFixo(){
        double gasto = carrosPorMes() * carro.getCusto();
        gasto += this.fabrica.getGastoPorMes();
        return gasto;
    }
    
    public double calcularGastoPorMes(){
        double total;
        
        total = this.gastoFuncionarios  + this.investimentoMarketing + gastoFixo();
        
        return total;
    }
    
    public void fabricarCarro(int quantidade){
        this.estoqueCarro += Math.min(carrosPorMes(), fabrica.getCapacidade());
    }
    
    public void fecharMes(){
        // FAZER TODAS AS ATUALIZAÇÕES PARA O FINAL DO MÊS
    }
    
    private int calcularProbPreco(int opcao){
        int prob = 0;
        switch(opcao){
            case Carro.TIPO_PRECO_NORMAL: 
                prob += 25;
                break;
            case Carro.TIPO_PRECO_CARO:
                prob += 15;
        }
        return prob;
    }
    
    private int calcularProbMarketing(int opcao){
        int prob = 0;
        switch(opcao){
            case MARKETING_NORMAL: 
                prob += 35;
                break;
            case MARKETING_ALTO: 
                prob += 55;
        }
        
        return prob;
    }
    
    private int calcularProbabilidade(int preco, int marketing){
        int prob = 0;
        
        prob += this.calcularProbPreco(preco);
        
        prob += this.calcularProbMarketing(marketing);
        
        return prob;
    }
    
    public void investir(int opcao){
        this.probabilidadeVenda = this.calcularProbabilidade(this.carro.getTipoPreco(), opcao);
        switch(opcao){
            case INVESTIMENTO_MARKETING_NORMAL:
                this.investimentoMarketing = this.capital * 0.25;
                this.capital = this.capital * 0.75;
                break;
            case INVESTIMENTO_MARKETING_ALTO:
                this.investimentoMarketing = this.capital * 0.4;
                this.capital = this.capital * 0.6;
                break;
        }
    }
    
    public void atualizarFuncionarios(int opcao){
        // 1 -> contrata 10
        // 0 -> demite 10 ou o restante
        if(opcao == 1 && this.limiteFuncionarios >= 10){
            this.numeroFuncionarios += 10;
            this.limiteFuncionarios -= 10;
            return;
        }
        if(opcao == 2 && this.numeroFuncionarios < 10){
                this.limiteFuncionarios += this.numeroFuncionarios;
                this.numeroFuncionarios = 0;
            } else {
                this.limiteFuncionarios += 10;
                this.numeroFuncionarios -= 10;
        }
        this.gastoFuncionarios = this.salarioFuncionario * this.numeroFuncionarios;
    }
    
    public void venderCarro(){
        this.capital += carro.getPrecoVenda();
        this.estoqueCarro--;
    }
    
    public Empresa escolhaAcoes(int opMarketing, int opPreco, int opFuncionario) {
        Empresa novaEmpresa = this;
        novaEmpresa.investir(opMarketing);
        novaEmpresa.carro.alterarPreco(opPreco);
        novaEmpresa.atualizarFuncionarios(opFuncionario);
        return novaEmpresa;
    }
            
    /*GET SET*/

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isIsBot() {
        return isBot;
    }
    
    public double getCapital() {
        return capital;
    }

    public Carro getCarro() {
        return carro;
    }
    
    public void setCarro(int modelo){
        this.carro = new Carro(modelo, carro.getTipoPreco());
    }

    public Fabrica getFabrica() {
        return fabrica;
    }
    
    public void setFabrica(Fabrica fabrica){
        this.fabrica = fabrica;
    }

    public int getEstoqueCarro() {
        return estoqueCarro;
    }

    public int getNumeroFuncionarios() {
        return numeroFuncionarios;
    }

    public void setNumeroFuncionarios(int numeroFuncionarios) {
        this.numeroFuncionarios = numeroFuncionarios;
    }

    public double getSalarioFuncionario() {
        return salarioFuncionario;
    }

    public void setSalarioFuncionario(double salarioFuncionario) {
        this.salarioFuncionario = salarioFuncionario;
    }

    public double getGastoFuncionarios() {
        return gastoFuncionarios;
    }

    public void setGastoFuncionarios(double gastoFuncionarios) {
        this.gastoFuncionarios = gastoFuncionarios;
    }

    public double getInvestimentoMarketing() {
        return investimentoMarketing;
    }

    public void setInvestimentoMarketing(double investimentoMarketing) {
        this.investimentoMarketing = investimentoMarketing;
    }    

    public int getLimiteFuncionarios() {
        return limiteFuncionarios;
    }

    public void setLimiteFuncionarios(int limiteFuncionarios) {
        this.limiteFuncionarios = limiteFuncionarios;
    }

    public double getGastosTotais() {
        return gastosTotais;
    }

    public void setGastosTotais(double gastosTotais) {
        this.gastosTotais = gastosTotais;
    }

    public int getProbabilidadeVenda() {
        return probabilidadeVenda;
    }

    public void setProbabilidadeVenda(int probabilidadeVenda) {
        this.probabilidadeVenda = probabilidadeVenda;
    }
    
}
