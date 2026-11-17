package telas.opcoesVoluntario;
import animais.Animal;
import animais.AnimalDomestico;
import usuario.Voluntario;

import java.util.ArrayList;
import java.util.Scanner;

public class funcoesVoluntario {

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

    // 4º Opção

}