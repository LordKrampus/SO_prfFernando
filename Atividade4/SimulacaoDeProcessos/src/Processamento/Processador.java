package Processamento;

import java.util.ArrayList;

import entidades.Processo;
import enumeracoes.EstadoProcessamento;
import estruturas.PCB;
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
    
    private Escalonador escalonador;
    private int quantum;
    
    private static Processador instancia;
    
    private Processador(int quantum){
        this.quantum = quantum;
        
        try{
            this.escalonador = new Escalonador(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        //utilidade de sorteador (probabildiade de ocorrencia)
        this.sorteadores = new ReferenciaContexto();
        this.sorteadores.add(new SorteadorCincoPorCento());
        this.sorteadores.add(new SorteadorTrintaPorCento());
    }
    
    public static Escalonador getInstancia(){
        if(instancia == null)
            instancia = new Processador(1000);
        
        return instancia.escalonador;
    }
    
    
    // jogar isso para um  simulador de entrada e saida
    private Processo validarEstado(Processo processo, EstadoProcessamento estado){
        if(estado == EstadoProcessamento.Pronto){
            this.trocarContexto(processo, EstadoProcessamento.Executando);
            return processo;
        }
        
        Sorteador sorteador;
        
        if(estado == EstadoProcessamento.Bloqueado){
            sorteador = (SorteadorTrintaPorCento)sorteadores.getObject(this.OBJETO_SORTEADOR_TRINTA);
            
            if(sorteador.sortear())
                this.trocarContexto(processo, EstadoProcessamento.Pronto);
        }
        
        return processo;
    }
    
    private byte sortearInstrucao(){
        Sorteador sorteador;
        
        sorteador =(SorteadorCincoPorCento)sorteadores.getObject(this.OBJETO_SORTEADOR_CINCO);
            
        if(sorteador.sortear())
            return this.INSTRUCAO_ES;
        
        return this.INSTRUCAO_PROCESSO;
    }
    
    private void processarInstrucao(Processo processo, byte tipoInstrucao) throws Exception{
        if(tipoInstrucao == this.INSTRUCAO_PROCESSO){
            processo.getInstrucao();
            return;
        }
        else if(tipoInstrucao == this.INSTRUCAO_ES){
            processo.getInstrucao();
            processo.contarES();
            
            this.trocarContexto(processo, EstadoProcessamento.Bloqueado);
        }
        else
            throw new Exception("Instrucao indefinida de processo.");
    }
    
    private void trocarContexto(Processo processo, EstadoProcessamento estado){
        EstadoProcessamento estadoAntigo;
        estadoAntigo = processo.getEstado();
        
        processo.setEstado(estado);
        
        System.out.println(processo.toString());
        System.out.println(".\tTroca de Contexto de " + estadoAntigo + " para " + estado);
    }
    
    public Processo processar(Processo processo){
        //implementar rotina de processamento
        
        //resolução inicial de execução do processo
        this.validarEstado(processo, processo.getEstado());
        if(processo.getEstado() != EstadoProcessamento.Executando){
            return processo;
        }
        
        processo.contarCPU();
        
        int contadorQuantum = 0;
        //execução do proceso
        while(processo.getEstado() == EstadoProcessamento.Executando){
            //bloco de execuçãoo do processo
            
            processo.contarCiclo();
            System.out.println(processo.toStringCiclo());
            
            try{
                this.processarInstrucao(processo, this.sortearInstrucao());
            }catch(Exception e){
                System.out.println(e.getMessage());
                break;
            }
            
            if(processo.completou()){
                this.trocarContexto(processo, EstadoProcessamento.Terminado);
                
                System.out.println(processo.toString() + "\n\tTerminado!");
                break;
            }
            
            contadorQuantum++;
            if(contadorQuantum >= this.quantum)
                break;
        }
        
        EstadoProcessamento estado;
        estado = processo.getEstado();
        if(estado != EstadoProcessamento.Terminado && estado != EstadoProcessamento.Bloqueado)
            this.trocarContexto(processo, EstadoProcessamento.Pronto);

        return processo;
    }
    
}
