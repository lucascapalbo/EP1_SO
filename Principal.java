import java.io.File;

public class Principal {

    private static final String caminhoData = "processos/";
    private static final String caminhoPriori = "prioridades.txt";
    private static final String caminhoQuantum = "quantum.txt";

    public void iniciaComputacao(String pasta) {
        File file = new File("./Saida.txt");
        String[] prioridades = Leitor.lerArquivo(caminhoPriori);
        int quantum = Integer.parseInt(Leitor.lerArquivo(caminhoQuantum)[0]);
        File[] filesProgramas = Leitor.lerProgramas();

        Processador processador = new Processador();
        Escalonador escalonador = new Escalonador(processador);

        for (int i = 0; i < filesProgramas.length; i++) {
            Bcp processo = new Bcp(i, Leitor.lerArquivo(filesProgramas[i]), Integer.parseInt(prioridades[i]), quantum);
            Escritor.escreve(processo.toString());
            escalonador.carregarProcesso(processo);
        }

        escalonador.executar();
    }

    public static void main(String[] args) {
        Leitor.setPasta(caminhoData);

        new Principal().iniciaComputacao(caminhoData);
    }
}
