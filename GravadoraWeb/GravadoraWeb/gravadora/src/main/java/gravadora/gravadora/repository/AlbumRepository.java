package gravadora.gravadora.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import gravadora.gravadora.model.Album;

/**
 * Interface responsável pelo acesso aos dados da entidade Album.
 * Estende JpaRepository, fornecendo métodos CRUD automaticamente.
 */
public interface AlbumRepository extends JpaRepository<Album, Long> { 
    /**
     * Método personalizado para buscar álbuns pelo nome do artista.
     * @param nome Nome do artista.
     * @return Lista de álbuns associados ao artista informado.
     */
    List<Album> findByArtistaNome(String nome); // Consulta baseada no nome do artista
    List<Album> findByGravadoraNome(String nome); // Consulta baseada no nome da gravadora
}
