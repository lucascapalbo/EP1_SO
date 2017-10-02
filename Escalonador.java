import java.io.File;
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
  private static final String CAMINHO_DATA = "processos/";
  private static final String CAMINHO_PRIORI = "prioridades.txt";
  private static final String CAMINHO_QUANTUM = "quantum.txt";

  public static final int TEMPO_ESPERA_ES = 2;
  private int quantum;

  private Processador processador;

  private List<Bcp> tabelaProcessos = new ArrayList<Bcp>();

  private List<Bcp> filaDeProntos = new LinkedList<Bcp>();
  private Queue<Bcp> filaDeBloqueados = new LinkedList<Bcp>();

  public Escalonador(Processador processador) {
    this.processador = processador;
  }

  public void carregarProcesso(Bcp processo) {
    this.tabelaProcessos.add(processo);
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
    for (Bcp bcp : this.tabelaProcessos)
      if (bcp.credito != 0)
        return false;
    return true;
  }

  private void distribuirCreditos() {
    for (Bcp bcp : this.tabelaProcessos)
      bcp.credito = bcp.prioridade;

    this.ordenaFilaProntos();
  }

  private void decrementaEsperaBloqueados() {
    for (Iterator<Bcp> iterator = this.filaDeBloqueados.iterator(); iterator.hasNext();) {
      Bcp bcp = iterator.next();
      if (bcp.esperaBloqueado == 0) {
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
    while (this.tabelaProcessos.size() > 0) { // Enquanto a fila de processos não for vazia...
      if (deveDistribuirCreditos())
        distribuirCreditos();

      decrementaEsperaBloqueados();

      Bcp bcp = getPrimeiroProcesso();
      if (bcp == null) // Nenhum processo pronto
        continue;

      bcp.estaRodando = true;

      Logger.escreveExecutando(bcp);

      int pcInicial = bcp.getContexto().PC;
      Interrupcao interrupcao = this.processador.executar(bcp);
      bcp.credito -= 1;
      Estatistica.adicionarTroca(bcp);
      Estatistica.adicionarInstrucao(bcp, bcp.getContexto().PC - pcInicial);

      boolean fim = false;
      switch (interrupcao) {
      case QUANTUM:
        this.addProcessoPronto(bcp);
        break;
      case ES:
        this.addProcessoBloqueado(bcp);
        Logger.escreveES(bcp);
        break;
      case EOF:
        this.tabelaProcessos.remove(bcp);
        fim = true;
        break;
      }
      
      Logger.escreveCallbackBCP(bcp, bcp.getContexto().PC - pcInicial);
      if (fim)
        Logger.escreveFim(bcp);
      bcp.estaRodando = false;
    }

    Logger.escreveEstatistica(Estatistica.calculaMediaTrocas(), Estatistica.calculaMediaInstrucoes(), this.quantum);
  }

  public static void main(String[] args) {
    Leitor.setPasta(CAMINHO_DATA);

    String[] prioridades = Leitor.lerArquivo(CAMINHO_PRIORI);
    int quantum = Integer.parseInt(Leitor.lerArquivo(CAMINHO_QUANTUM)[0]);
    File[] filesProgramas = Leitor.lerProgramas();

    Logger.inicializa("log" + String.format("%02d", quantum) + ".txt");

    Processador processador = new Processador();
    Escalonador escalonador = new Escalonador(processador);
    escalonador.quantum = quantum;
    processador.quantum = quantum;

    for (int i = 0; i < filesProgramas.length; i++) {
      Bcp processo = new Bcp(Leitor.lerArquivo(filesProgramas[i]), Integer.parseInt(prioridades[i]));
      escalonador.carregarProcesso(processo);
    }

    escalonador.distribuirCreditos();
    for (Bcp p : escalonador.filaDeProntos)
      Logger.escreveCarregando(p);

    escalonador.executar();
  }
}
