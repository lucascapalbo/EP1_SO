
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;
/**
 * Classe utilitária para leitura de arquivos.
 */
public class Leitor {
    private static String pasta = "";

    public static void setPasta(String folder) {
        Leitor.pasta = folder;
    }

    public static String getPasta() {
        return pasta;
    }

    public static String[] lerArquivo(File arquivo) {
        try {
            FileReader fr = new FileReader(arquivo);
            return lerArquivo(fr);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Lê um arquivo e retorna suas linhas
    public static String[] lerArquivo(String nomeArquivo) {
        try {
            FileReader fr = new FileReader(pasta + nomeArquivo);
            return lerArquivo(fr);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Lê um arquivo e retorna suas linhas
    private static String[] lerArquivo(FileReader fr) {
        try {
            BufferedReader reader = new BufferedReader(fr);
            int i = 0;
            String linha = null;
            List<String> l = new ArrayList<String>();
            while ((linha = reader.readLine()) != null) {
                l.add(linha);
            }
            return l.toArray(new String[l.size()]);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // metodo para contar quantos .txt temos
    public static File[] lerProgramas() {
        int contador = 0;
        String temp = null;
        File dir = new File(getPasta());
        File[] listaProgramas = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return Pattern.matches("(\\d)+\\.txt", pathname.getName());
            }
        });
        Arrays.sort(listaProgramas, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                int vo1 = Integer.parseInt(o1.getName().substring(0, 2));
                int vo2 = Integer.parseInt(o2.getName().substring(0, 2));
                return vo1 - vo2;
            }
        });
        return listaProgramas;
    }
}
