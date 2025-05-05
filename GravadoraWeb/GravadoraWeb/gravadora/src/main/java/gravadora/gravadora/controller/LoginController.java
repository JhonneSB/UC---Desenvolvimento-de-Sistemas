package gravadora.gravadora.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import gravadora.gravadora.model.Usuario;
import gravadora.gravadora.repository.UsuarioRepository;
import gravadora.gravadora.service.CookieService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;

/**
 * Controlador responsável pelo gerenciamento do login e registro de usuários.
 */
@Controller // Indica que essa classe é um controlador Spring MVC
public class LoginController {

    @Autowired // Injeta automaticamente o repositório de usuários
    private UsuarioRepository usuarioRepository; 

    /**
     * Método que exibe a página de login.
     * @return Retorna a view "login".
     */
    @GetMapping("/login") // Mapeia a requisição GET para "/login"
    public String login() {
        return "login"; // Retorna a página de login
    }

    /**
     * Método que processa o login do usuário.
     * @param usuario Objeto contendo email e senha enviados pelo formulário.
     * @param model Objeto para adicionar atributos à página.
     * @param response Objeto para manipulação de cookies.
     * @return Redireciona para a página inicial se o login for bem-sucedido ou retorna ao login em caso de falha.
     * @throws UnsupportedEncodingException Exceção caso haja erro na codificação dos cookies.
     */
    @PostMapping("/logar") // Mapeia a requisição POST para "/logar"
    public String loginUsuario(Usuario usuario, Model model, HttpServletResponse response) throws UnsupportedEncodingException {
        // Verifica se existe um usuário no banco com o email e senha fornecidos
        Usuario usuarioLogado = this.usuarioRepository.findByEmailAndSenha(usuario.getEmail(), usuario.getSenha());

        if (usuarioLogado != null) { // Se encontrar um usuário válido
            // Define cookies para armazenar o ID e nome do usuário logado
            CookieService.setCookie(response, "usuarioId", String.valueOf(usuarioLogado.getId()), 1000);
            CookieService.setCookie(response, "nomeusuario", String.valueOf(usuarioLogado.getNome()), 1000);
            return "redirect:/"; // Redireciona para a página inicial após login bem-sucedido
        }

        // Caso o login falhe, adiciona uma mensagem de erro e retorna para a página de login
        model.addAttribute("Erro", "Usuário Inválido!");
        return "login"; // Retorna a página de login
    }

    /**
     * Método que exibe a página de registro de novos usuários.
     * @return Retorna a view "registrar_usuario".
     */
    @GetMapping("/registrar") // Mapeia a requisição GET para "/registrar"
    public String registro() {
        return "registrar_usuario"; // Retorna a página de registro
    }

    /**
     * Método que processa o registro de novos usuários.
     * @param usuario Objeto contendo os dados do usuário a ser cadastrado.
     * @param result Objeto para validação de erros.
     * @param model Objeto para adicionar atributos à página.
     * @return Redireciona para a página de login após um registro bem-sucedido ou retorna à página de registro em caso de erro.
     */
    @PostMapping("/registrar") // Mapeia a requisição POST para "/registrar"
    public String registrar(@Validated Usuario usuario, BindingResult result, Model model) {
        // Se houver erros na validação dos campos, retorna para a página de registro
        if (result.hasErrors()) {
            return "registrar_usuario";
        }

        // Validação manual: verifica se a senha tem no mínimo 6 caracteres
        if (usuario.getSenha().length() < 6) {
            model.addAttribute("Erro", "A senha deve ter no mínimo 6 caracteres.");
            return "registrar_usuario"; // Retorna para a página de registro se a senha for muito curta
        }

        // Salva o novo usuário no banco de dados
        usuarioRepository.save(usuario);

        return "redirect:/login"; // Redireciona para a página de login após um registro bem-sucedido
    }
}
