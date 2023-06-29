import java.util.Scanner;

public class TrabalhoFinal {


    private TrabalhoFinal() {
        char tabuleiro[][] = new char[6][7]; //[linha][coluna]
        Scanner teclado = new Scanner(System.in);
        char jogador = ' ';
        boolean jogoAcabou = false; //antes que qualquer jogada seja feita, o jogo ainda não acabou. Portanto, é necessário inicializar 'jogoAcabou' como 'false'.
        while (!jogoAcabou) { //enquanto for falso.
            inicializarTabuleiro(tabuleiro);
            jogador = escolherCor(teclado);
            while (!jogoAcabou) {
                jogoAcabou = jogadaJogador(teclado, tabuleiro, jogador, jogoAcabou);
                if (!jogoAcabou) {
                    jogoAcabou = jogadaComputador(tabuleiro, jogoAcabou);
                }
            }
            jogoAcabou = perguntarReinicio(teclado);
            
        }
    }

    private void inicializarTabuleiro(char tabuleiro[][]) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                tabuleiro[i][j] = '.';
            }
        }
    }

    private char escolherCor(Scanner teclado) {
        System.out.println("Escolha sua cor (V = Vermelho / A = Azul):");
        char jogador = teclado.next().toUpperCase().charAt(0);
        if(jogador != 'A' && jogador != 'V') {
            System.out.println("Cor incorreta, tente novamente");
            return escolherCor(teclado);
        }
        return jogador;
    }

    private boolean jogadaJogador(Scanner teclado, char tabuleiro[][], char jogador, boolean jogoAcabou) {
        System.out.println("Jogador " + jogador + ", faça sua jogada (0-6):");
        int coluna = teclado.nextInt();
        if (coluna < 0 || coluna > 6 || tabuleiro[0][coluna] != '.') {
            System.out.println("Coluna inválida! Tente novamente.");
            jogadaJogador(teclado, tabuleiro, jogador, jogoAcabou);
        } else {
            fazerJogada(coluna, jogador, tabuleiro);
            imprimirTabuleiro(tabuleiro);
            if (verificarVitoria(jogador, tabuleiro)) {
                jogoAcabou = true;
                System.out.println("Parabéns! O jogador " + jogador + " venceu!");
            } else if (verificarEmpate(tabuleiro)) {
                jogoAcabou = true;
                System.out.println("Empate!");
            }
        }
        return jogoAcabou;
    }

   private boolean jogadaComputador(char tabuleiro[][], boolean jogoAcabou) {
        System.out.println("Vez do computador:");
        int coluna = (int) (Math.random() * 7); //retorna um número decimal entre 0 e 1. Multiplicando esse valor por 7 e convertendo para um número inteiro com (int), obtemos um número aleatório entre 0 e 6.
        if (tabuleiro[0][coluna] == '.') {//se a posição estiver vazia, significa que é possível fazer a jogada nessa coluna.
            fazerJogada(coluna, 'C', tabuleiro);
            imprimirTabuleiro(tabuleiro);
            if (verificarVitoria('C', tabuleiro)) {
                jogoAcabou = true;
                System.out.println("O computador venceu!");
            } else if (verificarEmpate(tabuleiro)) {
                jogoAcabou = true;
                System.out.println("Empate!");
            }
        } else {
            jogoAcabou = jogadaComputador(tabuleiro, jogoAcabou);
        }
        return jogoAcabou;
    }

    private void fazerJogada(int coluna, char cor, char tabuleiro[][]) {
        for (int i = 5; i >= 0; i--) { //de baixo para cima até a primeira linha (índice 0)
            if (tabuleiro[i][coluna] == '.') {
                tabuleiro[i][coluna] = cor;
                break;
            }
        }
    }

    private void imprimirTabuleiro(char tabuleiro[][]) {
        System.out.println("\nTabuleiro:");
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(tabuleiro[i][j] + " | ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private boolean verificarVitoria(char cor, char tabuleiro[][]) {
        // Verificar vitória na horizontal
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) { //O limite 3 é usado porque é necessário ter 4 posições consecutivas para formar uma vitória
                if (tabuleiro[i][j] == cor && tabuleiro[i][j + 1] == cor && tabuleiro[i][j + 2] == cor && tabuleiro[i][j + 3] == cor) {
                    return true;
                }
            }
        }

        // Verificar vitória na vertical
        for (int i = 0; i < 3; i++) { //bom ele vai percorrer da linha 0 a até a linha 3, se a letra do jogador estiver ali e condir com as condições, o jogador ganhará[i]
            for (int j = 0; j < 7; j++) {//ja percorre todas as colunas[j]
                if (tabuleiro[i][j] == cor && tabuleiro[i + 1][j] == cor && tabuleiro[i + 2][j] == cor && tabuleiro[i + 3][j] == cor) {
                    return true;
                }
            }
        }

        // Verificar vitória na diagonal (direita)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j <= 4; j++) {//percorre até 4 colunas pois apenas 4 colunas é o suficiente para ganhar.
                if (tabuleiro[i][j] == cor && tabuleiro[i + 1][j + 1] == cor && tabuleiro[i + 2][j + 2] == cor && tabuleiro[i + 3][j + 3] == cor) {
                    return true;
                }
            }
        }

        // Verificar vitória na diagonal (esquerda)
        for (int i = 0; i < 3; i++) {
            for (int j = 3; j <= 6; j++) {//pois o numero não pode dar -1. 
                if (tabuleiro[i][j] == cor && tabuleiro[i + 1][j - 1] == cor && tabuleiro[i + 2][j - 2] == cor && tabuleiro[i + 3][j - 3] == cor) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean verificarEmpate(char tabuleiro[][]) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (tabuleiro[i][j] == '.') {// se tiver qualquer 'B' no tabuleiro ainda não acabou, portanto não é empate return false.
                    return false;
                }
            }
        }
        return true; // se todas as posições estiverem preenchidas dai retorna true sendo empate.
    }

    private boolean perguntarReinicio(Scanner teclado) {
        boolean acabou = true;
        System.out.println("Deseja jogar novamente? (S/N):");
        char opcao = teclado.next().toUpperCase().charAt(0);
        if (opcao == 'S') {
            acabou = false;
        } else {
            System.out.println("Fim do jogo. Obrigado por jogar!");
        }
        return acabou;
    }

    public static void main(String[] args) {
         new TrabalhoFinal();
    }
}

