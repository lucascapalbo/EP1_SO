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
    this.ordenaFilaProntos();
  }

  public void addProcessoBloqueado(Bcp bcp) {
    bcp.esperaBloqueado = TEMPO_ESPERA_ES;
    this.filaDeBloqueados.add(bcp);
  }

  private void ordenaFilaProntos() {
    Collections.sort(filaDeProntos, new Comparator<Bcp>() {
      @Override
      public int compare(Bcp bcp1, Bcp bcp2) {
        int r = bcp2.credito - bcp1.credito;
        if (r == 0) { //Quem já está rodando tem prioridade em relação a bcps com mesma prioridade
          if (bcp1.estaRodando)
            return -1;
          if (bcp2.estaRodando)
            return 1;
        }
        return r;
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

    this.ordenaFilaProntos();
  }

  private void decrementaEsperaBloqueados() {
    for (Iterator<Bcp> iterator = this.filaDeBloqueados.iterator(); iterator.hasNext();) {
      Bcp bcp = iterator.next();
      if (bcp.esperaBloqueado == 0) {
        System.out.println("* " + bcp.nome + " saiu da fila de bloqueados");
        this.addProcessoPronto(bcp);
        iterator.remove();
      }
      bcp.esperaBloqueado -= 1;
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
      System.out.println("Interrupção por " + interrupcao + ", Contexto=" + bcp.getContexto());
      bcp.credito -= 1;

      switch (interrupcao) {
      case QUANTUM:
        this.addProcessoPronto(bcp);
        break;
      case ES:
        this.addProcessoBloqueado(bcp);
        break;
      case EOF:
        this.processos.remove(bcp);
        break;
      }

      bcp.estaRodando = false;

      System.out.println();
      System.out.print("Prontos: ");
      for (Bcp b : this.filaDeProntos)
        System.out.print("[" + b.nome + "(" + b.credito + ")], ");
      System.out.println();
      System.out.print("Bloqueados: ");
      for (Bcp b : this.filaDeBloqueados)
        System.out.print("[" + b.nome + "(" + b.credito + ")(" + b.esperaBloqueado + ")], ");
      System.out.println();
    }
  }
}
