package gravadora.gravadora.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gravadora.gravadora.model.Genero;
import gravadora.gravadora.repository.GeneroRepository;

/**
 * Serviço responsável pela manipulação de gêneros musicais.
 * Este serviço interage com o repositório para buscar e gerenciar dados de gêneros.
 */
@Service
public class GeneroService {
    
    @Autowired // Injeta automaticamente o repositório de Gêneros
    private GeneroRepository generoRepository;

    /**
     * Lista todos os gêneros musicais cadastrados no banco de dados.
     * 
     * @return Lista contendo todos os gêneros disponíveis.
     */
    public List<Genero> listarTodos() {
        return generoRepository.findAll(); // Busca todos os registros na tabela de gêneros
    }
}
