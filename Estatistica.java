import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Estatistica {
    private static Map<Bcp, Integer> trocas = new HashMap<Bcp, Integer>();
    private static Map<Bcp, List<Integer>> instrucoes = new HashMap<Bcp, List<Integer>>();

    public static void adicionarTroca(Bcp bcp) {
        if (trocas.get(bcp) == null)
            trocas.put(bcp, 0);
        trocas.put(bcp, trocas.get(bcp) + 1);
    }

    public static void adicionarInstrucao(Bcp bcp, int v) {
        if (instrucoes.get(bcp) == null)
            instrucoes.put(bcp, new LinkedList<Integer>());
        instrucoes.get(bcp).add(v);
    }

    public static double calculaMediaTrocas() {
        int n = 0;
        int sum = 0;
        for (Integer value : trocas.values()) {
            sum += value;
            n++;
        }
        return ((double) sum) / n;
    }

    public static double calculaMediaInstrucoes() {
        int total = 0;
        int totalCount = 0;
        Iterator it = instrucoes.entrySet().iterator();
        for (Map.Entry<Bcp, List<Integer>> entry : instrucoes.entrySet()) {
            for (Integer value : entry.getValue()) {
                total += value;
                totalCount++;
            }
        }
        return ((double) total) / totalCount;
    }
}