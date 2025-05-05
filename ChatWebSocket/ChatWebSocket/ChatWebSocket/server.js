// Implementação da lógica do server

//------------------------------------------------------------------------------------------------
// Importação de frameworks e bibliotecas
//------------------------------------------------------------------------------------------------
//

// Importa o framework Express, para a criação de servidores web com Node.js.
// Com o Express, cria-se rotas HTTP, para lidar com requisições e respostas de forma simplificada.
const express = require('express');

// Importa o módulo nativo HTTP do Node.js.
const http = require('http');

// Importa a biblioteca ws, que implementa o protocolo WebSocket para comunicação bidirecional em tempo real.
// Utilizado para criar e manipular servidores HTTP diretamente (como o servidor que servirá as conexões WebSocket).
const WebSocket = require('ws');

// Cria uma instância do Express, que é a aplicação web propriamente dita.
// A variável app será usada para definir rotas HTTP, middlewares e configurações do servidor.
const app = express();

// Cria um servidor HTTP usando o módulo nativo do Node.js.
// Necessário para integrar o servidor HTTP com WebSockets, pois, a biblioteca ws precisa de um servidor HTTP para funcionar.
const server = http.createServer(app);

// Criando um servidor WebSocket
// Passa o servidor HTTP criado anteriormente como base para o WebSocket. 
// Tanto as requisições HTTP quanto as conexões WebSocket vão ser tratadas pelo mesmo servidor.
const wss = new WebSocket.Server({ server });

// Cria um objeto para armazenar os clientes conectados
let clients = {};

// Quando um cliente se conectar
wss.on('connection', (ws, req) => {
    console.log('Novo cliente conectado');

    // Captura o IP do cliente a partir do cabeçalho do pedido
    const clientIp = req.socket.remoteAddress;

    const clientIpv4 = clientIp.includes('::ffff:') ? clientIp.split('::ffff:')[1] : clientIp;

    // Receber mensagens do cliente
    // O servidor está aguardando receber uma mensagem de um cliente conectado
    // via WebSocket. Quando um cliente envia uma mensagem, o evento 'message' é disparado,
    // e a função de callback (ou ouvinte de evento) é chamada com a mensagem recebida como argumento.

    // A mensagem recebida é armazenada na variável message, que geralmente é uma string JSON.

    ws.on('message', (message) => {
        // A mensagem que o servidor recebe é uma string JSON. O método JSON.parse(message)
        // converte essa string JSON em um objeto JavaScript. Esse objeto é armazenado na variável data.
        const data = JSON.parse(message);
        
        // O código verifica o tipo de mensagem recebida, que é determinado pela propriedade type
        // do objeto data. Existem duas possíveis verificações neste caso.
        if (data.type === 'login') {
            // Armazena o nome de usuário e o IP
            // Aqui, o servidor armazena o WebSocket (ws) e o IP do cliente (clientIp) no objeto clients,
            // usando o nome de usuário (data.username) como chave. Isso permite identificar rapidamente
            // um cliente conectado.
            clients[data.username] = { ws, ip: clientIpv4 };
            console.log(`Usuário ${data.username} conectado de IP: ${clientIpv4}`);
            broadcastClients();
        }
        
        if (data.type === 'message') {
            // Quando o tipo da mensagem é 'message', o código entra no segundo bloco de if e verifica
            // para quem a mensagem deve ser enviada. A mensagem contém duas informações importantes:
            // data.from: O nome do remetente.
            // data.to: O destinatário (pode ser um nome de usuário ou "Todos")
            if (data.to === 'Todos') {
                broadcastMessage(data.from, data.message);
            } else {
                // Se o destinatário (recipient) for encontrado, a mensagem é enviada via WebSocket para esse cliente:
                const recipient = clients[data.to];
                // Aqui, o servidor envia uma mensagem para o cliente específico usando o método send do WebSocket (recipient.ws.send).
                // A mensagem é uma string JSON que contém o remetente (data.from) e o conteúdo da mensagem (data.message). 
                // A linha console.log exibe no servidor o envio da mensagem para o destinatário.
                if (recipient) {
                    recipient.ws.send(JSON.stringify({ from: data.from, message: data.message }));
                    console.log(`Mensagem de ${data.from} para ${data.to}: ${data.message}`);
                }
            }
        }
    });

// Escuta o evento de fechamento da conexão WebSocket.
ws.on('close', () => {
    for (let user in clients) {
        if (clients[user].ws === ws) {
            console.log(`Usuário ${user} desconectado`);
            delete clients[user];
            broadcastClients();
            break;
        }
    }
});
});

// Função para propagar a lista de clientes logados
function broadcastClients() {
    // Cria a lista de usuários com nome e IP
    const userDetails = Object.keys(clients).map((username) => {
        return { username: username, ip: clients[username].ip };
    });

    console.log('Usuários online:', userDetails);
    
    wss.clients.forEach((client) => {
        if (client.readyState === WebSocket.OPEN) {
            client.send(JSON.stringify({ type: 'updateUsers', users: userDetails }));
        }
    });
}

// Função para propagar a mensagem para todos os clientes
function broadcastMessage(from, message) {
    // Itera por cada cliente conectado.
    wss.clients.forEach((client) => {
        // Verifica o estado da conexão do WebSocket.
        // WebSocket.OPEN: Estado que indica que a conexão está aberta e pronta para comunicação.
        if (client.readyState === WebSocket.OPEN) {

            // Converte o objeto { from, message } para JSON, para facilitar o envio e tratamento no frontend.
            client.send(JSON.stringify({ from, message }));
        }
    });
    console.log(`Mensagem global de ${from}: ${message}`);
}	



// Configura o Express para servir arquivos estáticos, como HTML, CSS, JavaScript, imagens ou áudios,
// a partir de uma pasta específica (neste caso, a pasta public).
app.use(express.static(__dirname + '/public'));

// Iniciando o servidor WebSocket na porta 3000
server.listen(3000, () => {
    console.log('Servidor WebSocket rodando na porta 3000');
});
