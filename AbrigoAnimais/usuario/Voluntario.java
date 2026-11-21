package usuario;

import eventos.Evento;
import excecoes.SenhaFracaException;
import telas.menu.MenuUsuarios;
import animais.Animal;
import telas.opcoesVoluntario.FuncoesVoluntario;

import java.util.ArrayList;

public class Voluntario extends Usuario {
    private ArrayList<Animal> minhasSolicitacoes;
    private ArrayList<Evento> meusEventos;

    public Voluntario(String nomeUser, String senha) throws SenhaFracaException {
        super(nomeUser, senha);
        this.minhasSolicitacoes = new ArrayList<>();
        this.meusEventos = new ArrayList<>();
    }

    public void adicionarSolicitacao(Animal pet) {
        this.minhasSolicitacoes.add(pet);
    }

    public ArrayList<Animal> getMinhasSolicitacoes() {
        return this.minhasSolicitacoes;
    }

    public ArrayList<Evento> getMeusEventos() {
        return meusEventos;
    }

    public void participarDeEvento(ArrayList<Evento> listaEventos) {
        FuncoesVoluntario fVoluntario = new FuncoesVoluntario();

        //Mostrar os eventos e pegar o ID
        int idEvento = fVoluntario.escolherEvento(listaEventos);

        if (idEvento <= 0) {
            return;
        }

        //Se esse evento realmente existe na lista geral
        Evento eventoEscolhido = null;
        for (Evento ev : listaEventos) {
            if (ev.getId() == idEvento) {
                eventoEscolhido = ev;
                break;
            }
        }

        if (eventoEscolhido == null) {
            System.out.println("Erro: Evento não encontrado!");
            return;
        }

        //Verifica se o voluntário já não está inscrito nesse evento!
        if (this.meusEventos.contains(eventoEscolhido)) {
            System.out.println("[Aviso] Você já está inscrito no evento: " + eventoEscolhido.getNome());
            return;
        }

        // 4. Se deu tudo certo, adiciona na lista particular dele!
        this.meusEventos.add(eventoEscolhido);
        System.out.println("[Sucesso] Inscrição confirmada no evento: " + eventoEscolhido.getNome());
    }

    // lista todos os eventos que está participando
    public void verMinhaAgenda() {
        FuncoesVoluntario fVoluntario = new FuncoesVoluntario();
        fVoluntario.listarMeusEventos(this.meusEventos);
    }

    // exibe o histórico
    public void verHistoricoDeEventos() {
        FuncoesVoluntario fVoluntario = new FuncoesVoluntario();

        fVoluntario.exibirHistoricoParticipacoes(this.meusEventos);
    }

    //NOtificações
    @Override
    public void notificacoes(ArrayList<Usuario> listaUsuarios, ArrayList<Animal> listaAnimais, ArrayList<Evento> listaEventos) {
        System.out.println("\n\tNOTIFICAÇÕES");
        int cont = 0;

        // Avisa sobre os eventos agendados para este voluntário
        for (Evento ev : this.meusEventos) {
            if (ev.isAtivo()) {
                System.out.println("[LEMBRETE] Você tem o evento '" + ev.getNome() + "' agendado para às " + ev.getHora() + ".");
                cont++;
            }
        }

        // Avisa sobre as solicitações de adoção
        if (!this.minhasSolicitacoes.isEmpty()) {
            System.out.println("[AVISO] Você tem " + this.minhasSolicitacoes.size() + " solicitação(ões) de adoção aguardando aprovação.");
            cont++;
        }

        if (cont == 0) {
            System.out.println("Tudo tranquilo! Nenhuma notificação nova no momento.");
        }
    }

    // Exibe o menu do Voluntário
    @Override
    public void exibirMenu(ArrayList<Usuario> listaUsuarios, ArrayList<Animal> listaAnimais, ArrayList<Evento> listaEventos) {
        FuncoesVoluntario fVoluntario = new FuncoesVoluntario();
        int opcao = -1;

        do {
            try {
                opcao = MenuUsuarios.menuVoluntario();

                switch (opcao) {
                    case 1:
                        // Visualizar Animais Disponíveis
                        System.out.println("\n\tANIMAIS DISPONÍVEIS PARA ADOÇÃO");
                        fVoluntario.visualizarAnimaisDisponiveis(listaAnimais);
                        break;

                    case 2:
                        // Solicitar Adoções
                        int idEscolhido = fVoluntario.solicitarAdocao(listaAnimais);

                        Animal animalEncontrado = null;
                        for (Animal pet : listaAnimais) {
                            if (pet.getId() == idEscolhido) {
                                animalEncontrado = pet;
                                break;
                            }
                        }

                        if (animalEncontrado == null) {
                            System.out.println("Erro: Animal não encontrado! Verifique o ID digitado.");

                        } else if (this.minhasSolicitacoes.contains(animalEncontrado)) {
                            System.out.println("[Aviso] Você já solicitou a adoção deste animal e está aguardando aprovação.");

                        } else if (animalEncontrado instanceof interfaces.Adotavel) {

                            // Transformamos o animal genérico na interface Adotavel
                            interfaces.Adotavel petAdotavel = (interfaces.Adotavel) animalEncontrado;

                            // se reservar, salvar na lista deste voluntário e imprimir a mensagem!
                            petAdotavel.solicitarAdocao(this);

                        } else {
                            System.out.println("Erro: Este animal é Silvestre! Pela lei, ele não pode ser adotado, apenas devolvido à natureza.");
                        }
                        break;
                    case 3:
                        // Consultar Minhas Solicitações
                        fVoluntario.exibirMinhasSolicitacoes(this);
                        break;

                    case 4:
                        // Participar de Eventos
                        this.participarDeEvento(listaEventos);
                        break;

                    case 5:
                        // Histórico de Participações
                        this.verHistoricoDeEventos();
                        break;

                    case 6:
                        // Notificações
                        this.notificacoes(listaUsuarios, listaAnimais, listaEventos);
                        break;

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