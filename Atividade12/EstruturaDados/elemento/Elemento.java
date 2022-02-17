package EstruturaDados.elemento;

public class Elemento {
    private Object valor;
    protected Elemento proximo;
    protected Elemento anterior;

    public Elemento(Object valor, Elemento proximo, Elemento anterior){
        this.valor = valor;
        this.proximo = proximo;
        this.anterior = anterior;
    }

    public Object getValor(){
        return this.valor;
    }

    public Elemento getProximo(){
        return this.proximo;
    }

    public Elemento getAnterior(){
        return this.anterior;
    }

    public void setProximo(Elemento proximo){
        this.proximo = proximo;
    }

    public void setAnterior(Elemento anterior){
        this.anterior = anterior;
    }
}
