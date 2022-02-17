package EstruturaDados.Fila;

import Componente.Cadeira;
import EstruturaDados.elemento.Elemento;

public class FilaCircular{
    protected Elemento top;
    protected Elemento first;
    protected Elemento iterador;
    protected int tamanho;
    protected int maximo;

    public FilaCircular(int maximo){
        this.top = null;
        this.first = null;
        this.iterador = null;
        this.tamanho = 0;
        this.maximo = maximo;
    }

    public int getTamanho(){
        return this.tamanho;
    }

    public Object getTop(){
        return this.top.getValor();
    }

    public Object getFirst(){
        return this.first.getValor();
    }

    public Object getIterador(){
        return this.iterador.getValor();
    }

    public boolean estaVazia(){
        return this.iterador == null;
    }

    public boolean estaCheia(){
        return this.tamanho == this.maximo;
    }

    public void incrementarIterador(){
        this.iterador = this.iterador.getAnterior();
    }

    public void decrementarIterador(){
        this.iterador = this.iterador.getProximo();
    }

    public void add(Object valor) throws StackOverflowError{
        if(this.estaCheia()) throw new StackOverflowError(".error - A estrutura está cheia.");

        Elemento topBefore = this.top;

        Elemento elementoNovo = new Elemento(valor, topBefore, this.first);
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

    public Object remover() throws StackOverflowError{
        if(this.estaVazia()) throw new StackOverflowError(".error - A estrutura está vazia.");

        // referencias de alocação de memoria (variáveis)
        Object valorRemovido;
        Elemento refIterador;

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

    public Object buscar(Object busca){
        if(this.estaVazia()) return null;

        Elemento iteradorAux = this.top;

        Object valor = null;
        do{
            valor = iteradorAux.getValor();
            if(valor == busca) {

                return iteradorAux;
            }

            iteradorAux = iteradorAux.getProximo();
        }while(iteradorAux != this.top);

        return null;
    }



    /*
    public void redistribuirContexto(Mesa mesa){
        int idCadeira, nivelFome;
        Garfo garfo;


        //verifica iterador
        idCadeira = this.iterador.getValor().getId();
        nivelFome = this.iterador.getValor().getFilosofo().getNivelFome();

        garfo = mesa.buscarGarfo(idCadeira);

        if(garfo.getEstado() == false);
    }
*/


    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();

        Elemento iterador = this.top;

        if(iterador == null)
            return "Null";

        Object valor;
        do{
            valor = iterador.getValor();

            sb.append(valor.toString() + " ");

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

    public String[] toStrArray(){
        if(this.estaVazia())
            return null;

        String[] saida = new String[this.tamanho];

        Elemento iterador = this.top;

        Object valor;
        int i = 0;
        do{
            valor = iterador.getValor();

            saida[i] = valor.toString();

            if(iterador == this.top){
                saida[i] += "\t(top)";
            }
            if(iterador == this.first){
                saida[i] += "\t(first)";
            }
            if(iterador == this.iterador){
                saida[i] += "\t(iterador)";
            }

            iterador = iterador.getProximo();
            i++;
        }while(iterador != this.top);

        return saida;
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