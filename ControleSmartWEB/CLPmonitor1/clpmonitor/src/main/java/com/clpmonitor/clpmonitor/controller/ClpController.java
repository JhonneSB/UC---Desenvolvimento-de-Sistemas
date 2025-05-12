package com.clpmonitor.clpmonitor.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.clpmonitor.CLP.PlcConnector;
import com.clpmonitor.clpmonitor.model.Tag;
import com.clpmonitor.clpmonitor.model.TagWriteRequest;
import com.clpmonitor.clpmonitor.service.ClpSimulatorService;
import com.clpmonitor.clpmonitor.util.TagValueParser;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class ClpController {

    // Injeta automaticamente uma instância da classe ClpSimulatorService.
    // Essa classe é responsável por simular os dados dos CLPs e gerenciar
    // os eventos SSE que serão enviados ao frontend.
    @Autowired
    private ClpSimulatorService simulatorService;


    // Mapeia a URL raiz (http://localhost:8080/) para o método index().
    // Retorna a view index.html, localizada em src/main/resources/templates/index.html (Thymeleaf).
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("tag", new TagWriteRequest());
        return "index";
    }

    // Rota "/clp-data-stream" — Comunicação via SSE (Server-Sent Events)
    // Essa rota é chamada no JavaScript pelo EventSource:
    @GetMapping("/clp-data-stream")

    // Retorna um objeto SseEmitter, que é a classe do Spring para enviar
    // dados do servidor para o cliente continuamente usando Server-Sent Events.
    public SseEmitter streamClpData() {
        // Esse método delega a lógica para simulatorService.subscribe() que:
        //  Cria o SseEmitter.
        //  Armazena ele numa lista de ouvintes (clientes conectados).
        //  Inicia o envio periódico dos dados simulados
        return simulatorService.subscribe();
    }

 @PostMapping("/write-tag")
    public String writeTag(@ModelAttribute Tag tag, Model model) {
        try {
            PlcConnector plc = new PlcConnector(tag.getIp().trim(), tag.getPort());
            plc.connect();

            boolean success = false;

            switch (tag.getType().toUpperCase()) {
                case "STRING":
                    success = plc.writeString(tag.getDb(), tag.getOffset(), tag.getSize(), tag.getValue().trim());
                    break;
                case "BLOCK":
                    byte[] bytes = PlcConnector.hexStringToByteArray(tag.getValue().trim());
                    success = plc.writeBlock(tag.getDb(), tag.getOffset(), tag.getSize(), bytes);
                    break;
                case "FLOAT":
                    success = plc.writeFloat(tag.getDb(), tag.getOffset(), Float.parseFloat(tag.getValue().trim()));
                    break;
                case "INTEGER":
                    success = plc.writeInt(tag.getDb(), tag.getOffset(), Integer.parseInt(tag.getValue().trim()));
                    break;
                case "BYTE":
                    success = plc.writeByte(tag.getDb(), tag.getOffset(), Byte.parseByte(tag.getValue().trim()));
                    break;
                case "BIT":
                    if (tag.getBitNumber() == null) {
                        throw new IllegalArgumentException("Bit Number é obrigatório para tipo BIT");
                    }
                    success = plc.writeBit(tag.getDb(), tag.getOffset(), tag.getBitNumber(),
                            Boolean.parseBoolean(tag.getValue().trim()));
                    break;
                default:
                    throw new IllegalArgumentException("Tipo não suportado: " + tag.getType());
            }

            plc.disconnect();

            if (success) {
                model.addAttribute("mensagem", "Escrita no CLP realizada com sucesso!");
            } else {
                model.addAttribute("erro", "Erro de escrita no CLP!");
            }
        } catch (Exception ex) {
            model.addAttribute("erro", "Erro: " + ex.getMessage());
        }

        return "index"; // ou o nome da sua página que contém o fragmento
    }


    @GetMapping("/fragmento-formulario")
    public String carregarFragmentoFormulario(Model model) {
        model.addAttribute("tag", new TagWriteRequest()); // substitua pelo seu DTO real
        return "fragments/formulario :: clp-write-fragment";
    }

    @PostMapping("/atualizarSimulacao")
    public String atualizarGrids() {
        simulatorService.startSimulation();
        return "index";
    }
    @GetMapping("/editar_clp1")
    public String editarClp1(Model model) {
    return "editar_clp1"; // Retorna a tela que você criou acima
    }
    

    /*
     * Descrição do Funcionamento:
     * 
       1 - O usuário acessa http://localhost:8080/ → o método index() retorna o HTML.

       2 - O HTML carrega o JavaScript (scripts.js), que cria:

            const eventSource = new EventSource('/clp-data-stream');

       3 - O navegador faz uma requisição GET para /clp-data-stream.

       4 - O Spring chama simulatorService.subscribe() e devolve um SseEmitter ao navegador.

       5 - A cada X milissegundos, o ClpSimulatorService envia eventos como:

            clp1-data
            clp2-data
            clp3-data
            clp4-data

       6 - O JavaScript escuta cada evento separadamente e atualiza a interface conforme os dados recebidos.
     */
}