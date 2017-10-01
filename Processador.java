/**
 * Classe responsável por executar os programas
 */
public class Processador {

    private static enum ResultadoProcessamento {
        OK, ES, ERRO, FIM
    }

    public static class Estado {
        int X = 0;
        int Y = 0;
        int PC = 1;

        @Override
        public String toString() {
            return "<X=" + this.X + " Y=" + this.Y + " PC=" + this.PC + ">";
        }
    }

    private Estado contexto;

    public void setContexto(Estado contexto) {
        this.contexto = contexto;
    }

    public Estado getContexto() {
        return contexto;
    }

    private ResultadoProcessamento processar(String instrucao) {
        instrucao = instrucao.trim();
        if (instrucao.equals("E/S"))
            return ResultadoProcessamento.ES;

        if (instrucao.equals("COM"))
            return ResultadoProcessamento.OK;

        if (instrucao.equals("SAIDA"))
            return ResultadoProcessamento.FIM;
        try {
            if (instrucao.startsWith("X=")) {
                this.contexto.X = Integer.parseInt(instrucao.substring(2));
                return ResultadoProcessamento.OK;
            }

            if (instrucao.startsWith("Y=")) {
                this.contexto.Y = Integer.parseInt(instrucao.substring(2));
                return ResultadoProcessamento.OK;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultadoProcessamento.ERRO;
        }

        return ResultadoProcessamento.ERRO;

    }

    public Interrupcao executar(Bcp processo) {
        this.setContexto(processo.getContexto());

        Estado registradores = this.getContexto();

        int quantumCont = processo.quantum;
        Escritor.escreve("Iniciando: " + processo.nome);
        for (; registradores.PC <= processo.programa.length;) {
            if (quantumCont == 0)
                return Interrupcao.QUANTUM;
            ResultadoProcessamento resultado = this.processar(processo.programa[registradores.PC]);
            Escritor.escreve(this.contexto.PC + ">'" + processo.programa[registradores.PC] + "' ---> " + resultado);
            registradores.PC++;

            switch (resultado) {
            case ES:
                return Interrupcao.ES;
            case FIM:
                return Interrupcao.EOF;
            case ERRO:
                return Interrupcao.EOF;

            default:
            }
            quantumCont--;
        }

        return Interrupcao.EOF;
    }

}
