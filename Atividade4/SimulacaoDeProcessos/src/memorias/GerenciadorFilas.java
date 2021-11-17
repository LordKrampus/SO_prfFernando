package memorias;

import entidades.Processo;

public class GerenciadorFilas {
    private Fila[] filas;
    
    public GerenciadorFilas(int numFilas){
        this.filas = new Fila[numFilas];
        
        for(int i = 0; i < numFilas; i++){
            this.filas[i] = new Fila();
        }
    }
    
    public void adicionarProcesso(int fila, Processo processo){
        this.filas[fila].adicionarProcesso(processo);
    }
    
    public Processo retirarProcesso(int fila){
        return this.filas[fila].retirarProcesso();
    }
    
    public boolean estaVazia(int fila){
        return this.filas[fila].estaVazia();
    }
    
}
