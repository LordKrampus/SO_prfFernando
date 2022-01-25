package estruturasDados.fila;

import enumeracoes.ReferenciaAtributoTP;
import estruturasDados.elemento.ElementoDL;

public class FilaCircular{
    private ElementoDL top;
    private ElementoDL first;
    private ElementoDL iterador;
    private int tamanho;

    public FilaCircular(){
        this.top = null;
        this.first = null;
        this.iterador = null;
        this.tamanho = 0;
    }

    public int getTamanho(){
        return this.tamanho;
    }

    public int[] getTop(){
        return this.top.getValor();
    }

    public int[] getFirst(){
        return this.first.getValor();
    }

    public boolean estaVazia(){
        return this.iterador == null;
    }

    public int[] getIterador(){
        return this.iterador.getValor();
    }

    public void incrementarIterador(){
        this.iterador = this.iterador.getAnterior();
    }

    public void add(int[] valor){
        ElementoDL topBefore = this.top;

        ElementoDL elementoNovo = new ElementoDL(valor, topBefore, this.first);
        this.top = elementoNovo;

        if(this.first == null) {
            this.first = this.top;
            this.iterador = this.first;
        }

        this.top.setAnterior(this.first);
        this.first.setProximo(this.top);

        if(topBefore == null) {
            this.top.setProximo(this.first);
        }else{
            topBefore.setAnterior(this.top);
            this.top.setProximo(topBefore);
        }

        this.tamanho++;
    }

    public int[] remover(){
        if(this.estaVazia()) return null;

        // referencias de alocação de memoria (variáveis)
        int[] valorRemovido;
        ElementoDL refIterador;

        // fim inescapavel - só tem um elemento, e esse será removido.
        if(this.top == this.first){
            valorRemovido = this.iterador.getValor();
            this.top = null;
            this.first = null;
            this.iterador = null;

            this.tamanho--;

            return valorRemovido;
        }

        // guardando referencia do elemento removido
        refIterador = this.iterador;
        valorRemovido = refIterador.getValor();

        // removendo referencia do elemento alvo da conexao da estrutura de dados
        this.iterador = this.iterador.getAnterior();
        this.iterador.setProximo(refIterador.getProximo());
        refIterador.getProximo().setAnterior(this.iterador);

        // ajustando referencias principais da classe
        if (refIterador == this.top){
            this.top = this.iterador.getProximo();
        }
        if (refIterador == this.first){
            this.first = this.iterador;
        }

        // ajustes terminais do método
        this.tamanho--;

        return valorRemovido;
    }

    public int[] busca(int numero){
        ElementoDL iteradorAux = this.top;

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

        ElementoDL iterador = this.top;

        if(iterador == null)
            return "Null";

        int i;
        int[] valor;
        do{
            valor = iterador.getValor();
            for(i = 0; i < valor.length; i++) {
                sb.append(valor[i] + " ");
            }

            if(iterador == this.top){
                sb.append("\t(top)");
            }
            if(iterador == this.first){
                sb.append("\t(first)");
            }
            if(iterador == this.iterador){
                sb.append("\t(iterador)");
            }

            sb.append("\n");
            iterador = iterador.getProximo();
        }while(iterador != this.top);

        return sb.toString();
    }
}

        /*
        if(this.iterador == this.top){
            this.top = this.top.getProximo();
            this.top.setAnterior(this.iterador.getAnterior());
            this.iterador.getAnterior().setProximo(this.top);

            valorRemovido = this.iterador.getValor();
            this.iterador = this.top.getAnterior();

            this.tamanho--;

            return valorRemovido;
        }
        if(this.iterador == this.first){
            this.first = this.first.getAnterior();
            this.first.setProximo(this.iterador.getProximo());
            this.iterador.getProximo().setAnterior(this.first);

            valorRemovido = this.iterador.getValor();
            this.iterador = this.first;

            this.tamanho--;

            return valorRemovido;
        }
         */