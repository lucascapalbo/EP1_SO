import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Classe responsável por armazenar e gerenciar a troca de processos
 */
public class Escalonador {

  private Processador processador;

  private List<Bcp> processos = new ArrayList<Bcp>();

  private List<Bcp> filaDeProntos = new ArrayList<Bcp>();
  private List<Bcp> listaDeBloqueados = new ArrayList<Bcp>();

  public Escalonador(Processador processador) {
    this.processador = processador;
  }

  public void carregarProcesso(Bcp processo) {
    this.processos.add(processo);
    addProcessoPronto(processo);
  }

  public void addProcessoPronto(Bcp bcp) {
    filaDeProntos.add(bcp);
    this.ordenaFila();
  }

  public void addProcessoBloqueado(Bcp bcp) {
    listaDeBloqueados.add(bcp);
  }

  private void ordenaFila() {
    Collections.sort(filaDeProntos, new Comparator<Bcp>() {
      @Override
      public int compare(Bcp bcp1, Bcp bcp2) {
        return bcp2.prioridade - bcp1.prioridade;
      }
    });
  }

  /**
   * Inicia execução.
   */
  public void executar() {
    //Enquanto a fila de processos não for vazia...
    rodaProximoProcesso();
  }

  /**
   * Pega primeiro processo da fila.
   */
  public Bcp getPrimeiroProcesso() {
    return filaDeProntos.get(0);
  }

  /**
   * 
   */
  public void rodaProximoProcesso() {
    Bcp bcp = getPrimeiroProcesso();

    while (bcp.credito > 0){
      // ALGUMA COISA Q RODE A LINHA LÁ
      // this.processador.executar(bcp);
      bcp.credito--;
    }
  }

}
