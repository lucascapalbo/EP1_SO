
public class Processador {
    public static void main(String[] args) {
        String caminhoPriori = "/Users/lucasbordinhoncapalbo/Documents/EP1_SO_git/processos/prioridades.txt";
        String caminhoQuantum = "/Users/lucasbordinhoncapalbo/Documents/EP1_SO_git/processos/quantum.txt";
        Bcp inicia = null;
        Bcp[] Processos = new Bcp[21];
        Ler leituraPrioridade = new Ler(caminhoPriori);
        Ler leituraQuantum = new Ler(caminhoQuantum);
        String[] t;
        String[] prioridades = leituraPrioridade.criarVetor();
        String[] quanta = leituraQuantum.criarVetor();
        String fim = null;
        String caminho = "/Users/lucasbordinhoncapalbo/Documents/EP1_SO_git/processos/";
        String numero = null;
        String txt = ".txt";
        System.out.println(caminho);
        for (int i = 0; i < 10; ++i) {
            if (i < 9) {
                numero = "0" + (i + 1);
            } else
                numero = (i + 1) + "";
            fim = caminho + "" + numero + "" + txt;
            System.out.println(fim);
            System.out.println(prioridades[i]);
            System.out.println(quanta[0]);// apenas um quantum para todo mundo
            Ler leitura = new Ler(fim);
            t = leitura.criarVetor();
            inicia = new Bcp(t, Integer.parseInt(prioridades[i]), Integer.parseInt(quanta[0]));
            Processos[i] = inicia;
        }
        int teste = 0;
        System.out.println(teste);
    }
}
