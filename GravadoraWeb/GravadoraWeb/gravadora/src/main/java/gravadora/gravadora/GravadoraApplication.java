package gravadora.gravadora;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal que inicializa a aplicação Spring Boot.
 */
@SpringBootApplication // Anotação que configura automaticamente a aplicação Spring Boot
public class GravadoraApplication {

    /**
     * Método principal (main) que inicia a execução da aplicação.
     * 
     * @param args Argumentos de linha de comando (se houver).
     */
    public static void main(String[] args) {
        SpringApplication.run(GravadoraApplication.class, args); // Inicia a aplicação Spring Boot
    }

}
