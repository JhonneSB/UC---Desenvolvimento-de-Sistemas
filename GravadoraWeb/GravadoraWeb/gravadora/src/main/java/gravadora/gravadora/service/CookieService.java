package gravadora.gravadora.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Optional;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

/**
 * Serviço para manipulação de cookies no Spring Boot.
 * Permite definir, recuperar e remover cookies nas requisições HTTP.
 */
@Service
public class CookieService {
    
    /**
     * Cria e adiciona um cookie à resposta HTTP.
     * 
     * @param response Objeto HttpServletResponse usado para adicionar o cookie.
     * @param key Nome do cookie.
     * @param valor Valor do cookie, codificado em UTF-8 para evitar problemas com caracteres especiais.
     * @param segundos Tempo de vida do cookie em segundos.
     * @throws UnsupportedEncodingException Caso ocorra erro na codificação do valor.
     */
    public static void setCookie(HttpServletResponse response, String key, String valor, int segundos) throws UnsupportedEncodingException {
        Cookie cookie = new Cookie(key, URLEncoder.encode(valor, "UTF-8")); // Codifica o valor do cookie para UTF-8
        cookie.setMaxAge(segundos); // Define o tempo de vida do cookie
        response.addCookie(cookie); // Adiciona o cookie à resposta HTTP
    }

    /**
     * Obtém o valor de um cookie da requisição HTTP.
     * 
     * @param request Objeto HttpServletRequest contendo os cookies da requisição.
     * @param key Nome do cookie a ser buscado.
     * @return O valor do cookie, decodificado em UTF-8, ou null se o cookie não existir.
     * @throws UnsupportedEncodingException Caso ocorra erro na decodificação do valor.
     */
    public static String getCookie(HttpServletRequest request, String key) throws UnsupportedEncodingException {
        String valor = Optional.ofNullable(request.getCookies()) // Verifica se há cookies na requisição
            .flatMap(cookies -> Arrays.stream(cookies)  // Converte o array de cookies para stream
            .filter(cookie -> key.equals(cookie.getName())) // Filtra pelo nome do cookie desejado
            .findAny()) // Retorna o primeiro encontrado, se existir
            .map(Cookie::getValue) // Obtém o valor do cookie
            .orElse(null); // Caso não encontre, retorna null

        return (valor != null) ? URLDecoder.decode(valor, "UTF-8") : null; // Decodifica o valor antes de retornar
    }

    /**
     * Remove um cookie da resposta HTTP, definindo seu tempo de vida como 0.
     * 
     * @param response Objeto HttpServletResponse usado para modificar os cookies.
     * @param key Nome do cookie a ser removido.
     */
    public static void removeCookie(HttpServletResponse response, String key) {
        Cookie cookie = new Cookie(key, null);  // Cria um cookie com o mesmo nome, mas valor null
        cookie.setMaxAge(0); // Define a idade do cookie para 0, removendo-o imediatamente
        cookie.setPath("/"); // Define o caminho como raiz para garantir remoção em todo o site
        response.addCookie(cookie);  // Adiciona o cookie "expirado" à resposta
    }
}
