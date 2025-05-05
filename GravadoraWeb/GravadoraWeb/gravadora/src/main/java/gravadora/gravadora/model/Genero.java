package gravadora.gravadora.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um gênero musical no sistema.
 * Cada gênero pode estar associado a vários álbuns.
 */
@Entity // Define que esta classe é uma entidade JPA
@Table(name = "genero") // Mapeia a entidade para a tabela "genero" no banco de dados
public class Genero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id; // Identificador único do gênero (chave primária)
    
    private String nome; // Nome do gênero musical (ex: Rock, Pop, Jazz)

    /**
     * Relacionamento um-para-muitos com a entidade Album.
     * Um gênero pode estar associado a vários álbuns.
     * - mappedBy = "genero" indica que a entidade Album gerencia essa relação.
     * - cascade = CascadeType.ALL faz com que alterações no gênero afetem os álbuns associados.
     * - orphanRemoval = true garante que ao remover um gênero, seus álbuns também sejam removidos.
     */
    @OneToMany(mappedBy = "genero", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Album> albuns = new ArrayList<>(); // Lista inicializada para evitar NullPointerException

    // Métodos Getters e Setters para acessar e modificar os atributos

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
}
