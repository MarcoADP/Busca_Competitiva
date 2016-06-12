package modelos;

public class Empresa {
    
    public final static double INVESTIMENTO_MARKETING_NORMAL = 0.02; // 5% de investimento do capital
    public final static double INVESTIMENTO_MARKETING_ALTO = 0.05;   // 10% de investimento do capital
    
    public final static int MARKETING_NORMAL = 0;
    public final static int MARKETING_ALTO = 1;
    
    public final static int FATOR_FUNCIONARIO_CONTRATAR = 10;
    public final static int FATOR_FUNCIONARIO_DEMITIR = 10;
    
    public final static int SALARIO_FUNCIONARIO = 1000;
    public final static int LIMITE_FUNCIONARIO = 100;      //Número máximo de funcionários por empresa
    
    //CONFIGURAÇÃO INICIAL
    private String nome;
    private double capital;
    
    private Carro carro;
    private Fabrica fabrica;
    
    private int estoqueCarro;
    
    private int numeroFuncionarios;
    private int limiteFuncionarios;
    
    private double gastosTotais;
    private int probabilidadeVenda;
    
    private final boolean isBot;          // true se for IA, false se for jogador
            
    //PARÂMETROS VARIÁVEIS
    private double investimentoMarketing;
    private int tipoMarketing;
    
    private double lucrouNoMes;
    private int carrosVendidosNoMes;

    public Empresa(String nome, int investimento, boolean isBot){
        this.nome = nome;
        this.capital = investimento;
        this.isBot = isBot;
        if (isBot){
            this.carro = Carro.aleatorio();
            setFabrica(Fabrica.aleatorio());
        } else {
            this.carro = new Carro(Carro.MODELO_POPULAR, Carro.TIPO_PRECO_NORMAL);
            setFabrica(Fabrica.PEQUENA);
        }
        this.estoqueCarro = 0;
        this.investimentoMarketing = 0;
        this.tipoMarketing = MARKETING_NORMAL;
        this.limiteFuncionarios = LIMITE_FUNCIONARIO;
        this.numeroFuncionarios = this.fabrica.getNumeroFuncionarioInicial();
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
        double custo = this.fabricarCarros();
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
        this.numeroFuncionarios = this.atualizarNumeroFuncionarios(opFunc); 
        this.limiteFuncionarios = this.atualizarLimiteFuncionarios(opFunc);
        //System.out.println("sadaa =>" + this.numeroFuncionarios);
        //this.mostraEmpresa();
        //Acho que tudo foi atualizado. Agora falta a logica do jogo.
    }
    
    public boolean temEstoque(){
        return estoqueCarro > 0;
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
    
    public int calcularGastoFuncionarios(){
        return calcularGastoFuncionario(numeroFuncionarios);
    }
    
    public double calcularInvestimentoMarketing() {
        return investir(tipoMarketing);
    }
    
    public double calcularGastoMensal(){
        return calcularGastoFixo() + calcularGastoFuncionario(numeroFuncionarios) + calcularInvestimentoMarketing();
    }
    
    public void fecharMes(){
        lucrouNoMes = 0;
        carrosVendidosNoMes = 0;
        
        double gastoMensal = calcularGastoMensal();
        this.capital -= gastoMensal; 
        this.probabilidadeVenda = calcularProbabilidade();
        this.fabricarCarros();
    }
    
    public void venderCarro() {
        carrosVendidosNoMes++;
        lucrouNoMes += carro.getPrecoVenda();
        this.capital += carro.getPrecoVenda();
        this.estoqueCarro--;
    }
    
    public double fabricarCarros(){
        this.estoqueCarro += calcularCarrosProduzidos();
        return calcularGastoProducaoCarros();
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
        switch(opcao){
            case MARKETING_NORMAL:
                return this.capital * (1 - INVESTIMENTO_MARKETING_NORMAL);
            case MARKETING_ALTO:
                return this.capital * (1 - INVESTIMENTO_MARKETING_ALTO);
        }
        return -1.0;
    }
    
    public Double investir(int opcao){
        switch(opcao){
            case MARKETING_NORMAL:
                return this.capital * INVESTIMENTO_MARKETING_NORMAL;
            case MARKETING_ALTO:
                return this.capital * INVESTIMENTO_MARKETING_ALTO;
        }
        return -1.0;
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
        double custo = this.fabricarCarros();
        this.capital -= custo;
        
        //Atualizar Funcionários
        this.numeroFuncionarios = this.atualizarNumeroFuncionarios(opFunc);
        this.limiteFuncionarios = this.atualizarLimiteFuncionarios(opFunc);
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
        System.out.println("Salario Funcionarios => " + SALARIO_FUNCIONARIO);
        System.out.println("Gastos Funcionarios => " + this.calcularGastoFuncionarios());
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
        if (this.fabrica != null)
            this.capital += this.fabrica.getPreco();    // devolve dinheiro de compra da fábrica
        this.fabrica = fabrica;
        this.capital -= this.fabrica.getPreco();    // desconta dinheiro de compra da nova fabrica
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

    public double getLucrouNoMes() {
        return lucrouNoMes;
    }

    public int getCarrosVendidosNoMes() {
        return carrosVendidosNoMes;
    }
    
}