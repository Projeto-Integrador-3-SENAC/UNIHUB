<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="head.jsp" %>
<%@ include file="menu.jsp" %>
<div class="col-10">

<form class="mt-5" action="/admin?acao=EditarCurso" method="post">
    <div class="mb-3">
      <label for="nome" class="form-label"> Nome </label>
      <input type="text" class="form-control" id="nome" name="nome" value="${curso.nome}">
    </div>

    <div class="mb-3">
          <label for="tipo" class="form-label"> Tipo </label>
          <input type="text" class="form-control" id="tipo" name="tipo" value="${curso.tipo}">
        </div>

    <div class="mb-3">
      <label for="area" class="form-label"> Área </label>
      <input type="text" class="form-control" id="area" name="area" value="${curso.area}">
    </div>

    <div class="mb-3">
           <input type="text" name="id" class="form-control" id="exampleFormControlInput1" placeholder="name@example.com" value="${curso.id}">
    </div>

    <div class="mb-3">
          <button type="submit" class="btn btn-primary mb-3"> Confirmar</button>
          <button type="button" class="btn btn-danger mb-3" onclick="cancelar()"> Cancelar </button>
    </div>
<form>
</div>
</div>
<script>
  function cancelar() {
    window.location.href = "/admin?acao=ExibirTelaCurso";
  }
</script>
<%@ include file="footer.jsp" %>