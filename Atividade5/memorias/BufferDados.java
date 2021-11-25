package memorias;

import entidades.Dados;

public class BufferDados extends Memoria{    
    public BufferDados(){
        super();
    }

    @Override
    public Dados retirar(){
        return (Dados)super.retirar();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("bufferDados: ");

        Object[] lista = super.getLista();
        int comprimento = lista.length;
        for (int i = 0; i < comprimento; i++){
            sb.append(((Dados)lista[i]).toString());
            sb.append(" ");
        }

        return sb.toString();
    }
}