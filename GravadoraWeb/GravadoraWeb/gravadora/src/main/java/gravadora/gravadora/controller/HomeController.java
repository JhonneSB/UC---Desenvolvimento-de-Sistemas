package gravadora.gravadora.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import gravadora.gravadora.service.UsuarioService;

/**
 * Controlador responsável pela página inicial (Home) da aplicação.
 */
@Controller // Indica que essa classe é um controlador Spring MVC
public class HomeController {

    @Autowired // Injeta automaticamente o serviço de usuário
    private UsuarioService usuarioService;  // Serviço para buscar informações do usuário

    /**
     * Método para exibir a página inicial.
     * Verifica se o usuário está autenticado antes de permitir o acesso.
     * 
     * @param request Objeto HttpServletRequest para acessar os cookies da requisição.
     * @return Retorna a página home caso o usuário esteja logado ou redireciona para o login.
     */
    @GetMapping("/") // Mapeia a requisição HTTP GET para a URL raiz "/"
    public ModelAndView home(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies(); // Obtém todos os cookies armazenados na requisição
        boolean usuarioLogado = false; // Variável que indica se o usuário está autenticado
        String nomeUsuario = "Usuário"; // Nome padrão caso o usuário não seja encontrado

        // Verifica se há cookies armazenados no navegador do usuário
        if (cookies != null) { // Se houver cookies na requisição
            for (Cookie cookie : cookies) { // Percorre todos os cookies
                if ("usuarioId".equals(cookie.getName())) {  // Verifica se existe um cookie chamado 'usuarioId'
                    usuarioLogado = true; // Define que o usuário está logado
                    String usuarioId = cookie.getValue(); // Obtém o valor do cookie (ID do usuário)

                    // Busca o nome do usuário no banco de dados usando o ID armazenado no cookie
                    nomeUsuario = usuarioService.buscarNomePorId(usuarioId);  // Método no serviço de usuário
                    break; // Interrompe o loop após encontrar o cookie correto
                }
            }
        }

        // Se o usuário não estiver logado, redireciona para a tela de login
        if (!usuarioLogado) {
            return new ModelAndView("redirect:/login");  // Redireciona para a página de login
        }

        // Caso o usuário esteja logado, renderiza a página home e passa o nome do usuário
        ModelAndView modelAndView = new ModelAndView("home"); // Define a página home
        modelAndView.addObject("username", nomeUsuario);  // Adiciona o nome do usuário ao modelo
        return modelAndView;  // Retorna a página home com o nome do usuário
    }
}
