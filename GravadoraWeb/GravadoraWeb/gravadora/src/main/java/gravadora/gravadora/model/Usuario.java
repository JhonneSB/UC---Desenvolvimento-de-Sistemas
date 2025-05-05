package gravadora.gravadora.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Classe que representa um Usuário no sistema.
 * Cada usuário possui um ID, nome, e-mail e senha.
 */
@Entity // Indica que esta classe é uma entidade JPA que será mapeada para uma tabela no banco de dados
public class Usuario {
    
    @Id // Define o atributo 'id' como chave primária da entidade
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gera o ID automaticamente no banco de dados
    private long id; // Identificador único do usuário

    private String nome; // Nome do usuário
    private String email; // Endereço de e-mail do usuário
    private String senha; // Senha do usuário (deveria ser armazenada de forma segura, como criptografada)

    // Métodos Getters e Setters para acessar e modificar os atributos

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
