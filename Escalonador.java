import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Escalonador {

	private List<Bcp> listaDeProntos = new ArrayList<Bcp>();
	private List<Bcp> listaDeBloqueado = new ArrayList<Bcp>();

	public void addProcessoPronto(Bcp bcp) {

		listaDeProntos.add(bcp);

		Collections.sort(listaDeProntos, new Comparator<Bcp>() {
			@Override
			public int compare(Bcp bcp1, Bcp bcp2) {
				return bcp2.prioridade - bcp1.prioridade;
			}
		});
	}

	public void addProcessoBloqueado(Bcp bcp) {
		listaDeBloqueado.add(bcp);
	}
}
