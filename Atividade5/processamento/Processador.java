package processamento;

import java.io.IOException;

import entidades.Instrucao;
import entidades.Processo;
import entidades.ProcessoLE;
import enumeracoes.EstadoProcessamento;
import enumeracoes.TipoInstrucao;
import enumeracoes.TipoSituacao;
import utilidades.Log;
import utilidades.ReferenciaContexto;
import utilidades.SorteadorCincoPorCento;
import utilidades.SorteadorTrintaPorCento;
import utilidades.templates.Sorteador;

public class Processador {
    //atributos funcionais de contexto
    private final byte OBJETO_SORTEADOR_CINCO = 0;
    private final byte OBJETO_SORTEADOR_TRINTA = 1;
    
    private ReferenciaContexto sorteadores;
    
    //atributos do processador
    private static final byte INSTRUCAO_ES = 0;
    private static final byte INSTRUCAO_PROCESSO = 1;
    
    //private Escalonador escalonador;
    private int quantum;

    private RC regiaoCritica; 

    private Log log;
    
    public Processador(int quantum, RC rc, Log log){
        this.quantum = quantum;
        this.regiaoCritica = rc;
        this.log = log;
        
        //utilidade de sorteador (probabildiade de ocorrencia)
        this.sorteadores = new ReferenciaContexto();
        this.sorteadores.add(new SorteadorCincoPorCento());
        this.sorteadores.add(new SorteadorTrintaPorCento());
    }
    
    // jogar isso para um  simulador de entrada e saida
    private Processo validarEstado(Processo processo, EstadoProcessamento estado){
        if(estado == EstadoProcessamento.PRONTO){
            this.trocarContexto(processo, EstadoProcessamento.EXECUTANDO);
            return processo;
        }
        
        Sorteador sorteador;
        
        if(estado == EstadoProcessamento.BLOQUEADO){
            sorteador = (SorteadorTrintaPorCento)sorteadores.getObject(this.OBJETO_SORTEADOR_TRINTA);
            
            if(sorteador.sortear())
                this.trocarContexto(processo, EstadoProcessamento.PRONTO);
        }
        
        return processo;
    }
    
    private boolean validarSituacao(ProcessoLE processo){

        if(processo.getSituacao() == TipoSituacao.WAKUP)
            return true;

        return false;
    } 

    private byte sortearInstrucao(){
        Sorteador sorteador;
        
        sorteador =(SorteadorCincoPorCento)sorteadores.getObject(this.OBJETO_SORTEADOR_CINCO);
            
        if(sorteador.sortear())
            return this.INSTRUCAO_ES;
        
        return this.INSTRUCAO_PROCESSO;
    }
    
    private void processarInstrucao(Processo processo) throws Exception{
        Instrucao instrucao;
        TipoInstrucao tipo;
        
        instrucao = processo.getInstrucao();
        tipo = instrucao.getTipo();

        if(tipo == TipoInstrucao.PROCESSO){
            this.log.appendLog(instrucao.toString());

        }else if(tipo == TipoInstrucao.E_S){
            this.log.appendLog(instrucao.toString());
            processo.contarES();
            
            this.trocarContexto(processo, EstadoProcessamento.BLOQUEADO);

        }else if(processo instanceof ProcessoLE){
            this.regiaoCritica.Acessar((ProcessoLE)processo, instrucao);
        
        }else
            throw new Exception("Instrucao indefinida de processo.");
    }
    
    private void trocarContexto(Processo processo, EstadoProcessamento estado){
        EstadoProcessamento estadoAntigo;
        estadoAntigo = processo.getEstado();
        
        processo.setEstado(estado);
        
        try{
            this.log.appendLog(processo.toString());
            this.log.appendLog(".\tTroca de Contexto de " + estadoAntigo + " para " + estado);
        }catch(IOException e){
            System.out.println(e.fillInStackTrace());
        }
    }

    protected Processo processar(Processo processo){
        //implementar rotina de processamento
        
        if(processo instanceof ProcessoLE)
        if(!this.validarSituacao((ProcessoLE)processo))
            return processo;

        //resolução inicial de execução do processo
        this.validarEstado(processo, processo.getEstado());

        if(processo.getEstado() != EstadoProcessamento.EXECUTANDO){
            return processo;
        }
        
        processo.contarCPU();
        
        int contadorQuantum = 0;
        //execução do proceso
        while(processo.getEstado() == EstadoProcessamento.EXECUTANDO){
            //bloco de execuçãoo do processo
            
            processo.contarCiclo();
            try{
            this.log.appendLog(processo.toStringCiclo());
            }catch(IOException e){
                System.out.println(e.fillInStackTrace());
            }
            
            try{
                this.processarInstrucao(processo);
            }catch(Exception e){
                System.out.println(e.getMessage());
                break;
            }
            

            // se o processo LE for para sleep
            if(processo instanceof ProcessoLE)
               if(!this.validarSituacao((ProcessoLE)processo))
                    break;


            if(processo.completou()){
                this.trocarContexto(processo, EstadoProcessamento.TERMINADO);
                
                try{
                this.log.appendLog(processo.toString() + "\n\tTerminado!");
                }catch(IOException e){
                    System.out.println(e.fillInStackTrace());
                }
                break;
            }
            
            contadorQuantum++;
            if(contadorQuantum >= this.quantum)
                break;
        }
        
        EstadoProcessamento estado;
        estado = processo.getEstado();
        if(estado != EstadoProcessamento.TERMINADO && estado != EstadoProcessamento.BLOQUEADO)
            this.trocarContexto(processo, EstadoProcessamento.PRONTO);

        return processo;
    }
    
}
