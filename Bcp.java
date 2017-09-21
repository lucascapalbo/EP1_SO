/**
 * Classe que representa um processo na tabela de bcp.
 */
public class Bcp {

    String[] programa;

    int pid;
    String nome;
    int quantum;
    int prioridade;

    int credito;
    int pc;

    private Processador.Estado estadoAtual;

    public Bcp(int pid, String[] programa, int prioridade, int quantum) {
        this.pid = pid;
        this.nome = programa[0];
        this.programa = programa;
        this.prioridade = prioridade;
        this.quantum = quantum;

        this.credito = 0;
        this.pc = 1; // comeca do 1 pois 0 é o nome do processo.
    }

    public void setEstadoAtual(Processador.Estado estadoAtual) {
        this.estadoAtual = estadoAtual;
    }

    public Processador.Estado getEstadoAtual() {
        return estadoAtual;
    }

    @Override
    public String toString() {
        return "BCP<nome=" + this.nome + " prioridade=" + this.prioridade + ">";
    }
}
