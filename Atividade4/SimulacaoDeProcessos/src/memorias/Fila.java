package memorias;

import java.util.LinkedList;
import entidades.Processo;



public class Fila {
   
    private LinkedList<Processo> fila;
    
    public Fila(){
        this.fila = new LinkedList();
    }
    
    public void adicionarProcesso(Processo processo){
        this.fila.add(processo);
    }
    
    public Processo retirarProcesso(){
        if(this.fila.isEmpty()) return null;
        
        return this.fila.pop();
    }
    
    public boolean estaVazia(){
        return this.fila.isEmpty();
    }
}
