package usuario;

import excecoes.SenhaFracaException;
import telas.menu.MenuUsuarios;

public class Administrador extends Usuario{
    public Administrador(String nomeUser, String senha) throws SenhaFracaException {
        super(nomeUser, senha);

    }

    @Override
    public void notificacoes(){

    }

    @Override
    public void exibirMenu(){
        int opcao = -1;

        do {
            try {
                opcao = MenuUsuarios.menuAdmin();

                switch (opcao) {
                    case 1:
                        // Gerenciar Animais
                        break;
                    case 2:
                        // Gerenciar Usuarios
                        break;
                    case 3:
                        // Aprovar Adoções
                        break;
                    case 4:
                        // Gerenciar Eventos
                        break;
                    case 5:
                        // Relatórios
                        break;
                    case 6:
                        // Estatísticas
                        break;
                    case 7:
                        // Notificações
                    case 0:
                        System.out.println("\nSaindo da área do Admin...");
                        break;
                    default:
                        System.out.println("\nErro: Opção inválida! Escolha um número de 0 a 6.");
                }
            } catch (Exception e) {
                System.out.println("\nErro: Entrada inválida. Tente novamente.");
                opcao = -1;
            }

        } while (opcao != 0); // Só sai desse menu se a pessoa digitar 0
    }
}
