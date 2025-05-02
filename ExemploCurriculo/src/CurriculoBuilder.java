public class CurriculoBuilder {
    private String nome;
    private String profissao;
    private String experiencia;
    private String habilidades;

    public CurriculoBuilder setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public CurriculoBuilder setProfissao(String profissao) {
        this.profissao = profissao;
        return this;
    }

    public CurriculoBuilder setExperiencia(String experiencia) {
        this.experiencia = experiencia;
        return this;
    }

    public CurriculoBuilder setHabilidades(String habilidades) {
        this.habilidades = habilidades;
        return this;
    }

    public Curriculo build() {
        return new Curriculo(nome, profissao, experiencia, habilidades);
    }
}
