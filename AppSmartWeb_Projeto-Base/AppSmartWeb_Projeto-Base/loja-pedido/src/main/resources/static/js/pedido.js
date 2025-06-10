// Função para renderizar os blocos conforme o tipo de pedido selecionado
function renderBlocos() {
    const tipo = document.getElementById("tipoPedido").value;
    const container = document.getElementById("blocosContainer");
    container.innerHTML = "";

    let blocos = tipo === "simples" ? 1 : tipo === "duplo" ? 2 : 3;

    for (let b = 0; b < blocos; b++) {
        const nBloco = b + 1;
        const blocoDiv = document.createElement("div");
        const tipo = document.getElementById("tipoPedido").value;
        blocoDiv.classList.add("bloco");
        blocoDiv.id = "bloco-container-" + nBloco;
        blocoDiv.innerHTML = `
        <h2>Bloco ${b + 1}</h2>
    
        <div class="pedido-view" id="pedido-view${nBloco}" data-is-spun="false">
            <!-- Container que vai girar - apenas imagens -->
            <div class="images-container" id="images-container-${nBloco}">
                <div class="bloco-spin-container">
                    <img class="imagem bloco-img" id="bloco-${nBloco}" src="assets/bloco/rblocoCor0.png" alt="Bloco">
                </div>
                <img class="imagem" id="lamina${nBloco}-3" src="#" alt="Lâmina 3">
                <img class="imagem" id="lamina${nBloco}-1" src="#" alt="Lâmina 1">
                <img class="imagem" id="lamina${nBloco}-2" src="#" alt="Lâmina 2">
                <img class="imagem" id="padrao${nBloco}-1" src="#" alt="Padrão 1">
                <img class="imagem" id="padrao${nBloco}-2" src="#" alt="Padrão 2">
                <img class="imagem" id="padrao${nBloco}-3" src="#" alt="Padrão 3">
            </div>
            <button id="spin${nBloco}" class="spin" onclick="spin(${nBloco})">
                <span class="material-symbols-rounded">sync</span>
            </button>
        </div>
        
            
            <div class="input-box">
                <label for="block-color-${nBloco}">Cor do Bloco:</label>
                <select name="block-color-${nBloco}" id="block-color-${nBloco}" onchange="changePedidoView(${nBloco})">
                    <option value="">Nenhum</option>
                    <option value="preto">Preto</option>
                    <option value="vermelho">Vermelho</option>
                    <option value="azul">Azul</option>
                </select>
            </div>
            <div class="input-combo">
                <div class="input-box">
                    <label for="l1-color-${nBloco}">Cor Lâmina 1</label>
                    <select name="l1-color-${nBloco}" id="l1-color-${nBloco}" disabled onchange="changePedidoView(${nBloco})">
                        <option value="" hidden selected>Selecione</option>
                        <option value="1">Vermelho</option>
                        <option value="2">Azul</option>
                        <option value="3">Amarelo</option>
                        <option value="4">Verde</option>
                        <option value="5">Preto</option>
                        <option value="6">Branco</option>
                    </select>
                </div>
                <div class="input-box">
                    <label for="l1-pattern-${nBloco}">Padrão Lâmina 1</label>
                    <select name="l1-pattern-${nBloco}" id="l1-pattern-${nBloco}" disabled onchange="changePedidoView(${nBloco})">
                        <option value="" hidden selected>Selecione</option>
                        <option value="">Nenhum</option>
                        <option value="1">Casa</option>
                        <option value="2">Navio</option>
                        <option value="3">Estrela</option>
                    </select>
                </div>
            </div>
            <div class="input-combo">
                <div class="input-box">
                    <label for="l2-color-${nBloco}">Cor Lâmina 2</label>
                    <select name="l2-color-${nBloco}" id="l2-color-${nBloco}" disabled onchange="changePedidoView(${nBloco})">
                        <option value="" hidden selected>Selecione</option>
                        <option value="1">Vermelho</option>
                        <option value="2">Azul</option>
                        <option value="3">Amarelo</option>
                        <option value="4">Verde</option>
                        <option value="5">Preto</option>
                        <option value="6">Branco</option>
                    </select>
                </div>
                <div class="input-box">
                    <label for="l2-pattern-${nBloco}">Padrão Lâmina 2</label>
                    <select name="l2-pattern-${nBloco}" id="l2-pattern-${nBloco}" disabled onchange="changePedidoView(${nBloco})">
                        <option value="" hidden selected>Selecione</option>
                        <option value="">Nenhum</option>
                        <option value="1">Casa</option>
                        <option value="2">Navio</option>
                        <option value="3">Estrela</option>
                    </select>
                </div>
            </div>
            <div class="input-combo">
                <div class="input-box">
                    <label for="l3-color-${nBloco}">Cor Lâmina 3</label>
                    <select name="l3-color-${nBloco}" id="l3-color-${nBloco}" disabled onchange="changePedidoView(${nBloco})">
                        <option value="" hidden selected>Selecione</option>
                        <option value="1">Vermelho</option>
                        <option value="2">Azul</option>
                        <option value="3">Amarelo</option>
                        <option value="4">Verde</option>
                        <option value="5">Preto</option>
                        <option value="6">Branco</option>
                    </select>
                </div>
                <div class="input-box">
                    <label for="l3-pattern-${nBloco}">Padrão Lâmina 3</label>
                    <select name="l3-pattern-${nBloco}" id="l3-pattern-${nBloco}" disabled onchange="changePedidoView(${nBloco})">
                        <option value="" hidden selected>Selecione</option>
                        <option value="">Nenhum</option>
                        <option value="1">Casa</option>
                        <option value="2">Navio</option>
                        <option value="3">Estrela</option>
                    </select>
                </div>
            </div>
        `;
        

        container.appendChild(blocoDiv);
    }
}

// Atualiza a visualização do pedido
function changePedidoView(id, lamina) {
    const blockColor = document.getElementById("block-color-" + id).value;

    if (blockColor !== "") {
        const idCor = (blockColor === "preto" ? 1 : blockColor === "vermelho" ? 2 : 3);
        document.getElementById("bloco-" + id).src = "assets/bloco/rBlocoCor" + idCor + ".png";

        // Habilita os selects de cor
        ["l1-color-", "l2-color-", "l3-color-"].forEach(prefix => {
            document.getElementById(prefix + id).disabled = false;
        });

        const l1Color = document.getElementById("l1-color-" + id).value;
        const l2Color = document.getElementById("l2-color-" + id).value;
        const l3Color = document.getElementById("l3-color-" + id).value;

        const l1Pattern = document.getElementById("l1-pattern-" + id).value;
        const l2Pattern = document.getElementById("l2-pattern-" + id).value;
        const l3Pattern = document.getElementById("l3-pattern-" + id).value;

        const view = document.getElementById("pedido-view" + id);
        const isSpun = view.dataset.isSpun === "true";

        // Atualiza as lâminas e padrões dependendo do giro
        if (isSpun) {
            document.getElementById("lamina" + id + "-3").src = l1Color ? "assets/laminas/lamina3-" + l1Color + ".png" : "#";
            document.getElementById("lamina" + id + "-1").src = l3Color ? "assets/laminas/lamina1-" + l3Color + ".png" : "#";

            const padrao3 = document.getElementById("padrao" + id + "-3");
            const padrao1 = document.getElementById("padrao" + id + "-1");

            padrao3.src = l3Pattern ? "assets/padroes/padrao" + l3Pattern + "-1.png" : "#";
            padrao3.hidden = false;
            padrao1.src = l1Pattern ? "assets/padroes/padrao" + l1Pattern + "-1.png" : "#";
            padrao1.hidden = true;
        } else {
            document.getElementById("lamina" + id + "-1").src = l1Color ? "assets/laminas/lamina1-" + l1Color + ".png" : "#";
            document.getElementById("lamina" + id + "-3").src = l3Color ? "assets/laminas/lamina3-" + l3Color + ".png" : "#";

            const padrao1 = document.getElementById("padrao" + id + "-1");
            const padrao3 = document.getElementById("padrao" + id + "-3");

            padrao1.src = l1Pattern ? "assets/padroes/padrao" + l1Pattern + "-1.png" : "#";
            padrao1.hidden = false;
            padrao3.src = l3Pattern ? "assets/padroes/padrao" + l3Pattern + "-1.png" : "#";
            padrao3.hidden = true;
        }

        // Atualiza lâmina/padrão do meio
        document.getElementById("lamina" + id + "-2").src = l2Color ? "assets/laminas/lamina2-" + l2Color + ".png" : "#";
        document.getElementById("padrao" + id + "-2").src = l2Pattern ? "assets/padroes/padrao" + l2Pattern + "-2.png" : "#";

        // Habilita os padrões caso uma cor esteja selecionada
        ["l1-pattern-", "l2-pattern-", "l3-pattern-"].forEach((prefix, index) => {
            const color = [l1Color, l2Color, l3Color][index];
            document.getElementById(prefix + id).disabled = !color;
        });

    } else {
        // Desabilita todos os campos e limpa imagens
        ["l1-color-", "l2-color-", "l3-color-", "l1-pattern-", "l2-pattern-", "l3-pattern-"].forEach(prefix => {
            document.getElementById(prefix + id).disabled = true;
        });

        document.getElementById("bloco-" + id).src = "assets/bloco/rBlocoCor0.png";

        ["lamina", "padrao"].forEach(prefix => {
            for (let i = 1; i <= 3; i++) {
                document.getElementById(prefix + id + "-" + i).src = "#";
            }
        });
    }
}

// Gira apenas a imagem do bloco
function spin(id) {
    const blocoImg = document.getElementById("bloco-" + id);
    const view = document.getElementById("pedido-view" + id);
    const isSpun = view.dataset.isSpun !== "true";
    view.dataset.isSpun = isSpun;
    
    // Adiciona a classe de animação ao bloco
    blocoImg.classList.add("spin-animation");
    
    // Remove a classe após a animação terminar
    blocoImg.addEventListener("animationend", function() {
        blocoImg.classList.remove("spin-animation");
    }, {once: true});
    
    // Atualiza as imagens após a animação
    setTimeout(() => {
        const lamina1 = document.getElementById("lamina" + id + "-1");
        const lamina3 = document.getElementById("lamina" + id + "-3");

        const src1 = lamina1.src;
        const src3 = lamina3.src;

        const newSrc3 = src1.replace(/lamina\d-(\d)/, `lamina3-$1`);
        const newSrc1 = src3.replace(/lamina\d-(\d)/, `lamina1-$1`);

        document.getElementById("padrao" + id + "-3").hidden = !isSpun;
        document.getElementById("padrao" + id + "-1").hidden = isSpun;

        lamina1.src = newSrc1;
        lamina3.src = newSrc3;
    }, 500); // Tempo deve coincidir com a duração da animação
}

// Envia pedido para a base de dados
function enviarPedido() {
    const tipo = document.getElementById("tipoPedido").value;
    const blocos = document.querySelectorAll(".bloco");

    const pedido = {
        tipo: tipo,
        blocos: []
    };

    blocos.forEach((bloco, index) => {
        const numBloco = index + 1;

        // Captura a cor do bloco usando o ID corretamente
        const corBloco = document.getElementById("block-color-" + numBloco).value;

        const laminas = [];

        // Captura as cores e padrões das lâminas
        const cores = [
            document.getElementById("l1-color-" + numBloco),
            document.getElementById("l2-color-" + numBloco),
            document.getElementById("l3-color-" + numBloco)
        ];

        const padroes = [
            document.getElementById("l1-pattern-" + numBloco),
            document.getElementById("l2-pattern-" + numBloco),
            document.getElementById("l3-pattern-" + numBloco)
        ];

        for (let i = 0; i < 3; i++) {
            laminas.push({
                cor: cores[i].value,
                padrao: padroes[i].value
            });
        }

        pedido.blocos.push({
            cor: corBloco,
            laminas: laminas
        });
    });

    console.log("Pedido:", JSON.stringify(pedido, null, 2));

    fetch("/store/orders", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify([pedido])
    }).then(res => {
        if (res.ok) {
            alert("Pedido enviado com sucesso!");
            listarPedidos();
        } else {
            alert("Erro ao enviar pedido.");
        }
    });
}

// Inicializa a página
window.onload = renderBlocos;