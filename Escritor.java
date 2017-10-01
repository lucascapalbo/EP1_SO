import java.io.PrintWriter;
import java.io.File;
 import java.io.FileNotFoundException;
public class Escritor {

    private static PrintWriter escritor;
    public static void escreve(String txt) {
          try {
            escritor = new PrintWriter("Saida.txt", "UTF-8");
          } catch (Exception e) {
            System.out.println(e.getMessage());
          }
          escritor.println("");
          escritor.println(txt);
          escritor.close();
    }
}
