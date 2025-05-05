package app_cad_pro.app_cad_pro.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import app_cad_pro.app_cad_pro.model.Produto;
import app_cad_pro.app_cad_pro.service.ProdutoService;

import java.io.IOException;

@Controller
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/cadastrar")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("produto", new Produto());
        return "cadastrar";
    }

    @PostMapping("/cadastrar")
    public String cadastrarProduto(Produto produto, MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            produto.setImagemBytes(file.getBytes());
        }
        produtoService.salvarProduto(produto);
        return "redirect:/listar";
    }

    @GetMapping("/listar")
    public String listarProdutos(Model model) {
        model.addAttribute("produto", produtoService.listarProdutos());
        return "listar";
    }

    @GetMapping("/produto/imagem/{id}")
    public ResponseEntity<byte[]> obterImagemProduto(@PathVariable Long id) {
        Produto produto = produtoService.buscarPorId(id);
        byte[] imagem = produto.getImagemBytes(); // assume que o campo imagem é byte[]

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG); // ou IMAGE_PNG dependendo do formato

        return new ResponseEntity<>(imagem, headers, HttpStatus.OK);
    }

    @PostMapping("/produto/deletar/{id}")
    public String deletarProduto(@PathVariable Long id) {
        produtoService.deletarPorId(id); // Assumindo que você já tem um método no service para deletar por id
        return "redirect:/listar"; // Redireciona para a lista de produtos após a exclusão
    }

    @GetMapping("/produto/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable Long id, Model model) {
        Produto produto = produtoService.buscarPorId(id); // Busca o produto pelo ID
        if (produto != null) {
            model.addAttribute("produto", produto); // Adiciona o produto ao modelo
            return "editar_produto"; // Retorna o template de edição
        } else {
            return "redirect:/produto/lista"; // Se o produto não existir, redireciona para a lista
        }
    }

    @PostMapping("/produto/editar")
    public String editarProduto(@ModelAttribute Produto produto) {
        produtoService.salvarProduto(produto); // Atualiza o produto no banco
        return "redirect:/listar"; // Redireciona para a lista de produtos após a edição
    }

    @PostMapping("/produto/salvar")
    public String salvarProduto(@ModelAttribute Produto produto, @RequestParam("imagem") MultipartFile imagem) {
        if (!imagem.isEmpty()) {
            produto.setImagem(imagem);
        }
        produtoService.salvarProduto(produto); // Salva o produto no banco de dados
        return "redirect:/listar"; // Redireciona para a lista de produtos
    }
}
