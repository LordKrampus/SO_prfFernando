package estruturasDados.elemento;

public class Elemento {
    private int[] valor;
    private Elemento proximo;

    public Elemento(int[] valor, Elemento proximo){
        this.valor = valor;
        this.proximo = proximo;
    }

    public int[] getValor(){
        return this.valor;
    }

    public Elemento getProximo(){
        return this.proximo;
    }

    public void setProximo(Elemento proximo){
        this.proximo = proximo;
    }
}
