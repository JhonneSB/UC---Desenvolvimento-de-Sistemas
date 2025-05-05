package com.example.appteste.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Corrigido para o Model do Spring MVC
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.appteste.ApptesteApplication;
import com.example.appteste.model.Produto;

@Controller
public class ProdutoController {

    private final ApptesteApplication apptesteApplication;

    private List<Produto> produtos = new ArrayList<>();

    
    @GetMapping("/")
    public String exibirFormulario(Model model) {
        model.addAttribute("produto", new Produto());
        return "form";
    }

    @PostMapping("/excluir")
public String excluirProduto(@ModelAttribute Produto produto, Model model) {
    List<Produto> produtosAtualizados = new ArrayList<>();

    // Ler o arquivo e adicionar os produtos que NÃO serão excluídos na nova lista
    try (BufferedReader br = new BufferedReader(new FileReader("produtos.txt"))) {
        String linha;
        while ((linha = br.readLine()) != null) {
            String[] dados = linha.split(";");
            if (dados.length == 4) {
                String codigo = dados[0].trim();
                
                // Se o código for diferente do que queremos excluir, mantemos na lista
                if (!codigo.equals(produto.getCodigo())) {
                    int quantidade = Integer.parseInt(dados[2].trim());
                    double preco = Double.parseDouble(dados[3].trim());

                    Produto p = new Produto();
                    p.setCodigo(codigo);
                    p.setDescricao(dados[1].trim());
                    p.setQtd(quantidade);
                    p.setPreco(preco);

                    produtosAtualizados.add(p);
                }
            }
        }
    } catch (Exception e) {
        System.err.println("Erro ao ler o arquivo: " + e.getMessage());
    }

    // Reescrever o arquivo com os produtos restantes
    try (PrintWriter pw = new PrintWriter(new FileWriter("produtos.txt", false))) { 
        for (Produto p : produtosAtualizados) {
            pw.println(p.getCodigo() + ";" + p.getDescricao() + ";" + p.getQtd() + ";" + p.getPreco());
        }
    } catch (Exception e) {
        System.err.println("Erro ao atualizar o arquivo: " + e.getMessage());
    }

    // Atualiza a lista e exibe novamente a tabela
    model.addAttribute("produtos", produtosAtualizados);
    return "redirect:/tabela";
}

    @PostMapping("/adicionar")
    public String adicionarProduto(@ModelAttribute Produto produto, Model model) {
        //produtos.clear();
        produtos.add(produto);

         // **Salvar no arquivo**
          try (BufferedWriter bw = new BufferedWriter(new FileWriter("produtos.txt", true))) { 
        // `true` para adicionar sem apagar os registros anteriores
        bw.write(produto.getCodigo() + ";" + produto.getDescricao() + ";" + produto.getQtd() + ";" + produto.getPreco());
        bw.newLine(); // Quebra de linha para o próximo registro
    } catch (IOException e) {
        System.err.println("Erro ao salvar o produto no arquivo: " + e.getMessage());
    }

    return "redirect:/tabela"; // Retorna para a página da tabela
}
    ProdutoController(ApptesteApplication apptesteApplication) {
        this.apptesteApplication = apptesteApplication;
    }

    @GetMapping("/tabela")
    public String MostrarProduto(Model model) {
        produtos.clear();
        try(BufferedReader br = new BufferedReader(new FileReader("produtos.txt"))){
            String linha;
            while ((linha = br.readLine()) != null){
                String[] dados = linha.split(";");

                if(dados.length == 4){
                    try {
                        //parse dos valores
                        String codigo = dados[0].trim();
                        String desc = dados[1].trim();
                        int quantidade = Integer.parseInt(dados[2].trim());
                        double preco = Double.parseDouble(dados[3].trim());

                        // Criação do produto
                        Produto produto = new Produto();

                        produto.setCodigo(codigo);
                        produto.setDescricao(desc);
                        produto.setQtd(quantidade);
                        produto.setPreco(preco);

                        produtos.add(produto);
                        
                    } catch (NumberFormatException e) {
                        System.err.println("Erro de processamento...Lnha:"+linha+" - " + e.getMessage());
                        // TODO: handle exception
                    }
                }
            }
            
        }
        
            catch(Exception e){
                System.err.println("Erro de leitura de arquivos: " + e.getMessage());
            }
            model.addAttribute("produtos", produtos);

            return "tabela";

    }
}
