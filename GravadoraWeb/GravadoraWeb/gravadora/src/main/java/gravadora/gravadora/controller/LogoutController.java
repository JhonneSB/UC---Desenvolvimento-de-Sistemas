package gravadora.gravadora.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import gravadora.gravadora.service.CookieService;

/**
 * Controlador responsável pelo logout dos usuários.
 */
@Controller // Indica que essa classe é um controlador Spring MVC
public class LogoutController {

    /**
     * Método para realizar o logout do usuário.
     * Remove o cookie de autenticação e redireciona para a página de login.
     *
     * @param response Objeto HttpServletResponse usado para manipular os cookies.
     * @return Redireciona para a página de login após remover o cookie.
     */
    @GetMapping("/logout") // Mapeia a requisição GET para "/logout"
    public String logout(HttpServletResponse response) {
        // Log para depuração no console
        System.out.println("Removendo cookie de autenticação...");

        // Remove o cookie "usuarioId" que identifica o usuário logado
        CookieService.removeCookie(response, "usuarioId");

        // Redireciona o usuário para a tela de login após o logout
        return "redirect:/login";
    }
}
