package entidades;

import enumeracoes.TipoInstrucao;

public class Instrucao {
    private TipoInstrucao tipo;
    private Dados dados;
    
    public Instrucao(TipoInstrucao tipo, Dados dados){
        this.tipo = tipo;
        this.dados = dados;
    }

    public TipoInstrucao getTipo(){
        return this.tipo;
    }

    public char[] getDados(){
        return this.dados.getDados();
    }
}
