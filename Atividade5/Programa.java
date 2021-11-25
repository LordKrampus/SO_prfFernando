import java.util.Scanner;

public class Programa {
    static Scanner entrada;

    public static void main(String[] agrs){

        entrada = new Scanner(System.in);
        ProgramaLeitorEscritor p = new ProgramaLeitorEscritor();

        SO so =  new SO();

        String palavra;
        boolean sair = false;
        do{
            System.out.print(">");
            palavra = (String)entrada.nextLine();
            if(palavra.equals("sair."))
                sair = true;
            
            if(!sair)
                p.escrever(palavra);

            so.rotina();
        }while(!sair && so.parado());
    }
    
}
