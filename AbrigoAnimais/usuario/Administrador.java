package usuario;

import animais.Animal;
import animais.AnimalDomestico;
import eventos.Evento;
import excecoes.SenhaFracaException;
import telas.menu.MenuUsuarios;
import telas.opcoesAdmin.FuncoesAdmin;

import java.util.ArrayList;
import java.util.Scanner;

public class Administrador extends Usuario{
    public Administrador(String nomeUser, String senha) throws SenhaFracaException {
        super(nomeUser, senha);

    }

    @Override
    public void notificacoes(){

    }

    public void removerAnimal(ArrayList<Animal> listaAnimais){
        FuncoesAdmin fadmin = new FuncoesAdmin();

        int id = fadmin.removerAnimal(listaAnimais);

        if (id == 0) {
            System.out.println("Operação de remoção cancelada.");
            return;
        }

        boolean encontrar = false;

        for(Animal pet : listaAnimais){
            if(id == pet.getId()){
                listaAnimais.remove(pet);
                encontrar = true;
                break;
            }
        }

        if(!encontrar){
            System.out.println("Erro: O Id " + id + " não foi encontrado no sistema! Nenhum animal foi removido.");
        } else {
            System.out.println("Animal removido com sucesso!");
        }
    }

    public void editarAnimal(ArrayList<Animal> listaAnimais){
        FuncoesAdmin fAdmin = new FuncoesAdmin();
        fAdmin.editarAnimal(listaAnimais);
    }

    public void cadastrarAnimais(ArrayList<Animal> listaAnimais){
        FuncoesAdmin fAdmin = new FuncoesAdmin();
        fAdmin.cadastrarAnimais(listaAnimais);
    }

    public void listarUsers(ArrayList<Usuario> listaUsers){
        FuncoesAdmin fAdmin = new FuncoesAdmin();
        fAdmin.listarUsuarios(listaUsers);
    }

    public void aprovarAdocoes(ArrayList<Usuario> listaUsuarios, ArrayList<Animal> listaAnimais) {
        FuncoesAdmin fAdmin = new FuncoesAdmin();

        int idAnimal = fAdmin.selecionarSolicitacao(listaUsuarios);

        if (idAnimal == 0 || idAnimal == -1) {
            return;
        }

        // Encontrar o Animal e o Voluntario
        Voluntario voluntarioSolicitante = null;
        Animal animalSolicitado = null;

        for (Usuario user : listaUsuarios) {
            if (user instanceof Voluntario) {
                Voluntario vol = (Voluntario) user;
                for (Animal pet : vol.getMinhasSolicitacoes()) {
                    if (pet.getId() == idAnimal) {
                        voluntarioSolicitante = vol;
                        animalSolicitado = pet;
                        break;
                    }
                }
            }
        }

        // Se o admin digitou um ID que não estava na lista de pedidos
        if (animalSolicitado == null) {
            System.out.println("Erro: Nenhuma solicitação encontrada para o ID informado.");
            return;
        }

        int decisao = fAdmin.obterDecisaoAdmin(animalSolicitado.getNome(), voluntarioSolicitante.getNomeUser());

        if (decisao == 1) {
            // APROVADO
            voluntarioSolicitante.getMinhasSolicitacoes().remove(animalSolicitado);

            // O animal deixa de estar presente no abrigo
            animalSolicitado.setPresenteNoAbrigo(false);

            System.out.println("Adoção aprovada com sucesso!");

        } else if (decisao == 2) {
            // NEGADO
            voluntarioSolicitante.getMinhasSolicitacoes().remove(animalSolicitado);

            // O animal volta a ficar disponível no sistema
            if (animalSolicitado instanceof AnimalDomestico) {
                ((AnimalDomestico) animalSolicitado).setDisponivelParaAdocao(true);
            }
            System.out.println("Adoção negada com sucesso!");
        }
    }

    public void gerenciarEventos(ArrayList<Evento> listaEventos, ArrayList<Usuario> listaUsuarios) {
        Scanner sc = new Scanner(System.in);
        FuncoesAdmin fAdmin = new FuncoesAdmin();
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n\tGERENCIAR EVENTOS");
            System.out.println("1 - Cadastrar Novo Evento");
            System.out.println("2 - Listar Eventos");
            System.out.println("3 - Cancelar Evento");
            System.out.println("0 - Voltar");
            System.out.print("> ");

            try {
                opcao = Integer.parseInt(sc.nextLine());

                switch (opcao) {
                    case 1:
                        Evento novoEvento = fAdmin.criarNovoEvento();
                        listaEventos.add(novoEvento);
                        System.out.println("Evento salvo com sucesso!");
                        break;

                    case 2:
                        fAdmin.listarEventos(listaEventos);
                        break;

                    case 3: //Encerra o evento e conta os participantes
                        fAdmin.listarEventos(listaEventos);
                        System.out.print("\nDigite o ID do evento para encerrar (ou 0 para cancelar): ");
                        int idEncerrar = Integer.parseInt(sc.nextLine());

                        if (idEncerrar == 0) break;

                        boolean encontrou = false;
                        for (Evento ev : listaEventos) {
                            if (ev.getId() == idEncerrar && ev.isAtivo()) {

                                // Conta os voluntários
                                int qtdParticipantes = 0;
                                for (Usuario user : listaUsuarios) {
                                    if (user instanceof Voluntario) {
                                        Voluntario vol = (Voluntario) user;
                                        if (vol.getMeusEventos().contains(ev)) {
                                            qtdParticipantes++;
                                        }
                                    }
                                }

                                // Desativa o Evento
                                ev.setAtivo(false);

                                // Salva o número para o relatório
                                ev.setTotalParticipantes(qtdParticipantes);

                                System.out.println("\nEvento '" + ev.getNome() + "' encerrado com sucesso!");
                                System.out.println("-> Total de voluntários que participaram: " + ev.getTotalParticipantes() + " pessoas.");

                                encontrou = true;
                                break;
                            }
                        }
                        if (!encontrou) {
                            System.out.println("Erro: ID não encontrado ou evento já encerrado.");
                        }
                        break;

                    case 0:
                        break;

                    default:
                        System.out.println("Erro: Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite apenas números nas opções!");
            }
        }
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
