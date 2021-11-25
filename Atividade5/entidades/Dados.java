package entidades;

public class Dados {
    private char[] dados;

    public Dados(char[] dados){
        this.dados = dados;
    }

    public char[] getDados(){
        return this.dados;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        for(char dado : this.dados)
            sb.append(dado);

        return sb.toString();
    }
}
