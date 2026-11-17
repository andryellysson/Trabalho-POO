package telas.opcoesAdmin;

import animais.Animal;
import animais.AnimalDomestico;
import animais.AnimalSilvestre;
import eventos.Evento;
import usuario.Administrador;
import usuario.Usuario;
import usuario.Voluntario;

import java.util.Scanner;
import java.util.ArrayList;

public class FuncoesAdmin {

    //Cadastro de Animais
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

    //Remover Animal
    public int removerAnimal(ArrayList<Animal> listaAnimais){
        Scanner sc = new Scanner(System.in);

        System.out.println("\n\tREMOVER ANIMAIS\n");

        for (Animal pet : listaAnimais) {
            if (pet.isPresenteNoAbrigo()) {
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

        int id = -1;

        while(true) {
            System.out.println("\nDigite o Id do animal que deseja remover (ou 0 para cancelar)");
            System.out.print("> ");

            try {
                id = Integer.parseInt(sc.nextLine());
                break;

            } catch (NumberFormatException e) {
                System.out.println("Erro: Entrada inválida! Por favor, digite apenas números.");
            }
        }

        return id;
    }

    //Editar Animal
    public void editarAnimal(ArrayList<Animal> listaAnimais) {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n\tEDITAR ANIMAL\n");

        for (Animal pet : listaAnimais) {
            if (pet.isPresenteNoAbrigo()) {
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

        int id = -1;
        while (true) {
            System.out.println("\nDigite o Id do animal que deseja editar (ou 0 para cancelar):");
            System.out.print("> ");
            try {
                id = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite apenas números!");
            }
        }

        if (id == 0) {
            System.out.println("Edição cancelada.");
            return;
        }

        // Busca animal pelo Id
        Animal animalParaEditar = null;
        for (Animal pet : listaAnimais) {
            if (pet.getId() == id) {
                animalParaEditar = pet;
                break;
            }
        }

        // Se não achou ninguém com aquele ID, avisa e cancela
        if (animalParaEditar == null) {
            System.out.println("Erro: Id não encontrado no sistema!");
            return;
        }

        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\nEditando os dados de: " + animalParaEditar.getNome());
            System.out.println("1 - Nome");
            System.out.println("2 - Espécie");
            System.out.println("3 - Raça");
            System.out.println("4 - Idade");
            System.out.println("5 - Porte");
            System.out.println("6 - Saúde");
            System.out.println("7 - Data de Resgate (Dia/Mes/Ano)");
            System.out.println("0 - Concluir edição e Sair\n");
            System.out.print("> ");

            try {
                opcao = Integer.parseInt(sc.nextLine());

                switch (opcao) {
                    case 1:
                        System.out.print("Digite o novo nome: ");
                        String novoNome = sc.nextLine();
                        animalParaEditar.setNome(novoNome);
                        System.out.println("Nome atualizado com sucesso!");
                        break;
                    case 2:
                        System.out.print("Digite a nova espécie: ");
                        animalParaEditar.setEspecie(sc.nextLine());
                        System.out.println("Espécie atualizada com sucesso!");
                        break;
                    case 3:
                        System.out.print("Digite a nova raça: ");
                        animalParaEditar.setRaca(sc.nextLine());
                        System.out.println("Raça atualizada com sucesso!");
                        break;
                    case 4:
                        System.out.print("Digite a nova idade: ");
                        animalParaEditar.setIdade(Integer.parseInt(sc.nextLine()));
                        System.out.println("Idade atualizada com sucesso!");
                        break;
                    case 5:
                        System.out.print("Digite o novo porte: ");
                        animalParaEditar.setPorte(Double.parseDouble(sc.nextLine()));
                        System.out.println("Porte atualizado com sucesso!");
                        break;
                    case 6:
                        System.out.print("A saúde está boa? (1 - Sim / 2 - Não): ");
                        int opcaoSaude = Integer.parseInt(sc.nextLine());
                        animalParaEditar.setSaude(opcaoSaude == 1);
                        System.out.println("Status de saúde atualizado com sucesso!");
                        break;
                    case 7:
                        System.out.print("Novo Dia: ");
                        animalParaEditar.setDia(Integer.parseInt(sc.nextLine()));
                        System.out.print("Novo Mês: ");
                        animalParaEditar.setMes(Integer.parseInt(sc.nextLine()));
                        System.out.print("Novo Ano: ");
                        animalParaEditar.setAno(Integer.parseInt(sc.nextLine()));
                        System.out.println("Data de resgate atualizada com sucesso!");
                        break;
                    case 0:
                        System.out.println("Saindo do menu de edição...");
                        break;
                    default:
                        System.out.println("Erro: Opção inválida!");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Você digitou uma letra onde deveria ser número. Tente novamente.");
            }
        }
    }

    //Lista todos os usuarios
    public void listarUsuarios(ArrayList<Usuario> listaUsuarios) {
        System.out.println("\n\tLISTA DE USUÁRIOS DA EQUIPE (Voluntários e Veterinários)\n");

        boolean encontrouAlguem = false;

        for (Usuario user : listaUsuarios) {
            // todos os usuarios, menos o admin
            if (!(user instanceof Administrador)) {

                // O getSimpleName() vai imprimir Voluntario ou Veterinario
                System.out.println("- Login: " + user.getNomeUser() + " | Cargo: " + user.getClass().getSimpleName());

                encontrouAlguem = true;
            }
        }

        if (!encontrouAlguem) {
            System.out.println("Nenhum voluntário ou veterinário cadastrado no sistema.");
        }
    }

    public void removerUsuario(ArrayList<Usuario> listaUsuarios) {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n\tDELETAR USUÁRIO");
        System.out.println("Digite o nome do usuário que deseja deletar (ou '0' para cancelar):");
        System.out.print("> ");
        String login = sc.nextLine();

        if (login.equals("0")) {
            System.out.println("Operação cancelada.");
            return;
        }

        Usuario usuarioParaRemover = null;

        // Busca o usuário pelo nome
        for (Usuario user : listaUsuarios) {
            if (user.getNomeUser().equalsIgnoreCase(login)) {
                usuarioParaRemover = user;
                break;
            }
        }

        if (usuarioParaRemover == null) {
            System.out.println("Erro: Usuário não encontrado!");
        } else {
            // Remove o objeto da lista
            listaUsuarios.remove(usuarioParaRemover);
            System.out.println("O usuário " + usuarioParaRemover.getNomeUser() + " foi deletado do sistema com sucesso!");
        }
    }

    // Mostra os pedidos e pergunta qual ID quer julgar
    public int selecionarSolicitacao(ArrayList<Usuario> listaUsuarios) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n\tAPROVAR ADOÇÕES\n");

        boolean temSolicitacao = false;

        // Apenas imprime na tela
        for (Usuario user : listaUsuarios) {
            if (user instanceof Voluntario) {
                Voluntario vol = (Voluntario) user;
                for (Animal pet : vol.getMinhasSolicitacoes()) {
                    System.out.println("Voluntário: " + vol.getNomeUser() + " solicitou o animal -> ID: " + pet.getId() + " | Nome: " + pet.getNome());
                    temSolicitacao = true;
                }
            }
        }

        if (!temSolicitacao) {
            System.out.println("Não há nenhuma solicitação de adoção pendente.");
            return -1;
        }

        // Lê o ID
        int idAnimal = -1;
        while (true) {
            System.out.println("\nDigite o ID do animal que deseja julgar (ou 0 para cancelar):");
            System.out.print("> ");
            try {
                idAnimal = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite apenas números!");
            }
        }
        return idAnimal;
    }

    // Decide se aprova ou não
    public int obterDecisaoAdmin(String nomeAnimal, String nomeVoluntario) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nJulgando a adoção do animal " + nomeAnimal + " para o usuário " + nomeVoluntario);
        System.out.println("1 - Aprovar Adoção");
        System.out.println("2 - Negar Adoção");
        System.out.print("> ");

        while(true) {
            try {
                int decisao = Integer.parseInt(sc.nextLine());
                if(decisao == 1 || decisao == 2) {
                    return decisao;
                }
                System.out.println("Erro: Opção inválida! Digite 1 ou 2.");
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite apenas números.");
            }
        }
    }

    //Cria o Evento
    public Evento criarNovoEvento() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n\tCADASTRAR NOVO EVENTO");

        System.out.print("Nome do Evento: ");
        String nome = sc.nextLine();

        System.out.print("Horário (ex: 14:00): ");
        String hora = sc.nextLine();

        System.out.print("Local: ");
        String local = sc.nextLine();

        return new Evento(nome, hora, local);
    }

    //Lista os eventos que ja estão cadastrados
    public void listarEventos(ArrayList<Evento> listaEventos) {
        System.out.println("\n\tLISTA DE EVENTOS ATIVOS");

        boolean temEvento = false;
        for (Evento ev : listaEventos) {
            // Só mostra na tela se estiver ativo!
            if (ev.isAtivo()) {
                System.out.println("- ID: " + ev.getId() + " | " + ev.getNome() + " | " + ev.getHora() + " | " + ev.getLocal());
                temEvento = true;
            }
        }
        if (!temEvento) {
            System.out.println("Nenhum evento ativo no momento.");
        }
    }
}