import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CurriculoBuilder builder = new CurriculoBuilder();

        System.out.print("Digite seu nome (ou pressione Enter para pular): ");
        String nome = scanner.nextLine();
        if (!nome.isEmpty()) {
            builder.setNome(nome);
        }

        System.out.print("Digite sua profissão (ou pressione Enter para pular): ");
        String profissao = scanner.nextLine();
        if (!profissao.isEmpty()) {
            builder.setProfissao(profissao);
        }

        System.out.print("Descreva sua experiência (ou pressione Enter para pular): ");
        String experiencia = scanner.nextLine();
        if (!experiencia.isEmpty()) {
            builder.setExperiencia(experiencia);
        }

        System.out.print("Liste suas habilidades (separadas por vírgula) (ou pressione Enter para pular): ");
        String habilidades = scanner.nextLine();
        if (!habilidades.isEmpty()) {
            builder.setHabilidades(habilidades);
        }

        Curriculo curriculo = builder.build();

        System.out.println(curriculo);  // Mostra o currículo completo

        scanner.close();
    }
}