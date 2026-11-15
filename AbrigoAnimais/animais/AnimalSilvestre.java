package animais;

import interfaces.Soltavel;
import usuario.Usuario;

// Classe Animal Silvestre
public class AnimalSilvestre extends Animal implements Soltavel{

    // Construtor do Animal Silvestre
    public AnimalSilvestre(String nome, String especie, String raca, int idade, double porte, boolean saude, int dia, int mes, int ano, boolean adocao) {
        super(nome, especie, raca, idade, porte, saude, dia, mes, ano);
    }

    // Registrar a soltura do Animal
    @Override
    public void registrarSoltura(String localDaSoltura) {

        if (isSaude()) {
            setPresenteNoAbrigo(false);
            System.out.println("\nO animal silvestre " + getNome() + " está saudável e foi devolvido para o(a) " + localDaSoltura);
        } else {
            System.out.println("\nO animal " + getNome() + " não pode ser solto! Ele ainda está em tratamento veterinário");
        }
    }
}
