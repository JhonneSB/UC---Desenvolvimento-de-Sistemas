package gravadora.gravadora.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import gravadora.gravadora.model.Genero;

/**
 * Interface responsável pelo acesso aos dados da entidade Genero.
 * Extende JpaRepository para fornecer operações CRUD automaticamente.
 */
public interface GeneroRepository extends JpaRepository<Genero, Long> { 

    /**
     * Método para buscar um gênero pelo nome.
     * O Spring Data JPA gera automaticamente a consulta correspondente com base no nome do método.
     *
     * @param nome Nome do gênero a ser buscado.
     * @return Retorna o gênero encontrado ou null caso não exista.
     */
    Genero findByNome(String nome);  
}
