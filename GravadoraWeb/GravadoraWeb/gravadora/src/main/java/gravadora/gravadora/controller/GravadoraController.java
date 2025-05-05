package gravadora.gravadora.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import gravadora.gravadora.model.Album;
import gravadora.gravadora.model.Gravadora;
import gravadora.gravadora.service.AlbumService;
import gravadora.gravadora.service.GravadoraService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador responsável pelo gerenciamento de gravadoras no sistema.
 */
@Controller
@RequestMapping("/gravadoras") // Define o caminho base para este controlador
public class GravadoraController {

    @Autowired
    private GravadoraService gravadoraService; // Serviço para operações relacionadas a gravadoras

    @Autowired
    private AlbumService albumService; // Serviço para operações relacionadas a albuns

    /**
     * Lista todas as gravadoras cadastradas.
     * @param model Objeto Model usado para enviar dados para a view.
     * @return Retorna a página de listagem de gravadoras.
     */
    @GetMapping
public String listarTodos(Model model) {
    List<Gravadora> gravadoras = gravadoraService.listarTodos();
    model.addAttribute("gravadoras", gravadoras);

    // Mapa de álbuns por artista (igualzinho o do AlbumController)
    Map<Long, List<Album>> albunsPorGravadora = new HashMap<>();
    for (Gravadora gravadora : gravadoras) {
        List<Album> albuns = albumService.buscarPorGravadora(gravadora.getNome());
        albunsPorGravadora.put(gravadora.getId(), albuns);
    }

    model.addAttribute("albunsPorGravadora", albunsPorGravadora);
    return "listar_gravadora";
}

    /**
     * Exibe o formulário de cadastro de uma nova gravadora.
     * @param model Objeto Model para enviar dados à view.
     * @return Retorna a página de cadastro de gravadora.
     */
    @GetMapping("/cadastrar_gravadora")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("gravadora", new Gravadora()); // Adiciona um novo objeto gravadora ao modelo
        return "cadastrar_gravadora"; // Retorna a página de cadastro de gravadora
    }

    /**
     * Cadastra uma nova gravadora no banco de dados.
     * @param gravadora Objeto Gravadora preenchido no formulário.
     * @return Redireciona para a listagem de gravadoras após o cadastro.
     */
    @PostMapping("/cadastrar_gravadora")
    public String cadastrarGravadora(@ModelAttribute Gravadora gravadora) {
        gravadoraService.salvar(gravadora); // Salva a gravadora no banco de dados
        return "redirect:/gravadoras"; // Redireciona para a listagem de gravadoras
    }

    /**
     * Exibe o formulário de edição de uma gravadora específica.
     * @param id Identificador da gravadora a ser editada.
     * @param model Objeto Model para enviar dados à view.
     * @return Retorna a página de edição da gravadora ou redireciona se a gravadora não for encontrada.
     */
    @GetMapping("/editar_gravadora/{id}")
    public String mostrarFormularioEdicao(@PathVariable Long id, Model model) {
        Gravadora gravadora = gravadoraService.buscarPorId(id); // Busca a gravadora pelo ID
        if (gravadora != null) {
            model.addAttribute("gravadora", gravadora); // Adiciona a gravadora ao modelo
            return "editar_gravadora"; // Retorna a página de edição
        }
        return "redirect:/gravadoras"; // Se não encontrar, redireciona para a listagem
    }

    /**
     * Atualiza os dados de uma gravadora existente.
     * @param id Identificador da gravadora a ser atualizada.
     * @param gravadora Objeto Gravadora atualizado.
     * @return Redireciona para a listagem de gravadoras após a edição.
     */
    @PostMapping("/editar_gravadora/{id}")
    public String editarGravadora(@PathVariable Long id, @ModelAttribute Gravadora gravadora) {
        gravadora.setId(id); // Garante que o ID da gravadora seja mantido
        gravadoraService.salvar(gravadora); // Salva as alterações no banco de dados
        return "redirect:/gravadoras"; // Redireciona para a listagem de gravadoras
    }

    /**
     * Exclui uma gravadora do banco de dados.
     * @param id Identificador da gravadora a ser excluída.
     * @param model Objeto Model para enviar mensagens à view.
     * @return Redireciona para a listagem de gravadoras ou exibe erro se a gravadora tiver álbuns vinculados.
     */
    @GetMapping("/deletar_gravadora/{id}")
    public String deletarGravadora(@PathVariable Long id, Model model) {
        if (gravadoraService.possuiAlbuns(id)) { // Verifica se a gravadora tem álbuns vinculados
            model.addAttribute("erro", "Não é possível excluir uma gravadora que possui álbuns vinculados.");
            return listarTodos(model); // Retorna à listagem com a mensagem de erro
        }
        gravadoraService.deletar(id); // Remove a gravadora do banco de dados
        return "redirect:/gravadoras"; // Redireciona para a listagem de gravadoras
    }
}
