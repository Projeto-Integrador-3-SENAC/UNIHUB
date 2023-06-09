package br.com.projetopi.redesocial.dao;

import br.com.projetopi.redesocial.interfaces.Dao;
import br.com.projetopi.redesocial.model.Postagem;
import br.com.projetopi.redesocial.model.dto.QtdPostagemByDate;
import br.com.projetopi.redesocial.model.dto.QtdPostagemInstituicao;
import br.com.projetopi.redesocial.repository.ConnectionFactory;
import com.fasterxml.jackson.databind.ext.SqlBlobSerializer;

import java.sql.*;
import java.util.ArrayList;

public class PostagemDao implements Dao<Postagem> {

    private Connection conexao;

    public PostagemDao(){
        this.conexao = ConnectionFactory.getConnectionH2();
    }

    @Override
    public void add(Postagem postagem) {
        String sqlQuery = "insert into postagem " +
                "(conteudo, foto_id, conta_id, data_postagem, foto)" +
                "values (?,?,?,?,?)";

    try (PreparedStatement ps = conexao.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)){

        ps.setString(1, postagem.getConteudo());
        ps.setInt(2, postagem.getFoto_id());
        ps.setInt(3, postagem.getConta_id());
        ps.setDate(4, postagem.getData_postagem());
        ps.setString(5, postagem.getFoto());
        ps.execute();

        try(ResultSet generatedKeys = ps.getGeneratedKeys()){
            postagem.setId(generatedKeys.getInt(1));
        } catch (Exception e){
            e.printStackTrace();
        }

    }catch (SQLException e){
        System.out.println(e.getMessage());
        e.printStackTrace();
    }
    }

    @Override
    public boolean update(Postagem postagem) {
        return false;
    }

    @Override
    public boolean remove(int id) {
        String sqlQuery = "update postagem set ic_ativo = ? where id = ?";
        try(PreparedStatement ps =  conexao.prepareStatement(sqlQuery)){
            ps.setInt(1, 0);
            ps.setInt(2, id);
            ps.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public ArrayList<Postagem> findAllPageable(int qtd_elementos, int num_inicio) {
        return null;
    }

    public ArrayList<Postagem> findAllPostagem(){
        String SQL = "select * \n" +
                "from postagem p \n" +
                "left join \n" +
                "(SELECT  postagem_id, count(*) qtd FROM curtida_postagem group by postagem_id ) c on p.id = c.postagem_id where ic_ativo = 1 order by id desc";

        ArrayList<Postagem> postagens = new ArrayList<>();

        try (PreparedStatement preparedStatement = conexao.prepareStatement(SQL)){
            preparedStatement.execute();

            ResultSet set = preparedStatement.getResultSet();

            while(set.next()){
                Postagem postagem = new Postagem();

                postagem.setId(set.getInt("id"));
                postagem.setConteudo(set.getString("conteudo"));
                postagem.setConta_id(set.getInt("conta_id"));
                postagem.setFoto_id(set.getInt("foto_id"));
                postagem.setData_postagem(set.getDate("data_postagem"));
                postagem.setFoto(set.getString("foto"));
                postagem.setQtdLikes(set.getInt("qtd"));
                postagens.add(postagem);
            }
            return postagens;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList findAllPageableByInstituicao(int instituicao_id, int qtd_elementos, int num_inicio) {

        ArrayList<Postagem> postagens = new ArrayList<>();

        String sqlQuery = """ 
                select * from postagem\s
                inner join conta on conta.id = postagem.conta_id
                inner join instituicao  i on i.id = conta.instituicao_id\s
                where instituicao_id = ?
                order by  data_postagem desc\s 
                LIMIT ? OFFSET ?;"""
                ;

        try (PreparedStatement ps = conexao.prepareStatement(sqlQuery)){

            ps.setInt(1, instituicao_id);
            ps.setInt(2, qtd_elementos);
            ps.setInt(3, num_inicio);

            ResultSet result = ps.executeQuery();

            while(result.next()){

                Postagem postagem = new Postagem();


                postagem.setConteudo(result.getString("conteudo"));
                postagem.setConta_id(result.getInt("conta_id"));
                postagem.setFoto_id(result.getInt("foto_id"));
                postagem.setData_postagem(result.getDate("data_postagem"));
                postagem.setFoto(result.getString("foto"));
                postagens.add(postagem);
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return postagens;
    }

    public ArrayList findByAccountPageable(int conta_id, int qtd_elementos, int num_inicio) {

        ArrayList<Postagem> postagens = new ArrayList<>();

        String sqlQuery = "select * from postagem where conta_id = ? and ic_ativo = 1 order by id desc LIMIT ? OFFSET ?";

        try (PreparedStatement ps = conexao.prepareStatement(sqlQuery)){

            ps.setInt(1, conta_id);
            ps.setInt(2, qtd_elementos);
            ps.setInt(3, num_inicio);

            ResultSet result = ps.executeQuery();

            while(result.next()){

                Postagem postagem = new Postagem();
                postagem.setId(result.getInt("id"));
                postagem.setConteudo(result.getString("conteudo"));
                postagem.setConta_id(result.getInt("conta_id"));
                postagem.setFoto_id(result.getInt("foto_id"));
                postagem.setData_postagem(result.getDate("data_postagem"));
                postagem.setFoto(result.getString("foto"));
                postagens.add(postagem);
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return postagens;
    }

    public Postagem findById(int id){
        String SQL = "select * \n" +
                "from postagem p \n" +
                "left join \n" +
                "(SELECT  postagem_id, count(*) qtd FROM curtida_postagem group by postagem_id ) c on p.id = c.postagem_id\n" +
                "where id = ? and ic_ativo = 1";
        Postagem postagem = new Postagem();

        try(PreparedStatement statement = conexao.prepareStatement(SQL)){

            statement.setInt(1, id);
            statement.execute();

            try(ResultSet rs = statement.getResultSet()){

                while(rs.next()){
                    postagem.setId(rs.getInt("id"));
                    postagem.setConteudo(rs.getString("conteudo"));
                    postagem.setConta_id(rs.getInt("conta_id"));
                    postagem.setFoto_id(rs.getInt("foto_id"));
                    postagem.setData_postagem(rs.getDate("data_postagem"));
                    postagem.setFoto(rs.getString("foto"));
                    postagem.setQtdLikes(rs.getInt("qtd"));
                }
            }

            return postagem;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int getCount(){
        String sqlQuery = "select count(*) from postagem where ic_ativo = 1";
        try (PreparedStatement statement = conexao.prepareStatement(sqlQuery)){
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt(1);
            }else{
                return 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<QtdPostagemInstituicao> getQtdPostagemByInstituicao(){
        String sqlQuery = "select i.nome, count(*) qtd from conta c inner join instituicao i on c.instituicao_id = i.id left join postagem p on p.conta_id = c.id";
        ArrayList<QtdPostagemInstituicao> qtdPostagemInstituicaos = new ArrayList<>();
        try (PreparedStatement ps = conexao.prepareStatement(sqlQuery)){
            ResultSet result = ps.executeQuery();
            while(result.next()){

                QtdPostagemInstituicao qtdPostagemInstituicao = new QtdPostagemInstituicao();
                qtdPostagemInstituicao.setQtd(result.getInt("qtd"));
                qtdPostagemInstituicao.setNome(result.getString("nome"));
                qtdPostagemInstituicaos.add(qtdPostagemInstituicao);
            }

            return qtdPostagemInstituicaos;

        }catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<QtdPostagemByDate> getQtPostsByDate(){
        String sqleQuery = "select data_postagem, count(*) qtd from postagem order by data_postagem asc";
        ArrayList<QtdPostagemByDate> qtdPostagemByDate = new ArrayList<>();
        try(PreparedStatement ps = conexao.prepareStatement(sqleQuery)){
            ResultSet result = ps.executeQuery();
            while(result.next()){
                QtdPostagemByDate qtdPostagemByDate1 = new QtdPostagemByDate();
                qtdPostagemByDate1.setData_postagem(result.getDate("data_postagem"));
                qtdPostagemByDate1.setQtd(result.getInt("qtd"));
                qtdPostagemByDate.add(qtdPostagemByDate1);
            }
            return qtdPostagemByDate;

        }catch (SQLException e){
            System.out.println(e);
            return null;
        }
    }


}
