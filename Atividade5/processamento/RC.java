package processamento;

import semaforos.Semaforo;
import semaforos.FabricaSemaforo;
import utilidades.Log;
import memorias.Memoria;

import java.io.IOException;

import entidades.Dados;
import entidades.Instrucao;
import entidades.ProcessoLE;
import enumeracoes.TipoInstrucao;
import enumeracoes.TipoMemoria;
import enumeracoes.TipoSemaforo;
import fabricas.FabricaMemoria;

/**
 * Implementar na Região critica. Esta tem um buffer que armazena e consome dados (D1, D2, ...).
 *  Mais necessáriamente os processos, por meio do controle de região critica, armazenam ou consomem dados.
 *  Um processo ao ser processado pode solicitar, instrução, acesso a região crítica; nesse caso, ele deve
 *  declarar o objetivo de acesso a região crítica, escrever dados ou acessar dador. Dessa forma tem-se o
 *  compartilhamento de dados entre processos, por meio do gerenciamento de buffer em região critica.
 *  
 *  @category Recursos : um Buffer (armazenamento compartilhado); e, Semaforos (gerenciamento de acesso ao buffer).
 */
public class RC {
    
    private Semaforo full;
    private Semaforo empty;
    private Semaforo mutex;

    private Memoria bufferDados;
    
    private Log log;

    public RC(Log log){
        FabricaSemaforo fs = FabricaSemaforo.getIsntancia();
        this.mutex = fs.gerarSemaforoMutex();
        this.empty = fs.gerarSemaforo(TipoSemaforo.EMPTY, 3);
        this.full = fs.gerarSemaforo(TipoSemaforo.FULL, 3);

        FabricaMemoria fm = FabricaMemoria.getInstancia();
        this.bufferDados = fm.gerarMemoria(TipoMemoria.BUFFER_DADOS);
        
        this.log = log;

    }

    public void Acessar(ProcessoLE processo, Instrucao instrucao){
        if(this.mutex.getEstado() == 0) return;

        TipoInstrucao tipo = instrucao.getTipo();
        if(tipo == TipoInstrucao.ESCREVER && (this.empty.getEstado() > 0))
            this.escrever(processo, instrucao);
        else if(tipo == TipoInstrucao.CONSUMIR && (this.full.getEstado() > 0))
            this.consumir(processo, instrucao);

        //adiciona ao notificador
        //if(!this.notificador.contem(processo))
        //    this.notificador.adicionar(processo);
    }

    private void semaforoUp(Semaforo semaforo) throws Exception{
        try{
            semaforo.up();
        }catch(Exception e){
            throw e;
        }
    }

    private void semaforoDown(Semaforo semaforo) throws Exception{
        try{
            semaforo.down();
        }catch(Exception e){
            throw e;
        }
    }

    private void escreverDados(Instrucao instrucao){
        Dados dados;

        dados = new Dados(instrucao.getDados()); // reencapsulando os dados

        this.bufferDados.adicionar(dados);
    }

    private void consumirDados(Instrucao instrucao){
        Dados dados;

        dados = (Dados) this.bufferDados.retirar();

        System.out.println("<" + dados.toString());
    }

    private void escrever(ProcessoLE processo, Instrucao instrucao){
        int estadoFull = this.full.getEstado();

        try{
            this.semaforoDown(this.mutex);
            log.appendLog(this.toString());
        }catch(Exception e){
            System.out.print(e.fillInStackTrace());
            return;
        }
        try{
            this.semaforoDown(this.empty);
            this.log.appendLog(this.toString());
        }catch(Exception e){
            System.out.print(e.fillInStackTrace());
            return;
        }

        //consumir a instrucao
        //escrever no buffer
        this.escreverDados(instrucao);
        try{
            this.log.appendLog(this.toString());
        }catch(IOException e){
            System.out.println(e.fillInStackTrace());
        }

        try{
            this.semaforoUp(this.full);
            this.log.appendLog(this.toString());
        }catch(Exception e){
            System.out.print(e.fillInStackTrace());
            return;
        }
        try{
            this.semaforoUp(this.mutex);
            this.log.appendLog(this.toString());
        }catch(Exception e){
            System.out.print(e.fillInStackTrace());
            return;
        }

        //acordar processo consumidor
        if(estadoFull == 0){
            //this.notificador.acordar(processo.getRegistro().getPID());
            ((ProcessoLE)processo.getCorrespondente()).acordar();
        }
            

        if(this.empty.getEstado() == 0){
            processo.dormir();
        }
    }

    private void consumir(ProcessoLE processo, Instrucao instrucao){
        int estadoEmpty = this.empty.getEstado();

        try{
            this.semaforoDown(this.mutex);
            this.log.appendLog(this.toString());
        }catch(Exception e){
            System.out.print(e.fillInStackTrace());
            return;
        }
        try{
            this.semaforoDown(this.full);
            this.log.appendLog(this.toString());
        }catch(Exception e){
            System.out.print(e.fillInStackTrace());
            return;
        }

        //consumir a instrucao
        //pegar do buffer
        this.consumirDados(instrucao);
        try{
            this.log.appendLog(this.toString());
        }catch(IOException e){
            System.out.println(e.fillInStackTrace());
        }

        try{
            this.semaforoUp(this.empty);
            this.log.appendLog(this.toString());
        }catch(Exception e){
            System.out.print(e.fillInStackTrace());
            return;
        }
        try{
            this.semaforoUp(this.mutex);
            this.log.appendLog(this.toString());
        }catch(Exception e){
            System.out.print(e.fillInStackTrace());
            return;
        }

        //acordar processo escritor
        if(estadoEmpty == 0){
            //this.notificador.acordar(processo.getRegistro().getPID());
            ((ProcessoLE)processo.getCorrespondente()).acordar();
        }

        if(this.full.getEstado() == 0){
            processo.dormir();
        }
    }

    @Override
    public String toString(){
        return String.format(".RC - \tempty: %d \tfull: %d \tmutex: %d \t%s", 
            this.empty.getEstado(), this.full.getEstado(), this.mutex.getEstado(), this.bufferDados.toString());
    }
}
