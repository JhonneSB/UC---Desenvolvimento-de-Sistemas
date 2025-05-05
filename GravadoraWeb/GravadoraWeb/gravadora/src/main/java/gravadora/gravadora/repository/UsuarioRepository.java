package gravadora.gravadora.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import gravadora.gravadora.model.Usuario;

/**
 * Interface responsável pelo acesso aos dados da entidade Usuario.
 * Extende JpaRepository para fornecer operações CRUD automaticamente.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Método para buscar um usuário pelo e-mail.
     * O Spring Data JPA gera automaticamente a consulta baseada no nome do método.
     */
    Usuario findByEmail(String email);

    /**
     * Método para buscar um usuário pelo e-mail e senha.
     * O Spring Data JPA gera a consulta automaticamente.
     */
    Usuario findByEmailAndSenha(String email, String senha);

    // Método comentado para buscar usuário pelo e-mail utilizando Optional.
    // Pode ser útil caso queira lidar com a possibilidade de usuário não encontrado.
    // Optional<Usuario> findByEmail(String email);

    /**
     * Consulta personalizada utilizando SQL nativo.
     * Busca um usuário pelo e-mail e senha na tabela 'applogin.usuario'.
     * O uso de SQL nativo pode ser necessário quando há necessidade de consultas mais específicas.
     */
    @Query(value = "SELECT * FROM applogin.usuario WHERE email = :email AND senha = :senha", nativeQuery = true)
    public Usuario login(String email, String senha);
}
