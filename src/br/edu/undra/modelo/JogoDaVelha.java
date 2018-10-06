package br.edu.undra.modelo;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Um jogo da velha
 *
 * @author alexandre
 * @param <T>
 */
public class JogoDaVelha<T extends Jogador> extends Jogo {

    private String id;

    private JogadorJogoDaVelha jogador1;
    private JogadorJogoDaVelha jogador2;
    private String ondeVenceu = "";

    private Set<T> ultimosAJogar = new HashSet<>();

    public JogoDaVelha(String nome) {

        super();

        jogador1 = new JogadorJogoDaVelha("jogador 1");
        jogador2 = new JogadorJogoDaVelha("jogador 2");
        Tabuleiro tabuleiro = new Tabuleiro(3);

        List<JogadorJogoDaVelha> jogadores = Arrays.asList(jogador1, jogador2);
        setNome(nome);
        setJogadores(jogadores);
        setTabuleiro(tabuleiro);

        setUpJogadores();
    }

    public JogoDaVelha(String nome, List<T> jogadores, Tabuleiro tabuleiro) {
        super(nome, jogadores, tabuleiro);
        if (jogadores.size() != 2) {
            throw new IllegalArgumentException("Devem haver EXATAMENTE 2 jogadores para o jogo da velha ok.");
        }
        setUpJogadores();
    }

    public JogoDaVelha(List<T> jogadores, Tabuleiro tabuleiro) {
        super(jogadores, tabuleiro);
        if (jogadores.size() != 2) {
            throw new IllegalArgumentException("Devem haver EXATAMENTE 2 jogadores para o jogo da velha ok.");
        }
        setUpJogadores();
    }

    public JogoDaVelha(String nome, String id, List<T> jogadores, Tabuleiro tabuleiro) {
        super(nome, jogadores, tabuleiro);
        if (jogadores.size() != 2) {
            throw new IllegalArgumentException("Devem haver EXATAMENTE 2 jogadores para o jogo da velha ok.");
        }
        this.id = id;
        setUpJogadores();
    }

    @Override
    public void setUpJogadores() {
        for (JogadorJogoDaVelha jogador : (List<JogadorJogoDaVelha>) getJogadores()) {
            jogador.setJogo(this);
        }
    }

    public JogadorJogoDaVelha getJogador1() {
        return jogador1;
    }

    public JogadorJogoDaVelha getJogador2() {
        return jogador2;
    }

    public String getOndeVenceu() {
        return ondeVenceu;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void inicia() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String toString() {

        String toString = "";

        toString += getNome();
        toString += "\n\n";

        String[] estados = getTabuleiro().getEstado().split((String) Tabuleiro.SEPARADOR);

        int coluna = 1;

        for (String estado : estados) {

            int valor = Integer.parseInt(estado.split(",")[2]);

            if (valor == 0) {
                toString += ". ";
            } else if (valor % 2 == 0) {
                toString += "o ";
            } else {
                toString += "x ";
            }

            if (coluna % getTabuleiro().getDimensao() == 0) {
                toString += "\n";
            }

            coluna++;

        }

        return toString;
    }

    public String getEstado() {

        String comoEstaOJogo = "";

        comoEstaOJogo += "\n";

        String[] estados = getTabuleiro().getEstado().split((String) Tabuleiro.SEPARADOR);

        int coluna = 1;

        for (String estado : estados) {

            int valor = Integer.parseInt(estado.split(",")[2]);

            if (valor == 0) {
                comoEstaOJogo += ". ";
            } else if (valor % 2 == 0) {
                comoEstaOJogo += "o ";
            } else {
                comoEstaOJogo += "x ";
            }

            if (coluna % getTabuleiro().getDimensao() == 0) {
                comoEstaOJogo += "\n";
            }

            coluna++;

        }

        return comoEstaOJogo;

    }

    JogadorJogoDaVelha proximoAJogar = null;

    public void setProximoAJogar(JogadorJogoDaVelha proximoAJogar) {
        this.proximoAJogar = proximoAJogar;
    }

    @Override
    public JogadorJogoDaVelha getProximoAJogar() {

        if (proximoAJogar != null) {

            if (!proximoAJogar.jogou()) {
                return proximoAJogar;
            }

            getUltimosAJogar().clear();

            for (JogadorJogoDaVelha j : (List<JogadorJogoDaVelha>) getJogadores()) {

                if (!j.equals(proximoAJogar)) {

                    proximoAJogar = j;
                    break;

                }
            }

        } else {

            for (JogadorJogoDaVelha j : (List<JogadorJogoDaVelha>) getJogadores()) {

                if (j.isPrimeiroAJogar()) {

                    proximoAJogar = j;

                }

            }

        }

        proximoAJogar.setElemento(proximoAJogar.getAtual());
        proximoAJogar.setAtual(proximoAJogar.getAtual() + 2);

        proximoAJogar.setJogou(false);

        return proximoAJogar;

    }

    @Override
    public String getProximaJogadaParaJogador(Jogador jogador) {

        if (jogador.jogou()) {
            return null;
        }

        List<Object> posicoesLivres = getTabuleiro().getPosicoesLivres();

        byte[] seed = new byte[6];
        seed[0] = 2;
        seed[1] = -1;
        seed[2] = 12;
        seed[3] = 11;
        seed[4] = -25;
        seed[5] = 30;

        SecureRandom r = new SecureRandom(seed);

        int posicao = r.nextInt(posicoesLivres.size() >= 1 ? posicoesLivres.size() : 1);

        String posicaoLivre = (String) posicoesLivres.get(posicao);

        String[] p = posicaoLivre.split(",");

        //System.out.println("pegando proxima jogada " + p[0] + "," + p[1] + " para " + jogador.getNome() + ", " + System.nanoTime());
        return p[0] + "," + p[1];

    }

    @Override
    public Set getUltimosAJogar() {
        return this.ultimosAJogar;
    }

    public boolean jogadorVenceu(JogadorJogoDaVelha jogador) {

        boolean venceu = false;

        List<Object> elementos;

        //varre colunas procurando trinca
        for (int coluna = 1; coluna <= getTabuleiro().getDimensao(); coluna++) {

            elementos = getTabuleiro().getColuna(coluna);

            if (aoMenosUmaTrinca(elementos, jogador)) {
                venceu = true;
                ondeVenceu = "coluna " + coluna;
                break;
            }

        }

        if (!venceu) {//CONTINUA PROCURANDO TRINCA ...

            //varre linhas procurando trinca
            for (int linha = 1; linha <= getTabuleiro().getDimensao(); linha++) {

                elementos = getTabuleiro().getLinha(linha);

                if (aoMenosUmaTrinca(elementos, jogador)) {
                    venceu = true;
                    ondeVenceu = "linha " + linha;
                    break;
                }

            }

        }

        if (!venceu) {//CONTINUA PROCURANDO TRINCA ...

            //varre diagonal principal procurando trinca
            for (int i = 1; i <= getTabuleiro().getDimensao(); i++) {

                elementos = getTabuleiro().getDiagonalPrincipal();

                if (aoMenosUmaTrinca(elementos, jogador)) {
                    venceu = true;
                    ondeVenceu = "diagonal principal";
                    break;
                }

            }

        }

        if (!venceu) {//CONTINUA PROCURANDO TRINCA ...

            //varre diagonal secundaria procurando trinca
            for (int i = 1; i <= getTabuleiro().getDimensao(); i++) {

                elementos = getTabuleiro().getDiagonalSecundaria();

                if (aoMenosUmaTrinca(elementos, jogador)) {
                    venceu = true;
                    ondeVenceu = "diagonal secundaria";
                    break;
                }

            }

        }

        return venceu;
    }

    public boolean aoMenosUmaTrinca(List<Object> elementos, JogadorJogoDaVelha jogador) {

        boolean aoMenosUmaTrinca = true;

        if (jogador.isPrimeiroAJogar()) {
            //procura por apenas IMPARES
            for (Object e : elementos) {
                if (((Integer) e) % 2 == 0) {
                    aoMenosUmaTrinca = false;
                    break;
                }
            }

        } else {

            //procura por apenas PARES, DIFERENTES DE MARCADOR POSICAO_LIVRE
            for (Object e : elementos) {
                if (Objects.equals((Integer) e, (Integer) Tabuleiro.POSICAO_LIVRE) || (((Integer) e) % 2 != 0)) {
                    aoMenosUmaTrinca = false;
                    break;
                }
            }

        }

        return aoMenosUmaTrinca;
    }

    @Override
    public void iniciar() {
        //
    }

    @Override
    public boolean terminou() {
        return jogador1.venceu() || jogador2.venceu() || getTabuleiro().getPosicoesLivres().isEmpty();
    }


}
