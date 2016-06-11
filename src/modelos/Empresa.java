package modelos;

public class Empresa {
    
    public final static double INVESTIMENTO_MARKETING_NORMAL = 0.25; // 25% de investimento do capital
    public final static double INVESTIMENTO_MARKETING_ALTO = 0.40;   // 40% de investimento do capital
    
    public final static int MARKETING_NORMAL = 0;
    public final static int MARKETING_ALTO = 1;
    
    public final static int FATOR_FUNCIONARIO_CONTRATAR = 10;
    public final static int FATOR_FUNCIONARIO_DEMITIR = 10;
    
    public final static int SALARIO_FUNCIONARIO = 1000;
    
    public final static int LIMITE_FUNCIONARIO = 100;
    
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
    private int tipoMarketing;

    public Empresa(String nome, int investimento, boolean isBot, Fabrica fabrica){
        this.nome = nome;
        this.capital = investimento;
        this.carro = new Carro(Carro.MODELO_POPULAR, Carro.TIPO_PRECO_NORMAL);        
        this.isBot = isBot;
        this.estoqueCarro = 0;
        this.investimentoMarketing = 0;
        this.tipoMarketing = MARKETING_NORMAL;
        this.limiteFuncionarios = LIMITE_FUNCIONARIO;      //Número máximo de funcionários por empresa
        this.salarioFuncionario = SALARIO_FUNCIONARIO;
        this.fabrica = fabrica;
        this.numeroFuncionarios = this.fabrica.getNumeroFuncionarioInicial();
        //atualizarGastosFuncionarios();
        // atributos padrões
    }

    public Empresa(Empresa empresa, int opInv, int opPreco, int opFunc) {
        this.nome = empresa.nome;
        
        //recebe atributos pai
        this.capital = empresa.capital;
        this.carro = empresa.carro;
        this.fabrica = empresa.fabrica;
        this.isBot = empresa.isBot;
        this.estoqueCarro = empresa.estoqueCarro;
        
        //alterar Atributos Variáveis
        this.carro.alterarPreco(opPreco);
                
        //atualizar Marketing
        this.tipoMarketing = opInv;
        this.investimentoMarketing = this.investir(opInv);
        this.capital = this.atualizarCapitalMarketing(opInv);
        
        this.probabilidadeVenda = this.calcularProbabilidade(opPreco, opInv);
        
        //Fabricar carros
        //System.out.println("CARRROS => " + this.carrosPorMes());
        //this.estoqueCarro += this.carrosPorMes();
        //System.out.println("Estoque => " + this.estoqueCarro);
        double custo = this.fabricarCarro();
        //System.out.println("Capital => " + this.capital);
        //System.out.println("Custo => " + custo);
        this.capital -= custo;
        //System.out.println("Capital => " + this.capital);
        
        //Vender Carros
        //System.out.println("Prob => " + this.probabilidadeVenda + "Estoque => " + this.estoqueCarro);
        int vendaEstimada = this.estoqueCarro * this.probabilidadeVenda/100;
        //System.out.println("Venda => " + vendaEstimada + "Preco => " + this.carro.getPrecoVenda());
        int ganhoVenda = vendaEstimada * this.carro.getPrecoVenda();
        //System.out.println("ganho Venda => "+ ganhoVenda);
        this.capital += ganhoVenda;
        //System.out.println("Capital => " + this.capital);
        this.estoqueCarro -= vendaEstimada;
        
        
        //atualizar funcionarios
        this.limiteFuncionarios = empresa.limiteFuncionarios;
        this.salarioFuncionario = empresa.salarioFuncionario;
        this.numeroFuncionarios = this.atualizarNumeroFuncionarios(opFunc); 
        this.limiteFuncionarios = this.atualizarLimiteFuncionarios(opFunc);
        this.gastoFuncionarios = this.atualizarGastosFuncionarios();
        //System.out.println("sadaa =>" + this.numeroFuncionarios);
        //this.mostraEmpresa();
        //Acho que tudo foi atualizado. Agora falta a logica do jogo.
    }
    

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
    
    public int calcularCarrosProduzidos(){
        int produzidos;
        if (carrosPorMes() + estoqueCarro > fabrica.getCapacidade()){
            produzidos = fabrica.getCapacidade() - estoqueCarro;
        } else {
            produzidos = carrosPorMes();
        }
        return produzidos;
    }
    
    public double calcularGastoProducaoCarros(){
        return calcularCarrosProduzidos() * carro.getCusto();
    }
    
    public double calcularGastoFixo(){
        double gasto = calcularGastoProducaoCarros();
         gasto += this.fabrica.getGastoPorMes();
         return gasto;
     }
     
    public int calcularGastoFuncionario(int numFuncionario){
        return numFuncionario * SALARIO_FUNCIONARIO;
    }
    
    public double calcularGastoMensal(){
        return calcularGastoFixo() + calcularGastoFuncionario(numeroFuncionarios);
    }
    
    public void fecharMes(){ //BASTA CHAMAR ESSA FUNÇÃO PRA RETORNAR O GASTO MENSAL E ATUALIZAR O CAPITAL
        double gastoMensal = calcularGastoMensal();//getGastoFuncionarios()  + this.investimentoMarketing + gastoFixo();
        this.capital -= gastoMensal; 
        this.probabilidadeVenda = calcularProbabilidade();
    }
    
    public double fabricarCarro(){
        int auxiliar = this.estoqueCarro + carrosPorMes();
        if (auxiliar <= fabrica.getCapacidade()){ //NÃO POSSO PRODUZIR MAIS CARROS, ENTÃO RETORNO O CAPITAL INVESTIDO
            this.estoqueCarro += auxiliar;            
            return (carrosPorMes()*this.carro.getCusto());
            
        } else {
            auxiliar = fabrica.getCapacidade() - this.estoqueCarro; //QTD MÁXIMA DE CARROS QUE POSSO PRODUZIR
            this.estoqueCarro = fabrica.getCapacidade();
            return (auxiliar*this.carro.getCusto());
        }        
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
        int probabilidade = this.calcularProbPreco(preco) + this.calcularProbMarketing(marketing);        
        return probabilidade;
    }
    
    public int calcularProbabilidade(){
        return calcularProbabilidade(this.carro.getTipoPreco(), this.tipoMarketing);
    }
    
    public Double atualizarCapitalMarketing(int opcao){
        //this.probabilidadeVenda = this.calcularProbabilidade(this.carro.getTipoPreco(), opcao);
        switch(opcao){
            case MARKETING_NORMAL:
                return this.capital * (1 - INVESTIMENTO_MARKETING_NORMAL);
            case MARKETING_ALTO:
                return this.capital * (1 - INVESTIMENTO_MARKETING_ALTO);
        }
        return -1.0;
    }
    
    public Double investir(int opcao){
        //this.probabilidadeVenda = this.calcularProbabilidade(this.carro.getTipoPreco(), opcao);
        switch(opcao){
            case MARKETING_NORMAL:
                return this.capital * INVESTIMENTO_MARKETING_NORMAL;
                //this.capital = this.capital * 0.75;
            case MARKETING_ALTO:
                return this.capital * INVESTIMENTO_MARKETING_ALTO;
                //this.capital = this.capital * 0.6;
        }
        return -1.0;
    }
    
    public Double atualizarGastosFuncionarios(){
        return this.salarioFuncionario * this.numeroFuncionarios;
    }
    
    public int atualizarNumeroFuncionarios(int opcao){
        // 1 -> contrata 10
        // 0 -> demite 10 ou o restante
        if(opcao == 1 && this.limiteFuncionarios >= 10){
            return this.numeroFuncionarios + 10;
        }
        if(opcao == 0 && this.numeroFuncionarios < 10){
            return this.numeroFuncionarios = 0;
        } 
        if(opcao == 0 && this.numeroFuncionarios >= 10){
            return this.numeroFuncionarios - 10;
        }
        return this.numeroFuncionarios;
    }
    
    public int atualizarLimiteFuncionarios(int opcao){
        // 1 -> contrata 10
        // 0 -> demite 10 ou o restante
        if(opcao == 1 && this.limiteFuncionarios >= 10){
            return this.limiteFuncionarios - 10;            
        }
        if(opcao == 0 && this.numeroFuncionarios < 10){
            return this.limiteFuncionarios + this.numeroFuncionarios;
        } 
        if(opcao == 0 & this.numeroFuncionarios >= 10){
            return this.limiteFuncionarios + 10;
        }
        return this.limiteFuncionarios;
    }
    
    public void venderCarro(){
        this.capital += carro.getPrecoVenda();
        this.estoqueCarro--;
    }
    
    public void realizarAcoes(int opMarketing, int opPreco, int opFunc) {
        //Atualizar Preço Carro
        this.carro.alterarPreco(opPreco);
        
        //Atualizar Marketing
        this.tipoMarketing = opMarketing;
        this.investimentoMarketing = this.investir(opMarketing);
        this.capital = this.atualizarCapitalMarketing(opMarketing);
        
        //Atualizar Probabilidade
        this.probabilidadeVenda = this.calcularProbabilidade(opPreco, opMarketing);
        
        //Fabricar Carros
        double custo = this.fabricarCarro();
        this.capital -= custo;
        
        //Atualizar Funcionários
        this.numeroFuncionarios = this.atualizarNumeroFuncionarios(opFunc);
        this.limiteFuncionarios = this.atualizarLimiteFuncionarios(opFunc);
        this.gastoFuncionarios = this.atualizarGastosFuncionarios();
        
        
    }
    
    public void escolherAcoes(int id){
        switch(id){
            case 0: //MN PN FD
                this.realizarAcoes(0, 0, 0);
                break;
            case 1: //MN PN FM
                this.realizarAcoes(0, 0, 100);
                break;
            case 2: //MN PN FC
                this.realizarAcoes(0, 0, 1);
                break;
                
                
            case 3: //MN PA FM
                this.realizarAcoes(0, 1, 100);
                break;
            case 4: //MN PA FC
                this.realizarAcoes(0, 1, 1);
                break;
                
            case 5: //MA PN FM
                this.realizarAcoes(1, 0, 100);
                break;
            case 6: //MA PN FC
                this.realizarAcoes(1, 0, 1);
                break;
                
            case 7: //MA PA FM
                this.realizarAcoes(1, 1, 100);
                break;
            case 8: //MA PA FC
                this.realizarAcoes(1, 1, 1);
                break;
        }
    }
    
    public void mostraEmpresa(){
        System.out.println("====================================");
        System.out.println("Nome => " + this.nome);
        System.out.println("Capital => " + this.capital);
        System.out.println("Probabilidade = > " + this.probabilidadeVenda);
        System.out.println("");
        
        System.out.println("Carro");
        System.out.println("Estoque => " + this.estoqueCarro);
        System.out.println("Modelo => " + this.carro.getStringModelo());
        System.out.println("Tipo Preco => " + this.carro.getTipoPreco());
        System.out.println("Preco de Venda =>" + this.carro.getPrecoVenda());
        System.out.println("");
        
        System.out.println("Fabrica");
        System.out.println("Nome => " + this.fabrica.getNome());
        System.out.println("Numero Funcionarios => " + this.numeroFuncionarios);
        System.out.println("Salario Funcionarios => " + this.salarioFuncionario);
        System.out.println("Gastos Funcionarios => " + this.gastoFuncionarios);
        System.out.println("");
        
        System.out.println("Investimento => " + this.investimentoMarketing);
        //System.out.println("Gastos Totais => " + this.gastosTotais);
        System.out.println("Probabilidade Venda => " + this.probabilidadeVenda);
        System.out.println("");
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

    public int getTipoMarketing() {
        return tipoMarketing;
    }

    public void setTipoMarketing(int tipoMarketing) {
        this.tipoMarketing = tipoMarketing;
    }
    
}