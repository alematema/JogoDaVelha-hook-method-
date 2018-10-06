package br.edu.undra.app;

import br.edu.undra.modelo.Jogador;
import br.edu.undra.modelo.JogoDaVelha;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Jogo da velha : computador contra o computador
 *
 * @author alexandre
 */
public class JogoDaVelhaComputadorVersusComputador extends JogoDaVelha<Jogador> {

    public JogoDaVelhaComputadorVersusComputador(String nome) {
        super(nome);
    }

    @Override
    public void inicia() {
        
        System.err.println(super.getNome());

        super.getJogador1().setPrimeiroAJogar(true);

        while (!super.terminou()) {

            super.getProximoAJogar().joga();

            System.err.println(super.getEstado());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(JogoDaVelhaComputadorVersusComputador.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        if (super.getJogador1().venceu()) {
            System.err.println(super.getJogador1().getNome() + " venceu em " + super.getOndeVenceu());
        } else if (super.getJogador2().venceu()) {
            System.err.println(super.getJogador2().getNome() + " venceu em " + super.getOndeVenceu());
        }else{
            System.err.println(" EMPATOU ");
        }

    }

    public static void main(String[] args) {

        JogoDaVelhaComputadorVersusComputador computadorVersusComputador = new JogoDaVelhaComputadorVersusComputador("Jogo da velha : computador contra o computador");
        computadorVersusComputador.inicia();
    }

}
