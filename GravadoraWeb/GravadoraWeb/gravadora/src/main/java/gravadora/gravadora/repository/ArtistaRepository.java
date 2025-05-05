package gravadora.gravadora.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import gravadora.gravadora.model.Artista;

/**
 * Interface responsável pelo acesso aos dados da entidade Artista.
 * Estende JpaRepository para fornecer operações CRUD automaticamente.
 */
public interface ArtistaRepository extends JpaRepository<Artista, Long> { 
    // Método comentado que poderia ser usado para buscar um artista pelo nome
    // O Spring Data JPA pode gerar automaticamente a consulta correspondente
    // Artista findByNome(String nome);  
}
