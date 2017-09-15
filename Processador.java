
public class Processador {
    public static void main(String[] args) {
    String caminho = "/projects/ep1_so/processos/01.txt";
    int prioridade = 0;
    int quantum = 0;
    Ler leitura = new Ler(caminho);
    String[] t = leitura.criarVetor();
     for (int j = 0; j < t.length; j++) {
                System.out.println(t[j]);
            }
    Bcp bcp1 = new Bcp(t,prioridade,quantum);
}
}
