package gravadora.gravadora.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import gravadora.gravadora.model.Gravadora;

/**
 * Interface responsável pelo acesso aos dados da entidade Gravadora.
 * Extende JpaRepository para fornecer operações CRUD automaticamente.
 */
public interface GravadoraRepository extends JpaRepository<Gravadora, Long> {

    // Método comentado para buscar uma gravadora pelo nome.
    // Se necessário, basta descomentar e o Spring Data JPA gerará a consulta automaticamente.
    // Gravadora findByNome(String nome);  
}
