package gravadora.gravadora.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import gravadora.gravadora.model.Album;
import gravadora.gravadora.model.Artista;
import gravadora.gravadora.service.AlbumService;
import gravadora.gravadora.service.ArtistaService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador responsável pelo gerenciamento de artistas no sistema.
 */
@Controller
@RequestMapping("/artistas")
public class ArtistaController {

    @Autowired
    private ArtistaService artistaService;

    @Autowired
    private AlbumService albumService;

    /**
     * Lista todos os artistas cadastrados.
     */
    @GetMapping
public String listarTodos(Model model) {
    List<Artista> artistas = artistaService.listarTodos();
    model.addAttribute("artistas", artistas);

    // Mapa de álbuns por artista (igualzinho o do AlbumController)
    Map<Long, List<Album>> albunsPorArtista = new HashMap<>();
    for (Artista artista : artistas) {
        List<Album> albuns = albumService.buscarPorArtista(artista.getNome());
        albunsPorArtista.put(artista.getId(), albuns);
    }

    model.addAttribute("albunsPorArtista", albunsPorArtista);
    return "listar_artista";
}

    /**
     * Exibe o formulário de cadastro de um novo artista.
     */
    @GetMapping("/cadastrar_artista")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("artista", new Artista());
        return "cadastrar_artista";
    }

    /**
     * Cadastra um novo artista.
     */
    @PostMapping("/cadastrar_artista")
    public String cadastrarArtista(@ModelAttribute Artista artista) {
        artistaService.salvar(artista);  // Agora usando o service
        return "redirect:/artistas";
    }

    /**
     * Exibe o formulário de edição de um artista.
     */
    @GetMapping("/editar_artista/{id}")
    public String mostrarFormularioEdicao(@PathVariable Long id, Model model) {
        Artista artista = artistaService.buscarPorId(id);
        if (artista != null) {
            model.addAttribute("artista", artista);
            return "editar_artista";
        }
        return "redirect:/artistas";
    }

    /**
     * Atualiza os dados de um artista existente.
     */
    @PostMapping("/editar_artista/{id}")
    public String editarArtista(@PathVariable Long id, @ModelAttribute Artista artista) {
        artista.setId(id);
        artistaService.salvar(artista);
        return "redirect:/artistas";
    }

    /**
     * Exclui um artista do banco de dados.
     */
    @GetMapping("/deletar_artista/{id}")
    public String deletarArtista(@PathVariable Long id, Model model) {
        if (artistaService.possuiAlbuns(id)) {
            model.addAttribute("erro", "Não é possível excluir um artista que possui álbuns vinculados.");
            return listarTodos(model);
        }
        artistaService.deletar(id);
        return "redirect:/artistas";
    }
}
