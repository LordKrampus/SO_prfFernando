package entidades;

import enumeracoes.TipoInstrucao;
import enumeracoes.TipoSituacao;
import estruturas.PCB;

/**
 * Processo de Leitor e Escritor
 */
public class ProcessoLE extends Processo{

    private TipoSituacao situacao;
    private Processo correspondente;

    public ProcessoLE(PCB registro){
        super(registro);
        
        this.situacao = TipoSituacao.WAKUP;
    }

    public TipoSituacao getSituacao(){
        return this.situacao;
    }

    public void setSituacao(TipoSituacao tipo){
        this.situacao = tipo;
    } 

    public void addInstrucao(TipoInstrucao tipo ,String dados){
        super.getIsntrucoes().adicionar(
            new Instrucao(
                tipo, 
                new Dados(
                    dados.toCharArray()
                    )
                )
            );
    }

    public Processo getCorrespondente(){
        return this.correspondente;
    }

    public void setCorrespondente(Processo processo){
        this.correspondente = processo;
    }

    public void acordar(){
        this.setSituacao(TipoSituacao.WAKUP);
    }

    public void dormir(){
        this.setSituacao(TipoSituacao.SLEEP);
    }

    @Override
    public String toString(){
        return super.toString() + "\tSituacao: " + this.situacao.toString();
    }

}