package Componente;

public class Cadeira {
    private int id;
    private boolean estado;
    private Filosofo filosofo;
    private Mesa mesa;

    public Cadeira(int id, Mesa mesa){
        this.id = id;
        this.estado = false;

        this.filosofo = null;
        this.mesa = mesa;
    }

    public int getId(){
        return this.id;
    }

    public boolean getEstado(){
        return this.estado;
    }

    public Filosofo getFilosofo(){
        return this.filosofo;
    }

    /*
    public Servico getServico(){
        return this.mesa.getServico(this);
    }
    */

    protected boolean setEstado(){
        return this.estado = !this.estado;
    }

    protected void setFilosofo(Filosofo filosofo){
        this.filosofo = filosofo;

        if(filosofo == null) {
            this.estado = false;
            return;
        }
        else this.estado = true;

        this.filosofo.setCadeira(this);
    }

    public Mesa getMesa(){
        return this.mesa;
    }

    @Override
    public String toString(){
        return String.format("%s | \tCadeira %d", this.filosofo.toString(), this.id);
    }
}
