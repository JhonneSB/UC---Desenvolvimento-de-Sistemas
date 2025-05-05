package gravadora.gravadora.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa uma Gravadora no sistema.
 * Cada gravadora pode possuir vários álbuns associados.
 */
@Entity
@Table(name = "gravadora")
public class Gravadora {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome; // Nome da gravadora
    private String cnpj; // CNPJ da gravadora
    private String sede; // Localização da sede

    @OneToMany(mappedBy = "gravadora", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Album> albuns = new ArrayList<>();

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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public List<Album> getAlbuns() {
        if (albuns == null) {
            albuns = new ArrayList<>();
        }
        return albuns;
    }

    public void setAlbuns(List<Album> albuns) {
        this.albuns = albuns;
    }
}
