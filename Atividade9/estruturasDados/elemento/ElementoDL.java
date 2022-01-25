package estruturasDados.elemento;

public class ElementoDL {
    private int[] valor;
    private ElementoDL proximo;
    private ElementoDL anterior;

    public ElementoDL(int[] valor, ElementoDL proximo, ElementoDL anterior){
        this.valor = valor;
        this.proximo = proximo;
        this.anterior = anterior;
    }

    public int[] getValor(){
        return this.valor;
    }

    public ElementoDL getProximo(){
        return this.proximo;
    }

    public ElementoDL getAnterior(){
        return this.anterior;
    }

    public void setProximo(ElementoDL proximo){
        this.proximo = proximo;
    }

    public void setAnterior(ElementoDL anterior){
        this.anterior = anterior;
    }
}
