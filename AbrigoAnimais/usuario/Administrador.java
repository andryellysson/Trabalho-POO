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
    public void notificacoes(ArrayList<Usuario> listaUsuarios, ArrayList<Animal> listaAnimais, ArrayList<Evento> listaEventos) {
        System.out.println("\n\tNOTIFICAÇÕES");
        int cont = 0;

        //Procurar por novas adoções pendentes
        int adocoesPendentes = 0;
        for (Usuario user : listaUsuarios) {
            if (user instanceof Voluntario) {
                Voluntario vol = (Voluntario) user;
                adocoesPendentes += vol.getMinhasSolicitacoes().size();
            }
        }

        if (adocoesPendentes > 0) {
            System.out.println("[NOVA ADOÇÃO] Há " + adocoesPendentes + " solicitação(ões) de adoção aguardando sua análise e aprovação!");
            cont++;
        }

        //Notificação de usuário cadastrados
        int totalUsuarios = listaUsuarios.size();
        if (totalUsuarios > 1) { // Maior que 1 para não contar o próprio Admin que está logado
            System.out.println("[NOVOS USUÁRIOS] O sistema possui " + totalUsuarios + " usuários cadastrados para você gerenciar.");
            cont++;
        }

        //Notificação de novos animais resgatados
        int animaisNoAbrigo = 0;
        for (Animal pet : listaAnimais) {
            if (pet.isPresenteNoAbrigo()) {
                animaisNoAbrigo++;
            }
        }

        if (animaisNoAbrigo > 0) {
            System.out.println("[NOVOS ANIMAIS] Há " + animaisNoAbrigo + " animal(is) no abrigo no momento, aguardando adoção ou tratamento.");
            cont++;
        }

        if (cont == 0) {
            System.out.println("Tudo atualizado! Nenhuma nova atividade pendente para gerenciamento.");
        }
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

    //Animais Resgatados
    public void relatorioAnimaisResgatados(ArrayList<Animal> listaAnimais) {
        System.out.println("\n\tRELATÓRIO: ANIMAIS RESGATADOS (NO ABRIGO)");
        int cont = 0;
        for (Animal pet : listaAnimais) {
            if (pet.isPresenteNoAbrigo()) {
                System.out.println("ID: " + pet.getId() + " | Nome: " + pet.getNome() + " | Espécie: " + pet.getEspecie());
                cont++;
            }
        }
        System.out.println("Total de animais aguardando adoção: " + cont);
    }

    //Adoções Realizadas
    public void relatorioAdocoesRealizadas(ArrayList<Animal> listaAnimais) {
        System.out.println("\n\tRELATÓRIO: ADOÇÕES REALIZADAS");
        int cont = 0;
        for (Animal pet : listaAnimais) {
            if (!pet.isPresenteNoAbrigo()) {
                System.out.println("ID: " + pet.getId() + " | Nome: " + pet.getNome() + " | Espécie: " + pet.getEspecie() + " -> [ADOTADO]");
                cont++;
            }
        }
        if (cont == 0) {
            System.out.println("Nenhuma adoção realizada ainda.");
        } else {
            System.out.println("Total de vidas salvas com novas famílias: " + cont);
        }
    }

    //Animais em Tratamento
    public void relatorioAnimaisEmTratamento(ArrayList<Animal> listaAnimais) {
        System.out.println("\n\tRELATÓRIO: ANIMAIS EM TRATAMENTO");
        int cont = 0;
        for (Animal pet : listaAnimais) {
            if (pet.isPresenteNoAbrigo() && !pet.isSaude()) {
                System.out.println("ID: " + pet.getId() + " | Nome: " + pet.getNome() + " | Em tratamento médico.");
                cont++;
            }
        }
        if (cont == 0) {
            System.out.println("Ótima notícia! Nenhum animal em tratamento.");
        } else {
            System.out.println("Total de animais doentes/em recuperação: " + cont);
        }
    }

    //Voluntários Ativos
    public void relatorioVoluntariosAtivos(ArrayList<Usuario> listaUsuarios) {
        System.out.println("\n\tRELATÓRIO: VOLUNTÁRIOS ATIVOS");
        int cont = 0;
        for (Usuario user : listaUsuarios) {
            // Verifica se o usuário atual é uma instância da classe Voluntario
            if (user instanceof Voluntario) {
                System.out.println("Nome do Voluntário: " + user.getNomeUser());
                cont++;
            }
        }
        if (cont == 0) {
            System.out.println("Nenhum voluntário cadastrado no momento.");
        } else {
            System.out.println("Total de voluntários ajudando o abrigo: " + cont);
        }
    }

    //Eventos Realizados
    public void relatorioEventosRealizados(ArrayList<Evento> listaEventos) {
        System.out.println("\n\tRELATÓRIO: EVENTOS REALIZADOS");

        if (listaEventos.isEmpty()) {
            System.out.println("Nenhum evento registrado até o momento.");
        } else {
            for (Evento ev : listaEventos) {
                // Transforma o boolean 'ativo' em um texto para o Admin
                String statusEvento = ev.isAtivo() ? "Em andamento / Aberto" : "Finalizado";

                System.out.println("ID: " + ev.getId());
                System.out.println("Evento: " + ev.getNome());
                System.out.println("Horário: " + ev.getHora());
                System.out.println("Local: " + ev.getLocal());
                System.out.println("Status: " + statusEvento);
                System.out.println("Participantes: " + ev.getTotalParticipantes());
            }
            System.out.println("Total histórico de eventos/campanhas: " + listaEventos.size());
        }
    }

    public void exibirEstatisticas(ArrayList<Animal> listaAnimais, ArrayList<Evento> listaEventos) {
        System.out.println("\n\tSTATÍSTICAS DO ABRIGO");

        if (listaAnimais.isEmpty()) {
            System.out.println("Aviso: Nenhum animal cadastrado ainda.");
            System.out.println("Não é possível gerar estatísticas de adoção.\n");
            return;
        }

        //Coleta de Dados
        int totalResgatados = listaAnimais.size();
        int adotados = 0;
        int noAbrigo = 0;
        int saudaveisNoAbrigo = 0;

        for (Animal pet : listaAnimais) {
            if (!pet.isPresenteNoAbrigo()) {
                adotados++;
            } else {
                noAbrigo++;
                if (pet.isSaude()) {
                    saudaveisNoAbrigo++;
                }
            }
        }

        int totalEventos = listaEventos.size();
        int eventosAtivos = 0;
        int somaParticipantes = 0;

        for (Evento ev : listaEventos) {
            if (ev.isAtivo()) {
                eventosAtivos++;
            }
            somaParticipantes += ev.getTotalParticipantes();
        }

        //Taxas de adoção e saúde
        double taxaAdocao = (adotados * 100.0) / totalResgatados;
        double taxaSaude = 0.0;

        if (noAbrigo > 0) {
            taxaSaude = (saudaveisNoAbrigo * 100.0) / noAbrigo;
        }

        System.out.println("INDICADORES DE ADOÇÃO");
        System.out.println("Total Histórico de Resgates: " + totalResgatados);
        System.out.println("Animais Adotados: " + adotados);
        System.out.printf("Taxa de Sucesso (Adoções): %.2f%%\n", taxaAdocao);
        System.out.println();

        System.out.println("INDICADORES DE OCUPAÇÃO E SAÚDE");
        System.out.println("Animais morando no abrigo hoje: " + noAbrigo);
        System.out.println("Animais Saudáveis (Aptos): " + saudaveisNoAbrigo);
        System.out.printf("Índice de Saúde do Abrigo: %.2f%%\n", taxaSaude);
        System.out.println();

        System.out.println("INDICADORES DE ENGAJAMENTO (EVENTOS)");
        System.out.println("Total de Eventos Criados: " + totalEventos);
        System.out.println("Eventos em Andamento: " + eventosAtivos);
        System.out.println("Soma Total de Participantes: " + somaParticipantes);
        System.out.println("\n");
    }

    public void soltarAnimalSilvestre(ArrayList<Animal> listaAnimais) {
        FuncoesAdmin fAdmin = new FuncoesAdmin();
        fAdmin.soltarAnimal(listaAnimais);
    }

    //Exibe o menu do Admin
    @Override
    public void exibirMenu(ArrayList<Usuario> listaUsuarios, ArrayList<Animal> listaAnimais, ArrayList<Evento> listaEventos) {
        Scanner sc = new Scanner(System.in);
        FuncoesAdmin fAdmin = new FuncoesAdmin();
        int opcao = -1;

        do {
            try {
                opcao = MenuUsuarios.menuAdmin();

                switch (opcao) {
                    case 1:
                        // Gerenciar Animais
                        System.out.println("\n\tGERENCIAR ANIMAIS");
                        System.out.println("1 - Cadastrar Animal");
                        System.out.println("2 - Editar Animal");
                        System.out.println("3 - Remover Animal");
                        System.out.println("4 - Soltar Animal Silvestre na Natureza");
                        System.out.println("0 - Voltar");
                        System.out.print("> ");
                        int opAnimal = Integer.parseInt(sc.nextLine());

                        if (opAnimal == 1) this.cadastrarAnimais(listaAnimais);
                        else if (opAnimal == 2) this.editarAnimal(listaAnimais);
                        else if (opAnimal == 3) this.removerAnimal(listaAnimais);
                        else if (opAnimal == 4) this.soltarAnimalSilvestre(listaAnimais);
                        break;

                    case 2:
                        // Gerenciar Usuários
                        System.out.println("\n\tGERENCIAR USUÁRIOS");
                        System.out.println("1 - Listar Usuários");
                        System.out.println("2 - Remover Usuário");
                        System.out.println("0 - Voltar");
                        System.out.print("> ");

                        int opUser = Integer.parseInt(sc.nextLine());

                        if (opUser == 1) {
                            fAdmin.listarUsuarios(listaUsuarios);
                        } else if (opUser == 2) {
                            fAdmin.listarUsuarios(listaUsuarios); // Lista primeiro para o Admin ver quem ele quer apagar
                            fAdmin.removerUsuario(listaUsuarios); // Depois chama a função de apagar
                        } else if (opUser != 0) {
                            System.out.println("Opção inválida!");
                        }
                        break;

                    case 3:
                        // Aprovar Adoções
                        this.aprovarAdocoes(listaUsuarios, listaAnimais);
                        break;

                    case 4:
                        // Gerenciar Eventos
                        this.gerenciarEventos(listaEventos, listaUsuarios);
                        break;

                    case 5:
                        // Relatórios
                        int opRelatorio = fAdmin.menuRelatorios();
                        switch (opRelatorio) {
                            case 1:
                                this.relatorioAnimaisResgatados(listaAnimais);
                                break;
                            case 2:
                                this.relatorioAdocoesRealizadas(listaAnimais);
                                break;
                            case 3:
                                this.relatorioAnimaisEmTratamento(listaAnimais);
                                break;
                            case 4:
                                this.relatorioVoluntariosAtivos(listaUsuarios);
                                break;
                            case 5:
                                this.relatorioEventosRealizados(listaEventos);
                                break;
                            case 0:
                                break;
                            default:
                                System.out.println("Opção de relatório inválida!");
                                break;
                        }
                        break;

                    case 6:
                        // Estatísticas
                        this.exibirEstatisticas(listaAnimais, listaEventos);
                        break;

                    case 7:
                        // Notificações
                        this.notificacoes(listaUsuarios, listaAnimais, listaEventos);
                        break;

                    case 0:
                        System.out.println("\nSaindo da área do Admin...");
                        break;

                    default:
                        System.out.println("\nErro: Opção inválida! Escolha um número de 0 a 7.");
                }
            } catch (Exception e) {
                System.out.println("\nErro: Entrada inválida. Digite apenas números.");
                opcao = -1;
            }

        } while (opcao != 0);
    }
}
