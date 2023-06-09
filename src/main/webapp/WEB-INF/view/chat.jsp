<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0..1,0" />

    <link rel="stylesheet" href="../../style/root.css">
    <link rel="stylesheet" href="../../style/chat.css">

    <script src="../../js/theme.js" defer></script>
    <script src="../../js/chat.js" defer></script>

    <link rel="shortcut icon" href="../../img/system/favicon.ico" type="image/x-icon">
    <title>UNIHUB</title>
</head>

<body>
    <span id="btnTheme" data-theme="dark" class="icon material-symbols-outlined" style="display: none;">
        dark_mode
    </span>

    <input type="text" style="display: none;" value="${id_conta_origem}" id="id_origem">
    <input type="text" style="display: none;" value="${id_conta_destino}" id="id_destino">

    <div class="conteinerChatDisplay">
        <div class="infoFriend">
            <img src="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}/${contaDestino.foto}">
            <h4>${contaDestino.nome}</h4>
        </div>
        <div id="chat-display" data-parametro1="${pageContext.request.scheme}" data-parametro2="${pageContext.request.serverName}" data-parametro3="${pageContext.request.serverPort}" data-origem="${contaOrigem.foto}" data-destino="${contaDestino.foto}">
            <c:forEach items="${mensagens}" var="mensagem">
                <c:choose>
                    <c:when test="${mensagem.conta_remente_id == id_conta_origem}">
                        <p class="message sender">
                            ${mensagem.conteudo}
                            <img class='photoSender' src="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}/${contaOrigem.foto}">
                        </p>
                    </c:when>
                    <c:otherwise>
                        <p class="message recipient">
                            <img class='photoRecipient' src="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}/${contaDestino.foto}">
                            ${mensagem.conteudo}
                        </p>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>

        <div class="interactionSend">
            <input type="text" maxlength="400" placeholder="Enviar mensagem" id="message-input" oninput="updateCharCount()" required>
            <span id="char-count"><b>400</b> caracteres restantes</span>
            <button onclick="sendMessage()" class="material-symbols-outlined">
                send
            </button>
        </div>
    </div>
</body>
</html>