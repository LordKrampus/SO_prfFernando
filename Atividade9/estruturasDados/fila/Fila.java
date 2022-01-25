package estruturasDados.fila;

import enumeracoes.ReferenciaAtributoTP;
import estruturasDados.elemento.Elemento;

public class Fila {
    protected Elemento top;
    protected int tamanho;

    public Fila(){
        this.top = null;
        this.tamanho = 0;
    }

    public int getTamanho(){
        return this.tamanho;
    }

    public int[] getTop(){
        return this.top.getValor();
    }

    public int[] getFirst(){
        if(this.estaVazia()) return null;

        if(this.top.getProximo() == null) {
            this.top = null;
            return null;
        }

        Elemento iterador = this.top;
        while(iterador.getProximo() != null){
            iterador = iterador.getProximo();
        }

        return iterador.getValor();
    }

    public boolean estaVazia(){
        return this.top == null;
    }

    public void add(int[] valor){
        Elemento elementoNovo = new Elemento(valor, this.top);

        this.top = elementoNovo;

        this.tamanho++;
    }

    public int[] remover(){
        if(this.estaVazia()) return null;

        int[] valorRemovido;

        if(this.top.getProximo() == null) {
            valorRemovido = this.top.getValor();
            this.top = null;

            return valorRemovido;
        }

        Elemento iterador = this.top;
        while(iterador.getProximo().getProximo() != null){
            iterador = iterador.getProximo();
        }

        valorRemovido = iterador.getProximo().getValor();
        iterador.setProximo(null);

        this.tamanho--;

        return valorRemovido;
    }

    public int[] busca(int numero){
        Elemento iteradorAux = this.top;

        int[] valor = null;
        do{
            valor = iteradorAux.getValor();
            if(valor[ReferenciaAtributoTP.N.ordinal()] == numero)
                return valor;

            iteradorAux = iteradorAux.getProximo();
        }while(iteradorAux == this.top);

        return valor;
    }

    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();

        Elemento iterador = this.top;

        if(iterador == null)
            return "Null";

        int i;
        int[] valor;
        do{
            valor = iterador.getValor();
            for(i = 0; i < valor.length; i++)
                sb.append(valor[i] + " ");

            if(iterador == this.top){
                sb.append("\t(top)");
            }

            sb.append("\n");
            iterador = iterador.getProximo();
        }while(iterador != null);

        return sb.toString();
    }
}
