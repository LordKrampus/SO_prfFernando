import java.io.IOException;

import entidades.Processo;
import processamento.Escalonador;
import processamento.Processador;
import processamento.RC;
import utilidades.Log;

public class SO{

    private static Escalonador escalonador;
    private static Processador processador;
    private static RC rc;
    private Log log;

    public SO(){
        this.log = new Log();
        try{
            log.localizarArquivo(".\\arquivo\\Logs.txt");
        }catch(IOException e){
            System.out.println(e.fillInStackTrace());
        }

        rc = new RC(log);
        processador = new Processador(1000 , rc, this.log);
        escalonador = new Escalonador(processador, this.log);
    }

    public static void escalonarProcesso(Processo processo){
        if(escalonador == null) return;
        escalonador.escalonarProcesso(processo);
    }

    public void rotina(){
        if(escalonador == null) return;
        if(escalonador.temProcesso())
            escalonador.rotina();     
    }

    public boolean parado(){
        if(escalonador == null) return false;
        return escalonador.temProcesso();
    }
}