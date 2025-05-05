package gravadora.gravadora.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import gravadora.gravadora.model.Usuario;
import gravadora.gravadora.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Controlador responsável pelo processo de recuperação de senha.
 */
@Controller
public class RecuperacaoSenhaController {

    @Autowired
    private UsuarioRepository usuarioRepository; // Repositório para acessar os usuários no banco de dados

    /**
     * Exibe a página onde o usuário informa o e-mail para recuperação de senha.
     * @return Página "esqueci_senha".
     */
    @GetMapping("/esqueci-senha")
    public String exibirTelaEsqueciSenha() {
        return "esqueci_senha"; // Retorna a página onde o usuário informa o e-mail
    }

    /**
     * Processa o formulário de recuperação de senha.
     * Se o e-mail existir no banco de dados, redireciona para a tela de troca de senha.
     * Caso contrário, exibe uma mensagem de erro.
     * 
     * @param email E-mail informado pelo usuário.
     * @param model Objeto Model para enviar mensagens para a view.
     * @param request HttpServletRequest para armazenar o e-mail na sessão.
     * @return Redireciona para a página de troca de senha ou exibe erro.
     */
    @PostMapping("/esqueci-senha")
    public String processarEsqueciSenha(String email, Model model, HttpServletRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(email); // Busca o usuário pelo e-mail
        
        if (usuario != null) {
            request.getSession().setAttribute("emailRecuperacao", email); // Armazena o e-mail na sessão
            return "redirect:/trocar-senha"; // Redireciona para a tela de troca de senha
        }
        
        model.addAttribute("Erro", "E-mail não encontrado!"); // Exibe erro se o e-mail não for encontrado
        return "esqueci_senha"; // Retorna para a mesma página com a mensagem de erro
    }

    /**
     * Exibe a tela de troca de senha.
     * Se o e-mail de recuperação não estiver armazenado na sessão, redireciona o usuário para a página de recuperação.
     * 
     * @param request HttpServletRequest para verificar se há um e-mail armazenado na sessão.
     * @param model Objeto Model para enviar dados para a view.
     * @return Página de troca de senha ou redirecionamento para "esqueci_senha".
     */
    @GetMapping("/trocar-senha")
    public String exibirTelaTrocaSenha(HttpServletRequest request, Model model) {
        String email = (String) request.getSession().getAttribute("emailRecuperacao"); // Recupera o e-mail armazenado
        
        if (email == null) {
            return "redirect:/esqueci-senha"; // Se não houver e-mail armazenado, redireciona
        }
        
        model.addAttribute("email", email); // Passa o e-mail para a página
        return "trocar_senha"; // Retorna a página de troca de senha
    }

    /**
     * Processa a troca de senha.
     * Verifica se as senhas coincidem e se atendem aos requisitos mínimos antes de atualizar no banco de dados.
     * 
     * @param novaSenha Nova senha informada pelo usuário.
     * @param confirmarSenha Confirmação da nova senha.
     * @param model Objeto Model para exibir mensagens na view.
     * @param request HttpServletRequest para recuperar o e-mail armazenado na sessão.
     * @return Redireciona para login após sucesso ou retorna para a página de troca de senha com erro.
     */
    @PostMapping("/trocar-senha")
    public String trocarSenha(@RequestParam String novaSenha, 
                              @RequestParam String confirmarSenha, 
                              Model model, HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("emailRecuperacao"); // Recupera o e-mail armazenado
        
        if (email != null) {
            Usuario usuario = usuarioRepository.findByEmail(email); // Busca o usuário pelo e-mail
            if (usuario != null) {
                // Verifica se as senhas coincidem
                if (!novaSenha.equals(confirmarSenha)) {
                    model.addAttribute("Erro", "As senhas não coincidem.");
                    return "trocar_senha"; // Retorna para a tela com erro
                }

                // Verifica se a senha tem pelo menos 6 caracteres
                if (novaSenha.length() < 6) {
                    model.addAttribute("Erro", "A nova senha deve ter no mínimo 6 caracteres.");
                    return "trocar_senha"; // Retorna para a tela com erro
                }

                // Atualiza a senha do usuário no banco de dados
                usuario.setSenha(novaSenha);
                usuarioRepository.save(usuario);
                request.getSession().removeAttribute("emailRecuperacao"); // Remove a sessão

                return "redirect:/login"; // Redireciona para login após troca bem-sucedida
            }
        }

        return "redirect:/esqueci-senha"; // Se houver erro, volta para a tela inicial de recuperação
    }
}
