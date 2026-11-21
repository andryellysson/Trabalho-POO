import eventos.Evento;
import excecoes.SenhaFracaException;
import telas.menu.MenuEntrada;
import telas.menu.MenuPrincipal;
import usuario.Usuario;
import usuario.Administrador;
import animais.Animal;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        ArrayList<Usuario> listaUsuarios = new ArrayList<>();
        ArrayList<Animal> listaAnimais = new ArrayList<>();
        ArrayList<Evento> listaEventos = new ArrayList<>();

        try {
            Usuario adminPadrao = new Administrador("admin", "Admin123");
            listaUsuarios.add(adminPadrao);
        } catch (SenhaFracaException e) {
            System.out.println("ERRO: Falha ao criar o Admin padrão: " + e.getMessage());
        }

        int opcao = -1;

        while (opcao != 0) {
            //Menu principal
            opcao = MenuEntrada.exibir();

            switch (opcao) {
                case 1:
                    MenuPrincipal.login(listaUsuarios, listaAnimais, listaEventos);
                    break;
                case 2:
                    MenuPrincipal.cadastroSistema(listaUsuarios);
                    break;
                case 0:
                    System.out.println("\nEncerrando o sistema... Até logo!");
                    break;
                case -1:
                    System.out.println("\nErro: Por favor, digite apenas números válidos.");
                    break;
                default:
                    System.out.println("\nErro: Opção inválida!");
            }
        }
    }
}