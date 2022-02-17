package Componente;

public class Garfo {
    private int id;
    private boolean estado;

    public Garfo(int id){
        this.id = id;
        this.estado = true;
    }

    public int getId(){
        return this.id;
    }

    public boolean getEstado(){
        return this.estado;
    }

    public void setEstado(boolean estado)throws Exception{
        if(!this.estado && !estado) // abaixar sinal já abaixado, então, ocupado.
            throw new Exception(".exception - componente (prato) está ocupado.");

        this.estado = estado;
    }
}
