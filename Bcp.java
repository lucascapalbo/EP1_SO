/**
 * Classe que representa um processo na tabela de bcp.
 */
public class Bcp {
    
    public static enum Estado {
        PRONTO,
        BLOQUEADO,
        EXECUTANDO
    }

    String nome;//Nome do programa
    String[] programa; //Segmento de texto
    int prioridade; // Prioridade

    // Contexto do processo. Contém PC e registradores X e Y
    private Processador.Estado contexto;

    int credito; // Crédito
    int esperaBloqueado; // Contador para retorno de ES
    private Estado estado; // Estado do processo

    public Bcp(String[] programa, int prioridade) {
        this.nome = programa[0];
        this.programa = programa;
        this.prioridade = prioridade;

        this.credito = 0;
        this.contexto = new Processador.Estado();
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Processador.Estado getContexto() {
        return contexto;
    }
}
