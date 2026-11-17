package usuario;

import excecoes.SenhaFracaException;
import excecoes.UsuarioDuplicadoException;
import java.util.ArrayList;

public abstract class Usuario {
    private String nomeUser;
    private String senha;

    private static int qtdUser = 0;

    //Construtor de Usuario
    public Usuario(String nomeUser, String senha) throws SenhaFracaException{
        this.nomeUser = nomeUser;
        setSenha(senha);

        qtdUser++;
    }

    public static int getQtdUser(){
        return qtdUser;
    }

    public String getNomeUser(){
        return nomeUser;
    }

    public void setNomeUser(String nomeUser){
        this.nomeUser = nomeUser;
    }

    public String getSenha(){
        return senha;
    }

    //Tratamento para que a senha seja escrita da maneira correta
    public void setSenha(String senha) throws SenhaFracaException {
        if (senha == null || senha.length() < 8) {
            throw new SenhaFracaException("A senha deve ter no mínimo 8 caracteres.");
        }
        if (contarMaiusculas(senha) < 1) {
            throw new SenhaFracaException("A senha deve ter pelo menos uma letra maiúscula.");
        }
        if (contarMinusculas(senha) < 1) {
            throw new SenhaFracaException("A senha deve ter pelo menos uma letra minúscula.");
        }
        if (contarNum(senha) < 1) {
            throw new SenhaFracaException("A senha deve conter pelo menos um número.");
        }

        this.senha = senha;
    }

    // Função para contar as letras maiúsculas
    private int contarMaiusculas(String senha) {
        int contador = 0;
        for (char c : senha.toCharArray()) {
            if (Character.isUpperCase(c)) {
                contador++;
            }
        }
        return contador;
    }

    // Função para contar as letras minúsculas
    private int contarMinusculas(String senha) {
        int cont = 0;
        for (char c : senha.toCharArray()) {
            if (Character.isLowerCase(c)) {
                cont++;
            }
        }
        return cont;
    }

    // Função para contar os números
    private int contarNum(String senha) {
        int cont = 0;
        for (char c : senha.toCharArray()) {
            if (Character.isDigit(c)) {
                cont++;
            }
        }
        return cont;
    }

    //Analisa se tem usuarios repetidos
    public void analisarUser(ArrayList<Usuario> users) throws UsuarioDuplicadoException {
        for (Usuario u : users) {
            if (u.getNomeUser().equalsIgnoreCase(this.nomeUser)) {
                throw new UsuarioDuplicadoException("O usuário '" + this.nomeUser + "' já existe no sistema");
            }
        }
    }

    public abstract void notificacoes();

    public abstract void exibirMenu();
}