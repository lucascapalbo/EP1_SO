import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Classe responsável por armazenar e gerenciar a troca de processos
 */
public class Escalonador {

  public static final int TEMPO_ESPERA_ES = 2;

  private Processador processador;

  private List<Bcp> processos = new ArrayList<Bcp>();

  private List<Bcp> filaDeProntos = new LinkedList<Bcp>();
  private Queue<Bcp> filaDeBloqueados = new LinkedList<Bcp>();

  public Escalonador(Processador processador) {
    this.processador = processador;
  }

  public void carregarProcesso(Bcp processo) {
    this.processos.add(processo);
    addProcessoPronto(processo);
  }

  public void addProcessoPronto(Bcp bcp) {
    this.filaDeProntos.add(bcp);
    this.ordenaFila();
  }

  public void addProcessoBloqueado(Bcp bcp) {
    bcp.esperaBloqueado = TEMPO_ESPERA_ES;
    this.filaDeBloqueados.add(bcp);
  }

  private void ordenaFila() {
    //TODO Garantir que o processo que rodou por último tenha prioridade, caso haja outro com mesmo credito
    //#maybe ordenaFila(Bcp ultimoProcessoRodado)
    //#maybe checar flag estaRodando. Lembrar de resetar ela logo em seguida (Teria que ser fora do loop).
    Collections.sort(filaDeProntos, new Comparator<Bcp>() {
      @Override
      public int compare(Bcp bcp1, Bcp bcp2) {
        return bcp2.credito - bcp1.credito;
      }
    });
  }

  private boolean deveDistribuirCreditos() {
    for (Bcp bcp : this.processos)
      if (bcp.credito != 0)
        return false;
    return true;
  }

  private void distribuirCreditos() {
    for (Bcp bcp : this.processos)
      bcp.credito = bcp.prioridade;

    this.ordenaFila();
  }

  private void decrementaEsperaBloqueados() {
    for (Iterator<Bcp> iterator = this.filaDeBloqueados.iterator(); iterator.hasNext();) {
      Bcp bcp = iterator.next();
      bcp.esperaBloqueado -= 1;
      if (bcp.esperaBloqueado == 0) {
        this.addProcessoPronto(bcp);
        iterator.remove();
      }
    }
  }

  /**
   * Pega primeiro processo da fila.
   */
  public Bcp getPrimeiroProcesso() {
    if (this.filaDeProntos.size() == 0)
      return null;
    return this.filaDeProntos.remove(0);
  }

  /**
   * Inicia execução.
   */
  public void executar() {
    System.out.println();
    while (this.processos.size() > 0) { // Enquanto a fila de processos não for vazia...
      if (deveDistribuirCreditos())
        distribuirCreditos();

      decrementaEsperaBloqueados();

      Bcp bcp = getPrimeiroProcesso();
      if (bcp == null) // Nenhum processo pronto
        continue;

      bcp.estaRodando = true;

      Interrupcao interrupcao = this.processador.executar(bcp);
      bcp.credito -= 1;
      this.ordenaFila();

      switch (interrupcao) {
      case QUANTUM:
        this.addProcessoPronto(bcp);
        break;
      case IO:
        this.addProcessoBloqueado(bcp);
        break;
      case EOF:
        this.processos.remove(bcp);
        break;
      }
      for (Bcp b : this.filaDeProntos)
        System.out.print("[" + b.nome + "](" + b.credito + "), ");
      System.out.println();
    }
  }
}
