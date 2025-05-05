package gravadora.gravadora.model;

import java.time.LocalDate;
import jakarta.persistence.*;
import java.time.format.DateTimeFormatter;

/**
 * Classe que representa um álbum no sistema.
 * Cada álbum pertence a uma gravadora, um artista e um gênero musical.
 */
@Entity
@Table(name = "album")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private LocalDate lancamento;

    // Campos de nome fixo
    private String nomeArtista;     // Nome do artista salvo no momento do cadastro
    private String nomeGravadora;   // Nome da gravadora salvo no momento do cadastro

    @ManyToOne
    @JoinColumn(name = "gravadora_id")
    private Gravadora gravadora;

    @ManyToOne
    @JoinColumn(name = "artista_id")
    private Artista artista;

    @ManyToOne
    @JoinColumn(name = "genero_id")
    private Genero genero;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getLancamento() {
        return lancamento;
    }

    public void setLancamento(LocalDate lancamento) {
        this.lancamento = lancamento;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    public Gravadora getGravadora() {
        return gravadora;
    }

    public void setGravadora(Gravadora gravadora) {
        this.gravadora = gravadora;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getNomeArtista() {
        return nomeArtista;
    }

    public void setNomeArtista(String nomeArtista) {
        this.nomeArtista = nomeArtista;
    }

    public String getNomeGravadora() {
        return nomeGravadora;
    }

    public void setNomeGravadora(String nomeGravadora) {
        this.nomeGravadora = nomeGravadora;
    }
    public String getDataFormatada() {
        if (lancamento != null) {
            return lancamento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        return "Sem data";
    }
    
}
