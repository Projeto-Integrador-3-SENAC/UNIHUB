package br.com.projetopi.redesocial.controller.conta.action;

import br.com.projetopi.redesocial.interfaces.Action;
import br.com.projetopi.redesocial.model.Conta;
import br.com.projetopi.redesocial.model.Postagem;
import br.com.projetopi.redesocial.model.dto.ContaPerfilDto;
import br.com.projetopi.redesocial.service.ContaService;
import br.com.projetopi.redesocial.service.PostagemService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ExibirPerfil implements Action {

    private ContaService contaService;
    private PostagemService postagemService;
    public ExibirPerfil(){

        this.contaService = new ContaService();
        this.postagemService = new PostagemService();
    }
    @Override
    public String executa(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var session = request.getSession();

        Conta conta = (Conta) session.getAttribute("contaLogado");

        String conta_id = request.getParameter("id");
        System.out.println("Valor do parâmetro id: " + conta_id);

        Conta conta_parameter = null;
        if (conta_id != null) {
            int id = Integer.parseInt(conta_id);
            System.out.println("Valor do ID convertido: " + id);
            
            conta_parameter = contaService.getContaById(id);
            System.out.println("Conta encontrada: " + conta_parameter);
            
        }

        int qtdElemtos =  10; // Integer.valueOf(request.getParameter("qtd_elemtentos"));
        int numInicio = 0; // Integer.valueOf(request.getParameter("num_inicio"));

        ArrayList<Postagem>  postagens;
        ContaPerfilDto contaView;
        if(conta_parameter != null){
            contaView = this.contaService.getContaDadosPerfilView(conta_parameter.getId());
            postagens = postagemService.findByAccountId(conta_parameter.getId(), qtdElemtos, numInicio);
            System.out.println(contaView);
        }else{
            contaView = this.contaService.getContaDadosPerfilView(conta.getId());
            postagens = postagemService.findByAccountId(conta.getId(), qtdElemtos, numInicio);
            System.out.println(contaView);
        }

        request.setAttribute("postagens", postagens);
        request.setAttribute("conta", contaView);

        return "forward:perfil.jsp";
    }
}
