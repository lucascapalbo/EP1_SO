import java.io.File;

public class Processador {
    public static void main(String[] args) {
        Escalonador esc = new Escalonador();
        String caminhoPriori = "/Users/lucasbordinhoncapalbo/Documents/EP1_SO_git/processos/prioridades.txt";
        String caminhoQuantum = "/Users/lucasbordinhoncapalbo/Documents/EP1_SO_git/processos/quantum.txt";
        Bcp inicia = null;
        Ler leituraPrioridade = new Ler(caminhoPriori);
        Ler leituraQuantum = new Ler(caminhoQuantum);
        String[] t;
        String[] prioridades = leituraPrioridade.criarVetor();
        String[] quanta = leituraQuantum.criarVetor();
        String fim = null;
        String caminho = "/Users/lucasbordinhoncapalbo/Documents/EP1_SO_git/processos/";
        String numero = null;
        String txt = ".txt";
        int arquivos = contadorDeArquivos();
        Bcp[] Processos = new Bcp[arquivos];
        System.out.println(caminho);
        for (int i = 0; i < arquivos; ++i) {
            numero = arquivoAtual(i);
            fim = caminho + "" + numero + "" + txt;
            System.out.println(fim);
            System.out.println(prioridades[i]);
            System.out.println(quanta[0]);// apenas um quantum para todo mundo
            Ler leitura = new Ler(fim);
            t = leitura.criarVetor();
            inicia = new Bcp(t, Integer.parseInt(prioridades[i]), Integer.parseInt(quanta[0]));
            Processos[i] = inicia;
            esc.addProcessoPronto(inicia); // metodo que insere o processo na
            // fila de prontos. o
            // addProcessoBloqueado insere na
            // fila de bloqueados.
        }
        int teste = 0;
        System.out.println(teste);
    }
    
    static int contadorDeArquivos() {
        // metodo para contar quantos .txt temos
        int contador = 0;
        String temp = null;
        File dir = new File("/Users/lucasbordinhoncapalbo/Documents/EP1_SO_git/processos/");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                temp = arquivoAtual(contador);
                if (child.toString().contains(temp))
                    contador++;
            }
        }
        return contador;
    }
    
    static String arquivoAtual(int contador) {
        String atual = null;
        if (contador < 9) {
            atual = "0" + (contador + 1);
        } else
            atual = (contador + 1) + "";
        return atual;
    }
}
