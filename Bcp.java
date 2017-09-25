/**
 * Classe que representa um processo na tabela de bcp.
 */
public class Bcp {
    
    int pid;
    String nome;
    String[] programa;
    int quantum;
    int prioridade;

    private Processador.Estado contexto;

    int credito;
    int esperaBloqueado;
    boolean estaRodando; 

    public Bcp(int pid, String[] programa, int prioridade, int quantum) {
        this.pid = pid;
        this.nome = programa[0];
        this.programa = programa;
        this.prioridade = prioridade;
        this.quantum = quantum;

        this.credito = 0;
        this.contexto = new Processador.Estado();
    }

    public Processador.Estado getContexto() {
        return contexto;
    }

    @Override
    public String toString() {
        return "BCP<nome=" + this.nome + " prioridade=" + this.prioridade + ">";
    }
}
