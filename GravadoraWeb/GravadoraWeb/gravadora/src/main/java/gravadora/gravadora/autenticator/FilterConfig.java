package gravadora.gravadora.autenticator;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Classe de configuração para registrar o filtro de autenticação no Spring Boot.
 */
@Configuration // Indica que esta classe fornece configurações para a aplicação
public class FilterConfig {

    /**
     * Registra o filtro de autenticação para determinadas rotas da aplicação.
     *
     * @return Um objeto FilterRegistrationBean que configura e aplica o filtro AuthenticationFilter.
     */
    @Bean
    public FilterRegistrationBean<AuthenticationFilter> authenticationFilter() {
        // Cria um bean para registrar o filtro de autenticação
        FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();

        // Define o filtro que será usado
        registrationBean.setFilter(new AuthenticationFilter());

        // Especifica as URLs protegidas pelo filtro (usuário deve estar autenticado para acessá-las)
        registrationBean.addUrlPatterns(
            "/home", 
            "/artistas/cadastrar_artista", 
            "/albuns/cadastrar", 
            "/albuns", 
            "/artistas", 
            "/gravadoras", 
            "/gravadoras/cadastrar_gravadora"
        );

        return registrationBean; // Retorna a configuração do filtro para ser registrada no contexto do Spring Boot
    }
}
