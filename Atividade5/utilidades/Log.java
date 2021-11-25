package utilidades;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Log {
    
    private BufferedWriter arquivo;

    public Log(){
        this.arquivo = null;
    }

    public void localizarArquivo(String localizacao) throws IOException{
        this.deixarArquivo();

        FileWriter fw;
        
        //fw = new FileWriter(localizacao, true); append
        fw = new FileWriter(localizacao);
        this.arquivo = new BufferedWriter(fw);
    }

    public void deixarArquivo() throws IOException{
        if(arquivo == null) return;

        this.arquivo.close();
    }

    public void appendLog(String valor) throws IOException{
        this.arquivo.append(valor);
        this.arquivo.append('\n');
        this.arquivo.flush();
    }
}
