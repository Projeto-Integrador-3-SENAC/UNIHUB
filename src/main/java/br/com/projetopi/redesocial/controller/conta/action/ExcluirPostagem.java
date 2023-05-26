package br.com.projetopi.redesocial.controller.conta.action;

import br.com.projetopi.redesocial.interfaces.Action;
import br.com.projetopi.redesocial.service.ContaService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExcluirPostagem implements Action {
    private ContaService contaService;

    public ExcluirPostagem(){
        this.contaService = new ContaService();
    }

    @Override
    public String executa(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.valueOf(request.getParameter("id"));
        System.out.println("ID DA POSTAGEM: " + id);
//        this.contaService.removePost(id, conta_id); TODO: ARRUMAR METODO REMOVEPOST PARA EXECUTAR A SEGUINTE QUERY DELETE FROM POSTAGEM WHERE CONTA_ID = ? LIMIT=1 OFFSET=?
        return "redirect:conta?acao=ExibirPerfil";
    }
}
