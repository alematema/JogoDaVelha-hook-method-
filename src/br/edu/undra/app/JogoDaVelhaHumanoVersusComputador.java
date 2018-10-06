package br.edu.undra.app;

import br.edu.undra.modelo.JogoDaVelha;
import java.util.Scanner;

/**
 * Jogo da velha : humano versus o computador
 *
 * @author alexandre
 */
public class JogoDaVelhaHumanoVersusComputador extends JogoDaVelha {

    public JogoDaVelhaHumanoVersusComputador(String nome) {
        super(nome);
    }

    @Override
    public void inicia() {

        System.err.println(super.getNome());

        super.getJogador1().setPrimeiroAJogar(true);
        super.getJogador1().setNome("Computador");
        super.getJogador2().setNome("Humano");

        Scanner s = new Scanner(System.in);

        while (! super.terminou()) {

            super.getProximoAJogar().joga();
            if(super.terminou()){
                System.err.println(super.getEstado());
                break;
            }

            System.err.println(super.getEstado());

            System.err.println("Digite uma posicao (1 a 9)");

            int posicao = s.nextInt();

            while (!super.getProximoAJogar().joga(posicao)) {

                System.err.println("\n" + posicao + " É POSIÇÃO INVÁLIDA! VÁLIDAS SÃO ENTRE 1 e 9 E DESOCUPADAS...");
                System.out.println("\nSUA VEZ DE JOGAR... digite a posicao (1 a 9) ");

                posicao = s.nextInt();

            }
            
            if(super.terminou()){
                System.err.println(super.getEstado());
                break;
            }

        }
        System.err.println("\t"+">>>>>>>>>>> FIM DE JOGO <<<<<<<<<<");
        if (super.getJogador1().venceu()) {
            System.err.println("\t"+super.getJogador1().getNome() + " venceu em " + super.getOndeVenceu());
        } else if (super.getJogador2().venceu()) {
            System.err.println("\t"+super.getJogador2().getNome() + " venceu em " + super.getOndeVenceu());
        } else {
            System.err.println("\t"+" EMPATOU ");
        }

    }

    public static void main(String[] args) {
        JogoDaVelhaHumanoVersusComputador humanoVersusComputador = new JogoDaVelhaHumanoVersusComputador("Jogo da velha : humano versus o computador");
        humanoVersusComputador.inicia();
    }

}
