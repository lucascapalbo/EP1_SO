/**
 * Classe responsável por executar os programas
 */
public class Processador {

    public static class Estado {
        int x = 0;
        int y = 0;
        int pc;
    }

    private Estado contexto;

    public void setContexto(Estado contexto) {
        this.contexto = contexto;
    }

    public Estado getContexto() {
        return contexto;
    }

    public Interrupcao executar(Bcp processo) {
        this.setContexto(processo.getContexto());

        Estado registradores = this.getContexto();

        int quantumCont = processo.quantum;

        for (; registradores.pc <= processo.programa.length; registradores.pc++) {
            if (quantumCont == 0)
                return Interrupcao.QUANTUM;
            quantumCont--;
            //TODO interpretar linha de código
            // return Interrupcao.IO;
        }

        return Interrupcao.EOF;
    }

}