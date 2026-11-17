package usuario;

import excecoes.SenhaFracaException;
import telas.menu.MenuUsuarios;
import animais.Animal;

import java.util.ArrayList;

public class Voluntario extends Usuario {
    private ArrayList<Animal> minhasSolicitacoes;

    public Voluntario(String nomeUser, String senha) throws SenhaFracaException {
        super(nomeUser, senha);
        this.minhasSolicitacoes = new ArrayList<>();
    }

    public void adicionarSolicitacao(Animal pet) {
        this.minhasSolicitacoes.add(pet);
    }

    public ArrayList<Animal> getMinhasSolicitacoes() {
        return this.minhasSolicitacoes;
    }

    @Override
    public void notificacoes(){

    }

    // Exibe o menu do Voluntário
    @Override
    public void exibirMenu() {
        int opcao = -1;

        do {
            try {
                opcao = MenuUsuarios.menuVoluntario();

                switch (opcao) {
                    case 1:
                        // Visualizar Animais Disponíveis
                        break;
                    case 2:
                        // Solicitar Adoções
                        break;
                    case 3:
                        // Consultar Minhas Solicitações
                        break;
                    case 4:
                        // Participar de Eventos
                        break;
                    case 5:
                        // Histórico de Participações
                        break;
                    case 6:
                        // Notificações
                    case 0:
                        System.out.println("\nSaindo da área do Voluntário...");
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