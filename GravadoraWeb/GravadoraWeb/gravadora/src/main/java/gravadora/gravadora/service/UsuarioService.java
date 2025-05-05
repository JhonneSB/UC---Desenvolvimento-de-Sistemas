package gravadora.gravadora.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gravadora.gravadora.model.Usuario;
import gravadora.gravadora.repository.UsuarioRepository;

/**
 * Serviço responsável pelo gerenciamento dos usuários do sistema.
 */
@Service
public class UsuarioService {

    @Autowired // Injeção de dependência do repositório de usuários
    private UsuarioRepository usuarioRepository; 

    /**
     * Lista todos os usuários cadastrados no banco de dados.
     * 
     * @return Lista contendo todos os usuários disponíveis.
     */
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll(); // Busca todos os usuários no repositório
    }

    /**
     * Busca um usuário pelo ID.
     * 
     * @param id Identificador único do usuário.
     * @return Objeto Usuario correspondente ao ID informado ou null se não encontrado.
     */
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null); // Retorna o usuário ou null caso não encontrado
    }

    /**
     * Salva ou atualiza um usuário no banco de dados.
     * 
     * @param usuario Objeto Usuario a ser salvo.
     */
    public void salvar(Usuario usuario) {
        usuarioRepository.save(usuario); // Salva ou atualiza o usuário no banco
    }

    /**
     * Deleta um usuário do banco de dados pelo seu ID.
     * 
     * @param id Identificador único do usuário a ser removido.
     */
    public void deletar(Long id) {
        usuarioRepository.deleteById(id); // Deleta o usuário pelo ID
    }

    /**
     * Busca o nome do usuário pelo ID.
     * 
     * @param usuarioId ID do usuário (em formato de String).
     * @return Nome do usuário se encontrado, ou "Usuário Desconhecido" caso contrário.
     */
    public String buscarNomePorId(String usuarioId) {
        try {
            Long id = Long.parseLong(usuarioId); // Converte a String para Long
            Optional<Usuario> usuario = usuarioRepository.findById(id); // Busca pelo ID

            return usuario.map(Usuario::getNome).orElse("Usuário Desconhecido"); // Retorna o nome se existir
        } catch (NumberFormatException e) { // Captura erro caso o ID não seja um número válido
            return "Usuário Desconhecido"; 
        }
    }
}
