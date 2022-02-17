package Componente;

//Um servico é uma thread, e sua operação confere o acesso aos garfos e ao prato.
// A composição responssável de ter um filosofo que consome, e uma mesa que dá acesso aos pratos e garfos.
// O filosofo tem a cadeira, quem  o deu referencia da mesa, mas com referencia a cadeira, o filosofo
//      consegue acesso aos ao indice da cadeira anterior e assim o indice do garfo e do prato. O que o permite
//      pela mesa obter os proprios objetos para a interaçao do serviço - o filosofo come... se tiver 2 garfos.
public class Servico extends Thread{
    private Garfo garfoE; // garfo esquerdo
    private Garfo garfoD; // garfo direito

    private Filosofo filosofo;
    private Mesa mesa;
    private Prato prato; //!!!

    public Servico(Cadeira cadeira, Mesa mesa) throws Exception{
        this.filosofo = cadeira.getFilosofo();
        this.mesa = mesa; // !!!

        this.pegarGarfoD(mesa.buscarGarfoD(cadeira));
        // tenta pegar o esequedo, se nao resolver prioridades
        try {
            this.pegarGarfoE(mesa.buscarGarfoE(cadeira));
        }catch(Exception e){
            //ganha vez segundo criterio de prioridade resolvido
            if(this.mesa.resolverPrioridade(cadeira))
                this.pegarGarfoE(mesa.buscarGarfoE(cadeira));
        }

        this.prato = this.mesa.buscarPrato(filosofo.getCadeira());

        //this.filosofo.setEstado("comendo");
        if(!this.vrfSituacao()) {
            throw new Exception(".exception - Situação não favorece o consumo do Prato.");
        }

        //abre transação e condiciona o cenário de ocupação critica garantida
        try {
            this.prato.setEstado(false);
        }catch(Exception e){
            System.out.println(e.getMessage());

            this.liberarRecursos();

            throw new Exception(".exception - Não foi possível consumir do Prato.");
        }
    }

    public boolean vrfDisponibilidade(Garfo garfo){
         return garfo.getEstado();
    }

    public void pegarGarfo(Garfo garfo) throws Exception{
        if(this.vrfDisponibilidade(garfo))
            try{
                garfo.setEstado(false);
            } catch(Exception e){
                //this.liberarRecursos();
                throw e;
            }

        else
            throw new Exception(".exception - Garfo esta ocupado.");
    }

    public void pegarGarfoE(Garfo garfoE) throws Exception{
        try{
            this.pegarGarfo(garfoE);
            //se não ocorre erro, oK
            this.garfoE = garfoE;
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
        //System.out.println("não foi possível pegar o garfo ESQUERDO.");
    }

    public void pegarGarfoD(Garfo garfoD) throws Exception{
        try{
            this.pegarGarfo(garfoD);
            //se não ocorre erro, oK
            this.garfoD = garfoD;
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
    }

    /*
    vai verificar se a situação confere os dois garfos.
     */
    public boolean vrfSituacao(){
        return this.garfoE != null && this.garfoD != null;
    }

    public void consumir() throws Exception{
        //rotina comum
        prato.servir(this.filosofo);

        //liverar recursos e terminar;
        this.liberarRecursos();

        if(prato.vazio())
            filosofo.setEstado("terminou");
    }

    private void liberarRecursos(){
        try{
            this.prato.setEstado(true);
            this.garfoE.setEstado(true);
            this.garfoD.setEstado(true);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String toString(){
        if(this.garfoE == null || this.garfoD == null) return "";

        StringBuilder sb  = new StringBuilder();
        sb.append(this.filosofo.getCadeira().getId() + " - ");
        sb.append("[ "+ garfoD.getEstado() + "," + garfoE.getEstado() + " ]");

        return sb.toString();
    }
}
