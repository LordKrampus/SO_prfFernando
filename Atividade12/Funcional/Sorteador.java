package Funcional;

/**
 * Delimita uma probabilidade e deve sortear um valor delimitando-o se atingiu a probabilidade.
 */
public class Sorteador {
    private int valor;
    private boolean sorte;
    private int probabilidade;
    
    public Sorteador(){
        this.valor = 0;
        this.sorte = false;
        this.probabilidade = 0;
    }

    public boolean getSorte(){
        return this.sorte;
    }
    
    public int getValor(){
        return this.valor;
    }

    public boolean sortear(){
        this.valor = GeradorAleatorio.gerarInt(1, 100);

        sorte = this.valor <= this.getProbabilidade();

        return this.sorte;
    }
    
    public int getProbabilidade(){
        return this.probabilidade;
    }

    /**
    * @param probabilidade Precisa estar definido entre o intervalo de 0 a 100. Para:
    * 0 -> nunca ocorre; e, 1 a 100, provavel ocorrer.
    */
    public void setProbabilidade(int probabilidade){
        this.probabilidade = probabilidade;
    }
}
