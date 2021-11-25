package memorias;

import entidades.Instrucao;

public class ListaInstrucoes extends Memoria {

    public ListaInstrucoes(){
        super();
    }
    
    @Override
    public Instrucao retirar(){
        Instrucao valor = (Instrucao)super.retirar();

        return valor;
    }
}
