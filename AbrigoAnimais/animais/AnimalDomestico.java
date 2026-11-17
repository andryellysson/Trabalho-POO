package animais;

import interfaces.Adotavel;
import usuario.Usuario;
import usuario.Voluntario;

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

    // Solicita a adoção do animal e envia para o Admin
    @Override
    public void solicitarAdocao(Usuario adotante) {

        if (getDisponivelParaAdocao()) {
            setDisponivelParaAdocao(false); // Fica reservado

            // Converte para Voluntário e adiciona na lista dele!
            if (adotante instanceof Voluntario) {
                Voluntario vol = (Voluntario) adotante;
                vol.adicionarSolicitacao(this);
            }

            System.out.println("Sua solicitação para o(a) " + getNome() + " foi enviada para o Administrador!");
        } else {
            System.out.println("Sua solicitação para o(a) " + getNome() + " foi negada! Animal em tratamento ou já reservado.");
        }
    }
}