import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Escalonador {

	private List<Bcp> filaDeProntos = new ArrayList<Bcp>();
	private List<Bcp> listaDeBloqueados = new ArrayList<Bcp>();

	public void addProcessoPronto(Bcp bcp) {
		filaDeProntos.add(bcp);
    ordenaFila();
	}

	public void addProcessoBloqueado(Bcp bcp) {
		listaDeBloqueados.add(bcp);
	}

  private static void ordenaFila() {
    Collections.sort(filaDeProntos, new Comparator<Bcp>() {
      @Override
      public int compare(Bcp bcp1, Bcp bcp2) {
        return bcp2.prioridade - bcp1.prioridade;
      }
    });
  }


  public void rodaPrimeiroProcesso() {
    Bcp bcp = filaDeProntos.get(0); // pega o primeiro da fila

    while(bcp.quantum > 0)
      // ALGUMA COISA Q RODE A LINHA LÁ

    bcp.prioridade--;
    ordenaFila():
  }
}
