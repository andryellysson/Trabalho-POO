package telas.opcoesVeterinario;

import animais.Animal;

import java.util.ArrayList;
import java.util.Scanner;

public class FuncoesVeterinario {

    //Lista os animais que estão no abrigo
    public int listarEscolherAnimal(ArrayList<Animal> listaAnimais) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n\tPACIENTES DO ABRIGO");

        boolean temPaciente = false;
        for (Animal pet : listaAnimais) {
            // Só mostra quem está no abrigo
            if (pet.isPresenteNoAbrigo()) {
                System.out.println("Id: " + pet.getId());
                System.out.println("Nome: " + pet.getNome());
                System.out.println("Espécie: " + pet.getEspecie());
                System.out.println("Raça: " + pet.getRaca());
                System.out.println("Idade: " + pet.getIdade());
                System.out.println("Porte: " + pet.getPorte());
                System.out.println("Saúde: " + pet.isSaude());
                System.out.println("Data de Resgate: " + pet.getDia() + "/" + pet.getMes() + "/" + pet.getAno() + "\n");
                temPaciente = true;
            }
        }

        if (!temPaciente) {
            System.out.println("Não há animais no abrigo no momento.");
            return -1;
        }

        System.out.println("\nDigite o ID do animal para abrir a ficha médica (ou 0 para voltar):");
        System.out.print("> ");

        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite apenas números!");
            return -1;
        }
    }

    public boolean atualizarStatusSaude() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nQual é o estado de saúde do animal após esse atendimento?");
        System.out.println("1 - Saudável (Pronto para adoção)");
        System.out.println("2 - Em tratamento (Doente/Ferido)");
        System.out.print("> ");

        while (true) {
            try {
                int opcao = Integer.parseInt(sc.nextLine());
                if (opcao == 1) return true;  // true para saudável
                if (opcao == 2) return false; // false para doente
                System.out.println("Erro: Digite 1 ou 2.");
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite apenas números.");
            }
        }
    }

    //Lista apenas quem está doente/em tratamento
    public int listarAnimaisEmTratamento(ArrayList<Animal> listaAnimais) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n\tPACIENTES EM TRATAMENTO");

        boolean temPacienteDoente = false;
        for (Animal pet : listaAnimais) {
            //Só mostra se está no abrigo e se não está saudável
            if (pet.isPresenteNoAbrigo() && !pet.isSaude()) {
                System.out.println("Id: " + pet.getId() + " | Nome: " + pet.getNome() + " | Espécie: " + pet.getEspecie());
                temPacienteDoente = true;
            }
        }

        if (!temPacienteDoente) {
            System.out.println("Ótima notícia! Nenhum animal em tratamento no momento.");
            return -1;
        }

        System.out.println("\nDigite o ID do animal para atualizar o tratamento (ou 0 para voltar):");
        System.out.print("> ");

        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite apenas números!");
            return -1;
        }
    }

    //Pede o que foi feito hoje
    public String digitarEvolucaoTratamento(String nomeAnimal) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n\tATUALIZAR TRATAMENTO");
        System.out.println("Paciente: " + nomeAnimal);
        System.out.println("Descreva a evolução (ex: 'Tomou antibiótico', 'Ferida cicatrizando'):");
        System.out.print("> ");
        return sc.nextLine();
    }

    //Pergunta se o animal recebeu alta
    public boolean perguntarAltaMedica() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nO animal está curado ou continua em tratamento?");
        System.out.println("1 - Curado (Volta a ficar saudável e apto para adoção)");
        System.out.println("2 - Continua em tratamento");
        System.out.print("> ");

        while (true) {
            try {
                int opcao = Integer.parseInt(sc.nextLine());
                if (opcao == 1) return true;  // Curado
                if (opcao == 2) return false; // Continua doente
                System.out.println("Erro: Digite 1 ou 2.");
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite apenas números.");
            }
        }
    }

    //Pede os dados da vacina
    public String digitarVacina(String nomeAnimal) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n\tREGISTRAR VACINAÇÃO");
        System.out.println("Paciente: " + nomeAnimal);
        System.out.println("Qual vacina foi aplicada hoje?");
        System.out.print("> ");

        return sc.nextLine(); // Devolve o nome da vacina
    }

    //Apenas lê o que o veterinário digitou
    public String digitarRelatorio(String nomeAnimal) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n\tEMITIR RELATÓRIO MÉDICO");
        System.out.println("Paciente: " + nomeAnimal);
        System.out.println("Digite o diagnóstico, vacina ou receituário (Pressione ENTER para salvar):");
        System.out.print("> ");

        return sc.nextLine(); // Devolve o texto para salvar
    }

    // Ficha mostra os relatórios passados
    public void exibirFichaMedica(Animal pet) {
        System.out.println("\n\tFICHA MÉDICA");
        System.out.println("Id: " + pet.getId());
        System.out.println("Nome: " + pet.getNome());
        System.out.println("Espécie: " + pet.getEspecie());
        System.out.println("Raça: " + pet.getRaca());
        System.out.println("Idade: " + pet.getIdade());
        System.out.println("Porte: " + pet.getPorte());
        System.out.println("Saúde: " + pet.isSaude());
        System.out.println("Data de Resgate: " + pet.getDia() + "/" + pet.getMes() + "/" + pet.getAno() + "\n");

        System.out.println("\nHISTÓRICO MÉDICO");
        if (pet.getProntuarioMedico().isEmpty()) {
            System.out.println("Nenhum relatório médico cadastrado.");
        } else {
            // Imprime todos os relatórios que o animal já recebeu
            for (int i = 0; i < pet.getProntuarioMedico().size(); i++) {
                System.out.println("Relatório " + (i + 1) + ": " + pet.getProntuarioMedico().get(i));
            }
        }
        System.out.println();
    }
}