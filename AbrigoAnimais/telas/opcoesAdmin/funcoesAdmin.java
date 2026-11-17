package telas.opcoesAdmin;

import animais.Animal;
import animais.AnimalDomestico;
import animais.AnimalSilvestre;

import java.util.Scanner;
import java.util.ArrayList;

public class funcoesAdmin {

    public void cadastrarAnimais(ArrayList<Animal> listaAnimais){
        Scanner sc = new Scanner(System.in);
        int escolha;

        while(true) {
            System.out.println("\n\tCADASTRO DE ANIMAIS\n");
            System.out.println("O animal é (1 - domestico) ou (2 - silvestre)? (0 para cancelar)");
            System.out.print("> ");

            try {
                escolha = Integer.parseInt(sc.nextLine());

                if (escolha == 0) return;
                if (escolha == 1 || escolha == 2) break;

                System.out.println("Erro: Opção inválida!");

            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, digite apenas números!");
            }
        }

        //Cadastro as informações do animal
        try {
            System.out.print("\nNome: ");
            String nome = sc.nextLine();

            System.out.print("Espécie: ");
            String esp = sc.nextLine();

            System.out.print("Raça: ");
            String raca = sc.nextLine();

            System.out.print("Idade: ");
            int idade = Integer.parseInt(sc.nextLine());

            System.out.print("Porte: ");
            double porte = Double.parseDouble(sc.nextLine());

            System.out.print("Saúde está boa? (1 - Sim / 2 - Não): ");
            int opcaoSaude = Integer.parseInt(sc.nextLine());
            boolean saude = (opcaoSaude == 1);

            System.out.print("Dia do resgate: ");
            int dia = Integer.parseInt(sc.nextLine());

            System.out.print("Mes: ");
            int mes = Integer.parseInt(sc.nextLine());

            System.out.print("Ano: ");
            int ano = Integer.parseInt(sc.nextLine());

            if(escolha == 1) {
                Animal domestico = new AnimalDomestico(nome, esp, raca, idade, porte, saude, dia, mes, ano);
                listaAnimais.add(domestico);
                System.out.println("\nAnimal domestico adicionado com sucesso!");

            } else if (escolha == 2) {
                Animal silvestre = new AnimalSilvestre(nome, esp, raca, idade, porte, saude, dia, mes, ano);
                listaAnimais.add(silvestre);
                System.out.println("\nAnimal silvestre adicionado com sucesso!");
            }

        } catch (NumberFormatException e) {
            // Se qualquer conversão de número falhar
            System.out.println("\nErro: Você digitou letras onde deveria ser um número");
            System.out.println("O cadastro foi cancelado. Por favor, tente novamente.");
        }
    }

    //TENHO QUE AJEITAR A PARTE DE SE O ANIMAL FOI ADOTADO OU SOLTO
    public void removerAnimal(ArrayList<Animal> listaAnimais){
        System.out.println("\n\tREMOVER ANIMAIS\n");

        for (Animal pet : listaAnimais) {
            AnimalDomestico petDomestico = (AnimalDomestico) pet;
            AnimalSilvestre petSilvestre = (AnimalSilvestre) pet;

            if (petDomestico.getDisponivelParaAdocao() ) {
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