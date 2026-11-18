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
    public void exibirMenu(ArrayList<Animal> listaAnimais, ArrayList<Evento> listaEventos) {
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