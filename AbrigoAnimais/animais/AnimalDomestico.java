package animais;

import interfaces.Adotavel;
import usuario.Usuario;

// Classe para o Animal Domestico
public class AnimalDomestico extends Animal implements Adotavel {
    private boolean disponivelParaAdocao;

    // Construtor do Animal Domestico
    public AnimalDomestico(String nome, String especie, String raca, int idade, double porte, boolean saude, int dia, int mes, int ano) {
        super(nome, especie, raca, idade, porte, saude, dia, mes, ano);

        this.disponivelParaAdocao = isSaude();
    }

    // Getter e Setter
    public boolean getDisponivelParaAdocao(){
        return disponivelParaAdocao;
    }

    public void setDisponivelParaAdocao(boolean disponivel){
        if(isSaude()){
            this.disponivelParaAdocao = disponivel;
        }else{
            this.disponivelParaAdocao = false;
        }
    }

    //Solicita a adoção do animal e envia para o Admin
    @Override
    public void solicitarAdocao() {
        if (isSaude()) {
            setDisponivelParaAdocao(false);
            System.out.println("Sua solicitação para o(a)" + getNome() + " foi enviada para o Administrador!");
        }else{
            System.out.println("Sua solicitação para o(a)" + getNome() + " foi negada! Animal em tratamento veterinario");
        }
    }
}
