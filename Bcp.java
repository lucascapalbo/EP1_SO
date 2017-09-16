
public class Bcp {
    String[] programa;
    int prioridade = 0;
    int quantum = 0;
    int pc = 1; // comeca do 1 pois 0 é o nome do processo, vamos usar para printar.
    String nome = programa[0];
    public Bcp(String[] programa , int prioridade, int quantum){
        this.programa = programa;
        this.prioridade = prioridade;
        this.quantum = quantum;
    }
}
