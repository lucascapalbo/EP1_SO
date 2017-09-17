import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;

public class Ler {
    private String nome;
    private String[] temp = new String[22]; // inicializa o vetor com o tamanho
    // do arquivo
    BufferedReader objeto;
    
    // O contrutor deve ter o mesmo nome da Classe topper
    public Ler(String nomeArquivo) {
        nome = nomeArquivo;
    }
    
    // método que retorna o vetor contendo as informações do arquivo
    public String[] criarVetor() {
        try {
            int i = 0;
            FileReader reader = new FileReader(nome);
            objeto = new BufferedReader(reader);
            String linha = null;
            while ((linha = objeto.readLine()) != null) {
                temp[i] = linha;
                i++;
            }
            String[] sai = new String[i];
            for (int j = 0; j < sai.length; j++) {
                sai[j] = temp[j];
            }
            return sai;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // só retorna null se der algum erro
    }
}
