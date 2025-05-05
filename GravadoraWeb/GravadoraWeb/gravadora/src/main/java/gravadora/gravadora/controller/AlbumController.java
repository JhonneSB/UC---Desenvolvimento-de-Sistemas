package gravadora.gravadora.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import gravadora.gravadora.model.Album;
import gravadora.gravadora.model.Artista;
import gravadora.gravadora.model.Genero;
import gravadora.gravadora.model.Gravadora;
import gravadora.gravadora.service.AlbumService;
import gravadora.gravadora.service.ArtistaService;
import gravadora.gravadora.service.GeneroService;
import gravadora.gravadora.service.GravadoraService;

@Controller
@RequestMapping("/albuns")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private ArtistaService artistaService;

    @Autowired
    private GravadoraService gravadoraService;

    @Autowired
    private GeneroService generoService;

    @GetMapping
    public String listarTodos(Model model) {
        List<Album> albuns = albumService.listarTodos();
        model.addAttribute("albuns", albuns);
        return "listar";
    }

    @GetMapping("/cadastrar")
    public String mostrarFormularioCadastro(Model model) {
        List<Artista> artistas = artistaService.listarTodos();
        List<Gravadora> gravadoras = gravadoraService.listarTodos();
        List<Genero> generos = generoService.listarTodos();

        model.addAttribute("album", new Album());
        model.addAttribute("artistas", artistas);
        model.addAttribute("gravadoras", gravadoras);
        model.addAttribute("generos", generos);

        if (artistas.isEmpty() || gravadoras.isEmpty() || generos.isEmpty()) {
            model.addAttribute("erro", "Não há dados suficientes para cadastrar um álbum.");
        }

        return "cadastrar_album";
    }

    @PostMapping("/cadastrar")
public String cadastrarAlbum(@ModelAttribute Album album, Model model) {
    try {
        if (album.getArtista() == null || album.getGravadora() == null || album.getGenero() == null) {
            model.addAttribute("erro", "Todos os campos obrigatórios devem ser preenchidos.");
            return "cadastrar_album";
        }

        // Buscar os objetos completos
        Artista artista = artistaService.buscarPorId(album.getArtista().getId());
        Gravadora gravadora = gravadoraService.buscarPorId(album.getGravadora().getId());

        album.setArtista(artista);
        album.setGravadora(gravadora);

        album.setNomeArtista(artista.getNome());
        album.setNomeGravadora(gravadora.getNome());

        albumService.salvar(album);
        return "redirect:/albuns";
    } catch (Exception e) {
        e.printStackTrace();
        model.addAttribute("erro", "Ocorreu um erro ao cadastrar o álbum. Tente novamente.");
        return "cadastrar_album";
    }
}


    @PostMapping("/deletar/{id}")
    public String deletarAlbum(@PathVariable Long id) {
        try {
            albumService.deletarPorId(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/albuns";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable Long id, Model model) {
        Album album = albumService.buscarPorId(id);
        if (album == null) {
            return "redirect:/albuns";
        }

        List<Genero> generos = generoService.listarTodos();
        List<Gravadora> gravadoras = gravadoraService.listarTodos();
        List<Artista> artistas = artistaService.listarTodos();

        model.addAttribute("album", album);
        model.addAttribute("generos", generos);
        model.addAttribute("gravadoras", gravadoras);
        model.addAttribute("artistas", artistas);

        return "editar_album";
    }

    @PostMapping("/editar")
public String editarAlbum(@ModelAttribute Album album) {
    Album albumExistente = albumService.buscarPorId(album.getId());
    if (albumExistente != null) {
        // Busca os objetos completos novamente
        Artista artista = artistaService.buscarPorId(album.getArtista().getId());
        Gravadora gravadora = gravadoraService.buscarPorId(album.getGravadora().getId());

        albumExistente.setNome(album.getNome());
        albumExistente.setGenero(album.getGenero());
        albumExistente.setGravadora(gravadora);
        albumExistente.setArtista(artista);

        // Atualiza os nomes fixos
        albumExistente.setNomeArtista(artista.getNome());
        albumExistente.setNomeGravadora(gravadora.getNome());

        albumExistente.setLancamento(album.getLancamento());

        albumService.salvar(albumExistente);
    }
    return "redirect:/albuns";
}
    
    
    @GetMapping("/por-gravadora/{id}")
    public String listarAlbunsPorGravadora(@PathVariable Long id, Model model) {
        Gravadora gravadora = gravadoraService.buscarPorId(id);
        if (gravadora == null) {
            return "redirect:/albuns";
        }

        List<Album> albuns = albumService.buscarPorGravadora(gravadora.getNome());
        model.addAttribute("albuns", albuns);
        model.addAttribute("filtro", "Gravadora: " + gravadora.getNome());
        return "listar";
    }
}
