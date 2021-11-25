package memorias;

import entidades.Processo;

public class GerenciadorFilas {
    private FilaProcessos[] filas;
    
    public GerenciadorFilas(int numFilas){
        this.filas = new FilaProcessos[numFilas];
        
        for(int i = 0; i < numFilas; i++){
            this.filas[i] = new FilaProcessos();
        }
    }
    
    public void adicionarProcesso(int fila, Processo processo){
        this.filas[fila].adicionar(processo);
    }
    
    public Processo retirarProcesso(int fila){
        return this.filas[fila].retirar();
    }
    
    public boolean estaVazia(int fila){
        return this.filas[fila].estaVazia();
    }
    
}
