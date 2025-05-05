public class Curriculo {
    private String nome;
    private String profissao;
    private String experiencia;
    private String habilidades;

    public Curriculo(String nome, String profissao, String experiencia, String habilidades) {
        this.nome = nome;
        this.profissao = profissao;
        this.experiencia = experiencia;
        this.habilidades = habilidades;
    }

    @Override
    public String toString() {
        return "\n======= CURRÍCULO =======\n"
             + "Nome: " + (nome != null ? nome : "Não informado") + "\n"
             + "Profissão: " + (profissao != null ? profissao : "Não informado") + "\n"
             + "Experiência: " + (experiencia != null ? experiencia : "Não informado") + "\n"
             + "Habilidades: " + (habilidades != null ? habilidades : "Não informado") + "\n"
             + "=========================";
    }
}
