package entidades;

import enumeracoes.EstadoProcessamento;
import estruturas.PCB;

public class Processo {
    
    private final PCB registro;
    private final int tamanho;
    
    public Processo(PCB registro, int tamanho){
        this.registro = registro;
        this.tamanho = tamanho;
    }
    
    public PCB getRegistro(){
        return this.registro;
    }
    
    public int getTamanho(){
        return this.tamanho;
    }
    
    public EstadoProcessamento getEstado(){
        return this.registro.getEP();
    }
    
    public void setEstado(EstadoProcessamento estado){
        this.registro.setEP(estado);
    }
    
    public int getInstrucao(){
        int TP = this.registro.getTP();
        
        this.registro.setCP(TP + 1);
        
        return TP;   
    }
    
    public boolean completou(){
        return (this.tamanho - this.registro.getCP() < 0);
    }
    
    public void contarCiclo(){
        int TP = this.registro.getTP();
        
        this.registro.setTP(TP + 1);
    }
    
    public void contarES(){
        int nES = this.registro.getnES();
        
        this.registro.setnES(nES + 1);
    }
    
    public void contarCPU(){
        int nCPU = this.registro.getnCPU();
        
        this.registro.setnCPU(nCPU + 1);
    }
    
    public String toStringCiclo(){
        return String.format("Ciclo [Processo:%d] \tCP:%d", this.registro.getPID(), this.registro.getCP());
    }
    
    @Override
    public String toString(){
        return String.format("Tamanho: %d\n\tPCB - %s", tamanho, registro.toString());
    } 
}
