package telas;

import java.util.Scanner;

public class MenuUsuarios {
    public static int menuVeterinario(){
        Scanner sc = new Scanner(System.in);

        System.out.println("\n\tÁREA DO VETERINÁRIO\n");
        System.out.println("1 - Consultar Animais ");
        System.out.println("2 - Registrar Atendimento");
        System.out.println("3 - Atualizar Tratamento");
        System.out.println("4 - Registrar Vacinação");
        System.out.println("5 - Emitir Relatório Médico");
        System.out.println("6 - Notificações");
        System.out.println("0 - Sair");

        System.out.println("Escolha uma opção: ");
        System.out.print("> ");

        return Integer.parseInt(sc.nextLine());
    }

    public static int menuVoluntario(){
        Scanner sc = new Scanner(System.in);

        System.out.println("\n\tÁREA DO VOLUNTÁRIO\n");
        System.out.println("1 - Visualizar Animais Disponíveis");
        System.out.println("2 - Solicitar Adoção");
        System.out.println("3 - Consultar minhas solicitações");
        System.out.println("4 - Participar de Eventos");
        System.out.println("5 - Histórico de Participações");
        System.out.println("6 - Notificações");
        System.out.println("0 - Sair");

        System.out.println("Escolha uma opção: ");
        System.out.print("> ");

        return Integer.parseInt(sc.nextLine());
    }

    public static int menuAdmin(){
        Scanner sc = new Scanner(System.in);

        System.out.println("\n\tÁREA DO ADMINISTRADOR\n");
        System.out.println("1 - Gerenciar Animais");
        System.out.println("2 - Gerenciar Usuários");
        System.out.println("3 - Aprovar Adoções");
        System.out.println("4 - Gerenciar Eventos");
        System.out.println("5 - Relatórios");
        System.out.println("6 - Estatísticas");
        System.out.println("7 - Notificações");
        System.out.println("0 - Sair");

        System.out.println("Escolha uma opção: ");
        System.out.print("> ");

        return Integer.parseInt(sc.nextLine());
    }
}
