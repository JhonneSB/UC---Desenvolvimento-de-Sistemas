package gravadora.gravadora.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gravadora.gravadora.model.Gravadora;
import gravadora.gravadora.repository.GravadoraRepository;

/**
 * Serviço responsável por gerenciar as operações relacionadas às gravadoras.
 */
@Service
public class GravadoraService {

    @Autowired // Injeção de dependência do repositório de gravadoras
    private GravadoraRepository gravadoraRepository;

    /**
     * Lista todas as gravadoras cadastradas no banco de dados.
     * 
     * @return Lista contendo todas as gravadoras disponíveis.
     */
    public List<Gravadora> listarTodos() {
        return gravadoraRepository.findAll();
    }

    /**
     * Busca uma gravadora pelo ID.
     * 
     * @param id Identificador único da gravadora.
     * @return Objeto Gravadora correspondente ao ID informado ou null se não encontrado.
     */
    public Gravadora buscarPorId(Long id) {
        return gravadoraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grvadora com ID " + id + " não encontrado."));
    }

    /**
     * Salva ou atualiza uma gravadora no banco de dados.
     * 
     * @param gravadora Objeto Gravadora a ser salvo.
     */
    public void salvar(Gravadora gravadora) {
        if (!validarCnpj(gravadora.getCnpj())) {
            throw new IllegalArgumentException("CNPJ inválido.");
        }
        gravadoraRepository.save(gravadora);
    }
    

    /**
     * Deleta uma gravadora do banco de dados pelo seu ID.
     * 
     * @param id Identificador único da gravadora a ser removida.
     */
    public void deletar(Long id) {
        gravadoraRepository.deleteById(id);
    }

    /**
     * Verifica se uma gravadora possui álbuns cadastrados.
     * 
     * @param gravadoraId ID da gravadora a ser verificada.
     * @return true se a gravadora possuir álbuns, false caso contrário.
     */
    public boolean possuiAlbuns(Long gravadoraId) {
        Gravadora gravadora = buscarPorId(gravadoraId);
        return gravadora != null && gravadora.getAlbuns() != null && !gravadora.getAlbuns().isEmpty();
    }
    /**
 * Valida um CNPJ verificando os dígitos verificadores.
 *
 * @param cnpj CNPJ como string (pode conter pontuação ou não)
 * @return true se o CNPJ for válido, false caso contrário
 */
public boolean validarCnpj(String cnpj) {
    if (cnpj == null) return false;

    // Remove qualquer caractere que não seja número
    cnpj = cnpj.replaceAll("[^\\d]", "");

    // CNPJ deve ter 14 dígitos
    if (cnpj.length() != 14) return false;

    // Verifica se todos os dígitos são iguais (ex: 11111111111111)
    if (cnpj.matches("(\\d)\\1{13}")) return false;

    int[] pesosPrimeiroDV = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
    int[] pesosSegundoDV  = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    try {
        // Calcula o primeiro dígito verificador
        int soma1 = 0;
        for (int i = 0; i < 12; i++) {
            soma1 += Character.getNumericValue(cnpj.charAt(i)) * pesosPrimeiroDV[i];
        }
        int resto1 = soma1 % 11;
        int dv1 = (resto1 < 2) ? 0 : 11 - resto1;

        // Calcula o segundo dígito verificador
        int soma2 = 0;
        for (int i = 0; i < 13; i++) {
            soma2 += Character.getNumericValue(cnpj.charAt(i)) * pesosSegundoDV[i];
        }
        int resto2 = soma2 % 11;
        int dv2 = (resto2 < 2) ? 0 : 11 - resto2;

        // Compara os dígitos verificadores com os do CNPJ original
        return dv1 == Character.getNumericValue(cnpj.charAt(12)) &&
               dv2 == Character.getNumericValue(cnpj.charAt(13));
    } catch (Exception e) {
        return false;
    }
}
}
