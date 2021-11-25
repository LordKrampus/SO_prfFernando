package processamento;

import memorias.GerenciadorFilas;
import utilidades.ConsultorPID;
import utilidades.Log;

import entidades.Processo;
import enumeracoes.EstadoProcessamento;

public class Escalonador {
    private final static int FILA_NOVO = 0;
    private final static int FILA_PRONTO = 1;
    private final static int FILA_BLOQUEADO = 2;
    
    private ConsultorPID consultorPID;

    private Processador processador;
    private GerenciadorFilas filasProcessos;

    private Log log;
    
    //private static Escalonador instancia;
    
    public Escalonador(Processador processador, Log log){
        this.processador = processador;
        this.log = log;
        
        this.iniciarFilasProcessos();

        this.consultorPID = new ConsultorPID(10);    // configuração engessada do numero de processo escalonados 
                                                    //  ao mesmo tempo com PIDs diferentes.
    }
    
    private void iniciarFilasProcessos(){
        this.filasProcessos = new GerenciadorFilas(3);
    }
    
    //public static Escalonador getInstancia() {
    //    if(instancia == null)
    //        instancia = new Escalonador();
    //    
    //    return instancia;
    //}
    
    private void escalonarNovo(Processo processo){
        processo.getRegistro().setPID(this.consultorPID.utilizarIndice());

        if(processo.getRegistro().getPID() == -1) return;

        this.filasProcessos.adicionarProcesso(this.FILA_NOVO, processo);
    }
    
    private void escalonarPronto(Processo processo){
        this.filasProcessos.adicionarProcesso(this.FILA_PRONTO, processo);
    }
    
    private void escalonarBloqueado(Processo processo){
        this.filasProcessos.adicionarProcesso(this.FILA_BLOQUEADO, processo);
    }

    private void escalonarTerminado(Processo processo){
        processo.getRegistro().setPID(this.consultorPID.devolverIndice( processo.getRegistro().getPID()));

        return;
    }
    
    
    public void escalonarProcesso(Processo processo){
        if(processo.getRegistro().getEP() == EstadoProcessamento.NOVO){
            this.escalonarNovo(processo);
        }else if(processo.getRegistro().getEP() == EstadoProcessamento.PRONTO){
            this.escalonarPronto(processo);
        }else if(processo.getRegistro().getEP() == EstadoProcessamento.BLOQUEADO){
            this.escalonarBloqueado(processo);
        }else if(processo.getRegistro().getEP() == EstadoProcessamento.TERMINADO){
            this.escalonarTerminado(processo);
        }else return;
    }
    
    private void prepararProcesso(Processo processo){
        // rotina de validação ???
        
        processo.setEstado(EstadoProcessamento.PRONTO);
        
        this.escalonarProcesso(processo);
    }
    
    
    private void rotinaProcessador(Processo processo){
        processo = this.processador.processar(processo);
        
        this.escalonarProcesso(processo);
    }
    
    private Processo rotinaEscalonamento(int id_fila) throws NullPointerException {
        Processo processo;
        
        processo = this.filasProcessos.retirarProcesso(id_fila);
        
        if(processo != null)
            return processo;
        
        throw new NullPointerException("Nenhum processo escalonado em (num. fila): " + id_fila);
    }
    
    //escala processos para o processador
    // ordem de prioridade: bloqueado; pronto; novo.
    public void rotina(){
        try{
            this.rotinaProcessador(
                    this.rotinaEscalonamento(this.FILA_BLOQUEADO)); 
        }catch(NullPointerException e){
            //System.out.println(e.getMessage());
        }
        try{
            this.rotinaProcessador(
                        this.rotinaEscalonamento(this.FILA_PRONTO));
        }catch(NullPointerException e){
            //System.out.println(e.getMessage());
        }
        
        try{
        // valida para processamento futuro
        this.prepararProcesso(
                this.rotinaEscalonamento(this.FILA_NOVO));
        }catch(NullPointerException e){
            //System.out.println(e.getMessage());
        }
    }
    
    public boolean temProcesso(){
        
        if(!this.filasProcessos.estaVazia(this.FILA_NOVO) || 
            !this.filasProcessos.estaVazia(this.FILA_PRONTO) ||
            !this.filasProcessos.estaVazia(this.FILA_BLOQUEADO))
            return true;
        
        return false;
    }
}
