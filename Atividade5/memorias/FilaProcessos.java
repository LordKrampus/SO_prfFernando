package memorias;

import entidades.Processo;

public class FilaProcessos extends Memoria {
    
    public FilaProcessos(){
        super();
    }
    
    @Override
    public Processo retirar(){
        return (Processo)super.retirar();
    }
}
