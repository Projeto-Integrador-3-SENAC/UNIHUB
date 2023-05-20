<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0..1,0" />
    
    <link rel="stylesheet" href="./style/feed.css">
    <link rel="stylesheet" href="./style/modal.css">
    <script src="./js/feed.js" defer></script>
    <script src="./js/modal.js" defer></script>

    <link rel="shortcut icon" href="./img/favicon.ico" type="image/x-icon">
    <title>UNIHUB</title>
</head>
<body>
    <main>
        <!-- HEADER -->
        <header>
            <div class="logo">
                <img src="./img/logo-white.png" alt="">
            </div>
        </header>
        <!-- POST's -->
        <div class="container">
            <c:forEach items="${postagens}" var="postagem">
                <div class="post">
                    <div class="userPost">
                        <img src="./img/user.png">
                        <h6>Username</h6>
                    </div>
                    <div class="img">
                        <img src="./img/background-register.jpg"> <!-- Adicionar imagem -->
                    </div>
                    <div class="legend">
                        <textarea class="textLegend" disabled>
                            ${postagem.conteudo}
                        </textarea>
                        <button class="btnlegend">
                            <span class="material-symbols-outlined arrow" data-arrow="down-arrow">
                                arrow_drop_down
                            </span>
                        </button>
                    </div>
                    <div class="interactions">
                        <button class="btnFavorite">
                            <span class="icon material-symbols-outlined favorite" data-like="dislike">favorite</span>
                        </button>
                        <button class="btnComment">
                            <span class="icon material-symbols-outlined comment" data-comment="closed">Comment</span>
                        </button>
                        <button class="btnShare">
                            <span class="icon material-symbols-outlined share" data-share="closed">share</span>
                        </button>
                    </div>
                        ${postagem.foto}
                </div>
            </c:forEach>

        <!-- BTN ADD POST -->
        <nav>
            <span class="icon material-symbols-outlined">
                add_box
            </span>
        </nav>
    </main>
    <aside>
        <section class="profile">
            <div class="user">
                <img id="imageProfile" src="./img/user.png"> <!-- Adicionar foto usuario -->
            </div>
            <div class="config">
                <span class="icon material-symbols-outlined">
                    settings
                </span>
                <a class="exit" href="login?acao=Deslogar">
                    <span class="icon material-symbols-outlined">
                        logout
                    </span>
                </a>
            </div>
        </section>
        <section class="container">
            <div class="search">
                <input type="search" name="" id="">
                <span class="icon material-symbols-outlined">
                    search
                </span>
            </div>
            <div class="friends">
                <c:forEach items="${contas}" var="conta">
                <div class="friend">
                    <img src="./img/user.png"> <!-- Adicionar foto friend -->
                    <h4>${conta.nome}</h4>
                </div>
                </c:forEach>
            </div>
        </section>
    </aside>
    <div id="modal-overlay">
        <div id="modal" class="modal-content">
            <h2>Postagem</h2>
            <form action="/foto" method="post" enctype="multipart/form-data">

                <div>
                    <textarea id="caption-input" name="legenda" placeholder="Digite a legenda" required></textarea>
                </div>
                <div>
                    <label for="file">Escolher imagem</label>
                    <input type="file" name="file" id="file">
                </div>
               <div class="btnModal">
                   <button id="btnCancelar">Cancelar</button>
                   <button type="submit">Enviar</button>
               </div>
            </form>
        </div>
    </div>
</body>
</html>