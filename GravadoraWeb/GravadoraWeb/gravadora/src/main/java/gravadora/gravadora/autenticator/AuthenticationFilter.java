package gravadora.gravadora.autenticator;

import gravadora.gravadora.service.CookieService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Filtro de autenticação que verifica se o usuário está logado antes de acessar qualquer rota.
 */
@WebFilter("/*")  // Aplica o filtro em todas as rotas do sistema
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Método opcional para inicialização do filtro (caso precise)
    }

    @Override
    public void doFilter(jakarta.servlet.ServletRequest request, jakarta.servlet.ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Faz o cast para HttpServletRequest e HttpServletResponse, pois o filtro recebe interfaces genéricas
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Obtém a URI da requisição (o caminho da página que o usuário está tentando acessar)
        String uri = httpRequest.getRequestURI();

        // Se a URL NÃO for a de login ou registro, verifica a autenticação
        if (!uri.contains("/login") && !uri.contains("/registrar")) {
            try {
                // Obtém o valor do cookie "usuarioId" (que identifica o usuário logado)
                String usuarioId = CookieService.getCookie(httpRequest, "usuarioId");

                // Se o cookie não existir, o usuário não está autenticado e é redirecionado para o login
                if (usuarioId == null) {
                    System.out.println("Cookie 'usuarioId' não encontrado. Redirecionando para login...");
                    httpResponse.sendRedirect("/login");
                    return; // Interrompe a requisição para evitar que o usuário continue sem autenticação
                }
            } catch (Exception e) {
                e.printStackTrace(); // Exibe o erro no console para depuração
                httpResponse.sendRedirect("/login"); // Em caso de erro, também redireciona para o login
                return;
            }
        }

        // Se o usuário estiver autenticado ou se estiver acessando /login ou /registrar, permite continuar
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Método opcional para liberar recursos do filtro ao desligar o servidor (se necessário)
    }
}
