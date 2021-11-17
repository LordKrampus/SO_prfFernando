package utilidades;

import java.util.LinkedList;

public class ReferenciaContexto {
    private LinkedList<Object> objetos;
    
    public ReferenciaContexto(){
        this.objetos = new LinkedList();
    }
    
    public void add(Object objeto){
        this.objetos.add(objeto);
    }
    
    public Object getObject(int id){
        return this.objetos.get(id);
    }
}
