package memorias;

import java.util.LinkedList;

public abstract class Memoria implements InterfaceMemoria {

    private LinkedList<Object> lista;

    protected Memoria(){
        this.lista = new LinkedList<>();
    }

    public void adicionar(Object objeto){
        this.lista.add(objeto);
    }
    
    public Object retirar(){
        if(this.lista.isEmpty()) return null;
        
        return this.lista.pop();
    }
    
    public boolean estaVazia(){
        return this.lista.isEmpty();
    }

    public int getTamanho(){
        return this.lista.size();
    }

    protected Object[] getLista(){
        return this.lista.toArray();
    }
}
