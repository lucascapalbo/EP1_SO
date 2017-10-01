import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Escritor {

  private static String nomeArquivo;

  public static void inicializa(String filename) {
    Escritor.nomeArquivo = filename;

    try {//Limpa arquivo de log
      new File(filename).delete();
    } catch (Exception e) {
    }
  }

  public static void escreve(String txt) {
    try {
      PrintWriter escritor = new PrintWriter(new FileOutputStream(nomeArquivo, true));
      escritor.println("");
      escritor.println(txt);
      escritor.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
