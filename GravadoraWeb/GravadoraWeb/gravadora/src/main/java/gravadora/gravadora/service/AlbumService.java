package gravadora.gravadora.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gravadora.gravadora.model.Album;
import gravadora.gravadora.repository.AlbumRepository;

/**
 * Classe de serviço responsável pela lógica de negócios relacionada aos álbuns.
 * Faz a intermediação entre o controlador e o repositório (banco de dados).
 */
@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    /**
     * Método para listar todos os álbuns cadastrados no banco de dados.
     * @return Lista de todos os álbuns.
     */
    public List<Album> listarTodos() {
        return albumRepository.findAll();
    }

    /**
     * Método para salvar um novo álbum no banco de dados.
     * Antes de salvar, verifica se o artista, gravadora e gênero estão preenchidos.
     * @param album Objeto do tipo Album a ser salvo.
     */
    public void salvar(Album album) {
        validarAlbum(album);
        albumRepository.save(album);
    }

    /**
     * Método para buscar álbuns por nome do artista.
     * @param nomeArtista Nome do artista cujo álbum será buscado.
     * @return Lista de álbuns que pertencem ao artista informado.
     */
    public List<Album> buscarPorArtista(String nomeArtista) {
        return albumRepository.findByArtistaNome(nomeArtista);
    }    

    /**
     * Método para buscar álbuns por nome da gravadora.
     * @param nomeGravadora Nome da gravadora cujos álbuns serão buscados.
     * @return Lista de álbuns que pertencem à gravadora informada.
     */
    public List<Album> buscarPorGravadora(String nomeGravadora) {
        return albumRepository.findByGravadoraNome(nomeGravadora);
    }

    /**
     * Método para buscar um álbum pelo seu ID.
     * @param id ID do álbum a ser buscado.
     * @return Retorna o álbum encontrado ou null se não existir.
     */
    public Album buscarPorId(Long id) {
        return albumRepository.findById(id).orElse(null);
    }

    /**
     * Método para deletar um álbum pelo ID.
     * @param id ID do álbum a ser removido.
     */
    public void deletarPorId(Long id) {
        albumRepository.deleteById(id);
    }

    /**
     * Método para atualizar um álbum existente.
     * @param album Objeto do tipo Album com os novos dados.
     * @return O álbum atualizado.
     */
    public Album atualizar(Album album) {
        validarAlbum(album);
        return albumRepository.save(album);
    }

    /**
     * Método adicional para editar um álbum.
     * Atualiza apenas os campos que foram preenchidos no objeto recebido.
     * @param album Objeto do tipo Album com os dados editados.
     */
    public void editarAlbum(Album album) {
        Album existente = albumRepository.findById(album.getId()).orElse(null);
        if (existente == null) {
            throw new IllegalArgumentException("Álbum não encontrado para edição");
        }

        if (album.getNome() != null) existente.setNome(album.getNome());
        if (album.getArtista() != null) existente.setArtista(album.getArtista());
        if (album.getGravadora() != null) existente.setGravadora(album.getGravadora());
        if (album.getGenero() != null) existente.setGenero(album.getGenero());

        validarAlbum(existente);
        albumRepository.save(existente);
    }

    /**
     * Valida se o álbum possui os campos obrigatórios preenchidos.
     * @param album Álbum a ser validado.
     */
    private void validarAlbum(Album album) {
        if (album.getArtista() == null) {
            throw new IllegalArgumentException("Artista não encontrado");
        }
        if (album.getGravadora() == null) {
            throw new IllegalArgumentException("Gravadora não encontrada");
        }
        if (album.getGenero() == null) {
            throw new IllegalArgumentException("Gênero não encontrado");
        }
    }
}
