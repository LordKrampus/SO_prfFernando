package Componente;

public class Prato {
    private int id;
    private boolean estado;
    private int quantidade;

    public Prato(int id){
        this.id = id;
        this.estado = true;

        this.quantidade = 100;
    }

    public int getId(){
        return this.id;
    }

    public boolean vazio(){
        return this.quantidade <= 0;
    }

    public boolean setEstado(boolean estado)throws Exception{
        if(!this.estado && !estado) // abaixar sinal já abaixado, então, ocupado.
            throw new Exception(".exception - componente (prato) está ocupado.");

        return this.estado = estado;
    }

    public void servir(Filosofo filosofo){
        //this.quantidade -= Math.ceil(filosofo.getNivelFome()); // o teto, para que não seja zero.
        this.quantidade -= 10;
    }

    @Override
    public String toString(){
        String saida = "";
        for(int i = 0; i < Math.round(this.quantidade/10); i++){
            saida += '#';
        }

        return saida;
    }
}
