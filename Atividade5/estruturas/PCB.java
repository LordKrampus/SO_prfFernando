package estruturas;

import entidades.Instrucao;
import enumeracoes.EstadoProcessamento;
import memorias.ListaInstrucoes;
import memorias.Memoria;

/**
 * Bloco de Controle de Processos. Guarda informações sobre o processo; 
 *  deve estar sincrono ao estado de execução do processo.
 * @author angel.rogrigues@estudante.ifgoiano.edu.br
 */
public class PCB {
    
    /*
    *    Descrição dos atributos:
    *        PID - Identificador de Processos;
    *        TP - Tempo de Processamento;
    *        CP - Contador de Programa;
    *        EP - Estado do Processo;
    *        nES - Número de Vezes de Operações de E/S;
    *        nCPU - Número de vezes de Uso de CPU.
    */
    
    private EstadoProcessamento EP;
    private int PID;
    private int TP;
    private int CP;
    private int nES;
    private int nCPU;

    private Memoria instrucoes;
    
    
    public PCB(ListaInstrucoes instrucoes){
        this.EP = EstadoProcessamento.NOVO;
        
        this.PID = -1;
        this.TP = 0;
        this.CP = 0;
        this.nES = 0;
        this.nCPU = 0;  
        
        this.instrucoes = instrucoes;
    }
    
    // gets :
    public EstadoProcessamento getEP(){
        return this.EP;
    }
    
    public int getPID(){
        return this.PID;
    }
    
    public int getTP(){
        return this.TP;
    }
    
    public int getCP(){
        return this.CP;
    }
    
    public int getnES(){
        return this.nES;
    }
    
    public int getnCPU(){
        return this.nCPU;
    }

    public Instrucao getInstrucao(){
        return (Instrucao)this.instrucoes.retirar();
    }

    public Memoria getInstrucoes(){
        return this.instrucoes;
    }

    public int getTamho(){
        return this.instrucoes.getTamanho();
    }

    // sets :
    public void setEP(EstadoProcessamento EP) {
        this.EP = EP;
    }

    public void setPID(int PID) {
        this.PID = PID;
    }

    public void setTP(int TP) {
        this.TP = TP;
    }

    public void setCP(int CP) {
        this.CP = CP;
    }

    public void setnES(int nES) {
        this.nES = nES;
    }

    public void setnCPU(int nCPU) {
        this.nCPU = nCPU;
    }
    // end of gets and sets.
    
    
    @Override
    public String toString(){
        return String.format("PID: %d\tEP: %s\tTP: %d\tCP: %d\tnES: %d\tnCPU: %d\t", 
                this.PID, this.EP.toString(), this.TP, this.CP, this.nES, this.nCPU);
    }
}
