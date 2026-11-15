package telas;

import java.util.Scanner;

public class MenuEntrada {

    public static int exibir() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\tSISTEMA DE ABRIGO DE ANIMAIS\n");
        System.out.println("1 - Fazer Login");
        System.out.println("2 - Cadastrar Novo Usuário");
        System.out.println("0 - Sair do Sistema");
        System.out.print("Escolha uma opção: \n> ");

        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return -1; // Retorna -1 se o usuário digitar letras
        }
    }
}