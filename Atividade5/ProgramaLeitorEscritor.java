
import entidades.ProcessoLE;
import enumeracoes.EstadoProcessamento;
import enumeracoes.TipoInstrucao;
import enumeracoes.TipoProcesso;
import enumeracoes.TipoSituacao;
import estruturas.PCB;
import fabricas.FabricaProcessos;
import memorias.ListaInstrucoes;

public class ProgramaLeitorEscritor{

    private ProcessoLE pEscritor;
    private ProcessoLE pConsumidor;

    //private boolean processando;

    public ProgramaLeitorEscritor(){
        FabricaProcessos fp = FabricaProcessos.getInstancia(TipoProcesso.LE);
        this.pEscritor = (ProcessoLE)fp.gerarProcesso(new PCB(new ListaInstrucoes()));
        this.pConsumidor = (ProcessoLE)fp.gerarProcesso(new PCB(new ListaInstrucoes()));

        this.pEscritor.setCorrespondente(this.pConsumidor);
        this.pConsumidor.setCorrespondente(this.pEscritor);
    }

    public void escrever(String dados){
        if(dados.equals("")) return;

        this.pEscritor.addInstrucao(TipoInstrucao.ESCREVER, dados);
        this.pConsumidor.addInstrucao(TipoInstrucao.CONSUMIR, dados);

        this.rotina();
    }

    private void rotina(){
        // para pEscritor
        if(this.pEscritor.getEstado() == EstadoProcessamento.TERMINADO){
            this.pEscritor.setEstado(EstadoProcessamento.NOVO);

            if(this.pEscritor.getSituacao() == TipoSituacao.SLEEP && ((ProcessoLE)this.pEscritor.getCorrespondente()).getSituacao() == TipoSituacao.SLEEP){
                this.pEscritor.setSituacao(TipoSituacao.WAKUP);
            }
        }

        if(this.pEscritor.getEstado() == EstadoProcessamento.NOVO){
            SO.escalonarProcesso(this.pEscritor);
        }


        // para pConsumidor
        if(this.pConsumidor.getEstado() == EstadoProcessamento.TERMINADO){
            this.pConsumidor.setEstado(EstadoProcessamento.NOVO);

            if(this.pConsumidor.getSituacao() == TipoSituacao.SLEEP && ((ProcessoLE)this.pConsumidor.getCorrespondente()).getSituacao() == TipoSituacao.SLEEP){
                this.pConsumidor.setSituacao(TipoSituacao.WAKUP);
            }
        }

        if(this.pConsumidor.getEstado() == EstadoProcessamento.NOVO){
            SO.escalonarProcesso(this.pConsumidor);
        }
    }

}