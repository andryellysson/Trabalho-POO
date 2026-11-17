package eventos;

//Classe Evento
public class Evento {
    private static int geradorId = 1;
    private int id;
    private String nome;
    private String hora;
    private String local;
    private boolean ativo = true;
    private int totalParticipantes = 0;

    //Construtor da classe Evento
    public Evento(String nome, String hora, String local) {
        this.id = geradorId++;
        this.nome = nome;
        this.hora = hora;
        this.local = local;
    }

    // Getters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getHora() { return hora; }
    public String getLocal() { return local; }
    public boolean isAtivo() { return ativo; }
    public int getTotalParticipantes() { return totalParticipantes; }

    //Setters
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
    public void setTotalParticipantes(int totalParticipantes) { this.totalParticipantes = totalParticipantes; }
}
