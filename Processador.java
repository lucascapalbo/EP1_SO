
public class Processador{
    public static void main(String[] args) {
     String caminhoPriori = "/projects/ep1_so/processos/prioridades.txt";
     String caminhoQuantum = "/projects/ep1_so/processos/quantum.txt";
     
    int prioridade = 0;
    int quantum = 0;
    Ler leituraPrioridade = new Ler(caminhoPriori);
    Ler leituraQuantum = new Ler(caminhoQuantum);
    String [] prioridades = leituraPrioridade.criarVetor();
    String [] quanta = leituraQuantum.criarVetor();
    String fim = null;
    String caminho = "/projects/ep1_so/processos/";
    String numero = null;
    String txt = ".txt";
    int repo = caminho.length() -4 ;
                    System.out.println(caminho);
    for (int i = 0; i < 21; ++i) {
      if(i < 10){
          numero = "0"+i;
      }else numero = i+"";
      fim = caminho+ "" + numero + "" +txt;
      
      System.out.println(fim);
            System.out.println(prioridades[i]);
                  System.out.println(quanta[i]);
  }
    Ler leitura = new Ler(fim);
    String[] t = leitura.criarVetor();
     for (int j = 0; j < t.length; j++) {
                System.out.println(t[j]);
    }
    Bcp bcp1 = new Bcp(t,prioridade,quantum);
    }
}
