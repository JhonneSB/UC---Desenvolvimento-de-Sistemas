package gravadora.gravadora.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gravadora.gravadora.model.Artista; 
import gravadora.gravadora.repository.ArtistaRepository;

/**
 * Classe de serviço responsável por gerenciar a lógica de negócios dos artistas.
 * Atua como intermediária entre o repositório e os controladores.
 */
@Service
public class ArtistaService {

    // Injeta automaticamente o repositório de Artista para manipulação no banco de dados
    @Autowired
    private ArtistaRepository artistaRepository;

    /**
     * Lista todos os artistas cadastrados no banco de dados.
     * @return Lista de objetos do tipo Artista.
     */
    public List<Artista> listarTodos() {
        return artistaRepository.findAll();
    }

    /**
     * Busca um artista pelo ID.
     * Caso o artista não seja encontrado, lança uma exceção informando o erro.
     * @param id ID do artista a ser buscado.
     * @return Objeto Artista encontrado.
     */
    public Artista buscarPorId(Long id) {
        return artistaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artista com ID " + id + " não encontrado."));
    }

    /**
     * Salva ou atualiza um artista no banco de dados.
     * Se o ID for nulo, um novo artista será criado; caso contrário, será atualizado.
     * @param artista Objeto do tipo Artista a ser salvo.
     * @return Artista salvo no banco de dados.
     */
    public Artista salvar(Artista artista) {
        return artistaRepository.save(artista);
    }

    /**
     * Deleta um artista do banco de dados com validação prévia de existência.
     * Se o artista não existir, uma exceção será lançada.
     * @param id ID do artista a ser deletado.
     */
    public void deletar(Long id) {
        if (!artistaRepository.existsById(id)) {
            throw new RuntimeException("Artista com ID " + id + " não encontrado.");
        }
        artistaRepository.deleteById(id);
    }

    /**
     * Verifica se um artista possui álbuns cadastrados no sistema.
     * @param artistaId ID do artista a ser verificado.
     * @return true se o artista possui álbuns, false caso contrário.
     */
    public boolean possuiAlbuns(Long artistaId) {
        return artistaRepository.findById(artistaId)
                .map(artista -> artista.getAlbuns() != null && !artista.getAlbuns().isEmpty())
                .orElse(false);
    }

    /**
     * Atualiza apenas o nome do artista sem alterar sua lista de álbuns.
     * Primeiro busca o artista existente no banco e depois atualiza apenas o nome.
     * @param artista Objeto Artista com o novo nome.
     * @return Objeto Artista atualizado.
     */
    public Artista atualizarArtista(Artista artista) {
        Artista artistaExistente = buscarPorId(artista.getId()); // Busca o artista pelo ID
        artistaExistente.setNome(artista.getNome()); // Atualiza apenas o nome
        return artistaRepository.save(artistaExistente); // Salva a atualização no banco
    }
}
