package usuario;

import excecoes.SenhaFracaException;
import telas.menu.MenuUsuarios;

public class Veterinario extends Usuario{
    public Veterinario(String nomeUser, String senha) throws SenhaFracaException {
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
                opcao = MenuUsuarios.menuVeterinario();

                switch (opcao) {
                    case 1:
                        // Consultar Animais
                        break;
                    case 2:
                        // Registrar Atendimentos
                        break;
                    case 3:
                        // Atualizar Tratamentos
                        break;
                    case 4:
                        // Registrar Vacinação
                        break;
                    case 5:
                        // Emitir Relatórios Médicos
                        break;
                    case 6:
                        // Notificações
                    case 0:
                        System.out.println("\nSaindo da área do Veterinário...");
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

