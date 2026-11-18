package animais;

import java.util.ArrayList;

public abstract class Animal {
    private String nome;
    private String especie;
    private String raca;
    private int idade;
    private double porte;
    private boolean saude;
    private int dia;
    private int mes;
    private int ano;
    private boolean presenteNoAbrigo;
    private int id;
    protected static int qtdAnimais = 0;
    private ArrayList<String> prontuarioMedico;

    // Construtor Animal
    public Animal(String nome, String especie, String raca, int idade, double porte, boolean saude, int dia, int mes, int ano){
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        setIdade(idade);
        setPorte(porte);
        this.saude = saude;
        setDia(dia);
        setMes(mes);
        setAno(ano);
        this.presenteNoAbrigo = true;
        setId();
        qtdAnimais++;
        this.prontuarioMedico = new ArrayList<>();
    }

    //Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        if(idade > 0) {
            this.idade = idade;
        }
    }

    public double getPorte() {
        return porte;
    }

    public void setPorte(double porte) {
        if(porte > 10) {
            this.porte = porte;
        }
    }

    public boolean isSaude() {
        return saude;
    }

    public void setSaude(boolean saude) {
        this.saude = saude;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        if(dia >= 1 && dia <= 30){
        this.dia = dia;
        }
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        if(mes >= 1 && mes <= 12 ) {
            this.mes = mes;
        }
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        if(ano >= 2026) {
            this.ano = ano;
        }
    }

    public int getId(){
        return id;
    }

    public void setId(){
        id++;
    }

    public boolean isPresenteNoAbrigo() {
        return presenteNoAbrigo;
    }

    public void setPresenteNoAbrigo(boolean presente) {
        this.presenteNoAbrigo = presente;
    }

    public static int getQtdAnimais(){
        return qtdAnimais;
    }

    public void setProntuarioMedico(String relatorio) {
        this.prontuarioMedico.add(relatorio);
    }

    public ArrayList<String> getProntuarioMedico() {
        return prontuarioMedico;
    }
}
