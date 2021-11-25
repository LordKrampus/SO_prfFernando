package utilidades;

import java.util.ArrayList;

public class ConsultorPID{
    private ArrayList<Integer> indices;
    private Integer indiceIterador;
    private int limiteIndices;
    private int quantidadeIndices;

    public ConsultorPID(int limiteIndices){
        this.indices = new ArrayList<>();
        this.indiceIterador = 0;

        this.limiteIndices = limiteIndices;
        this.quantidadeIndices = 0;
    }

    private void adicionarIndice(Integer indice){
        this.indices.add(indice);
        this.quantidadeIndices++;
    }

    public int devolverIndice(Integer indice){
        boolean aux_remove;

        aux_remove = this.indices.remove((Integer)indice);
        if(aux_remove){
            this.quantidadeIndices--;
        }

        return -1;
    }

    public int utilizarIndice(){
        if(!this.verificarEspaco()) return -1; // espaço cheio

        int aux_iterador;
        aux_iterador = this.indiceIterador;
        do{
            if(this.indiceIterador >= limiteIndices){
                this.indiceIterador = 0;
                continue;
            }

            if(!this.verificarIndice(this.indiceIterador)){ 
                this.indiceIterador++;
                continue;
            }

            //else
            this.adicionarIndice(this.indiceIterador);
            return this.indiceIterador;
            
        }while(aux_iterador != this.indiceIterador);

        return -1;
    }

    public int utilizarIndice(Integer indice){
        if(!this.verificarEspaco()) return -1; // espaço cheio

        if(!this.verificarIndice(indice)) return -1; // indice já está em uso

        this.adicionarIndice(indice);

        return indice;
    }

    public boolean verificarIndice(Integer indice){
        return !this.indices.contains(indice);
    }

    public boolean verificarEspaco(){
        return this.quantidadeIndices < this.limiteIndices;
    }
}