import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Logger {

  private static String nomeArquivo;

  public static void inicializa(String filename) {
    Logger.nomeArquivo = filename;

    try {//Limpa arquivo de log
      new File(filename).delete();
    } catch (Exception e) {
    }
  }

  public static void escreve(String txt) {
    try {
      PrintWriter escritor = new PrintWriter(new FileOutputStream(nomeArquivo, true));
      escritor.println(txt);
      escritor.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void escreveCarregando(Bcp processo) {
    Logger.escreve("Carregando " + processo.nome);
  }

  public static void escreveExecutando(Bcp processo) {
    Logger.escreve("Executando " + processo.nome);
  }

  public static void escreveES(Bcp processo) {
    Logger.escreve("E/S iniciada em " + processo.nome);
  }

  public static void escreveCallbackBCP(Bcp processo, int delta) {
    Logger.escreve("Interrompendo " + processo.nome + " após " + delta + " instruções");
  }

  public static void escreveFim(Bcp processo) {
    Logger.escreve(processo.nome + " terminado. X=" + processo.getContexto().X + ". Y=" + processo.getContexto().Y);
  }

  public static void escreveEstatistica(double mediaTroca, double mediaInstrucao, int quantum) {
    Logger.escreve("MEDIA DE TROCAS: " + mediaTroca);
    Logger.escreve("MEDIA DE INSTRUCOES: " + mediaInstrucao);
    Logger.escreve("QUANTUM: " + quantum);
  }
}
