package telas;

import java.util.Scanner;
import java.util.ArrayList;
import usuario.Usuario;
import usuario.Voluntario;
import usuario.Veterinario;
import excecoes.SenhaFracaException;
import excecoes.UsuarioDuplicadoException;

public class MenuPrincipal {

    //Login
    public static void login(ArrayList<Usuario> users) {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n\tSISTEMA DE GERENCIAMENTO DE ABRIGOS\n");
        System.out.print("Usuário: ");
        String user = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();

        // Vejo de não tem um usuario já existente
        Usuario usuarioLogado = autenticarUsuario(users, user, senha);

        if (usuarioLogado != null) {
            System.out.println("\nLogin realizado com sucesso! Bem-vindo(a), " + user + ".");
            usuarioLogado.exibirMenu();
        } else {
            System.out.println("\nErro: Usuário ou senha incorretos.");
        }
    }

    // Vejo se tem o mesmo nome de usuario no sistema
    private static Usuario autenticarUsuario(ArrayList<Usuario> users, String user, String senha) {
        for (Usuario u : users) {
            if (u.getNomeUser().equalsIgnoreCase(user) && u.getSenha().equals(senha)) {
                return u; // Retorna o objeto se achar
            }
        }
        return null; // Retorna nulo se não achar ninguém
    }

    // Cria um novo Usuário
    private static Usuario funcaoUsuario(int tipo, String user, String senha) throws SenhaFracaException {
        if (tipo == 1) {
            return new Voluntario(user, senha);
        } else if (tipo == 2) {
            return new Veterinario(user, senha);
        } else {
            System.out.println("Tipo inválido. Cancelando cadastro.");
            return null;
        }
    }

    // Cadastro de Usuário
    public static void cadastroSistema(ArrayList<Usuario> users) {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n\tCADASTRO DE USUÁRIOS\n");
        System.out.println("Qual o tipo de perfil?\n1 - Voluntário\n2 - Veterinário");
        System.out.print("> ");
        int tipo = Integer.parseInt(sc.nextLine());

        System.out.print("\nUsuário: ");
        String user = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();

        try {
            // Se ele é Veterinário ou Voluntário
            Usuario novoUsuario = funcaoUsuario(tipo, user, senha);

            if(novoUsuario != null) {
                novoUsuario.analisarUser(users);
                users.add(novoUsuario);
                System.out.println("\nUsuário " + novoUsuario.getNomeUser() + " cadastrado com sucesso");
            }

        } catch (SenhaFracaException e) {
            System.out.println("\nErro: " + e.getMessage());
            System.out.println("Tente cadastrar novamente com uma senha mais forte.");
        } catch (UsuarioDuplicadoException e) {
            System.out.println("\nErro: " + e.getMessage());
            System.out.println("Escolha um nome de usuário diferente.");
        }
    }
}