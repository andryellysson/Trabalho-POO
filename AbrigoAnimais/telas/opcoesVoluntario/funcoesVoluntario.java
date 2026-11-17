package telas.opcoesVoluntario;
import animais.Animal;
import animais.AnimalDomestico;
import eventos.Evento;
import usuario.Voluntario;

import java.util.ArrayList;
import java.util.Scanner;

public class FuncoesVoluntario {

    //1º Opção
    public void visualizarAnimaisDisponiveis(ArrayList<Animal> listaAnimais){
        for (Animal pet : listaAnimais) {
            // Checa se este animal específico é um AnimalDomestico
            if (pet instanceof AnimalDomestico) {
                // Converte a variável genérica para a classe filha
                AnimalDomestico petDomestico = (AnimalDomestico) pet;

                if (petDomestico.getDisponivelParaAdocao()) {
                    System.out.println("Id: " + pet.getId());
                    System.out.println("Nome: " + pet.getNome());
                    System.out.println("Espécie: " + pet.getEspecie());
                    System.out.println("Raça: " + pet.getRaca());
                    System.out.println("Idade: " + pet.getIdade());
                    System.out.println("Porte: " + pet.getPorte());
                    System.out.println("Saúde: " + pet.isSaude());
                    System.out.println("Data de Resgate: " + pet.getDia() + "/" + pet.getMes() + "/" + pet.getAno() + "\n");
                }
            }
        }
    }

    // 2º Opção
    public int solicitarAdocao(ArrayList<Animal> listaAnimais) {
        Scanner sc = new Scanner(System.in);
        visualizarAnimaisDisponiveis(listaAnimais);

        System.out.println("Digite o Id do Animal");
        System.out.print("> ");
        int escolher  = sc.nextInt();
        return escolher;
    }

    // 3º Opção
    public void exibirMinhasSolicitacoes(Voluntario voluntarioLogado) {

        System.out.println("\n\tMINHAS SOLICITAÇÕES DE ADOÇÃO\n");
        // Pega a lista do usuário logado
        ArrayList<Animal> lista = voluntarioLogado.getMinhasSolicitacoes();

        if (lista.isEmpty()) {
            System.out.println("Você ainda não solicitou nenhum animal.");
        } else {
            for (Animal pet : lista) {
                System.out.println("- " + pet.getNome() + " (Espécie: " + pet.getEspecie() + ")");
            }
        }
    }

    // Mostra os eventos gerais
    public int escolherEvento(ArrayList<Evento> listaEventos) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n\tEVENTOS DISPONÍVEIS");

        if (listaEventos.isEmpty()) {
            System.out.println("Nenhum evento programado no momento.");
            return -1;
        }

        // Mostra todos os eventos
        for (Evento ev : listaEventos) {
            System.out.println("- ID: " + ev.getId() + " | " + ev.getNome() + " | Hora: " + ev.getHora() + " | Local: " + ev.getLocal());
        }

        System.out.println("\nDigite o ID do evento que deseja participar (ou 0 para cancelar):");
        System.out.print("> ");
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite apenas números!");
            return -1;
        }
    }

    // Mostra os eventos que o voluntário está inscrito
    public void listarMeusEventos(ArrayList<Evento> meusEventos) {
        System.out.println("\n\tMINHA AGENDA DE EVENTOS");

        if (meusEventos.isEmpty()) {
            System.out.println("Você ainda não está participando de nenhum evento.");
            return;
        }

        for (Evento ev : meusEventos) {
            System.out.println("- " + ev.getNome() + " | Hora: " + ev.getHora() + " | Local: " + ev.getLocal());
        }
    }
    //Participações do Voluntário nos Eventos
    public void exibirHistoricoParticipacoes(ArrayList<Evento> meusEventos) {
        System.out.println("\n\tMEU HISTÓRICO DE PARTICIPAÇÕES");

        if (meusEventos.isEmpty()) {
            System.out.println("Você ainda não se inscreveu em nenhum evento do abrigo.");
            return;
        }

        for (Evento ev : meusEventos) {
            // Verifica se o evento já foi encerrado pelo Admin
            String status = ev.isAtivo() ? "(Agendado)" : "(Concluido)";

            System.out.println(status + " " + ev.getNome() + " | Local: " + ev.getLocal() + " | Hora: " + ev.getHora());
        }
    }
}