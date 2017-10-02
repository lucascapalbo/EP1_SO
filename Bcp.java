/**
 * Classe que representa um processo na tabela de bcp.
 */
public class Bcp {
    
    String nome;//Nome do programa
    String[] programa; //Segmento de texto
    int prioridade; // Prioridade

    // Contexto do processo. Contém PC e registradores X e Y
    private Processador.Estado contexto;

    int credito; // Crédito
    int esperaBloqueado; // Contador para retorno de ES
    boolean estaRodando; // Var aux para ordenação

    public Bcp(String[] programa, int prioridade) {
        this.nome = programa[0];
        this.programa = programa;
        this.prioridade = prioridade;

        this.credito = 0;
        this.contexto = new Processador.Estado();
    }

    public Processador.Estado getContexto() {
        return contexto;
    }
}
