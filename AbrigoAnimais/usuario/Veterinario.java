package usuario;

import animais.Animal;
import eventos.Evento;
import excecoes.SenhaFracaException;
import telas.menu.MenuUsuarios;
import telas.opcoesVeterinario.FuncoesVeterinario;

import java.util.ArrayList;

public class Veterinario extends Usuario{
    public Veterinario(String nomeUser, String senha) throws SenhaFracaException {
        super(nomeUser, senha);
    }

    //Lista os Animais que precisam de ajuda médica
    public void consultarAnimais(ArrayList<Animal> listaAnimais) {
        FuncoesVeterinario fVet = new FuncoesVeterinario();

        // Mostra a lista e devolve o ID digitado
        int idEscolhido = fVet.listarEscolherAnimal(listaAnimais);

        if (idEscolhido <= 0) {
            return;
        }

        // Procura o animal na lista
        Animal paciente = null;
        for (Animal pet : listaAnimais) {
            if (pet.getId() == idEscolhido && pet.isPresenteNoAbrigo()) {
                paciente = pet;
                break;
            }
        }

        // Verifica se encontrou e exibe a ficha
        if (paciente != null) {
            fVet.exibirFichaMedica(paciente);
        } else {
            System.out.println("Erro: Animal não encontrado ou não está mais no abrigo");
        }
    }

    //Registrar o atendimento do Animal
    public void registrarAtendimento(ArrayList<Animal> listaAnimais) {
        FuncoesVeterinario fVet = new FuncoesVeterinario();

        // O Veterinario escolhe de qual animal ele vai cuidar
        int idEscolhido = fVet.listarEscolherAnimal(listaAnimais);

        if (idEscolhido <= 0) {
            return;
        }

        // Busca o paciente na lista
        Animal paciente = null;
        for (Animal pet : listaAnimais) {
            if (pet.getId() == idEscolhido && pet.isPresenteNoAbrigo()) {
                paciente = pet;
                break;
            }
        }

        if (paciente != null) {
            //Pede o texto do diagnóstico
            String textoRelatorio = fVet.digitarRelatorio(paciente.getNome());

            //Salva o texto no histórico do Animal
            paciente.getProntuarioMedico().add(textoRelatorio);

            //Pergunta se a saúde mudou e atualiza
            boolean novaSaude = fVet.atualizarStatusSaude();
            paciente.setSaude(novaSaude); // Ajusta a saúde do Animal

            System.out.println("\nAtendimento registrado com sucesso na ficha de " + paciente.getNome() + "!");
        } else {
            System.out.println("Erro: Paciente não encontrado ou não está mais no abrigo.");
        }
    }

    public void atualizarTratamentos(ArrayList<Animal> listaAnimais) {
        FuncoesVeterinario fVet = new FuncoesVeterinario();

        //O Veterinario escolhe apenas dentre os que estão doentes
        int idEscolhido = fVet.listarAnimaisEmTratamento(listaAnimais);

        if (idEscolhido <= 0) {
            return; // Não tem doentes ou ele cancelou
        }

        //Busca o paciente
        Animal paciente = null;
        for (Animal pet : listaAnimais) {
            if (pet.getId() == idEscolhido && pet.isPresenteNoAbrigo() && !pet.isSaude()) {
                paciente = pet;
                break;
            }
        }

        if (paciente != null) {
            //Pede a atualização (evolução médica)
            String textoTratamento = fVet.digitarEvolucaoTratamento(paciente.getNome());

            //Salva no prontuário e atualiza o tratamento
            paciente.getProntuarioMedico().add("Atualização de Tratamento: " + textoTratamento);

            //Pergunta se o Animal estar melhor
            boolean recebeuAlta = fVet.perguntarAltaMedica();

            if (recebeuAlta) {
                paciente.setSaude(true); // O animal estar melhor!
                System.out.println("\nAlta médica registrada! " + paciente.getNome() + " está saudável novamente.");
            } else {
                System.out.println("\nEvolução salva. " + paciente.getNome() + " continua em recuperação.");
            }

        } else {
            System.out.println("Erro: Animal não encontrado ou não está em tratamento.");
        }
    }

    public void registrarVacinacao(ArrayList<Animal> listaAnimais) {
        FuncoesVeterinario fVet = new FuncoesVeterinario();

        //O Veterinario escolhe qualquer animal que esteja no abrigo
        int idEscolhido = fVet.listarEscolherAnimal(listaAnimais);

        if (idEscolhido <= 0) {
            return; // Cancelou ou erro
        }

        //Busca o paciente
        Animal paciente = null;
        for (Animal pet : listaAnimais) {
            if (pet.getId() == idEscolhido && pet.isPresenteNoAbrigo()) {
                paciente = pet;
                break;
            }
        }

        //Salvar a vacina
        if (paciente != null) {

            // Pede para ler qual foi a vacina
            String nomeVacina = fVet.digitarVacina(paciente.getNome());

            // Salva no prontuário para usar no relatório
            paciente.getProntuarioMedico().add("Vacinação: " + nomeVacina);

            System.out.println("\nVacina '" + nomeVacina + "' registrada no prontuário de " + paciente.getNome() + "!");

        } else {
            System.out.println("Erro: Paciente não encontrado ou não está no abrigo.");
        }
    }

    //Relatório Médico
    public void emitirRelatorioMedico(ArrayList<Animal> listaAnimais) {
        FuncoesVeterinario fVet = new FuncoesVeterinario();

        //O Veterinario escolhe qual animal vai cuidar
        int idEscolhido = fVet.listarEscolherAnimal(listaAnimais);

        if (idEscolhido <= 0) {
            return;
        }

        // Busca o paciente na lista
        Animal paciente = null;
        for (Animal pet : listaAnimais) {
            if (pet.getId() == idEscolhido && pet.isPresenteNoAbrigo()) {
                paciente = pet;
                break;
            }
        }

        // Se encontrou, pede o texto e salva no Animal!
        if (paciente != null) {
            String textoRelatorio = fVet.digitarRelatorio(paciente.getNome());

            // Salva no objeto Animal
            paciente.setProntuarioMedico(textoRelatorio);

            System.out.println("Relatório médico salvo na ficha de " + paciente.getNome() + " com sucesso!");
        } else {
            System.out.println("Erro: Paciente não encontrado ou não está mais no abrigo.");
        }
    }

    //Notificações
    @Override
    public void notificacoes(ArrayList<Usuario> listaUsuarios, ArrayList<Animal> listaAnimais, ArrayList<Evento> listaEventos) {
        System.out.println("\n\tNOTIFICAÇÕES");
        int animaisDoentes = 0;

        // Procura animais no abrigo que estão com saude baixa
        for (Animal pet : listaAnimais) {
            if (pet.isPresenteNoAbrigo() && !pet.isSaude()) {
                animaisDoentes++;
            }
        }

        if (animaisDoentes > 0) {
            System.out.println("[ALERTA MÉDICO] Urgente! Há " + animaisDoentes + " animal(is) doente(s) aguardando tratamento no abrigo!");
        } else {
            System.out.println("Ótimo trabalho! Nenhum animal precisando de cuidados médicos emergenciais.");
        }
    }

    //Exibir menu do Veterinario
    @Override
    public void exibirMenu(ArrayList<Usuario> listaUsuarios, ArrayList<Animal> listaAnimais, ArrayList<Evento> listaEventos) {
        int opcao = -1;

        do {
            try {
                opcao = MenuUsuarios.menuVeterinario();

                switch (opcao) {
                    case 1:
                        // Consultar Animais
                        this.consultarAnimais(listaAnimais);
                        break;

                    case 2:
                        // Registrar Atendimentos
                        this.registrarAtendimento(listaAnimais);
                        break;

                    case 3:
                        // Atualizar Tratamentos
                        this.atualizarTratamentos(listaAnimais);
                        break;

                    case 4:
                        // Registrar Vacinação
                        this.registrarVacinacao(listaAnimais);
                        break;

                    case 5:
                        // Emitir Relatórios Médicos
                        this.emitirRelatorioMedico(listaAnimais);
                        break;

                    case 6:
                        // Notificações
                        this.notificacoes(listaUsuarios, listaAnimais, listaEventos);
                        break;

                    case 0:
                        System.out.println("\nSaindo da área do Veterinário...");
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

