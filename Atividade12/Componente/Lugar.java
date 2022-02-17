package Componente;

import EstruturaDados.elemento.Elemento;

public class Lugar extends Elemento {
    private Garfo garfoE; // garfo esquerda
    private Garfo garfoD; // garfo direita
    private Prato prato;
    private boolean prioridade;

    public Lugar(Cadeira cadeira, Garfo garfo, Prato prato, Elemento proximo, Elemento anterior){
        super(cadeira, proximo, anterior);

        //contexto especifico local
        //Lugar aux = (Lugar)proximo;

        //this.garfoE =  aux.getGarfoD(); // garfo direito do lugar à esquerda

        this.garfoD = garfo; // garfo a direita - garfo comum
        this.prato = prato;

        this.prioridade = false;
    }

    public Garfo getGarfoE(){
        return this.garfoE;
    }

    public void setGarfoE(Garfo garfo){
        this.garfoE = garfo;
    }

    public Garfo getGarfoD(){
        return this.garfoD;
    }

    public Prato getPrato() {
        return this.prato;
    }

    public boolean getPrioridade(){
        return this.prioridade;
    }

    public void setPrioridade(boolean prioridade){
        this.prioridade = prioridade;
    }
}
