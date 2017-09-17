
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Escalonador {

    private List<Bcp> listaDeProcessos = new ArrayList<Bcp>();

    public void addProcesso(Bcp bcp) {

        listaDeProcessos.add(bcp);

        Collections.sort(listaDeProcessos, new Comparator<Bcp>() {
            @Override public int compare(Bcp bcp1, Bcp bcp2) {
                return bcp2.prioridade - bcp1.prioridade;
            }

        });
    }


}